package backtrack.example.puzzle.core;

import java.util.Objects;


public class Position implements Comparable<Position> {

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
	public int compareTo(Position p) {
		int result = -1;
		if (p != null) {
			if (row < p.row) {
				result = -1;
			} else if (row > p.row) {
				result = 1;
			} else {
				if (col < p.col) {
					result = -1;
				} else if (col > p.col) {
					result = 1;
				} else {
					result = 0;
				}
			}
		}
		return result;
	}
	
	@Override
	public String toString() {
		return toString;
	}
}
