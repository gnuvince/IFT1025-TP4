package mac;

/**
 * Politique qui attend selon les nombres de la suite de fibonacci.  Étant
 * donné un temps d'attente, la politique trouve le fibonnaci suivant et
 * retourne ce nouveau délai d'attente. 
 */
public class FibonacciPolicy implements Policy {

	@Override
	public int getInitialWaitTime() {
        return nextFib(Channel.WRITE_TIME);
	}

	@Override
	public int getNewWaitTime(int currentWaitTime) {
		return nextFib(currentWaitTime);
	}

	private int nextFib(int currentWaitTime) {
		int a = 0;
		int b = 1;
		
		while (a <= currentWaitTime) {
			int t = a;
			a = b;
			b = t + b;
		}
		
		return a;
	}
}
