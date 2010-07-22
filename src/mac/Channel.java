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
    
    public void send(Message m) throws InterruptedException {
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
    	ArrayList<Node> nodes = new ArrayList<Node>();
    	
    	for (int i = 0; i < 10; ++i) {
    		Node n = new Node(ch, i);
    		nodes.add(n);
    		n.start();
    	}
    	
    	try {
    		while (ch.receivedMessages.size() < messageLimit) {
    			Thread.sleep(20);
    			System.out.println("msg count: " + ch.receivedMessages.size());
    		}
    		
    		for (Node n: nodes)
    			n.join();
    		
    		System.out.println(Statistics.process(ch));
    	}
    	catch (InterruptedException e) {
    	}
    }
}
