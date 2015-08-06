package backtrack;

import java.awt.Color;
import java.io.IOException;
import java.io.Writer;

/**
 * <p>
 * <code>GraphFormat</code> is a class for formatting graph data in <code>GraphML</code> format.
 * </p>
 * <p>
 * Graph data is formatted in <code>yEd</code>'s <code>GraphML</code>. <code>yEd</code> can be
 * downloaded for free at <a href="http://www.yworks.com/en/products/yfiles/yed/">
 * http://www.yworks.com/en/products/yfiles/yed/</a>.
 * </p>
 * <p>
 * Once a graph file is opened in <code>yEd</code>, nodes need to be fitted to the label. This can be
 * done via the <code>Tools>Fit Node to Label</code> menu. Both width and height will have to be
 * selected.
 * </p>
 * <p>
 * A layout can then be calculated. For a backtracking traversal graph it is recommended to use a
 * <code>Hierarchical</code> layout (available from the <code>Layout</code> menu) with
 * <code>BFS Layering</code> and <code>Topmost Component Arrangement</code> (from the <code>Layers</code>
 * tab), and <code>Generic Edge Labeling</code> with <code>Best Label Model</code> (from the
 * <code>Labeling</code> tab).
 * </p>
 */
public class GraphFormat {
	
	private final Writer writer;

	public GraphFormat(Writer writer) {
		this.writer = writer;
	}
	
	public void open(String graphName) throws IOException {
		openGraphML();
		writeAttributes();
		openGraph(graphName);
	}
	
	private void openGraphML() throws IOException {
		writer.write("<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"\n");
		writer.write(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
		writer.write(" xmlns:y=\"http://www.yworks.com/xml/graphml\"\n");
		writer.write(" xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns\n");
		writer.write("   http://www.yworks.com/xml/schema/graphml/1.1/ygraphml.xsd\">\n\n");
	}
	
	private void writeAttributes() throws IOException {
		writer.write("  <key for=\"node\" id=\"key0\" yfiles.type=\"nodegraphics\"/>\n");
		writer.write("  <key for=\"node\" id=\"key1\" attr.name=\"description\" attr.type=\"string\"/>\n");
		writer.write("  <key for=\"edge\" id=\"edge1\" yfiles.type=\"edgegraphics\"/>\n\n");
	}
	
	private void openGraph(String name) throws IOException {
		writer.write(String.format("  <graph id=\"%s\" edgedefault=\"directed\">\n", name));
	}
	
	public void writeNode(String id, String label, String description, Color color) throws IOException {
		writer.write(String.format("    <node id=\"%s\">\n", id));
		writer.write("      <data key=\"key0\">\n");
		writer.write("        <y:ShapeNode>\n");
		if (color != null) {
			writer.write(String.format("          <y:Fill color=\"%s\" transparent=\"false\"/>\n", getHtmlColor(color)));
		}
		writer.write(String.format("          <y:NodeLabel alignment=\"center\" fontFamily=\"Courier New\" fontSize=\"12\" fontStyle=\"plain\">%s</y:NodeLabel>\n", label));
		writer.write("        </y:ShapeNode>\n");
		writer.write("      </data>\n");
		if (description != null) {
			writer.write(String.format("      <data key=\"key1\"><![CDATA[%s]]></data>\n", description));
		}
		writer.write("    </node>\n");
	}
	
	private static String getHtmlColor(Color color) {
		return "#" + Integer.toHexString(color.getRGB()).substring(2, 8);
	}
	
	public void writeEdge(String sourceId, String targetId, String label) throws IOException {
		writer.write(String.format("    <edge id=\"E%s-%s\" source=\"%s\" target=\"%s\">\n",
				sourceId, targetId, sourceId, targetId));
		if (label != null) {
			writer.write("      <data key=\"edge1\">\n");
			writer.write("        <y:PolyLineEdge>\n");
			writer.write(String.format("        <y:EdgeLabel>%s</y:EdgeLabel>\n", label));
			writer.write("        </y:PolyLineEdge>\n");
			writer.write("      </data>\n");
		}
		writer.write("    </edge>\n");
	}
	
	public void close() throws IOException {
		closeGraph();
		closeGraphML();
		writer.flush();
		writer.close();
	}
	
	private void closeGraph() throws IOException {
		writer.write("  </graph>\n");
	}
	
	private void closeGraphML() throws IOException {
		writer.write("</graphml>\n");
	}
}
