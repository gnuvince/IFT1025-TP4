package mac;

/**
 * Politique qui double le temps d'attente précédent.
 */
public class DoubleWaitPolicy implements Policy {
    @Override
    public int getNewWaitTime(int currentWaitTime) {
    	return 2 * currentWaitTime;
    }

    public int getInitialWaitTime() {
        return Channel.WRITE_TIME;
    }
}
