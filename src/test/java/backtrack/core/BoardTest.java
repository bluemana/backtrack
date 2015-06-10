package backtrack.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import backtrack.core.Move.Direction;
import backtrack.util.StandardFormatUtils;

public class BoardTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void place_ValidPlacement_Placed() {
		Board board = new Board(4, 3, 1);
		Piece piece = new Piece(0, 1, 2);
		Position position = new Position(2, 1);
		board.place(piece, position);
		Assert.assertTrue(piece.equals(board.pieceAt(position)));
		Assert.assertTrue(position.equals(board.positionOf(piece)));
	}
	
	@Test
	public void place_IdOutOfRange_Exception() {
		Board board = new Board(5, 4, 9);
		expectedException.expect(IllegalArgumentException.class);
		board.place(new Piece(9, 2, 2), new Position(1, 1));
	}
	
	@Test
	public void place_DuplicateId_Exception() {
		Board board = new Board(5, 4, 9);
		board.place(new Piece(0, 2, 2), new Position(0, 0));
		expectedException.expect(IllegalArgumentException.class);
		board.place(new Piece(0, 2, 2), new Position(2, 0));
	}
	
	@Test
	public void place_OverAnotherPiece_Exception() {
		Board board = new Board(5, 4, 9);
		board.place(new Piece(0, 2, 2), new Position(0, 0));
		expectedException.expect(IllegalArgumentException.class);
		board.place(new Piece(1, 2, 2), new Position(1, 1));
	}
	
	@Test
	public void place_OutsideOfBoard_Exception() {
		Board board = new Board(5, 4, 9);
		expectedException.expect(IllegalArgumentException.class);
		board.place(new Piece(1, 2, 2), new Position(-1, 4));
	}
	
	@Test
	public void place_NotEnoughSpace_Exception() {
		Board board = new Board(5, 4, 9);
		expectedException.expect(IllegalArgumentException.class);
		board.place(new Piece(1, 2, 2), new Position(4, 3));
	}
	
	@Test
	public void moves_BoardWithMoves_Returned() throws IOException {
		String boardString =
				"0 . . 1\n" +
				"3 . . .\n" +
				". 2 2 .\n" +
				"4 2 2 6\n" +
				"4 5 5 7\n";
		Set<Move> expected = new HashSet<Move>();
		expected.add(new Move(0, Direction.RIGHT, 1));
		expected.add(new Move(0, Direction.RIGHT, 2));
		expected.add(new Move(1, Direction.LEFT, 1));
		expected.add(new Move(1, Direction.LEFT, 2));
		expected.add(new Move(1, Direction.DOWN, 1));
		expected.add(new Move(1, Direction.DOWN, 2));
		expected.add(new Move(2, Direction.UP, 1));
		expected.add(new Move(2, Direction.UP, 2));
		expected.add(new Move(3, Direction.RIGHT, 1));
		expected.add(new Move(3, Direction.RIGHT, 2));
		expected.add(new Move(3, Direction.RIGHT, 3));
		expected.add(new Move(3, Direction.DOWN, 1));
		expected.add(new Move(4, Direction.UP, 1));
		expected.add(new Move(6, Direction.UP, 1));
		expected.add(new Move(6, Direction.UP, 2));
		Board board = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(boardString)), 5, 4);
		Set<Move> moves = new HashSet<Move>(board.moves());
		Assert.assertTrue(moves.equals(expected));
	}
	
	@Test
	public void move_OneCellMove_Applied() throws IOException {
		String boardString1 =
				"1 1 2 2\n" +
				"1 1 3 4\n" +
				". 9 3 4\n" +
				"5 6 6 7\n" +
				"5 8 8 0\n";
		String boardString2 =
				"1 1 2 2\n" +
				"1 1 3 4\n" +
				"5 9 3 4\n" +
				"5 6 6 7\n" +
				". 8 8 0\n";
		Board board1 = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(boardString1)), 5, 4);
		Board board2 = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(boardString2)), 5, 4);
		Move move = new Move(5, Direction.UP, 1);
		board1.apply(move);
		Assert.assertTrue(board1.equals(board2));
	}
	
	@Test
	public void move_OneOutOfTwoCellsMove_Applied() throws IOException {
		String boardString1 =
				"1 1 2 2\n" +
				"1 1 3 4\n" +
				"5 9 3 4\n" +
				". 6 6 7\n" +
				". 8 8 0\n";
		String boardString2 =
				"1 1 2 2\n" +
				"1 1 3 4\n" +
				". 9 3 4\n" +
				"5 6 6 7\n" +
				". 8 8 0\n";
		Board board1 = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(boardString1)), 5, 4);
		Board board2 = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(boardString2)), 5, 4);
		Move move = new Move(5, Direction.DOWN, 1);
		board1.apply(move);
		Assert.assertTrue(board1.equals(board2));
	}
	
	@Test
	public void move_TwoCellsMove_Applied() throws IOException {
		String boardString1 =
				"1 1 2 2\n" +
				"1 1 3 4\n" +
				"5 9 3 4\n" +
				"5 6 6 7\n" +
				". . 8 0\n";
		String boardString2 =
				"1 1 2 2\n" +
				"1 1 3 4\n" +
				"5 9 3 4\n" +
				"5 6 6 7\n" +
				"8 . . 0\n";
		Board board1 = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(boardString1)), 5, 4);
		Board board2 = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(boardString2)), 5, 4);
		Move move = new Move(8, Direction.LEFT, 2);
		board1.apply(move);
		Assert.assertTrue(board1.equals(board2));
	}
	
	@Test
	public void equals_BoardsWithEqualPiecesEqualIds_Equal() throws IOException {
		String boardString1 =
				"1 1 2 2\n" +
				"1 1 3 4\n" +
				". . 3 4\n" +
				"5 6 6 7\n" +
				"5 8 8 0\n";
		String boardString2 =
				"1 1 2 2\n" +
				"1 1 3 4\n" +
				". . 3 4\n" +
				"5 6 6 7\n" +
				"5 8 8 0\n";
		Board board1 = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(boardString1)), 5, 4);
		Board board2 = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(boardString2)), 5, 4);
		Assert.assertTrue(board1.equals(board2));
	}
	
	@Test
	public void equals_BoardsWithEqualPiecesNotEqualIds_NotEqual() throws IOException {
		String boardString1 =
				"1 1 2 2\n" +
				"1 1 3 4\n" +
				". . 3 4\n" +
				"5 6 6 7\n" +
				"5 8 8 0\n";
		String boardString2 =
				"0 0 2 2\n" +
				"0 0 4 3\n" +
				". . 4 3\n" +
				"5 8 8 7\n" +
				"5 6 6 1\n";
		Board board1 = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(boardString1)), 5, 4);
		Board board2 = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(boardString2)), 5, 4);
		Assert.assertTrue(!board1.equals(board2));
	}
	
	@Test
	public void equals_NotEqualBoards_NotEqual() throws IOException {
		String boardString1 =
				"1 1 2 2\n" +
				"2 2 3 3\n" +
				". . 4 4\n" +
				"5 5 6 6\n" +
				"7 7 0 0\n";
		String boardString2 =
				"1 2 2 3\n" +
				"1 4 4 3\n" +
				". . 5 6\n" +
				"9 7 5 6\n" +
				"9 8 8 0\n";
		Board board1 = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(boardString1)), 5, 4);
		Board board2 = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(boardString2)), 5, 4);
		Assert.assertTrue(!board1.equals(board2));
	}
	
	@Test
	public void equals_EqualBoards_EqualHashCode() throws IOException {
		String boardString1 =
				"1 1 2 2\n" +
				"1 1 3 4\n" +
				". . 3 4\n" +
				"5 6 6 7\n" +
				"5 8 8 0\n";
		String boardString2 =
				"1 1 2 2\n" +
				"1 1 3 4\n" +
				". . 3 4\n" +
				"5 6 6 7\n" +
				"5 8 8 0\n";
		Board board1 = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(boardString1)), 5, 4);
		Board board2 = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(boardString2)), 5, 4);
		Assert.assertTrue(board1.equals(board2));
		Assert.assertTrue(board1.hashCode() == board2.hashCode());
	}
	
	@Test
	public void equals_EqualBoardsByPieceId_Equal() throws IOException {
		String boardString1 =
				"1 1 2 2\n" +
				"1 1 3 4\n" +
				". . 3 4\n" +
				"5 6 6 7\n" +
				"5 8 8 0\n";
		String boardString2 =
				"1 1 . .\n" +
				"1 1 . .\n" +
				". . . .\n" +
				"0 2 2 3\n" +
				"0 4 4 5\n";
		Board board1 = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(boardString1)), 5, 4);
		Board board2 = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(boardString2)), 5, 4);
		Assert.assertTrue(board1.equals(board2, 1));
	}
}
