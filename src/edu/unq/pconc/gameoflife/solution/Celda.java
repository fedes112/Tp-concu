package edu.unq.pconc.gameoflife.solution;

public class Celda {
  public final short col;
  public final short row;
  public boolean viva = false;

  private final int HASHFACTOR = 5000; 
  
  public Celda( int col, int row ) {
    this.col = (short)col;
    this.row = (short)row;
  }
  
  public boolean equals(Object o) {
    if (!(o instanceof Celda) )
      return false;
    return col==((Celda)o).col && row==((Celda)o).row;
  }

  public int hashCode() {
    return HASHFACTOR*row+col;
  }

  public boolean estado() {
	  return this.viva;
  }

  public void setearEstado(boolean estado) {
	this.viva = estado;
  }
}

