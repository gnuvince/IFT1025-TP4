/**
 * 
 */
package mac;

import java.util.ArrayList;
import java.util.Formatter;

/**
 * @author foleybov
 *
 */
public class Statistics {
    private ArrayList<Message> messageList;
    
    public Statistics(ArrayList<Message> list) {
        messageList = list;
    }
    
    private double averageWaitTime() {
        double totalWaitTime = 0.0;
        
        for (Message m: messageList) {
            totalWaitTime += (m.getAccepted() - m.getCreation());
        }
        
        return totalWaitTime / messageList.size();
    }
    
    private double averageRejections() {
        double totalRejections = 0.0;
        
        for (Message m: messageList) {
            totalRejections += m.getRejections();
        }
        
        return totalRejections / messageList.size();
    }
    
    private int maxRejections() {
    	int max = Integer.MIN_VALUE;
    	
    	for (Message m: messageList) {
    		if (m.getRejections() > max)
    			max = m.getRejections();
    	}
    	
    	return max;
    }
    
    private int minRejections() {
    	int min = Integer.MAX_VALUE;
    	
    	for (Message m: messageList) {
    		if (m.getRejections() < min)
    			min = m.getRejections();
    	}
    	
    	return min;
    }
    
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
