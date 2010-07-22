/**
 * 
 */
package mac;

import java.util.ArrayList;

/**
 * @author foleybov
 *
 */
public class Node extends Thread {
    private static final int BUFFER_LIMIT = 10;
    private static Policy policy;
    
    private Producer producer;
    private Consumer consumer;
    private ArrayList<Message> buffer;
    private Channel channel;
    
    public Node(Channel channel, int id) {
        this.channel = channel;
        this.buffer = new ArrayList<Message>(BUFFER_LIMIT);
        this.producer = new Producer(buffer);
        this.consumer = new Consumer(channel, buffer, id);
    }
    
    @Override
    public void run() {
    	producer.start();
    	consumer.start();
    	
    	try {
    		while (channel.getReceivedMessages().size() < Channel.messageLimit) {
    			Thread.sleep(100);
    		}
    	}
    	catch (InterruptedException e) {
    		System.err.println(e);
    	}
    	
    	producer.interrupt();
    	consumer.interrupt();
    }
}
