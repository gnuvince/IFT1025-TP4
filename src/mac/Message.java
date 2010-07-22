/**
 * 
 */
package mac;

/**
 * @author foleybov
 *
 */
public class Message {
    private long creation;
    private long accepted;
    private int rejections;
    
    
    
    public Message() {
        this.creation = System.nanoTime();
        this.accepted = 0;
        this.rejections = 0;
    }
    
    public long getCreation() {
        return creation;
    }
    public void setCreation(long creation) {
        this.creation = creation;
    }
    public long getAccepted() {
        return accepted;
    }
    public void setAccepted(long accepted) {
        this.accepted = accepted;
    }
    public int getRejections() {
        return rejections;
    }
    public void setRejections(int rejections) {
        this.rejections = rejections;
    }
    
    public void incrementRejections() {
        this.rejections++;
    }
}
