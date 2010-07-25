package mac;

/**
 * Politique qui consiste à doubler le temps d'attente précédent et d'y ajouter
 * +/- <variation>%. 
 */
public class DoubleWaitDeltaPolicy implements Policy {
	double variation = 0.75;
	
	@Override
    public int getNewWaitTime(int currentWaitTime) {
		int newTime = 2 * currentWaitTime;
		double deltaMax = newTime * variation;
		double deltaPercentage = Math.random();
		double delta = (2 * deltaMax * deltaPercentage) - deltaMax;
		return (int)(newTime + delta);
		
    }

    public int getInitialWaitTime() {
    	return Channel.WRITE_TIME;
    }
}
