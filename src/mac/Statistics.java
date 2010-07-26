package mac;

import java.util.ArrayList;
import java.util.Arrays;
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
            totalWaitTime += m.waitTime();
        }
        
        return totalWaitTime / messageList.size();
    }
    
    private long minWaitTime() {
        long min = Integer.MAX_VALUE;
        
        for (Message m: messageList) {
            if (m.waitTime() < min)
                min = m.waitTime();
        }
        
        return min;
    }
    
    
    private long maxWaitTime() {
        long max = Integer.MIN_VALUE;
        
        for (Message m: messageList) {
            if (m.waitTime() > max)
                max = m.waitTime();
        }
        
        return max;
    }
    
    
    private long medianWaitTime() {
        long[] waitTimes = new long[messageList.size()];
        for (int i = 0; i < waitTimes.length; ++i)
            waitTimes[i] = messageList.get(i).waitTime();
        
        Arrays.sort(waitTimes);
        int mid = waitTimes.length / 2;
        if (waitTimes.length % 2 == 0)
            return (waitTimes[mid] + waitTimes[mid + 1]) / 2;
        else
            return waitTimes[mid];
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
    
    
    private int medianRejections() {
        int[] rejections = new int[messageList.size()];
        for (int i = 0; i < rejections.length; ++i)
            rejections[i] = messageList.get(i).getRejections();
        
        Arrays.sort(rejections);
        int mid = rejections.length / 2;
        if (rejections.length % 2 == 0)
            return (rejections[mid] + rejections[mid + 1]) / 2;
        else
            return rejections[mid];
    }

    /**
     * Calcule et retourne les statistiques
     * @return une chaîne contenant les statistiques
     */
    public static String process(Channel c) { 
        Statistics stats = new Statistics(c.getReceivedMessages());
        StringBuilder sb = new StringBuilder();
        Formatter format = new Formatter(sb);
        
        format.format("Temps d'attente minimal : %d ms\n", stats.minWaitTime());
        format.format("Temps d'attente moyen   : %.4f ms\n", stats.averageWaitTime());
        format.format("Temps d'attente médian  : %d ms\n", stats.medianWaitTime());
        format.format("Temps d'attente maximal : %d ms\n", stats.maxWaitTime());
        format.format("Nombre de rejets minimal: %d\n", stats.minRejections());
        format.format("Nombre de rejets moyen  : %.4f\n", stats.averageRejections());
        format.format("Nombre de rejets médian : %d\n", stats.medianRejections());
        format.format("Nombre de rejets maximal: %d\n", stats.maxRejections());
        
        return sb.toString();
    }
}
