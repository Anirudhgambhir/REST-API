package taskManagement.validator.impl;

import org.apache.commons.lang3.Validate;
import taskManagement.exceptions.InvalidRequestException;
import taskManagement.service.TaskUpdate;
import taskManagement.validator.Validator;

public class TaskUpdateValidatorImpl implements Validator<TaskUpdate> {
    @Override
    public void validate(TaskUpdate update) {
        try {
            Validate.notNull(update.getTitle(), "Title must not be Null");
            Validate.notNull(update.getCompleted(), "Status must not be null");
            Validate.notNull(update.getDescription(), "Description must not be null");
        }
        catch (Exception e) {
            throw new InvalidRequestException(e.getMessage());
        }

    }

}
