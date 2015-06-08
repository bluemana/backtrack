package backtrack.core;

import java.util.Objects;


public class Position {

	private final int row;
	private final int col;
	private final String toString;
	
	public Position(int row, int col) {
		this.row = row;
		this.col = col;
		toString = String.format("(%d,%d)", row, col);
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(row, col);
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj != null && obj instanceof Position) {
			Position p = (Position) obj;
			result = row == p.row && col == p.col;
		}
		return result;
	}
	
	@Override
	public String toString() {
		return toString;
	}
}
