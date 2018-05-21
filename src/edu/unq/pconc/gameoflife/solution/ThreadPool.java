package edu.unq.pconc.gameoflife.solution;

public class ThreadPool {

	private int cantidadDeWorkers = 0;
	private BufferDeTareas buff;
	private BufferDeFin buffDeFin;
	
	public ThreadPool(BufferDeTareas buffer,BufferDeFin bufferDeFin) {
		this.buff = buffer;
		this.buffDeFin = bufferDeFin;
	}
	public int getCantidadDeWorkers() {
		return this.cantidadDeWorkers;
	}
	public void startWorkers(int worker) {
		Worker var;
		for (int i = 0; i < worker; i++) {			
			var = new Worker(this.buff,this.buffDeFin);
			var.start();
		}
		this.cantidadDeWorkers += worker;
		System.out.println("Workers"+cantidadDeWorkers);
	}
	public void matarWorker(int worker){
		for(int i = 0; i < worker; i++){
			this.buff.poisonPill();
		}
		this.cantidadDeWorkers -= worker;
		System.out.println("Workers"+cantidadDeWorkers);
	}
}
