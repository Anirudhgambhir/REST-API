package taskManagement.validator.impl;

import org.apache.commons.lang3.Validate;
import taskManagement.exceptions.InvalidRequestException;
import taskManagement.validator.Validator;

import java.time.Instant;

public class InstantValidatorImpl implements Validator<Instant> {

    @Override
    public void validate(Instant input) {
        try {
            Validate.notNull(input, "Time Input should not be null");
        }catch (Exception e) {
            throw new InvalidRequestException(e.getMessage());
        }

    }
}
