package backtrack.core;

import java.util.Objects;

/**
 * <p>
 * <b>Design note:</b>
 * </p>
 * <p>
 * As an experiment in keeping cohesion as high as possible, a piece is considered
 * independently of its position on a board in the sense of "a piece is a rectangular
 * tile", rather than "a piece is a rectangular tile placed on a board". A
 * {@link Board} is responsible for keeping pieces in position.
 * </p>
 * <p>
 * Consistent with the level of abstraction of the definition of a piece as a
 * rectangular tile, two pieces are considered equal if their dimensions are equal,
 * regardless of their ID. Hence, a piece ID is mostly a helper field for a
 * <code>Board</code>.
 * </p>
 */
public class Piece {
	
	private final int id;
	private final int height;
	private final int width;
	private final String toString;
	
	public Piece(int id, int height, int width) {
		this.id = id;
		this.height = height;
		this.width = width;
		toString = String.format("P%d-%dx%d", id, height, width);
	}
	
	public int getId() {
		return id;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(height, width);
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj != null && obj instanceof Piece) {
			Piece p = (Piece) obj;
			result = height == p.height && width == p.width;
		}
		return result;
	}
	
	@Override
	public String toString() {
		return toString;
	}
}