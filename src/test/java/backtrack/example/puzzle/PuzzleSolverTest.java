package backtrack.example.puzzle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import backtrack.example.puzzle.core.Board;
import backtrack.example.puzzle.core.Move;
import backtrack.example.puzzle.util.StandardFormatUtils;

public class PuzzleSolverTest {

	@Test
	public void solve_TargetPieceInSamePosition_SolutionOfSizeOne() throws IOException {
		String startBoardString =
				"1 1 2 2\n" +
				"1 1 3 4\n" +
				". . 3 4\n" +
				"5 6 6 7\n" +
				"5 8 8 0\n";
		String targetBoardString =
				"1 1 2 2\n" +
				"1 1 3 4\n" +
				". . 3 4\n" +
				"5 6 6 0\n" +
				". . . .\n";
		Board startBoard = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(startBoardString)), 5, 4);
		Board targetBoard = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(targetBoardString)), 5, 4);
		PuzzleSolver solver = new PuzzleSolver();
		solver.setStart(new BoardTuple(startBoard, null, null, solver));
		solver.setTargetBoard(targetBoard);
		solver.setTargetPieceId(1);
		List<Move> moves = solver.solve();
		Assert.assertTrue(moves.isEmpty());
	}
	
	@Test
	public void solve_Quzzle_SolutionOf93Moves() throws Exception {
		String startBoardString =
				"0 0 1 1\n" +
				"0 0 2 3\n" +
				". . 2 3\n" +
				"4 5 5 6\n" +
				"4 7 7 8\n";
		String targetBoardString =
				"1 1 0 0\n" +
				"3 2 0 0\n" +
				"3 2 . .\n" +
				"6 5 5 4\n" +
				"8 7 7 4\n";
		Board startBoard = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(startBoardString)), 5, 4);
		Board targetBoard = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(targetBoardString)), 5, 4);
		PuzzleSolver solver = new PuzzleSolver();
		solver.setStart(new BoardTuple(startBoard, null, null, solver));
		solver.setTargetBoard(targetBoard);
		solver.setTargetPieceId(0);
		List<Move> moves = solver.solve();
		Assert.assertTrue(moves.size() == 93);
	}
}
