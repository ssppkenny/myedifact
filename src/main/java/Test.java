import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
		String s = "NADRFFDTMRFFCTACTANADRFFDTMRFFCTACTA";
		Pattern p1 = Pattern.compile("((NAD)(LOC){0,10}(RFF(DTM){0,1}){0,10}(CTA(COM){0,5}){0,10}){0,99}");
		Matcher matcher2 = p1.matcher(s);

		if (matcher2.matches()) {
			System.out.println("OK");
			Pattern p2 = Pattern.compile("(NAD)(LOC){0,10}(RFF(DTM){0,1}){0,10}(CTA(COM){0,5}){0,10}");
			Matcher matcher1 = p2.matcher(s);
			int pos = 0;
			while (matcher1.find(pos)) {
				int start = matcher1.start();
				pos = matcher1.end();
				System.out.println(s.substring(start, pos));
			}
		}

//
//		Pattern p = Pattern.compile("((?:RFF(?:DTM){0,1}){0,10})");
//		Matcher matcher = p.matcher("RFFDTMRFF");
//
//		if (matcher.matches()) {
//			System.out.println("OK");
//			Pattern p2 = Pattern.compile("RFF(DTM)?");
//			String s = "RFFDTMRFF";
//			Matcher matcher1 = p2.matcher(s);
//			int pos = 0;
//			while (matcher1.find(pos)) {
//				int start = matcher1.start();
//				pos = matcher1.end();
//				System.out.println(s.substring(start, pos));
//
//			}

//		}

	}

	public static void main1(String[] args) {
		Pattern pattern = Pattern.compile(
				"(?<unh>UNH)(BGM)(?<dtm>(?:DTM){0,10})(?<ali>(?:ALI){0,5})(?<mea>(?:MEA){0,5})(?<moa>(?:MOA){0,5})(?<cux>(?:CUX){0,5})(?<sg1>(?<block>((?:RFF)(?:DTM){0,1})){0,10})((?:NAD)(?:LOC){0,10}(RFF(?:DTM){0,1}){0,10}(CTA(?:COM){0,5}){0,10}){0,99}(TOD(LOC){0,5}(FTX){0,5}){0,5}(TDT(PCD){0,6}(TMD){0,1}(LOC(DTM){0,10}){0,10}){0,10}(EQD(MEA){0,5}(SEL){0,25}(EQA){0,5}(HAN(FTX){0,10}){0,10}){0,10}(CPS(FTX){0,5}(QVR){0,9}(PAC(MEA){0,10}(QTY){0,10}(HAN(FTX){0,10}(PCI(RFF){0,1}(DTM){0,5}(GIR(DTM){0,5}){0,99}(GIN(DLM){0,10}){0,99}(COD(MEA){0,9}(QTY){0,9}(PCD){0,9}){0,99}){0,1000}){0,10}){0,9999}(LIN(PIA){0,10}(IMD){0,25}(MEA){0,10}(QTY){0,10}(ALI){0,10}(GIN){0,100}(GIR){0,100}(DLM){0,100}(DTM){0,5}(NAD){0,99}(TDT){0,1}(TMD){0,1}(HAN){0,20}(FTX){0,99}(MOA){0,5}){0,9999}){0,9999}(CNT)(UNT)");

		String s = "UNHBGMDTMDTMDTMALIALICUXCUXCUXRFFRFFDTMNADNADLOCLOCTODTDTCPSLINPIAPIAIMDQTYDTMLINPIAPIAIMDQTYDTMCNTUNT";

		Matcher matcher = pattern.matcher(s);
		if (matcher.matches()) {

			System.out.println("sg1=");
			System.out.println(matcher.group("sg1"));
			System.out.println(matcher.group("block"));
			System.out.println(matcher.group("block"));

		}
	}
}
