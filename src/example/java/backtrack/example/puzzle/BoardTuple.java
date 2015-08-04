package backtrack.example.puzzle;

import java.util.List;

import backtrack.Tuple;
import backtrack.example.puzzle.core.Board;
import backtrack.example.puzzle.core.Move;

public class BoardTuple implements Tuple<Move> {
	
	private final Board board;
	private final BoardTuple previousTuple;
	private final Move lastMove;
	private final PuzzleSolver solver;
	private int visit;
	private final int size;
	
	public BoardTuple(Board board, BoardTuple previousTuple, Move lastMove,
			PuzzleSolver solver) {
		this.board = board;
		this.previousTuple = previousTuple;
		this.lastMove = lastMove;
		this.solver = solver;
		size = lastMove == null ? 0 : previousTuple.size() + 1;
	}
	
	public Board getBoard() {
		return board;
	}
	
	@Override
	public int getVisit() {
		return visit;
	}

	@Override
	public void setVisit(int visit) {
		this.visit = visit;
	}

	@Override
	public boolean isSolution() {
		return board.equals(solver.getTarget(), solver.getTargetPieceId());
	}

	@Override
	public List<Move> nextElements() {
		return board.moves();
	}
	
	@Override
	public Move lastElement() {
		return lastMove;
	}

	@Override
	public Tuple<Move> next(Move move) {
		Board next = new Board(board);
		next.apply(move);
		return new BoardTuple(next, this, move, solver);
	}

	@Override
	public Tuple<Move> previous() {
		return previousTuple;
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public int hashCode() {
		return board.shallowHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj != null && obj instanceof BoardTuple) {
			BoardTuple t = (BoardTuple) obj;
			result = board.equalsShallowly(t.board);
		}
		return result;
	}
	
	@Override
	public String getGraphNodeId() {
		return "N" + visit;
	}

	@Override
	public String getGraphNodeLabel() {
		return board.toString();
	}

	@Override
	public String getGraphNodeDescription() {
		return "Visit #" + visit + "\nLength: " + size + "\n" + nextElements().toString();
	}
	
	@Override
	public String getGraphEdgeLabel() {
		return lastMove == null ? null : lastMove.toString();
	}
	
	@Override
	public String toString() {
		return board.toString();
	}
}
