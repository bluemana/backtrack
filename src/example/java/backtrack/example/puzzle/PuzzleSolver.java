package backtrack.example.puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import backtrack.example.puzzle.core.Board;
import backtrack.example.puzzle.core.Move;

public class PuzzleSolver {

	public static final int DEFAULT_MAX_VISITS = 100000;
	
	private static class SolverBoard extends Board {
		
		private final SolverBoard previousBoard;
		
		public SolverBoard(Board board, SolverBoard previousBoard) {
			super(board);
			this.previousBoard = previousBoard;
		}
		
		public SolverBoard getPreviousBoard() {
			return previousBoard;
		}
	}

	private final Board start;
	private final Board target;
	private final int targetPieceId;
	private int maxVisits;
	private int visitsCount;
	private List<Board> result;

	public PuzzleSolver(Board start, Board target, int targetPieceId) {
		this.start = start;
		this.target = target;
		this.targetPieceId = targetPieceId;
		maxVisits = DEFAULT_MAX_VISITS;
		visitsCount = -1;
	}

	public Board getStart() {
		return start;
	}

	public Board getTarget() {
		return target;
	}

	public int getTargetPieceId() {
		return targetPieceId;
	}

	public void setMaxVisits(int maxVisits) {
		this.maxVisits = maxVisits;
	}

	public int getMaxVisits() {
		return maxVisits;
	}

	public int getVisitsCount() {
		return visitsCount;
	}

	public List<Board> solve() {
		return solve(start);
	}

	private List<Board> solve(Board start) {
		Queue<SolverBoard> traversalQueue = new LinkedList<SolverBoard>();
		Set<SolverBoard> visitedSet = new HashSet<SolverBoard>();
		visitsCount = 0;
		traversalQueue.add(new SolverBoard(start, null));
		while (!traversalQueue.isEmpty()) {
			SolverBoard current = traversalQueue.poll();
			if (visitsCount < maxVisits && !visitedSet.contains(current)) {
				// Mark as visited
				visitedSet.add(current);
				visitsCount++;
				// Continue
				if (!current.equals(getTarget(), getTargetPieceId())) {
					for (Move move : current.moves()) {
						SolverBoard next = new SolverBoard(current, current);
						next.apply(move);
						traversalQueue.add(next);
					}
				} else {
					result = path(current);
					break;
				}
			}
		}
		return result;
	}

	private List<Board> path(SolverBoard last) {
		List<Board> result = new ArrayList<Board>();
		do {
			result.add(last);
		} while ((last = last.getPreviousBoard()) != null);
		Collections.reverse(result);
		return result;
	}
}
