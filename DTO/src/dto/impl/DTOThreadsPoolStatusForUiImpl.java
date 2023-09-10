package dto.impl;

import dto.api.DTOThreadsPoolStatusForUi;

public class DTOThreadsPoolStatusForUiImpl implements DTOThreadsPoolStatusForUi {
    Integer queueSize;
    Integer activeThreadCount;
    Integer completedTaskCount;

    public DTOThreadsPoolStatusForUiImpl(int queueSize, int activeThreadCount, int completedTaskCount) {
        this.queueSize = queueSize;
        this.activeThreadCount = activeThreadCount;
        this.completedTaskCount = completedTaskCount;
    }

    @Override
    public Integer getQueueSize() {
        return queueSize;
    }

    @Override
    public Integer getActiveThreadCount() {
        return activeThreadCount;
    }

    @Override
    public Integer getCompletedTaskCount() {
        return completedTaskCount;
    }
}
