package edu.unq.pconc.gameoflife.solution;

import java.util.LinkedList;
import java.util.Queue;

public class BufferDeTareas {

	private Queue<Tarea> tareas = new LinkedList<>();
	private int maximoDeTareas = 1000;

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
		while(this.tareas.size() == this.maximoDeTareas) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
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