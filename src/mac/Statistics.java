package mac;

import java.util.ArrayList;
import java.util.Formatter;

/**
 * Classe produisant le rapport des statistiques.
 * 
 * @author foleybov
 */
public class Statistics {
	// La liste des messages envoyés + timestamps
    private ArrayList<Message> messageList;
    
    /**
     * Constructeur
	 * @param list la liste des messages envoyés + timestamps
     */
    public Statistics(ArrayList<Message> list) {
        messageList = list;
    }
    
    /**
     * Calcul du temps moyen d'attente par message
     * @return le temps moyen d'attente pour des messages
     */
    private double averageWaitTime() {
        double totalWaitTime = 0.0;
        
        for (Message m: messageList) {
            totalWaitTime += (m.getAccepted() - m.getCreation());
        }
        
        return totalWaitTime / messageList.size();
    }
    
    /**
     * Nombre moyen de collision par message
     * @return le nombre moyen de collision par message
     */
    private double averageRejections() {
        double totalRejections = 0.0;
        
        for (Message m: messageList) {
            totalRejections += m.getRejections();
        }
        
        return totalRejections / messageList.size();
    }

    /**
     * Nombre maximal de collision pour un message
     * @return le nombre maximal de collision pour un message
     */
    private int maxRejections() {
    	int max = Integer.MIN_VALUE;
    	
    	for (Message m: messageList) {
    		if (m.getRejections() > max)
    			max = m.getRejections();
    	}
    	
    	return max;
    }

    /**
     * Nombre minimal de collision pour un message
     * @return le nombre minimal de collision pour un message
     */
    private int minRejections() {
    	int min = Integer.MAX_VALUE;
    	
    	for (Message m: messageList) {
    		if (m.getRejections() < min)
    			min = m.getRejections();
    	}
    	
    	return min;
    }

    /**
     * Calcule et retourne les statistiques
     * @return une chaîne contenant les statistiques
     */
    public static String process(Channel c) { 
        Statistics stats = new Statistics(c.getReceivedMessages());
        StringBuilder sb = new StringBuilder();
        Formatter format = new Formatter(sb);
        
        format.format("Temps d'attente moyen   : %.4f ms\n", stats.averageWaitTime());
        format.format("Nombre de rejets minimal: %d\n", stats.minRejections());
        format.format("Nombre de rejets moyen  : %.4f\n", stats.averageRejections());
        format.format("Nombre de rejets maximal: %d\n", stats.maxRejections());
        
        return sb.toString();
    }
}
