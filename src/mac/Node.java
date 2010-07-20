/**
 * 
 */
package mac;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author foleybov
 *
 */
public class Node {
    private static final int BUFFER_LIMIT = 10;
    private static Policy policy;
    
    private Producer producer;
    private Consumer consumer;
    private LinkedBlockingQueue<Message> buffer;
    private Channel channel;
    
    public Node(Channel channel) {
        this.channel = channel;
        this.buffer = new LinkedBlockingQueue<Message>(BUFFER_LIMIT);
    }
}
