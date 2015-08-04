package backtrack.example.puzzle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import backtrack.Backtracker;
import backtrack.example.puzzle.core.Board;
import backtrack.example.puzzle.core.Move;

public class PuzzleSolver {

	private Board start;
	private Board target;
	private int targetPieceId;
	private Backtracker<BoardTuple, Move> backtracker;
	
	public Board getStart() {
		return start;
	}
	
	public void setStart(Board start) {
		this.start = start;
	}

	public Board getTarget() {
		return target;
	}
	
	public void setTarget(Board target) {
		this.target = target;
	}
	
	public int getTargetPieceId() {
		return targetPieceId;
	}
	
	public void setTargetPieceId(int targetPieceId) {
		this.targetPieceId = targetPieceId;
	}
	
	public void setBacktracker(Backtracker<BoardTuple, Move> backtracker) {
		this.backtracker = backtracker;
	}
	
	public Backtracker<BoardTuple, Move> getBacktracker() {
		return backtracker;
	}
	
	public List<Move> solve() throws IOException {
		BoardTuple startTuple = new BoardTuple(start, null, null, this);
		backtracker.setStart(startTuple);
		return backtracker.solve();
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
