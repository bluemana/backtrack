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

	public boolean isSolution();
	
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
	 * <code>null</code> if this is the empty tuple.
	 */
	public E lastElement();
	
	public Tuple<E> next(E element);
	
	public Tuple<E> previous();
}
