package taskManagement.validator.impl;

import org.apache.commons.lang3.Validate;
import taskManagement.exceptions.InvalidRequestException;
import taskManagement.validator.Validator;

public class ObjectValidatorImpl implements Validator<Object> {

    @Override
    public void validate(Object input) {
        try {
            Validate.notNull(input, "Object should not be null");
        }catch (Exception e) {
            throw new InvalidRequestException(e.getMessage());
        }

    }
}
