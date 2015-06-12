package backtrack.example.puzzle.util;

import java.io.BufferedReader;
import java.io.IOException;

import backtrack.example.puzzle.core.Board;
import backtrack.example.puzzle.core.Piece;
import backtrack.example.puzzle.core.Position;

/**
 * <p>
 * Utility methods for parsing and formatting a board from and into <i>standard format</i>.
 * </p>
 * <p>
 * The <i>standard format</i> of a board is such that:<br>
 * <ul>
 *   <li>Pieces are identified by a numerical ID</li>
 *   <li>IDs are numbered from <code>0</code> to <code>N - 1</code>, where <code>N</code>
 *   is the number of pieces on the board</li>
 *   <li>Pieces are arranged on the cells of a <code>MxN</code> matrix of <code>M</code> rows
 *   and <code>N</code> columns</li>
 *   <li>Each non-empty cell is marked with the ID of the piece it contains</li>
 *   <li>Empty cells are market with the dot character '.'</li>
 * </ul>
 * </p>
 * <p>
 * For example, the following is a board in <i>standard format</i> representing the initial
 * configuration of the Quzzle:<br>
 * <pre>
 * 		0 0 1 1
 * 		0 0 2 3
 * 		. . 2 3
 * 		4 5 5 6
 * 		4 7 7 8</pre>
 * </p>
 */
public class StandardFormatUtils {

	public static Board parseBoard(BufferedReader reader, int rows, int cols) throws IOException {
		int[][] intBoard = new int[rows][cols];
		int max = parseIntBoard(reader, intBoard, rows, cols);
		Board board = new Board(rows, cols, max + 1);
		placePieces(board, intBoard, rows, cols, max);
		return board;
	}
	
	private static int parseIntBoard(BufferedReader reader, int[][] intBoard, int rows, int cols) throws IOException {
		int max = -1;
		for (int i = 0; i < rows; i++) {
			String[] tokens = reader.readLine().split(" ");
			for (int j = 0; j < cols; j++) {
				int entry = tokens[j].equals(".") ? -1 : Integer.parseInt(tokens[j]);
				intBoard[i][j] = entry;
				if (entry > max) {
					max = entry;
				}
			}
		}
		return max;
	}
	
	private static void placePieces(Board board, int[][] intBoard, int rows, int cols, int max) {
		for (int k = 0; k <= max; k++) {
			int row = -1;
			int col = -1;
			int height = 0;
			int width = 0;
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					if (intBoard[i][j] == k) {
						if (row == -1) {
							row = i;
							height = 1;
						} else if (i > row && j == col) {
							height++;
						}
						if (col == -1) {
							col = j;
							width = 1;
						} else if (j > col && i == row) {
							width++;
						}
					}
				}
			}
			if (row != -1 && col != -1) {
				board.place(new Piece(k, height, width), new Position(row, col));
			} else {
				throw new IllegalArgumentException("Missing piece with ID " + k);
			}
		}
	}
	
	public static String format(Board board) {
		return board.toString();
	}
}
