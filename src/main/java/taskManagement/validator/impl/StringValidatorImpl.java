package taskManagement.validator.impl;

import org.apache.commons.lang3.Validate;
import taskManagement.exceptions.InvalidRequestException;
import taskManagement.validator.Validator;

public class StringValidatorImpl implements Validator<String> {
    @Override
    public void validate(String input) {
        try {
            Validate.notNull(input, "Input should not be null");
            Validate.notBlank(input, "Input should not be empty");
        }catch (Exception exception) {
            throw new InvalidRequestException(exception.getMessage());
        }
    }
}
