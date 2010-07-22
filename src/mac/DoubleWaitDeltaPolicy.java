package mac;

public class DoubleWaitDeltaPolicy implements Policy {
	@Override
    public int getNewWaitTime(int currentWaitTime) {
		int newTime = 2 * currentWaitTime;
		double deltaMax = newTime * 0.1;
		double deltaPercentage = Math.random();
		double delta = (2 * deltaMax * deltaPercentage) - deltaMax;
		return (int)(newTime + delta);
		
    }

    public int getInitialWaitTime() {
    	return Channel.WRITE_TIME;
    }
}
