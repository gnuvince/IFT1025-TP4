/**
 * 
 */
package mac;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author foleybov
 *
 */
public class Producer extends Thread {
    private static final double DEFAULT_LAMBDA = 1000;
    public static double lambda = DEFAULT_LAMBDA;
    private ArrayList<Message> buffer;

    public Producer(ArrayList<Message> buffer) {
        this.buffer = buffer;
    }

    private double getDelay() {
//        Random r = new Random();
//        return -Math.log(r.nextDouble()) * lambda;
    	return Math.random() * lambda;
    }
    
    private void produce() throws InterruptedException {
    	synchronized (buffer) {
    		while (buffer.size() == 10)
    			buffer.wait();
    		Thread.sleep((int)getDelay());
    		buffer.add(new Message());
    		buffer.notifyAll();
    	}
    }
    
    public String toString() {
        synchronized (buffer) {
            return buffer.toString();
        }
    }
    
    public void run() {
    	try {
    		while (true) {
    			produce();
    		}
    	}
    	catch (InterruptedException e) {
    	}
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            Producer p = new Producer(new ArrayList<Message>());
            p.start();
            while (true) {
                System.out.println(p);
                Thread.sleep(100);
            }
        }
        catch (InterruptedException e) {
            
        }
    }

}
