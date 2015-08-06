package backtrack;

import java.util.List;

/**
 * <p>
 * A tuple of elements as used by the {@link Backtracker}.
 * </p>
 * <p>
 * <b>Note:</b>
 * </p>
 * <p>
 * To reduce the size of the traversal graph, the {@link Backtracker} does not
 * visit nodes that have already been visited. This is accomplished by comparing
 * elements for equality. To take advantage of this, a tuple must implement
 * <code>equal(Object)</code> and <code>hashCode()</code> methods appropriately.
 * </p>
 *
 * @param <E> the type of elements of this tuple
 */
public interface Tuple<E> {
	
	/**
	 * Returns the number of elements in this tuple.
	 * 
	 * @return the number of elements in this tuple
	 */
	public int size();
	
	/**
	 * Returns the last element that was used to construct this tuple.
	 * 
	 * @return the last element that was used to construct this tuple, or
	 * <code>null</code> if this is the empty tuple
	 */
	public E lastElement();
	
	public Tuple<E> previous();
	
	public List<Tuple<E>> next();
	
	/**
	 * Returns the visit ID of this tuple.
	 * 
	 * @return the visit ID of this tuple, or <code>-1</code> if this tuple has
	 * not been visited
	 */
	public int getVisitId();
	
	public void setVisitId(int visitId);
	
	public boolean isSolution();
	
	public boolean isPartOfSolution();
	
	public void setPartOfSolution(boolean partOfSolution);
	
	public String getGraphNodeId();
	
	public String getGraphNodeLabel();
	
	public String getGraphNodeDescription();
	
	/**
	 * Returns the edge label of the last element used to construct this tuple.
	 * 
	 * @return the edge label of the last element used to construct this tuple
	 */
	public String getGraphEdgeLabel();
}
