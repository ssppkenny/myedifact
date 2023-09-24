package data;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Element {

	private String name;

	private String regex;

	private int repeats;

	private boolean mandatory;

	private List<Element> children;

	public Element(String name, String regex, int repeats, boolean mandatory, List<Element> children) {
		this.name = name;
		this.regex = regex;
		this.repeats = repeats;
		this.mandatory = mandatory;
		this.children = children;
	}

	public String getName() {
		return name;
	}

	public String getRegex() {
		return mandatory ? "(" + regex + ")" : "(" + regex + ")" + "{0," + repeats + "}";
	}

	public int getRepeats() {
		return repeats;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public List<Element> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Element.class.getSimpleName() + "[", "]")
				.add("name='" + name + "'")
				.add("regex='" + regex + "'")
				.add("repeats=" + repeats)
				.add("mandatory=" + mandatory)
				.add("children=" + children)
				.toString();
	}

	public static EdifactNode parse(String s, Element parseTree, List<String> lines, int counter) {

		Pattern pattern = Pattern.compile(parseTree.getRegex());
		Matcher matcher = pattern.matcher(s);
		List<EdifactNode> nodeChildren = new ArrayList<>();

		if (matcher.matches()) {
			Pattern p2 = Pattern.compile(parseTree.regex);
			Matcher matcher2 = p2.matcher(s);
			int pos = 0;
			while (matcher2.find(pos)) {
				int start = matcher2.start();
				pos = matcher2.end();
				String groupString = s.substring(start, pos);

				List<Element> children1 = parseTree.getChildren();

				for (Element child : children1) {
					Pattern chp = Pattern.compile(child.getRegex());
					Matcher matcher1 = chp.matcher(groupString);
					if (matcher1.find(0)) {
						int endPos = matcher1.end();
						String childString = groupString.substring(0, endPos);
						groupString = groupString.substring(endPos);
						if (endPos > 0) {

							EdifactNode node = null;
							if (child.children != null) {
								node = parse(childString, child, lines, counter);
								nodeChildren.add(node);
							} else {
								String[] chunks = childString.split("(?<=\\G.{3})");
								for (String chunk : chunks) {
									node = new EdifactNode(chunk, null, lines.get(counter));
									counter++;
									nodeChildren.add(node);
								}
							}
						}
					}
				}

			}

		}

		return new EdifactNode(parseTree.getName(), nodeChildren, null);
	}

}
