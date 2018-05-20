package edu.unq.pconc.gameoflife.solution;

public class ThreadPool {

	private int cantidadDeWorkers = 0;
	private Buffer buff;
	
	public ThreadPool(Buffer buffer) {
		this.buff = buffer;
	}
	public int getCantidadDeWorkers() {
		return this.cantidadDeWorkers;
	}
	public void startWorkers(int worker) {
		for (int i = 0; i < worker; i++) {			
			new Worker(this.buff).start();
		}
		this.cantidadDeWorkers += worker;
	}
	public void matarWorker(int worker){
		for(int i = 0; i < worker; i++){
			this.buff.poisonPill();
		}
		this.cantidadDeWorkers -= worker;
	}
}
