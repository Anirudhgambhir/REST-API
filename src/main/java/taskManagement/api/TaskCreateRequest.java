package taskManagement.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TaskCreateRequest {
    private final String title;
    private final String description;

    @JsonCreator
    public TaskCreateRequest(@JsonProperty(value = "title", required = true) String title,
                             @JsonProperty(value = "description", required = true) String description) {
        this.title = title;
        this.description = description;
    }

}
