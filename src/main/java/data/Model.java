package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

	private Map<String, List<String>> messageModel = new HashMap<String, List<String>>() {
		{
			put("SG1", Arrays.asList("RFF", "DTM"));
			put("SG2", Arrays.asList("NAD", "LOC", "SG3", "SG4"));
			put("SG3", Arrays.asList("RFF", "DTM"));
			put("SG4", Arrays.asList("CTA", "COM"));
			put("SG5", Arrays.asList("TOD", "LOC", "FTX"));
			put("SG6", Arrays.asList("TDT", "PCD", "TMD", "SG7"));
			put("SG7", Arrays.asList("LOC", "DTM"));
			put("SG8", Arrays.asList("EQD", "MEA", "SEL", "EQA", "SG9"));
			put("SG9", Arrays.asList("HAN", "FTX"));
			put("SG10", Arrays.asList("CPS", "FTX", "QVR", "SG11", "SG17"));
			put("SG11", Arrays.asList("PAC", "MEA", "QTY", "SG12", "SG13"));
			put("SG12", Arrays.asList("HAN", "FTX"));
			put("SG13", Arrays.asList("PCI", "RFF", "DTM", "SG14", "SG15", "SG16"));
			put("SG14", Arrays.asList("GIR", "DTM"));
			put("SG15", Arrays.asList("GIN", "DLM"));
			put("SG16", Arrays.asList("COD", "MEA", "QTY", "PCD"));
			put(
					"SG17",
					Arrays.asList(
							"LIN",
							"PIA",
							"IMD",
							"MEA",
							"QTY",
							"ALI",
							"GIN",
							"GIR",
							"DLM",
							"DTM",
							"NAD",
							"TDT",
							"TMD",
							"HAN",
							"FTX",
							"MOA",
							"SG18",
							"SG19",
							"SG20",
							"SG21",
							"SG22",
							"SG25"));
			put("SG18", Arrays.asList("RFF", "NAD", "CTA", "DTM"));
			put("SG20", Arrays.asList("LOC", "NAD", "DTM", "QTY"));
			put("SG21", Arrays.asList("SGP", "QTY"));
			put("SG22", Arrays.asList("PCI", "DTM", "MEA", "QTY", "SG23", "SG24"));
			put("SG23", Arrays.asList("GIN", "DLM"));
			put("SG24", Arrays.asList("HAN", "FTX", "GIN"));
			put("SG25", Arrays.asList("QVR", "DTM"));

		}
	};

	private List<String> elements = Arrays.asList("UNH", "BGM", "DTM", "ALI", "MEA", "MOA", "CUX", "SG1", "SG2", "SG5", "SG6", "SG10", "CNT", "UNT");

	public List<String> getElements() {
		return elements;
	}

	public void find(List<String> elements, String element, String parent, int level, List<String> answer) {

		for (String s : elements) {
			if (s.equals(element)) {
				System.out.println(parent + " on level " + level);
				answer.add(parent);
			}
			List<String> strings = messageModel.get(s);
			if (strings != null) {
				find(strings, element, s, level + 1, answer);
			}
		}

	}

	public static void main(String[] args) {
		Model m = new Model();
		List<String> answer = new ArrayList<>();
		m.find(m.elements, "RFF", "message", 0, answer);
		System.out.println(answer);
	}

}
