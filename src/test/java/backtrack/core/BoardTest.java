package backtrack.core;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BoardTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void place_ValidPlacement_Placed() {
		Board board = new Board(4, 3, 2);
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
}
