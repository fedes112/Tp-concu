package edu.unq.pconc.gameoflife.solution;

public class Worker extends Thread {

	private final BufferDeTareas buffer;
	private BufferDeFin bufferfin;
	private Tarea tarea;
	private boolean activo = true;

	public Worker(BufferDeTareas buff,BufferDeFin buffDeFin) {
		this.buffer = buff;
		this.bufferfin = buffDeFin;
	}

	public void run() {
		while(activo) {
			this.tarea = buffer.tomarTarea();
			if(this.tarea != null) {
				this.realizarTarea();
				this.bufferfin.dejarTarea(1);
			}else {
				this.activo = false;
			}
		}
	}

	private void realizarTarea() {
		for(int i = 0;i < this.tarea.getCantidadDeCeldas();i++) {
			int vecinos = this.ObtenerVecinos(tarea.columnaActual(),tarea.filaActual());
			if ( vecinos != 3 && vecinos != 2 ) {
				tarea.setCell( tarea.columnaActual(),tarea.filaActual(), false);
		    }
			if ( vecinos == 3 ) {
				tarea.setCell( tarea.columnaActual(),tarea.filaActual(), true );
			}
			this.tarea.siguienteCelda();
		}
	}

	private int ObtenerVecinos(int columnaActual, int filaActual) {
		int vecinos = 0;
		this.tarea.agregarVecino(columnaActual,filaActual,columnaActual -1, filaActual +1);
		this.tarea.agregarVecino(columnaActual -1, filaActual);
		this.tarea.agregarVecino(columnaActual -1, filaActual -1);
		this.tarea.agregarVecino(columnaActual +1, filaActual +1);
		this.tarea.agregarVecino(columnaActual +1, filaActual);
		this.tarea.agregarVecino(columnaActual +1, filaActual -1);
		this.tarea.agregarVecino(columnaActual  , filaActual +1);
		this.tarea.agregarVecino(columnaActual  , filaActual -1);
		return vecinos;
		if(columna >= 0 && fila >= 0 && columna <= this.gameOfLifeGrid.celdasEnColumna() && fila <= this.gameOfLifeGrid.celdasEnFila() )
	}
	
	
}
