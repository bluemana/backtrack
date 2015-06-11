package backtrack.example.puzzle.core;

import java.util.Objects;

public class Move {

	public enum Direction {

		UP("\u2191"),
		RIGHT("\u2192"),
		DOWN("\u2193"),
		LEFT("\u2190");

		private final String toString;

		private Direction(String toString) {
			this.toString = toString;
		}

		@Override
		public String toString() {
			return toString;
		}
	}
	
	private final int pieceId;
	private final Direction direction;
	private final int cells;
	private final String toString;

	public Move(int pieceId, Direction direction, int cells) {
		this.pieceId = pieceId;
		this.direction = direction;
		this.cells = cells;
		toString = "M" + pieceId + ":" + direction + "" + cells;
	}
	
	public int getPieceId() {
		return pieceId;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public int getCells() {
		return cells;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(pieceId, direction, cells);
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj != null && obj instanceof Move) {
			Move m = (Move) obj;
			result = pieceId == m.pieceId && direction == m.direction && cells == m.cells;
		}
		return result;
	}
	
	@Override
	public String toString() {
		return toString;
	}
}
