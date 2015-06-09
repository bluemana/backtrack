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
		String input =
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
		Board board = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(input)), 5, 4);
		Set<Move> moves = new HashSet<Move>(board.moves());
		Assert.assertTrue(moves.equals(expected));
	}
}
