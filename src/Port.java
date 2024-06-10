import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private int storageCapacity;
    private int currentContainers;
    private Lock lock;
    private Dock[] docks;
    private Semaphore semaphore;

    public Port(int numberOfDocks, int storageCapacity) {
        this.storageCapacity = storageCapacity;
        docks = new Dock[numberOfDocks];
        for (int i = 0; i < numberOfDocks; i++) {
            docks[i] = new Dock();
        }
        lock = new ReentrantLock();
        semaphore = new Semaphore(numberOfDocks);
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public Lock getLock() {
        return lock;
    }

    public Dock[] getDocks() {
        return docks;
    }

    public void loadContainers(Ship ship) {
        int tempContainer = ship.getContainer();
        if (currentContainers + tempContainer > storageCapacity) {
            int realContainerRemain = tempContainer - (storageCapacity - currentContainers);
            ship.setContainers(realContainerRemain);
            currentContainers = storageCapacity;
            System.out.println("Ship " + ship.getId()+ " uploaded "
                    + (tempContainer - realContainerRemain) + " containers. " +
                    "In stock: " + currentContainers + ", on the ship " + ship.getCurrentContainers());

        } else {
            currentContainers += tempContainer;
            ship.setContainers(0);

            System.out.println("Ship " + ship.getId() + " uploaded " + tempContainer + " containers. "
                    + "In stock: " + currentContainers + ", on the ship " + ship.getCurrentContainers());
        }

    }

    public void unloadContainers(Ship ship) {
        int tempContainers = ship.getContainer();
        if (currentContainers - tempContainers < 0) {

            ship.setContainers(currentContainers);
            tempContainers = currentContainers;
            currentContainers = 0;

            System.out.println("Ship " + ship.getId() + " unloaded "
                    +  tempContainers + " containers. " +
                    "In stock: " + currentContainers + ", on the ship " + ship.getCurrentContainers());
        } else {
            currentContainers -= tempContainers;
            ship.setContainers(0);

            System.out.println("Ship " + ship.getId() + " unloaded " + tempContainers
                    + " containers. " + "In stock: " + currentContainers
                    + ", on the ship " + ship.getCurrentContainers());
        }

    }

    public Dock getAvailableDock() {
        for (Dock dock : docks) {
            if (dock.isAvailable()) {
                return dock;
            }
        }
        return null;
    }
}
