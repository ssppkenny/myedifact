package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;

class ElementTest {

	@org.junit.jupiter.api.Test
	void parse() {

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

		Element tod = new Element("TOD", "TOD", 1, true, null);
		Element locSg5 = new Element("LOC", "LOC", 5, false, null);
		Element ftx = new Element("FTX", "FTX", 5, false, null);
		Element sg5 = new Element("SG5", "TOD(LOC){0,5}(FTX){0,5}", 10, false, Arrays.asList(tod, locSg5, ftx));

		Element tdt = new Element("TDT", "TDT", 1, true, null);
		Element pcd = new Element("PCD", "PCD", 6, false, null);
		Element tmd = new Element("TMD", "TMD", 1, false, null);
		// SG6 begin
		Element locSg7 = new Element("LOC", "LOC", 1, true, null);
		Element dtmSg7 = new Element("DTM", "DTM", 10, false, null);
		Element sg7 = new Element("SG7", "LOC(DTM){0,10}", 10, false, Arrays.asList(locSg7, dtmSg7));

		Element sg6 = new Element("SG6", "TDT(PCD){0,6}(TMD){0,1}(LOC(DTM){0,10}){0,10}", 10, false, Arrays.asList(tdt, pcd, tmd, sg7));
		// SG6 end

		// SG8 begin
		Element eqd = new Element("EQD", "EQD", 1, true, null);
		Element meaSg8 = new Element("MEA", "MEA", 5, false, null);
		Element sel = new Element("SEL", "SEL", 25, false, null);
		Element eqa = new Element("EQA", "EQA", 5, false, null);

		Element hanSg9 = new Element("HAN", "HAN", 1, true, null);
		Element ftxSg9 = new Element("FTX", "FTX", 10, false, null);

		Element sg9 = new Element("SG9", "HAN(FTX){0,10}", 10, false, Arrays.asList(hanSg9, ftxSg9));

		Element sg8 = new Element("SG8", "EQD(MEA){0,5}(SEL){0,25}(EQA){0,5}(HAN(FTX){0,10}){0,10}", 10, false, Arrays.asList(eqd, meaSg8, sel, eqa, sg9));
		// SG8 end

		Element sg1 = new Element("SG1", "RFF(DTM){0,1}", 10, false, Arrays.asList(rff, dtm));
		Element sg2 = new Element("SG2", "NAD(LOC){0,10}(RFF(DTM){0,1}){0,10}(CTA(COM){0,5}){0,10}", 99, false, Arrays.asList(nad, loc, sg3, sg4));

		Element message = new Element(
				"Message",
				"UNHBGM(DTM){0,10}(ALI){0,5}(MEA){0,5}(MOA){0,5}(CUX){0,9}(NAD(LOC){0,10}(RFF(DTM){0,1}){0,10}(CTA(COM){0,5}){0,10}){0,10}",
				1,
				true,
				Arrays.asList(unh, bgm, dtmHead, ali, mea, moa, cux, sg1, sg2, sg5, sg6, sg8));

		System.out.println("Message");

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

		EdifactNode node = Element.parse(buffer.toString(), message, lines, 0);
		Assertions.assertEquals(6, node.getChildren().size());
		Assertions.assertEquals(4, node.getChildren().get(5).getChildren().size());

	}
}
