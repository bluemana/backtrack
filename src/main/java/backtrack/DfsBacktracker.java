package backtrack;

import java.util.Queue;

public class DfsBacktracker<T extends Tuple<E>, E> extends Backtracker<T, E> {

	private final Queue<Tuple<E>> traversalQueue;
	
	public DfsBacktracker() {
		traversalQueue = new ArrayStack<Tuple<E>>();
	}
	
	@Override
	protected Queue<Tuple<E>> getTraversalQueue() {
		return traversalQueue;
	}
}
