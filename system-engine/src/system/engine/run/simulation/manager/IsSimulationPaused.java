package system.engine.run.simulation.manager;

public class IsSimulationPaused {
    private boolean isPaused =true;

    public synchronized void pause() {
        while (isPaused) {
            try {
                this.wait();
            } catch (InterruptedException e) {}
        }
        isPaused = true;
    }

    public synchronized void resume() {
        isPaused = false;
        this.notifyAll();
    }
 /*   public synchronized void pause() {
        while (!isPaused) {
            try {
                this.wait();
            } catch (InterruptedException e) {}
        }
        isPaused = false;
        this.notifyAll();
    }

    public synchronized void resume() {
        while (isPaused) {
            try {
                this.wait();
            } catch (InterruptedException e) {}
        }
        isPaused = true;
        this.notifyAll();
    }*/
}
