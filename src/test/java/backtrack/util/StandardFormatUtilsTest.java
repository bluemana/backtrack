package backtrack.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import backtrack.core.Board;

public class StandardFormatUtilsTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void parse_ValidBoard_FormatEqualToInput() throws IOException {
		String input =
				"0 0 1 1\n" +
				"0 0 2 3\n" +
				". . 2 3\n" +
				"4 5 5 6\n" +
				"4 7 7 8\n";
		Board board = StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(input)), 5, 4);
		Assert.assertEquals(input, StandardFormatUtils.format(board));
	}
	
	@Test
	public void parse_BoardWithMissingPieceID_Exception() throws IOException {
		String input =
				"0 0 1 1\n" +
				"0 0 2 3\n" +
				". . 2 3\n" +
				". 5 5 6\n" +
				". 7 7 8\n";
		expectedException.expect(IllegalArgumentException.class);
		StandardFormatUtils.parseBoard(new BufferedReader(new StringReader(input)), 5, 4);
	}
}
