import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Dock {
    private Ship ship;


    public Dock() {
        this.ship = null;
    }

    public  boolean isAvailable() {
        return ship == null;
    }

    public synchronized void assignShip(Ship ship) {
        this.ship = ship;
    }

    public  void releaseShip() {
        this.ship = null;
    }


}
