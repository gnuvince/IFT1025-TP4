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
        Formatter format = new Formatter();
        StringBuilder sb = new StringBuilder();
        
        sb.append(format.format("Temps d'attente moyen : %8d ms\n", stats.averageWaitTime()));
        sb.append(format.format("Nombre de rejets moyen: %8d\n", stats.averageRejections()));
        
        return sb.toString();
    }
}
