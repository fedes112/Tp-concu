package edu.unq.pconc.gameoflife.solution;

public class Worker extends Thread {

	private final BufferDeTareas buffer;
	private BufferDeFin bufferfin;
	private Tarea tarea;
	private boolean activo = true;
	private int vecinos = 0;

	public Worker(BufferDeTareas buff,BufferDeFin buffDeFin) {
		this.buffer = buff;
		this.bufferfin = buffDeFin;
	}

	public void run() {
		while(activo) {
			this.tarea = buffer.tomarTarea();
			if(this.tarea != null) {
				System.out.println("Worker comienza un Trabajo");
				this.realizarTarea();
				System.out.println("Worker termino un Trabajo");
				this.bufferfin.dejarTarea();
			}else {
				this.activo = false;
			}
		}
	}

	private void realizarTarea() {
		for(int i = 0;i < this.tarea.getCantidadDeCeldas();i++) {
			System.out.println("celda verificar "+i);
			vecinos = 0;
			this.mirarVecinos();
			if ( puedeMorir() ) {
				tarea.setCell( tarea.columnaActual(),tarea.filaActual(), false);
		    }
			if ( puedeRevivir() ) {
				tarea.setCell( tarea.columnaActual(),tarea.filaActual(), true );
			}
			this.tarea.siguienteCelda();
		}
	}
	
	private boolean puedeRevivir() {
		return vecinos == 3;
	}
	
	private boolean puedeMorir() {
		return vecinos != 3 && vecinos != 2;
	}

	private void mirarVecinos() {
		this.mirarVecino(tarea.columnaActual() , tarea.filaActual() +1);
		this.mirarVecino(tarea.columnaActual() +1, tarea.filaActual() );
		this.mirarVecino(tarea.columnaActual() +1, tarea.filaActual() +1);
		this.mirarVecino(tarea.columnaActual() +1, tarea.filaActual() -1);
		this.mirarVecino(tarea.columnaActual() , tarea.filaActual() -1);
		this.mirarVecino(tarea.columnaActual() -1, tarea.filaActual() );
		this.mirarVecino(tarea.columnaActual() -1, tarea.filaActual() +1);
		this.mirarVecino(tarea.columnaActual() -1, tarea.filaActual() -1);
	}
	
	private synchronized void mirarVecino(int columnaActual, int filaActual) {
		 if(this.tarea.getCell(columnaActual, filaActual)) {
			 vecinos++;
		 }
	}
	
}
