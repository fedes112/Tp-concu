package edu.unq.pconc.gameoflife.solution;

public class Worker extends Thread {

	private final Buffer buffer;
	private Tarea tarea;
	private boolean activo = true;

	public Worker(Buffer buff) {
		this.buffer = buff;
	}

	public void run() {
		while(activo ) {
			this.tarea = buffer.tomarTarea();
			if(this.tarea == null) {
				this.realizarTarea(tarea);
			}else {
				this.activo = false;
			}
		}
	}

	private void realizarTarea(Tarea tarea) {
		
	}
	
	
}
