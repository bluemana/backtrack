package backtrack.example.puzzle;

import java.util.ArrayList;
import java.util.List;

import backtrack.Backtracker;
import backtrack.Tuple;
import backtrack.example.puzzle.core.Board;
import backtrack.example.puzzle.core.Move;

public class PuzzleSolver extends Backtracker<Move> {

	private class BoardTuple extends Board implements Tuple<Move> {
		
		private final BoardTuple previousTuple;
		private final Move lastMove;
		
		public BoardTuple(Board board, BoardTuple previousTuple, Move lastMove) {
			super(board);
			this.previousTuple = previousTuple;
			this.lastMove = lastMove;
		}

		@Override
		public boolean isSolution() {
			return equals(target, targetPieceId);
		}

		@Override
		public List<Move> nextElements() {
			return moves();
		}
		
		@Override
		public Move lastElement() {
			return lastMove;
		}

		@Override
		public Tuple<Move> next(Move move) {
			Board next = new Board(this);
			next.apply(move);
			return new BoardTuple(next, this, move);
		}

		@Override
		public Tuple<Move> previous() {
			return previousTuple;
		}
	}

	private Board start;
	private Board target;
	private int targetPieceId;
	
	public void setStartBoard(Board start) {
		this.start = start;
		setStart(new BoardTuple(start, null, null));
	}
	
	public Board getStartBoard() {
		return start;
	}

	public Board getTargetBoard() {
		return target;
	}
	
	public void setTargetBoard(Board target) {
		this.target = target;
	}
	
	public int getTargetPieceId() {
		return targetPieceId;
	}
	
	public void setTargetPieceId(int targetPieceId) {
		this.targetPieceId = targetPieceId;
	}
	
	public static List<Board> boards(Board start, List<Move> moves) {
		List<Board> result = new ArrayList<Board>();
		Board current = new Board(start);
		result.add(current);
		for (Move move : moves) {
			current = new Board(current);
			current.apply(move);
			result.add(current);
		}
		return result;
	}
}
