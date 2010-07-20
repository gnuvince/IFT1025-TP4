/**
 * 
 */
package mac;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author foleybov
 *
 */
public class Consumer extends Thread {
    private static final Policy DEFAULT_POLICY = new DoubleWaitTime();
    public static Policy policy = DEFAULT_POLICY;

    private LinkedBlockingQueue<Message> buffer;
    private Channel channel;
    
    
    public Consumer (Channel channel, LinkedBlockingQueue<Message> buffer) {
        this.channel = channel;
        this.buffer = buffer;
    }
    
    private Message consume() { return new Message(); }
    
    public void run() {}


    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
