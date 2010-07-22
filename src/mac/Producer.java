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
        Random r = new Random();
        return -Math.log(r.nextDouble()) * lambda;
    }
    
    private void produce() {
        buffer.add(new Message());
    }
    
    public String toString() {
        synchronized (buffer) {
            return buffer.toString();
        }
    }
    
    public void run() {
        try {
            while (true) {
                synchronized (buffer) {
                    while (buffer.size() == 10)
                        buffer.wait();
                    Thread.sleep((int)getDelay());
                    produce();
                    buffer.notifyAll();
                }
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
