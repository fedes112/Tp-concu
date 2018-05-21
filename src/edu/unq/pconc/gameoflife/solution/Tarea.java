package edu.unq.pconc.gameoflife.solution;

public class Tarea {
	private Integer columnaDeCeldaAModificar;
	private Integer filaDeCeldaAModificar;
	private Integer cantidadDeCeldasActualizar;
	private Celda[][] tableroBase;
	private GameOfLifeGrid gameOfLifeGrid;
	
	public Tarea(int _columnaInicio, int _filaInicio, int _cantidadDeCeldasActualizar, Celda[][] _tablero,GameOfLifeGrid _gameOfLifeGrid) {
		this.columnaDeCeldaAModificar = _columnaInicio;
		this.filaDeCeldaAModificar = _filaInicio;
		this.cantidadDeCeldasActualizar = _cantidadDeCeldasActualizar;
		this.gameOfLifeGrid = _gameOfLifeGrid;
		this.tableroBase = _tablero;
	}
	
	public int columnaActual() {
		return this.columnaDeCeldaAModificar;
	}
	
	public int filaActual() {
		return this.filaDeCeldaAModificar;
	}
	
	public int getCantidadDeCeldas() {
		return this.cantidadDeCeldasActualizar;
	}

	public void siguienteCelda() {
		if(columnaDeCeldaAModificar >= gameOfLifeGrid.celdasEnColumna()) {
			this.columnaDeCeldaAModificar = 0;
			this.filaDeCeldaAModificar++;
		}else {
			this.columnaDeCeldaAModificar++;
		}
	}
	
	public boolean getCell( int columna, int fila ) {
	      return (tableroBase[columna][fila]).estado();  
	 }
	public void setCell( int columna, int fila ,boolean estado) {
		this.gameOfLifeGrid.actualizarCelda(columna, fila, estado);
	}
}
