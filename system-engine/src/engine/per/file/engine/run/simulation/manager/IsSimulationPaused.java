package engine.per.file.engine.run.simulation.manager;

import java.time.Instant;

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
}
