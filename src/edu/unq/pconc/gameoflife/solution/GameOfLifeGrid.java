package edu.unq.pconc.gameoflife.solution;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import edu.unq.pconc.gameoflife.CellGrid;
import edu.unq.pconc.gameoflife.shapes.Shape;


public class GameOfLifeGrid implements CellGrid {
  private int celdasFilas;
  private int celdasColumnas;
  private int generations;
  private ThreadPool threadPool;
  private BufferDeTareas buffer;
  private BufferDeFin bufferMutex;
  private List<Tarea> listaDeTareas;
  private Celda[][] tableroActualizado;
  private Celda[][] tablero;
  
  public GameOfLifeGrid() {
    this.celdasColumnas = 0;
    this.celdasFilas = 0;
    this.buffer = new BufferDeTareas();
    this.bufferMutex = new BufferDeFin();
    this.threadPool = new ThreadPool(this.buffer,this.bufferMutex);
    this.listaDeTareas = new ArrayList<Tarea>();
    tableroActualizado = new Celda[celdasColumnas][celdasFilas];
    tablero = new Celda[celdasColumnas][celdasFilas];
    this.clear();
  }

  public void clear() {
    generations = 0;
    for ( int c=0; c< celdasColumnas; c++) {
    	for ( int r=0; r< celdasFilas; r++ ) {
        	tablero[c][r] = new Celda( c, r );
        	tableroActualizado[c][r] = new Celda( c, r );;
        }
      }
  }
  
  public synchronized void next() {
	  System.out.println("threads"+Worker.activeCount());
	  this.divisionGrillaThreads();
	  System.out.println("primera Tarea "+1);
	  for(Tarea tarea : this.listaDeTareas) {
		  this.buffer.dejarTarea(tarea);
	  }
	  System.out.println("segunda Tarea "+2);
	  for(int i = 0;i < this.threadPool.getCantidadDeWorkers();i++) {
		  this.bufferMutex.tomarTarea();
	  }
	  System.out.println("Tercera Tarea"+3);
	  this.generations++;
	  System.out.println("Actualize tablero");
	  this.tablero = this.tableroActualizado;
  }
  
  public void actualizarCelda(int columna, int fila,boolean estado) {
	  System.out.println("Actualize celda");
	  tableroActualizado[columna][fila].setearEstado(estado);
  }

  public boolean getCell( int columna, int fila ) {
	  System.out.print("pide celda");
	  System.out.print(columna+" ");
	  System.out.println(fila);
      return (tablero[columna][fila]).estado();  
  }

  public synchronized void setCell( int columna, int fila, boolean estado ) {
	  System.out.println("esto no se imprime");
      tablero[columna][fila].setearEstado(estado);
  }
  
 
  public int getGenerations() {
    return generations;
  }
 
  public Dimension getDimension() {
    return new Dimension( celdasColumnas, celdasFilas );
  }

  public synchronized void resize(int celdasColumnasNew, int celdasFilasNew) {
    if ( celdasColumnas==celdasColumnasNew && celdasFilas==celdasFilasNew )
      return; 
    Celda[][] tableroNueva = new Celda[celdasColumnasNew][celdasFilasNew];
    for ( int c=0; c<celdasColumnasNew; c++) {
      for ( int r=0; r<celdasFilasNew; r++ ) {
        if ( c < celdasColumnas && r < celdasFilas ) {
        	tableroNueva[c][r] = this.tablero[c][r];
  		}else {
  			tableroNueva[c][r] = new Celda( c, r );
        }   
      }
    }
    this.tablero = tableroNueva;
    this.tableroActualizado = tableroNueva;
    this.celdasColumnas = celdasColumnasNew;
    this.celdasFilas = celdasFilasNew;
    this.divisionGrillaThreads();
    System.out.println("cantidad de Celdas"+cantidadDeCeldas());
    System.out.println("cantidad de columnas"+celdasColumnas);
    System.out.println("cantidad de filas"+celdasFilas);
  }
  
  public void divisionGrillaThreads() {
	  List<Tarea> tareasNuevas= new ArrayList<Tarea>();
	  int columnaInicio = 0;
	  int filaInicio = 0;
	  int celdasParaThreads = this.cantidadDeCeldas();
	  for(int i = 0;i < this.threadPool.getCantidadDeWorkers();i++) {
		  celdasParaThreads = (this.cantidadDeCeldas()/(this.threadPool.getCantidadDeWorkers()));
		  if(i < (cantidadDeCeldas()%this.threadPool.getCantidadDeWorkers())) {
			  celdasParaThreads++;
		  }
		  
		  Tarea tareaNew = new Tarea(columnaInicio,filaInicio,celdasParaThreads,this);
		  tareasNuevas.add(tareaNew);
		  while(celdasParaThreads > celdasColumnas) {
			  filaInicio++;
			  celdasParaThreads -= celdasColumnas;
		  }
		  columnaInicio = celdasParaThreads;
	  }
	  this.listaDeTareas = tareasNuevas;
  }
  
  
  public synchronized void setThreads(int threads) {
	  if(threads >= this.threadPool.getCantidadDeWorkers()) {
		  this.threadPool.startWorkers(restarThreads(threads));
	  }else {
		  this.threadPool.matarWorker(restarThreads(threads));
	  }
	  this.divisionGrillaThreads();
  }
  
  private int restarThreads(int threads) {
	  return Math.abs(threads - this.threadPool.getCantidadDeWorkers());
  }

  private int cantidadDeCeldas() {
	  return this.celdasFilas * this.celdasColumnas;
  }
  public Integer celdasEnColumna() {
	  return celdasColumnas;
  }

  public int celdasEnFila() {
	  return celdasFilas;
  }
  
}

