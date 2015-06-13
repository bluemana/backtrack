package backtrack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * <p>
 * A general implementation of a backtracking algorithm.
 * </p>
 * <p>
 * Given a problem, a backtracking algorithm operates under these assumptions
 * on its solutions:<br>
 * <ul>
 * <li>solutions can be modelled as tuples, not necessarily all of the same length</li>
 * <li>a solution can be constructed incrementally from its tuple's elements</li>
 * </ul>
 * </p>
 * The algorithm attempts to construct a solution element by element. At each step
 * <code>i</code>, a partially constructed tuple of length <code>i</code> is evaluated
 * and a collection of elements considered to construct the next tuple of length
 * <code>i + 1</code>. If the algorithm determines that the tuple cannot be extended
 * to a solution, either because of the properties of the tuple itself or because none
 * of the extensions of this tuple lead to a solution, then the algorithm backtracks
 * through the previous steps to consider other elements.
 * </p>
 * <p>
 * A backtracking algorithm can be thought of as a graph traversal algorithm: it
 * traverses the directed graph of candidate solutions (either partial or complete)
 * until a valid solution is found. Given a candidate solution <code>A</code>, a directed
 * edge originating at <code>A</code> represents an element that can be utilised to construct
 * another candidate solution by concatenating the elements of <code>A</code> and the
 * element at the edge.
 * </p>
 * 
 * @param E the type of elements of the tuples
 */
public class Backtracker<E> {

	public static final int DEFAULT_MAX_VISITS = 100000;
	
	private Tuple<E> start;
	private int maxVisits;
	private int visitsCount;
	private List<E> result;
	
	public Backtracker() {
		maxVisits = DEFAULT_MAX_VISITS;
		visitsCount = -1;
	}
	
	public void setStart(Tuple<E> start) {
		this.start = start;
	}
	
	public Tuple<E> getStart() {
		return start;
	}
	
	public void setMaxVisits(int maxVisits) {
		this.maxVisits = maxVisits;
	}

	public int getMaxVisits() {
		return maxVisits;
	}

	public int getVisitsCount() {
		return visitsCount;
	}
	
	public List<E> solve() {
		return solve(start);
	}
	
	private List<E> solve(Tuple<E> start) {
		Queue<Tuple<E>> traversalQueue = new LinkedList<Tuple<E>>();
		Set<Tuple<E>> visitedSet = new HashSet<Tuple<E>>();
		visitsCount = 0;
		traversalQueue.add(start);
		while (!traversalQueue.isEmpty()) {
			Tuple<E> current = traversalQueue.poll();
			if (visitsCount < maxVisits && !visitedSet.contains(current)) {
				// Mark as visited
				visitedSet.add(current);
				visitsCount++;
				// Continue
				if (!current.isSolution()) {
					for (E e : current.nextElements()) {
						traversalQueue.add(current.next(e));
					}
				} else {
					result = path(current);
					break;
				}
			}
		}
		return result;
	}
	
	private List<E> path(Tuple<E> last) {
		List<E> result = new ArrayList<E>();
		E element;
		while (last != null && (element = last.lastElement()) != null) {
			result.add(element);
			last = last.previous();
		}
		Collections.reverse(result);
		return result;
	}
	
	public List<E> getResult() {
		return result;
	}
}
