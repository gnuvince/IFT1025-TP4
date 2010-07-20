/**
 * 
 */
package mac;

/**
 * @author foleybov
 *
 */
public class DoubleWaitTime implements Policy {

    /* (non-Javadoc)
     * @see mac.Policy#getNewWaitTime(int)
     */
    @Override
    public int getNewWaitTime(int currentWaitTime) {
        return 2 * currentWaitTime;
    }

}
