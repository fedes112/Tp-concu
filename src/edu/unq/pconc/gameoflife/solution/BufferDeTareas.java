package edu.unq.pconc.gameoflife.solution;

import java.util.LinkedList;
import java.util.Queue;

public class BufferDeTareas {

	private Queue<Tarea> tareas = new LinkedList<>();
	private int maximoDeTareas = 0;
	//si el maximo es 0 o menor ignoramos que las tareas tengan un maximo

	public synchronized Tarea tomarTarea() {
		while (this.tareas.isEmpty()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
		return this.tareas.poll();
	}
	
	public synchronized void dejarTarea(Tarea tarea) {
		if(this.maximoDeTareas <= 0) {
			while(this.maximoDeTareas >= this.tareas.size()) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		this.tareas.add(tarea);
		notifyAll();
	}
	
	public synchronized void poisonPill() {
		this.tareas.add(null);
		notifyAll();
	}

}