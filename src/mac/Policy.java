package mac;

/**
 * Interface decrivant les méthodes communes à toutes les politiques
 * d'attente en cas de collision.
 */
public interface Policy {
    
    int getNewWaitTime(int currentWaitTime);
    int getInitialWaitTime();
}
