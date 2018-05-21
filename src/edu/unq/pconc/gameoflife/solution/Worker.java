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
			
			this.tarea.siguienteCelda();
		}
	}

	private int ObtenerVecinos(int columnaActual, int filaActual) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
