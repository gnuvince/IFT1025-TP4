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
    
    public static String process(Channel c) { 
        Statistics stats = new Statistics(c.getReceivedMessages());
        StringBuilder sb = new StringBuilder();
        Formatter format = new Formatter(sb);
        
        format.format("Temps d'attente moyen : %8.4f ms\n", stats.averageWaitTime());
        format.format("Nombre de rejets moyen: %8.4f\n", stats.averageRejections());
        
        return sb.toString();
    }
}
