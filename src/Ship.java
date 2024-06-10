public class Ship implements Runnable {
    private int maxContainers;
    private int id;
    private int container;
    boolean isLoad;
    boolean running;
    Thread thread;
    private int currentContainers;
    private Port port;

    public Ship(int id, Port port, int container, boolean isLoad, int maxContainers) {
        this.id = id;
        this.port = port;
        this.isLoad = isLoad;
        this.maxContainers = maxContainers;
        if (container <= this.maxContainers) {
            this.container = container;
        }
        if (isLoad) {
            currentContainers = container;
        }
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public int getId() {
        return id;
    }

    public int getCurrentContainers() {
        return currentContainers;
    }

    public int getContainer() {
        return container;
    }

    public void setContainers(int container) {
        try {
            if (container <= maxContainers
                    && currentContainers >= 0 && isLoad) {
                this.container = container;
                currentContainers = container;
            } else if (container + currentContainers <= maxContainers && container >= 0 && !isLoad) {
                if (container == 0) {
                    currentContainers += this.container;
                    this.container = container;
                } else {
                    currentContainers += container;
                    this.container -= container;
                }

            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            System.err.println("Нехватка места!");
        }
    }

    @Override
    public void run() {
        try {
            port.getSemaphore().acquire();
            Dock dock = port.getAvailableDock();
            if (dock != null) {
                dock.assignShip(this);
                System.out.println(id + " is docked at Port " + dock);
                port.getLock().lock();
                if (isLoad) {
                    port.loadContainers(this);
                } else {
                    port.unloadContainers(this);
                }
                dock.releaseShip();
                System.out.println(id + " has left the Dock");
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            port.getLock().unlock();
port.getSemaphore().release();
        }
    }

}
