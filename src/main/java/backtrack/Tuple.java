package backtrack;

import java.util.List;

public interface Tuple<E> {

	public boolean isSolution();
	
	/**
	 * Returns the elements that can be used to construct the next tuple.
	 * 
	 * @return the elements that can be used to construct the next tuple
	 */
	public List<E> nextElements();
	
	/**
	 * Returns the element that was used to construct this tuple, or <code>null</code>
	 * if this is the empty tuple.
	 * 
	 * @return the element that was used to construct this tuple
	 */
	public E lastElement();
	
	public Tuple<E> next(E element);
	
	public Tuple<E> previous();
}
