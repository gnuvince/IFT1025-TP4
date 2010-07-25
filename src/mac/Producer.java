package mac;

import java.util.ArrayList;
import java.util.Random;

/**
 * Classe dérivée de thread qui représente la partie producteur d'un noeud.
 * Le producteur génère des messages et les ajoute dans la liste de messages à envoyer.
 * Le producteur attend une période de temps aléatoire, fonction du paramètre
 * lambda entre chaque message généré.
 */
public class Producer extends Thread {
	// lambda par défaut
    private static final double DEFAULT_LAMBDA = 1000;
    
    // lambda paramétrisé
    public static double lambda = DEFAULT_LAMBDA;
    
    // la liste où les messages générés sont stockés
    private ArrayList<Message> buffer;
    
    // générateur de nombre aléatoire utilisé pour les temps d'attente
    private Random r = new Random();

    
    public Producer(ArrayList<Message> buffer) {
        this.buffer = buffer;
    }

    /**
     * Méthode générant aléatoirement la période de temps d'attente
     * @return la période de temps (ms)
     */
    private double getDelay() {
        return -Math.log(r.nextDouble()) * lambda;
    }

    /**
     * Méthode qui crée un objet Message et l'ajoute au buffer
     * @return vrai si un message a été créé, faux sinon (buffer plein)
     */
    private boolean produce() {
    	
    	if (buffer.size() == 10)
    		return false;
    	else {
    		buffer.add(new Message());
    		return true;
    	}
    }
    
    public String toString() {
        synchronized (buffer) {
            return buffer.toString();
        }
    }
    
    /**
     * Méthode principale de création de messages
     */
    public void run() {
    	try {
    		while (true) {
    			synchronized (buffer) {
    				if (!produce()) {
    					buffer.wait();
    				}
    				buffer.notifyAll();    					
    			}
        		Thread.sleep((int)getDelay());
    		}
    	}
    	catch (InterruptedException e) {
    	}
    }
}
