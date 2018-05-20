package edu.unq.pconc.gameoflife.solution;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {

	private static final Queue<Tarea> buf = new LinkedList<>();

	public void poisonPill() {}
}
/*
public class Buffer {
	private Object[] data = new Object[N + 1];
	private int begin = 0, end = 0;

	public synchronized void getCell(Object o) {
		while (isFull())
			wait();
		data[begin] = o;
		begin = next(begin);
		notifyAll();
	}

	public synchronized Object pop() {
		while (isEmpty())
			wait();
		Object result = data[end];
		end = next(end);
		notifyAll();
		return result;
	}

	private boolean isEmpty() {
		return begin == end;
	}

	private boolean isFull() {
		return next(begin) == end;
	}

	private int next(int i) {
		return (i + 1) % (N + 1);
	}
}
*/