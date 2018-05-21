package edu.unq.pconc.gameoflife.solution;

import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Hashtable;

import edu.unq.pconc.gameoflife.CellGrid;
import edu.unq.pconc.gameoflife.shapes.Shape;

/**
 * Contains the cellgrid, the current shape and the Game Of Life algorithm that changes it.
 *
 * @author Edwin Martin
 */
public class GameOfLifeGrid implements CellGrid {
  private int cellRows;
  private int cellCols;
  private int generations;
  /**
   * Contains the current, living shape.
   * It's implemented as a hashtable. Tests showed this is 70% faster than Vector.
   */
  private Hashtable currentShape;
  private Hashtable nextShape;
  private Cell[][] grid;
  
  private ThreadPool threadPool;
  private Buffer buffer;

  /**
   * Contructs a GameOfLifeGrid.
   * 
   * @param cellCols number of columns
   * @param cellRows number of rows
   */
  public GameOfLifeGrid() {
    this.cellCols = 0;
    this.cellRows = 0;
    this.buffer = new Buffer();
    this.threadPool = new ThreadPool(this.buffer);
    currentShape = new Hashtable();
    nextShape = new Hashtable();

    grid = new Cell[cellCols][cellRows];
    for ( int c=0; c<cellCols; c++)
      for ( int r=0; r<cellRows; r++ )
        grid[c][r] = new Cell( c, r );
  }

  public synchronized void clear() {
    generations = 0;
    currentShape.clear();
    nextShape.clear();
  }
  
  public synchronized void next() {
	  
    
  }

  public synchronized boolean getCell( int col, int row ) {
    try {
      return currentShape.containsKey(grid[col][row]);
    } catch (ArrayIndexOutOfBoundsException e) {
    }
    return false;
  }

  public synchronized void setCell( int col, int row, boolean c ) {
    try {
      Cell cell = grid[col][row];
      if ( c ) {
        currentShape.put(cell, cell);
      } else {
        currentShape.remove(cell);
      }
    } catch (ArrayIndexOutOfBoundsException e) {
    }
  }
 
  public int getGenerations() {
    return generations;
  }
 
  public Dimension getDimension() {
    return new Dimension( cellCols, cellRows );
  }

  public synchronized void resize(int cellColsNew, int cellRowsNew) {
    if ( cellCols==cellColsNew && cellRows==cellRowsNew )
      return; // Not really a resize
    // Create a new grid, reusing existing Cell's
    Cell[][] gridNew = new Cell[cellColsNew][cellRowsNew];
    for ( int c=0; c<cellColsNew; c++)
      for ( int r=0; r<cellRowsNew; r++ )
        if ( c < cellCols && r < cellRows )
          gridNew[c][r] = grid[c][r];
        else
          gridNew[c][r] = new Cell( c, r );
    // Copy existing shape to center of new shape
    int colOffset = (cellColsNew-cellCols)/2;
    int rowOffset = (cellRowsNew-cellRows)/2;
    Cell cell;
    Enumeration e;
    nextShape.clear();
    e = currentShape.keys();
    while ( e.hasMoreElements() ) {
      cell = (Cell) e.nextElement();
      int colNew = cell.col + colOffset;
      int rowNew = cell.row + rowOffset;
      try {
        nextShape.put( gridNew[colNew][rowNew], gridNew[colNew][rowNew] );
      } catch ( ArrayIndexOutOfBoundsException ex ) {
        // ignore
      }
    }
    // Copy new grid and hashtable to working grid/hashtable
    grid = gridNew;
    currentShape.clear();
    e = nextShape.keys();
    while ( e.hasMoreElements() ) {
      cell = (Cell) e.nextElement();
      currentShape.put( cell, cell );
    }
    cellCols = cellColsNew;
    cellRows = cellRowsNew;
  }
  
  public void setThreads(int threads) {
	  if(this.threadPool.getCantidadDeWorkers() <= threads) {
		  this.threadPool.startWorkers((threads - this.threadPool.getCantidadDeWorkers()));
	  }else {
		  this.threadPool.matarWorker((this.threadPool.getCantidadDeWorkers() - threads));
	  }
  }
  
}

