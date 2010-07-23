package mac;

public class TextProgressBar {
	public static String getProgressBar(double percentage) {
		int length = 50;
		String bar = "";
		String empty = "";
		
		for (int i = 0; i < length; ++i) {
			bar += "=";
			empty += " ";
		}
		
		
		StringBuilder sb = new StringBuilder();
		sb.append('|');
		sb.append(bar.substring(0, (int)(length * percentage)));
		sb.append(empty.substring(0, length - (int)(length * percentage)));
		sb.append('|');
		sb.append(String.format(" %.1f%% ", percentage * 100));
		
		return sb.toString();
	}
}
