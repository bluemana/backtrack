package backtrack.core;


public class Board {
	
	private final Piece[][] board;
	private final int rows;
	private final int cols;
	private final Piece[] pieces;
	private final Position[] positions;
	
	public Board(int rows, int cols, int piecesCount) {
		this.rows = rows;
		this.cols = cols;
		this.board = new Piece[rows][cols];
		pieces = new Piece[piecesCount];
		positions = new Position[piecesCount];
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
	
	public Piece pieceAt(Position position) {
		Piece result = null;
		if (position.getRow() >= 0 && position.getRow() < rows &&
				position.getCol() >= 0 && position.getCol() < cols) {
			result = board[position.getRow()][position.getCol()];
		}
		return result;
	}
	
	public Position positionOf(Piece piece) {
		Position result = null;
		if (piece.getId() >= 0 && piece.getId() < pieces.length) {
			result = positions[piece.getId()];
		}
		return result;
	}
	
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
