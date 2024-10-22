package org.skillbox.tasktracker.publisher;

import lombok.Getter;
import org.skillbox.tasktracker.dto.response.TaskResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Getter
@Component
public class TaskPublisher {

    private final Sinks.Many<TaskResponse> taskResponseSink;

    public TaskPublisher() {
        this.taskResponseSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public void publisher(TaskResponse task){
        taskResponseSink.tryEmitNext(task);
    }

}
