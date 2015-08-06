package backtrack.example.puzzle;

import java.util.ArrayList;
import java.util.List;

import backtrack.Tuple;
import backtrack.example.puzzle.core.Board;
import backtrack.example.puzzle.core.Move;

public class BoardTuple implements Tuple<Move> {
	
	private final Board board;
	private final BoardTuple previousTuple;
	private final Move lastMove;
	private final PuzzleSolver solver;
	private int visitId;
	private final int size;
	private boolean partOfSolution;
	
	public BoardTuple(Board board, PuzzleSolver solver) {
		this(board, null, null, solver);
	}
	
	private BoardTuple(Board board, BoardTuple previousTuple, Move lastMove,
			PuzzleSolver solver) {
		this.board = board;
		this.previousTuple = previousTuple;
		this.lastMove = lastMove;
		this.solver = solver;
		visitId = -1;
		size = lastMove == null ? 0 : previousTuple.size() + 1;
	}
	
	public Board getBoard() {
		return board;
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public Move lastElement() {
		return lastMove;
	}
	
	@Override
	public Tuple<Move> previous() {
		return previousTuple;
	}
	
	@Override
	public List<Tuple<Move>> next() {
		List<Tuple<Move>> result = new ArrayList<Tuple<Move>>();
		for (Move move : board.moves()) {
			result.add(next(move));
		}
		return result;
	}
	
	private BoardTuple next(Move move) {
		Board next = new Board(board);
		next.apply(move);
		return new BoardTuple(next, this, move, solver);
	}
	
	@Override
	public int getVisitId() {
		return visitId;
	}

	@Override
	public void setVisitId(int visitId) {
		this.visitId = visitId;
	}

	@Override
	public boolean isSolution() {
		return board.equals(solver.getTarget(), solver.getTargetPieceId());
	}
	
	@Override
	public boolean isPartOfSolution() {
		return partOfSolution;
	}
	
	@Override
	public void setPartOfSolution(boolean partOfSolution) {
		this.partOfSolution = partOfSolution;
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
		return "N" + visitId;
	}

	@Override
	public String getGraphNodeLabel() {
		return board.toString();
	}

	@Override
	public String getGraphNodeDescription() {
		return "Visit #" + visitId + "\nLength: " + size + "\n" + board.moves().toString();
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
