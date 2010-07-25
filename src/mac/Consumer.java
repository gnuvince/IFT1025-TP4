package mac;

import java.util.ArrayList;

/**
 * Classe dérivée de thread qui représente la partie consommateur d'un noeud.
 * Le consommateur prend un message dans la liste de messages à envoyer
 * et tente de faire son envoi sur le canal. 
 * 
 * @author foleybov
 */
public class Consumer extends Thread {
    // La politique d'attente par défaut
    public static final Policy DEFAULT_POLICY = new DoubleWaitPolicy();
    
    // La politique d'attente
    public static Policy policy = DEFAULT_POLICY;

    // La liste de message à envoyer sur le canal.
    private ArrayList<Message> buffer;
    
    // Le canal sur lequel envoyer les messages
    private Channel channel;
    
    
    public Consumer (Channel channel, ArrayList<Message> buffer) {
        this.channel = channel;
        this.buffer = buffer;
    }
    
    
    /**
     * Méthode qui prend un message dans la liste de messages
     * @return le prochain message à envoyer
     * @throws InterruptedException quand le thread est interrompu avant
     * d'avoir obtenu un message.
     */
    private Message consume() throws InterruptedException { 
    	Message m = null;
    	
    	synchronized (buffer) {
    		while (buffer.size() == 0) {
//    			System.out.println("buffer vide!");
    			buffer.wait();
    		}
    		m = buffer.remove(0);
    		buffer.notifyAll();
    	}
    	
    	return m;
    	
    }
    
    
    /**
     * La méthode run() tente d'acquérir un verrou sur la liste de messages pour
     * en consommer un et tente par la suite de l'envoyer sur le canal.  Lors
     * d'un rejet dû à une collision, le consommateur va consulter la politique
     * pour obtenir un nouveau temps d'attente et dormira pendant ce délai
     * avant de réessayer d'envoyer le message.
     */
    @Override
    public void run() {
    	int sleepTime;
    	
    	try {
    		synchronized (buffer) {
    			while (true) {
    				sleepTime = policy.getInitialWaitTime();
    				Message m = consume();
    				// Tant qu'on a pas reussi a envoyer notre message sur le channel
    				while (!channel.send(m)) {
    					// On prend note de notre echec lamentable
    					m.incrementRejections();
    					
    					// On calcule combien de temps passer dans le coin
    					sleepTime = policy.getNewWaitTime(sleepTime);
    					
    					// On attend un petit peu avant de recommencer
    					Thread.sleep(sleepTime);
    				}
    				m.setAccepted(System.currentTimeMillis());
//    				buffer.notifyAll();
    			}
    		}
    	}
    	catch (InterruptedException e) {
    	}
    }
}
