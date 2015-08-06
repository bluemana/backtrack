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

	public int getVisit();
	
	/**
	 * Sets the visit number of this tuple. The visit number is the traversal step
	 * at which this tuple is visited by the backtracker.
	 * 
	 * @param visit the visit number of this tuple
	 */
	public void setVisit(int visit);
	
	public boolean isSolution();
	
	public boolean isPartOfSolution();
	
	public void setPartOfSolution(boolean partOfSolution);
	
	/**
	 * <p>
	 * Returns the elements that can be used to construct the next tuple.
	 * </p>
	 * <p>
	 * The elements are traversed in the order given by the list.
	 * </p>
	 * 
	 * @return the elements that can be used to construct the next tuple
	 */
	public List<E> nextElements();
	
	/**
	 * Returns the last element that was used to construct this tuple.
	 * 
	 * @return the last element that was used to construct this tuple, or
	 * <code>null</code> if this is the empty tuple
	 */
	public E lastElement();
	
	public Tuple<E> next(E element);
	
	public Tuple<E> previous();
	
	/**
	 * Returns the number of elements in this tuple.
	 * 
	 * @return the number of elements in this tuple
	 */
	public int size();
	
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
