/**
 * 
 */
package mac;

import java.util.ArrayList;


/**
 * @author foleybov
 *
 */
public class Consumer extends Thread {
    private static final Policy DEFAULT_POLICY = new DoubleWaitTime();
    public static Policy policy = DEFAULT_POLICY;

    private int id;
    private ArrayList<Message> buffer;
    private Channel channel;
    private int waitTime;
    
    public Consumer (Channel channel, ArrayList<Message> buffer, int id) {
    	this.id = id;
        this.channel = channel;
        this.buffer = buffer;
        this.waitTime = 55;
    }
    
    private Message consume() throws InterruptedException { 
    	Message m = null;
    	
    	synchronized (buffer) {
    		while (buffer.size() == 0) {
    			buffer.wait();
    		}
    		m = buffer.remove(0);
    		buffer.notifyAll();
    	}
    	
    	return m;
    	
    }
    
    public void run() {
    	try {
    		synchronized (channel) {
    			while (true) {
    				Message m = consume();
    				System.out.println("ID: " + id);
    				while (channel.isOccupied()) {
    					m.incrementRejections();
    					waitTime = policy.getNewWaitTime(waitTime);
    					System.out.println("  >>  " + waitTime);
    					channel.wait(waitTime);
    				}
    				m.setAccepted(System.currentTimeMillis());
    				channel.send(m);
    				channel.notifyAll();
    			}
    		}
    	}
    	catch (InterruptedException e) {
    	}
    }
}
