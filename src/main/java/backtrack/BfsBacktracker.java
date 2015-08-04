package backtrack;

import java.util.ArrayDeque;
import java.util.Queue;

public class BfsBacktracker<T extends Tuple<E>, E> extends Backtracker<T, E> {

	private final Queue<Tuple<E>> traversalQueue;
	
	public BfsBacktracker() {
		traversalQueue = new ArrayDeque<Tuple<E>>();
	}
	
	@Override
	protected Queue<Tuple<E>> getTraversalQueue() {
		return traversalQueue;
	}
}
