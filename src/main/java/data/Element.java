package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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

	public static void main(String[] args) {

		Element unh = new Element("UNH", "UNH", 1, true, null);
		Element bgm = new Element("BGM", "BGM", 1, true, null);
		Element dtmHead = new Element("DTM", "DTM", 10, false, null);
		Element ali = new Element("ALI", "ALI", 5, false, null);
		Element mea = new Element("MEA", "MEA", 5, false, null);
		Element moa = new Element("MOA", "MOA", 5, false, null);
		Element cux = new Element("CUX", "CUX", 9, false, null);

		Element nad = new Element("NAD", "NAD", 1, true, null);
		Element loc = new Element("LOC", "LOC", 10, false, null);
		Element rff = new Element("RFF", "RFF", 1, true, null);
		Element dtm = new Element("DTM", "DTM", 1, false, null);
		Element sg3 = new Element("SG3", "RFF(DTM){0,1}", 10, false, Arrays.asList(rff, dtm));
		Element cta = new Element("CTA", "CTA", 1, true, null);
		Element com = new Element("COM", "COM", 5, false, null);
		Element sg4 = new Element("SG4", "CTA(COM){0,5}", 10, false, Arrays.asList(cta, com));

		Element sg1 = new Element("SG1", "RFF(DTM){0,1}", 10, false, Arrays.asList(rff, dtm));
		Element sg2 = new Element("SG2", "NAD(LOC){0,10}(RFF(DTM){0,1}){0,10}(CTA(COM){0,5}){0,10}", 99, false, Arrays.asList(nad, loc, sg3, sg4));

		Element message = new Element(
				"Message",
				"UNHBGM(DTM){0,10}(ALI){0,5}(MEA){0,5}(MOA){0,5}(CUX){0,9}(NAD(LOC){0,10}(RFF(DTM){0,1}){0,10}(CTA(COM){0,5}){0,10}){0,10}",
				1,
				true,
				Arrays.asList(unh, bgm, dtmHead, ali, mea, moa, cux, sg1, sg2));

		System.out.println(sg2.getName());

		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("file.edi");

		InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(streamReader);
		StringBuffer buffer = new StringBuffer();
		List<String> lines = new ArrayList<>();
		try {
			for (String line; (line = reader.readLine()) != null;) {
				// Process line
				System.out.println(line);
				buffer.append(line.substring(0, 3));
				lines.add(line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		EdifactNode node = parse(buffer.toString(), message, lines, 0);
		System.out.println(node);

	}

}
