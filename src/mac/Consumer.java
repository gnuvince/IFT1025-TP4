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
    private static final Policy DEFAULT_POLICY = new DoubleWaitDeltaPolicy();
    public static Policy policy = DEFAULT_POLICY;

    private ArrayList<Message> buffer;
    private Channel channel;
    
    public Consumer (Channel channel, ArrayList<Message> buffer) {
        this.channel = channel;
        this.buffer = buffer;
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
    		synchronized (buffer) {
    			while (true) {
    				int sleepTime = policy.getInitialWaitTime();
    				Message m = consume();
    				// Tant qu'on a pas reussi a envoyer notre message sur le channel
    				while (!channel.send(m)) {
    					// On prend note de notre echec lamentable
    					m.incrementRejections();
    					
    					// On calcule combien de temps passer dans le coin
    					sleepTime = policy.getNewWaitTime(sleepTime);
    					
    					// On attend un petit peu avant de recommencer
    					Thread.sleep(sleepTime);
    				}
    				m.setAccepted(System.currentTimeMillis());
    				buffer.notifyAll();
    			}
    		}
    	}
    	catch (InterruptedException e) {
    	}
    }
}
