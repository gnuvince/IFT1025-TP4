package mac;

public class FibonacciPolicy implements Policy {

	@Override
	public int getInitialWaitTime() {
		return 1;
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
