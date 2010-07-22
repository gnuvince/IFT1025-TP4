/**
 * 
 */
package mac;

import java.util.ArrayList;

/**
 * @author foleybov
 * Note: System.nanoTime() -> long
 */
public class Channel {
    private ArrayList<Message> receivedMessages;
    private int messageLimit;
    private boolean occupied;

    public ArrayList<Message> getReceivedMessages() {
        return receivedMessages;
    }
    
    public void send(Message m) {
    	occupied = true;
    	
    	receivedMessages.add(m);
    	
    	occupied = false;
    }

    public boolean isOccupied() {
    	return occupied;
    }
}
