package backtrack;

import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;

public class ArrayStack<E> extends AbstractQueue<E> {

	private final ArrayDeque<E> queue;
	
	public ArrayStack() {
		queue = new ArrayDeque<E>();
	}
	
	public ArrayStack(Collection<? extends E> collection) {
		this();
		for (E e : collection) {
			offer(e);
		}
	}

	@Override
	public boolean offer(E e) {
		return queue.offerFirst(e);
	}

	@Override
	public E poll() {
		return queue.pollFirst();
	}

	@Override
	public E peek() {
		return queue.peekFirst();
	}

	@Override
	public Iterator<E> iterator() {
		return queue.iterator();
	}

	@Override
	public int size() {
		return queue.size();
	}
}
