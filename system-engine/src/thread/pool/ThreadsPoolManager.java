package thread.pool;

import dto.primary.DTOThreadsPoolStatusForUi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadsPoolManager {
    private ExecutorService threadPool;
    private static int completedTaskCount =0;
    private int taskCount=0;
    private static int activeThreadCount = 0;
    private int newThreadPoolSize;

    public ThreadsPoolManager(int threadPoolSize) {
        threadPool = Executors.newFixedThreadPool(threadPoolSize);
    }
    public synchronized void addTaskToQueue(Runnable runSimulationRunnable){
        threadPool.submit(runSimulationRunnable);
        taskCount++;
    }
    public DTOThreadsPoolStatusForUi getThreadsPoolStatus(){
        int queueSize = taskCount - activeThreadCount - completedTaskCount;
        return new DTOThreadsPoolStatusForUi(queueSize, activeThreadCount, completedTaskCount);
    }

    public static synchronized void increaseCompletedTaskCount(){
        completedTaskCount++;
    }
    public static synchronized void increaseActiveCount(){
        activeThreadCount++;
    }
    public static synchronized void decreaseActiveCount(){
        activeThreadCount--;
    }
    public void setSizeOfThreadPool(int size){
        newThreadPoolSize = size;
        new Thread(this::runnableCode).start();
    }

    private void runnableCode(){
        while(taskCount != completedTaskCount){
        }
        threadPool = Executors.newFixedThreadPool(newThreadPoolSize);
    }
}
