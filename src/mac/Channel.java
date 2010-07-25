package mac;

import java.util.ArrayList;

/**
 * La classe qui représente l'objet canal sur lequel les messages doivent
 * êtres envoyés.
 * 
 * @author foleybov
 */
public class Channel {
    // Le temps (en millisecondes) requis pour écrire un message
    public static final int WRITE_TIME = 100;
    
    // Le nombre de messages à recevoir avant de quitter
	public static int messageLimit = 1000;

	// La liste des messages reçus (afin de compiler des statistiques)
	private ArrayList<Message> receivedMessages;
	
	// Variables qui déterminent si le canal est occupé et si 
	// un collision s'est produite.
    private boolean occupied;
    private boolean collisionDetected;
    
    
    public Channel() {
    	receivedMessages = new ArrayList<Message>();
    	occupied = false;
    	collisionDetected = false;
    }

    
    public ArrayList<Message> getReceivedMessages() {
        return receivedMessages;
    }
    
    
    /**
     * Méthode qui sert à envoyer un message sur le canal
     * @param m le message à envoyer
     * @return si le message a été envoyé avec succès ou non.
     * @throws InterruptedException si le thread est interrompu durant l'envoi.
     */
    public boolean send(Message m) throws InterruptedException {
    	synchronized (this) {
    		// Oh non! Quelqu'un utilise le channel en meme temps que moi!
    		if (occupied) {
//    			System.out.println("Collision!");
    			// On va indiquer qu'on a une collision
    			collisionDetected = true;
    			
    			// On va avertir le gars qui essaye d'ecrire qu'on est en collision
    			this.notifyAll();
    			
    			// Je retourne false parce que j'ai pas reussi a ecrire sur le channel
    			return false;
    		}
    		
    		// Yay! Personne sur le channel
    		else {
    			// Je vais indiquer que le channel est occupe
    			occupied = true;
    			
    			// Ca prend un certain temps ecrire sur le channel..
    			this.wait(WRITE_TIME);
    			
    			// Quelqu'un a voulu acceder au channel pendant que j'ecrivais :(
    			if (collisionDetected) {
    				// On sort du channel, donc il y a plus de collision
    				collisionDetected = false;
    				
    				// Je sors, donc le channel est plus occupe
    				occupied = false;
    				
    				// J'ai pas reussi a ecrire sur le channel
    				return false;    				
    			}
    			// Yay! J'ai fini d'ecrire!
    			else {
    				// On ajoute le message
    				receivedMessages.add(m);
    				
    				// Je sors, donc le channel est plus occupe
    				occupied = false;
    				
    				// Mission accomplie!
    				return true;
    			}
    		}
    	}
    }
}
