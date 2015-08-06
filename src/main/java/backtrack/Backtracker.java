package backtrack;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
 * @param <E> the type of elements of the tuples
 */
public abstract class Backtracker<T extends Tuple<E>, E> {

	public static final int DEFAULT_MAX_VISITS = 100000;
	private static final Color NODE_COLOR = new Color(0xCCCCFF);
	private static final Color SOLUTION_NODE_COLOR = new Color(0xFFCC00);
	
	private T start;
	private int maxVisits;
	private int visitsCount;
	private List<E> result;
	private final Set<Tuple<E>> visitedSet;
	private GraphFormat graphFormat;
	
	public Backtracker() {
		maxVisits = DEFAULT_MAX_VISITS;
		visitsCount = -1;
		visitedSet = new HashSet<Tuple<E>>();
	}
	
	protected abstract Queue<Tuple<E>> getTraversalQueue();
	
	public void setStart(T start) {
		this.start = start;
	}
	
	public T getStart() {
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
	
	public void setGraphFormat(GraphFormat graphFormat) {
		this.graphFormat = graphFormat;
	}
	
	public List<E> solve() throws IOException {
		List<E> result = solve(start);
		if (graphFormat != null) {
			graphFormat.open("G");
			writeGraph(visitedSet);
			graphFormat.close();
		}
		return result;
	}
	
	private List<E> solve(T start) throws IOException {
		Queue<Tuple<E>> traversalQueue = getTraversalQueue();
		traversalQueue.clear();
		visitedSet.clear();
		visitsCount = 0;
		traversalQueue.add(start);
		while (!traversalQueue.isEmpty() && visitsCount < maxVisits) {
			Tuple<E> current = traversalQueue.poll();
			if (!visitedSet.contains(current)) {
				// Mark as visited
				visitsCount++;
				visitedSet.add(current);
				current.setVisit(visitsCount);
				// Continue
				if (!current.isSolution()) {
					for (E e : current.nextElements()) {
						traversalQueue.add(current.next(e));
					}
				} else {
					updateSolutionPath(current);
					result = path(current);
					break;
				}
			}
		}
		return result;
	}
	
	private void updateSolutionPath(Tuple<E> solution) {
		do {
			solution.setPartOfSolution(true);
		} while ((solution = solution.previous()) != null);
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
	
	private void writeGraph(Set<Tuple<E>> visitedSet) throws IOException {
		for (Tuple<E> tuple : visitedSet) {
			writeGraphNode(tuple);
		}
	}
	
	private void writeGraphNode(Tuple<E> node) throws IOException {
		if (graphFormat != null) {
			Color nodeColor = node.isPartOfSolution() ? SOLUTION_NODE_COLOR : NODE_COLOR;
			graphFormat.writeNode(node.getGraphNodeId(), node.getGraphNodeLabel(), node.getGraphNodeDescription(), nodeColor);
			if (node.previous() != null) {
				graphFormat.writeEdge(node.previous().getGraphNodeId(), node.getGraphNodeId(), node.getGraphEdgeLabel());
			}
		}
	}
	
	public List<E> getResult() {
		return result;
	}
}
