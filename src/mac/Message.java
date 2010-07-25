package mac;

/**
 * Classe représentant un message a être envoyé.
 * Contient les temps de création et d'acceptation (transmission)
 * et le nombre de collisions.
 *
 */
public class Message {
	// Temps de la création du message
    private long creation;
    
    // Temps de la transmission du message
    private long accepted;
    
    // Nombre de collisions avant de réussir à envoyer le message
    private int rejections;
        
    public Message() {
        this.creation = System.currentTimeMillis();
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
