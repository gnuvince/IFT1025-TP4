package mac;

/**
 * Classe utilitaire pour afficher une ProgressBar textuelle
 * 
 * @author foleybov
 */
public class TextProgressBar {
	public static String getProgressBar(double percentage) {
		int length = 50;
		
		StringBuilder sb = new StringBuilder();
		sb.append('|');

		int current = (int)(length * percentage);
		for (int i = 0; i < current; ++i) sb.append('=');
		for (int i = 0; i < length - current; ++i) sb.append(' ');
		
		sb.append('|');
		sb.append(String.format(" %.1f%% ", percentage * 100));
		
		return sb.toString();
	}
}
