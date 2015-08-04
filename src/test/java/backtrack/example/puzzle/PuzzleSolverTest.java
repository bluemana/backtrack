package backtrack.example.puzzle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import backtrack.BfsBacktracker;
import backtrack.DfsBacktracker;
import backtrack.GraphFormat;
import backtrack.example.puzzle.core.Board;
import backtrack.example.puzzle.core.Move;
import backtrack.example.puzzle.util.StandardFormatUtils;

public class PuzzleSolverTest {

	@Test
	public void solveBfs_TargetPieceInSamePosition_SolutionOfSizeOne() throws IOException {
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
		solver.setStart(startBoard);
		solver.setTarget(targetBoard);
		solver.setTargetPieceId(1);
		solver.setBacktracker(new BfsBacktracker<BoardTuple, Move>());
		List<Move> moves = solver.solve();
		Assert.assertTrue(moves.isEmpty());
	}
	
	@Test
	public void solveBfs_Quzzle_SolutionOf93Moves() throws IOException {
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
		solver.setStart(startBoard);
		solver.setTarget(targetBoard);
		solver.setTargetPieceId(0);
		solver.setBacktracker(new BfsBacktracker<BoardTuple, Move>());
		List<Move> moves = solver.solve();
		Assert.assertTrue(moves.size() == 93);
	}
	
	/**
	 * A convenience method for writing to a file the BFS traversal graph of the Quzzle.
	 * 
	 * @throws IOException if an I/O exception occurs
	 */
	@Test
	public void graphBfs_Quzzle_Written() throws IOException {
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
		solver.setStart(startBoard);
		solver.setTarget(targetBoard);
		solver.setTargetPieceId(0);
		solver.setBacktracker(new BfsBacktracker<BoardTuple, Move>());
		File output = new File("quzzle_bfs_traversal.graphml");
		solver.getBacktracker().setGraphFormat(new GraphFormat(new BufferedWriter(new FileWriter(output))));
		solver.solve();
		Assert.assertTrue(output.exists());
	}
	
	@Test
	public void solveDfs_TargetPieceInSamePosition_SolutionOfSizeOne() throws IOException {
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
		solver.setStart(startBoard);
		solver.setTarget(targetBoard);
		solver.setTargetPieceId(1);
		solver.setBacktracker(new DfsBacktracker<BoardTuple, Move>());
		List<Move> moves = solver.solve();
		Assert.assertTrue(moves.isEmpty());
	}
	
	@Test
	public void solveDfs_Quzzle_SolutionOf149Moves() throws IOException {
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
		solver.setStart(startBoard);
		solver.setTarget(targetBoard);
		solver.setTargetPieceId(0);
		solver.setBacktracker(new DfsBacktracker<BoardTuple, Move>());
		List<Move> moves = solver.solve();
		Assert.assertTrue(moves.size() == 149);
	}
	
	/**
	 * A convenience method for writing to a file the DFS traversal graph of the Quzzle.
	 * 
	 * @throws IOException if an I/O exception occurs
	 */
	@Test
	public void graphDfs_Quzzle_Written() throws IOException {
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
		solver.setStart(startBoard);
		solver.setTarget(targetBoard);
		solver.setTargetPieceId(0);
		solver.setBacktracker(new DfsBacktracker<BoardTuple, Move>());
		File output = new File("quzzle_dfs_traversal.graphml");
		solver.getBacktracker().setGraphFormat(new GraphFormat(new BufferedWriter(new FileWriter(output))));
		solver.solve();
		Assert.assertTrue(output.exists());
	}
}
