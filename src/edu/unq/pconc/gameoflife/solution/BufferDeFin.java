package edu.unq.pconc.gameoflife.solution;

import java.util.LinkedList;
import java.util.Queue;

public class BufferDeFin {
	private int unNumero = 0;
		
	public synchronized void tomarTarea() {
		while (unNumero == 0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		unNumero--;
		notifyAll();
	}
	
	public synchronized void dejarTarea() {
		unNumero++;
		notifyAll();
	}
	
}