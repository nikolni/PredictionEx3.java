package dto.api;

public interface DTOThreadsPoolStatusForUi {
     Integer getQueueSize();

    Integer getActiveThreadCount();
    Integer getCompletedTaskCount(); // Amount of tasks performed
}
