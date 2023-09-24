package data;

import java.util.List;
import java.util.StringJoiner;

public class EdifactNode {

	private String name;

	private List<EdifactNode> children;

	private String content;

	public EdifactNode(String name, List<EdifactNode> children, String content) {
		this.name = name;
		this.children = children;
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public List<EdifactNode> getChildren() {
		return children;
	}

	public String getContent() {
		return content;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", EdifactNode.class.getSimpleName() + "[", "]")
				.add("name='" + name + "'")
				.add("children=" + children)
				.add("content='" + content + "'")
				.toString();
	}
}
