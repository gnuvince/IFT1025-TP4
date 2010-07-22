/**
 * 
 */
package mac;

/**
 * @author foleybov
 *
 */
public interface Policy {
    
    int getNewWaitTime(int currentWaitTime);
    int getInitialWaitTime();
}
