/**
 * 
 */
package mac;

/**
 * @author foleybov
 *
 */
public class DoubleWaitPolicy implements Policy {
    @Override
    public int getNewWaitTime(int currentWaitTime) {
        return 2 * currentWaitTime;
    }

    public int getInitialWaitTime() {
    	return 1;
    }
}
