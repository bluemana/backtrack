package backtrack.example.puzzle.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import backtrack.example.puzzle.core.Move.Direction;


public class Board {
	
	private final Piece[][] board;
	private final int rows;
	private final int cols;
	private final Piece[] pieces;
	private final Position[] positions;
	
	/**
	 * <p>
	 * Constructs a new sliding tile board. The expected behaviour is guaranteed
	 * only if <code>piecesCount</code> pieces are correctly placed on the board
	 * after this <code>Board</code> is constructed.
	 * </p>
	 * 
	 * @param rows the number of rows of the board
	 * @param cols the number of columns of the board
	 * @param piecesCount the number of pieces that will be placed on the board
	 */
	public Board(int rows, int cols, int piecesCount) {
		this.rows = rows;
		this.cols = cols;
		this.board = new Piece[rows][cols];
		pieces = new Piece[piecesCount];
		positions = new Position[piecesCount];
	}
	
	public Board(Board board) {
		this(board.rows, board.cols, board.pieces.length);
		for (int i = 0; i < board.pieces.length; i++) {
			place(board.pieces[i], board.positions[i]);
		}
	}
	
	public void place(Piece piece, Position position) {
		if (canPlace(piece, position)) {
			int row = position.getRow();
			int col = position.getCol();
			for (int i = row; i < row + piece.getHeight(); i++) {
				for (int j = col; j < col + piece.getWidth(); j++) {
					board[i][j] = piece;
				}
			}
			pieces[piece.getId()] = piece;
			positions[piece.getId()] = position;
		} else {
			throw new IllegalArgumentException(String.format("Invalid piece %s or position %s", piece, position));
		}
	}
	
	private boolean canPlace(Piece piece, Position position) {
		boolean result = false;
		if (piece.getId() >= 0 && piece.getId() < pieces.length && pieces[piece.getId()] == null) {
			int startRow = position.getRow();
			int endRow = (startRow + piece.getHeight()) - 1;
			int startCol = position.getCol();
			int endCol = (startCol + piece.getWidth()) - 1;
			if (startRow >= 0 && endRow < rows &&
					startCol >= 0 && endCol < cols) {
				result = true;
				OUTER_LOOP:
					for (int i = startRow; i <= endRow; i++) {
						for (int j = startCol; j <= endCol; j++) {
							if (board[i][j] != null) {
								result = false;
								break OUTER_LOOP;
							}
						}
					}
			}
		}
		return result;
	}
	
	private void remove(Piece piece) {
		Position position = positions[piece.getId()];
		int row = position.getRow();
		int col = position.getCol();
		for (int i = row; i < row + piece.getHeight(); i++) {
			for (int j = col; j < col + piece.getWidth(); j++) {
				board[i][j] = null;
			}
		}
		pieces[piece.getId()] = null;
		positions[piece.getId()] = null;
	}
	
	public Piece pieceAt(Position position) {
		return board[position.getRow()][position.getCol()];
	}
	
	public Position positionOf(Piece piece) {
		return positions[piece.getId()];
	}
	
	/**
	 * Returns a list of valid moves.
	 * 
	 * @return a list of valid moves
	 */
	public List<Move> moves() {
		List<Move> result = new ArrayList<Move>();
		for (Piece piece : pieces) {
			result.addAll(moves(piece));
		}
		return result;
	}
	
	private List<Move> moves(Piece piece) {
		List<Move> result = new ArrayList<Move>();
		Position position = positions[piece.getId()];
		int height = piece.getHeight();
		int width = piece.getWidth();
		// Direction.UP
		int row = position.getRow();
		int col = position.getCol();
		boolean canMove = true;
		int cells = 0;
		while (canMove && row > 0) {
			for (int j = col; j < col + width; j++) {
				if (board[row - 1][j] != null) {
					canMove = false;
					break;
				}
			}
			if (canMove) {
				cells++;
				result.add(new Move(piece.getId(), Direction.UP, cells));
			}
			row--;
		}
		// Direction.DOWN
		row = position.getRow();
		col = position.getCol();
		canMove = true;
		cells = 0;
		while (canMove && row + height < rows) {
			for (int j = col; j < col + width; j++) {
				if (board[row + height][j] != null) {
					canMove = false;
					break;
				}
			}
			if (canMove) {
				cells++;
				result.add(new Move(piece.getId(), Direction.DOWN, cells));
			}
			row++;
		}
		// Direction.LEFT
		row = position.getRow();
		col = position.getCol();
		canMove = true;
		cells = 0;
		while (canMove && col > 0) {
			for (int i = row; i < row + height; i++) {
				if (board[i][col - 1] != null) {
					canMove = false;
					break;
				}
			}
			if (canMove) {
				cells++;
				result.add(new Move(piece.getId(), Direction.LEFT, cells));
			}
			col--;
		}
		// Direction.RIGHT
		row = position.getRow();
		col = position.getCol();
		canMove = true;
		cells = 0;
		while (canMove && col + width < cols) {
			for (int i = row; i < row + height; i++) {
				if (board[i][col + width] != null) {
					canMove = false;
					break;
				}
			}
			if (canMove) {
				cells++;
				result.add(new Move(piece.getId(), Direction.RIGHT, cells));
			}
			col++;
		}
		return result;
	}
	
	public void apply(Move move) {
		if (canApply(move)) {
			Piece piece = pieces[move.getPieceId()];
			Direction direction = move.getDirection();
			Position position = positions[piece.getId()];
			int row = position.getRow();
			int col = position.getCol();
			if (direction == Direction.UP) {
				row -= move.getCells();
			} else if (direction == Direction.DOWN) {
				row += move.getCells();
			} else if (direction == Direction.RIGHT) {
				col += move.getCells();
			} else if (direction == Direction.LEFT) {
				col -= move.getCells();
			}
			remove(piece);
			place(piece, new Position(row, col));
		} else {
			throw new IllegalArgumentException("Invalid move " + move);
		}
	}
	
	private boolean canApply(Move move) {
		return moves().contains(move);
	}
	
	@Override
	public int hashCode() {
		Object[] hash = new Object[pieces.length + positions.length];
		for (int i = 0; i < pieces.length; i++) {
			hash[i] = pieces[i];
		}
		for (int i = 0; i < positions.length; i++) {
			hash[pieces.length + i] = positions[i];
		}
		return Arrays.deepHashCode(hash);
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj != null && obj instanceof Board) {
			Board b = (Board) obj;
			result = Arrays.deepEquals(pieces, b.pieces) && Arrays.deepEquals(positions, b.positions);
		}
		return result;
	}
	
	public boolean equals(Board board, int pieceId) {
		return board != null && rows == board.rows && cols == board.cols &&
				pieceId >= 0 && pieceId < pieces.length && pieces[pieceId] != null
				&& pieceId < board.pieces.length && board.pieces[pieceId] != null &&
				pieces[pieceId].equals(board.pieces[pieceId]) &&
				positions[pieceId].equals(board.positions[pieceId]);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * It is not guaranteed that this method returns a board in <i>standard format</i>.
	 * If a <i>standard format</i> representation is needed, use
	 * {@link backtrack.example.puzzle.util.StandardFormatUtils#format(Board)} instead.
	 * </p>
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				builder.append((board[i][j] == null ? "." : board[i][j].getId()) +
						(j == cols - 1 ? "\n" : " "));
			}
		}
		return builder.toString();
	}
}
