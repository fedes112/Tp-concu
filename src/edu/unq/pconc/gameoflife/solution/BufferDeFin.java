package edu.unq.pconc.gameoflife.solution;

import java.util.LinkedList;
import java.util.Queue;

public class BufferDeFin {
	private Queue<Integer> cola = new LinkedList<>();
		
	public synchronized void tomarTarea() {
		while (this.cola.isEmpty()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
	}
	
	public synchronized void dejarTarea(Integer unNumero) {
		this.cola.add(unNumero);
		notifyAll();
	}
	
}