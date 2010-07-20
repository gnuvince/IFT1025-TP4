/**
 * 
 */
package mac;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author foleybov
 *
 */
public class Producer extends Thread {
    private static final double DEFAULT_LAMBDA = 1000;
    public static double lambda = DEFAULT_LAMBDA;
    private LinkedBlockingQueue<Message> buffer;

    public Producer(LinkedBlockingQueue<Message> buffer) {
        this.buffer = buffer;
    }

    private double getDelay() {
        Random r = new Random();
        
        return -Math.log(r.nextDouble()) * lambda;
    }
    
    private void produce() {}
    
    public void run() {}

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
