package backtrack.core;

import java.util.Objects;


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