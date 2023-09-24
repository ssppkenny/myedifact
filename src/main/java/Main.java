import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import io.xlate.edi.stream.EDIInputFactory;
import io.xlate.edi.stream.EDIStreamReader;

public class Main {

	public static void main(String[] args) throws Exception {
		SortedMap<String, List<String>> messageModel = new TreeMap<>();

//        parse();
	}

	private static void parse() throws Exception {
		EDIInputFactory factory = EDIInputFactory.newFactory();

// Obtain Stream to the EDI document to read.
		InputStream stream = new FileInputStream("/Users/ugxnbmikhs/Downloads/edifact.dat");

		EDIStreamReader reader = factory.createEDIStreamReader(stream);

		while (reader.hasNext()) {
			switch (reader.next()) {
			case START_INTERCHANGE:
				/* Retrieve the standard - "X12", "EDIFACT", or "TRADACOMS" */
				String standard = reader.getStandard();

				/*
				 * Retrieve the version string array. An array is used to support
				 * the componentized version element used in the EDIFACT standard.
				 *
				 * e.g. [ "00501" ] (X12) or [ "UNOA", "3" ] (EDIFACT)
				 */
				String[] version = reader.getVersion();
				break;

			case START_SEGMENT:
				// Retrieve the segment name - e.g. "ISA" (X12), "UNB" (EDIFACT), or "STX" (TRADACOMS)
				String segmentName = reader.getText();
				System.out.println(segmentName);
				break;

			case END_SEGMENT:
				break;

			case START_TRANSACTION:
				break;

			case START_COMPOSITE:
				break;

			case END_COMPOSITE:
				break;

			case ELEMENT_DATA:
				// Retrieve the value of the current element
				String text = reader.getText();
				System.out.println(text);
				break;
			}
		}

		reader.close();
		stream.close();
	}
}
