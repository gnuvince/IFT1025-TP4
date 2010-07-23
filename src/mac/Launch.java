package mac;

import java.util.ArrayList;

public class Launch {
	
	private static final Policy[] POLICIES = {
		new DoubleWaitPolicy(), 
		new DoubleWaitDeltaPolicy(),
		new FibonacciPolicy(),
	};
	
	
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
	
	private static Policy parsePolicy(String s) {
		try {
			return POLICIES[Integer.parseInt(s)];
		}
		catch (Exception e) {
			usage();
		}
		
		return null; // unreachable!
	}
	   
    public static void main(String[] args) {
    	int lambda = 1000;
    	int n = 2;
    	Policy policy = Consumer.DEFAULT_POLICY;
    	
    	if (args.length < 2)
    		usage();
    	
    	try {
    		lambda = parseArg(args[0]);
    		n = parseArg(args[1]);
    	}
    	catch (IllegalArgumentException e) {
    		usage();
    	}
    	
    	if (args.length == 3)
    		policy = parsePolicy(args[2]);
    	Consumer.policy = policy;
    		
    	Producer.lambda = lambda;
    	
    	Channel ch = new Channel();
    	ArrayList<Node> nodes = new ArrayList<Node>();
    	
    	for (int i = 0; i < n; ++i) {
    		Node node = new Node(ch);
    		nodes.add(node);
    		node.start();
    	}
    	
    	try {
    		while (ch.getReceivedMessages().size() < Channel.messageLimit) {
    			Thread.sleep(20);
    			//System.out.println("msg count: " + ch.getReceivedMessages().size());
    			System.out.print("\r" + TextProgressBar.getProgressBar(
    					ch.getReceivedMessages().size() / (double)Channel.messageLimit));
    		}
    		
    		System.out.println();
    		
    		for (Node node: nodes)
    			node.join();
    		
    		System.out.println(Statistics.process(ch));
    	}
    	catch (InterruptedException e) {
    	}
    }
}
