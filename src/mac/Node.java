package mac;

import java.util.ArrayList;

/**
 * Classe qui simule un noeud qui transmet, sur channel, des messages tirés
 * de buffer. Utilise des objets Producer et Consumer.
 *
 */
public class Node extends Thread {
	// Taille du buffer
    private static final int BUFFER_LIMIT = 10;
    
    // L'objet producteur de messages
    private Producer producer;
    
    // L'objet consommateur de messages
    private Consumer consumer;
    
    // La liste où sont stockés les messages à envoyer
    private ArrayList<Message> buffer;
    
    // L'objet simulant le protocole CSMA/CD
    private Channel channel;
    
    public Node(Channel channel) {
        this.channel = channel;
        this.buffer = new ArrayList<Message>(BUFFER_LIMIT);
        this.producer = new Producer(buffer);
        this.consumer = new Consumer(channel, buffer);
    }
    
    /**
     * Classe principale. Démarre le producteur et le consommateur;
     * Vérifie périodiquement si le nombre maximal de message a été
     * envoyé et envoie un interrupt au producteur et au consommateur quand
     * c'est le cas.
     */
    @Override
    public void run() {
    	producer.start();
    	consumer.start();
    	
    	try {
    		while (channel.getReceivedMessages().size() < Channel.messageLimit) {
    			Thread.sleep(100);
    		}
    	}
    	catch (InterruptedException e) {
    		System.err.println(e);
    	}
    	
    	producer.interrupt();
    	consumer.interrupt();
    }
}
