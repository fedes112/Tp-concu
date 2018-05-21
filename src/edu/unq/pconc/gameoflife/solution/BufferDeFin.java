package edu.unq.pconc.gameoflife.solution;

import java.util.LinkedList;
import java.util.Queue;

public class BufferDeFin {
	private int unNumero = 0;
		
	public synchronized void tomarTarea() {
		while (unNumero == 0){
			try {
				System.out.println("estoy esperando");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		unNumero--;
		System.out.println("deje de esperar");
	}
	
	public synchronized void dejarTarea() {
		System.out.println("deje una tarea");
		unNumero++;
		notifyAll();
	}
	
}