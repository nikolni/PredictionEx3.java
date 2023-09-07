package system.engine.run.simulation.manager;

public class IsSimulationPaused {
    private boolean isPaused =false;

    public boolean isPaused() {
        return isPaused;
    }
    public synchronized void pause() {
        while (isPaused) {
            try {
                this.wait();
            } catch (InterruptedException e) {}
        }
        isPaused = true;
        this.notifyAll();
    }

    public synchronized void resume() {
        while (!isPaused) {
            try {
                this.wait();
            } catch (InterruptedException e) {}
        }
        isPaused = false;
        this.notifyAll();
    }
}
