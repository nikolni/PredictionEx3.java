package dto.primary;

public class DTOThreadsPoolStatusForUi {
    Integer queueSize;
    Integer activeThreadCount;
    Integer completedTaskCount;

    public DTOThreadsPoolStatusForUi(int queueSize, int activeThreadCount, int completedTaskCount) {
        this.queueSize = queueSize;
        this.activeThreadCount = activeThreadCount;
        this.completedTaskCount = completedTaskCount;
    }

    public Integer getQueueSize() {
        return queueSize;
    }

    public Integer getActiveThreadCount() {
        return activeThreadCount;
    }

    public Integer getCompletedTaskCount() {
        return completedTaskCount;
    }
}
