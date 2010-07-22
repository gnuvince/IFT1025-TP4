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

    private ArrayList<Message> buffer;
    private Channel channel;
    
    
    public Consumer (Channel channel, ArrayList<Message> buffer) {
        this.channel = channel;
        this.buffer = buffer;
    }
    
    private Message consume() { 
    	Message m = null;
    	
    	try {
    		synchronized (buffer) {
    			while (buffer.size() == 0) {
    				buffer.wait();
    			}
    			m = buffer.remove(0);
    			buffer.notifyAll();
    		}
    	} catch (InterruptedException e) { System.err.println(e); }
    	
    	return m;
    	
    }
    
    public void run() {
    	while (true) {
    		Message m = consume();
    	}
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
    	ArrayList<Message> list = new ArrayList<Message>();
    	Consumer c = new Consumer(null, list);
    	Producer p = new Producer(list);
    	Producer.lambda = 200.0;
    	c.start();
    	p.start();
    }

}
