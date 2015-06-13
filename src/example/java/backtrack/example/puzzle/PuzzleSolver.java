package backtrack.example.puzzle;

import java.util.ArrayList;
import java.util.List;

import backtrack.Backtracker;
import backtrack.example.puzzle.core.Board;
import backtrack.example.puzzle.core.Move;

public class PuzzleSolver extends Backtracker<BoardTuple, Move> {

	private Board target;
	private int targetPieceId;
	
	public Board getStartBoard() {
		return getStart().getBoard();
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
