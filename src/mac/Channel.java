/**
 * 
 */
package mac;

import java.util.ArrayList;

/**
 * @author foleybov
 */
public class Channel {
	public static int messageLimit = 100;

	private ArrayList<Message> receivedMessages;
    private boolean occupied;
    
    public Channel() {
    	receivedMessages = new ArrayList<Message>();
    	messageLimit = 100;
    	occupied = false;
    }

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
    
    public static void main(String[] args) {
    	Producer.lambda = 200.0;
    	Channel ch = new Channel();
    	Node n = new Node(ch);
    	n.start();
    	
    	try {
    		while (ch.receivedMessages.size() < messageLimit) {
    			Thread.sleep(20);
    			System.out.println(ch.receivedMessages.size());
    		}
    		
    		System.out.println(Statistics.process(ch));
    	}
    	catch (InterruptedException e) {
    	}
    }
}
