package mac;

import java.util.ArrayList;


/**
 * Classe pour lancer une simulation.
 */
public class Launch {
    // Un tableau des différentes politiques d'attente que l'utilisateur
    // peut choisir.
	private static final Policy[] POLICIES = {
		new DoubleWaitPolicy(), 
		new DoubleWaitDeltaPolicy(),
		new FibonacciPolicy(),
	};
	
	
	/**
	 * Affiche un message expliquant à l'utilisateur les différents paramètres
	 * de l'application et quitte le programme.
	 */
	private static void usage() {
		System.out.println("java mac.Launch <lambda> <n> [<politique>]");
		System.out.println("lambda > 0");
		System.out.println("n > 0");
		System.out.println("policy:");
		for (int i = 0; i < POLICIES.length; ++i) {
			System.out.print("    " + i + ": "+ POLICIES[i].getClass().getName());
			if (POLICIES[i].getClass().equals(Consumer.DEFAULT_POLICY.getClass()))
				System.out.print(" (option par defaut)");
			System.out.println();
		}
		System.exit(0);
	}

	/**
	 * Méthode pour "parser" un argument numérique.  Si s ne représente pas un
	 * entier, ou s'il représente un entier non-positif, la méthode lancera
	 * une exception IllegalArgumentException.
	 * @param s l'argument numérique en String
	 * @return s en int.
	 */
	private static int parseArg(String s) {
		try {
			int n = Integer.parseInt(s);
			if (n <= 0)
				throw new IllegalArgumentException();
			else
				return n;
		}
		catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
	}
	
	
	/**
	 * Retourne une politique selon son index dans le tableau POLICIES
	 * @param s l'index en String
	 * @return
	 */
	private static Policy parsePolicy(String s) {
		try {
			return POLICIES[Integer.parseInt(s)];
		}
		// Si s est non-entier ou un indice invalide de POLICIES, afficher
		// comment utiliser le programme.
		catch (Exception e) {
			usage();
		}
		
		return null; // unreachable!
	}
	   
	/**
	 * Méthode principale
	 */
    public static void main(String[] args) {
        // Des valeurs par défaut sont données, car sinon le
        // compilateur averti que les variables pourraient ne
        // pas être déclarées.
    	int lambda = 1000;
    	int n = 2;
    	Policy policy = Consumer.DEFAULT_POLICY;
    	
    	// Si l'utilisateur n'entre pas lambda et n, afficher l'utilisation.
    	if (args.length < 2)
    		usage();
    	
    	// Si l'utilisateur a entré lambda et n, mais que leurs valides sont
    	// invalides, afficher l'utilisation.
    	try {
    		lambda = parseArg(args[0]);
    		n = parseArg(args[1]);
    	}
    	catch (IllegalArgumentException e) {
    		usage();
    	}
    	
    	// Si un troisième argument a été fourni, obtenir la politique associée
    	if (args.length == 3)
    		policy = parsePolicy(args[2]);
    	
    	// Assigner le lambda et la politique du producteur et consommateur
    	Producer.lambda = lambda;
    	Consumer.policy = policy;
    	
    	// Créer le canal de communiation sur lequel communiqueront les noeuds.
    	Channel ch = new Channel();
    	
    	// Créer une liste qui contiendra les noeuds
    	ArrayList<Node> nodes = new ArrayList<Node>();
    	
    	
    	// Créer le nombre de noeuds demandé par l'utilisateur et les ajouter
    	// à la liste.
    	for (int i = 0; i < n; ++i) {
    		Node node = new Node(ch);
    		nodes.add(node);
    		node.start();
    	}
    	
    	try {
    	    // Recevoir des messages jusqu'à ce qu'il y en aie eu Chanel.messageLimit
    		while (ch.getReceivedMessages().size() < Channel.messageLimit) {
    			Thread.sleep(20);
    			
    			// Afficher une barre de progrès.
    			System.out.print("\r" + TextProgressBar.getProgressBar(
    					ch.getReceivedMessages().size() / (double)Channel.messageLimit));
    		}
    		
    		System.out.println();
    		
    		// Attendre que chaque Node aie terminé de travailler.
    		for (Node node: nodes)
    			node.join();
    		
    		
    		// Afficher les statistiques.
    		System.out.println(Statistics.process(ch));
    	}
    	catch (InterruptedException e) {
    	}
    }
}
