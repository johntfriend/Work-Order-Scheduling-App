package sru.edu.group1.workorders.security;


import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String>{

    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
        		//This is the set of rules for the password, at this point it's just it can be between 6-15 chars, the max value will need to be reduced, depending on max password variable length
                // at least 6 characters, but no more than 15
                new LengthRule(6, 15)
        ));

        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

     
        
        List<String> messages = validator.getMessages(result);
        String messageTemplate = messages.stream().collect(Collectors.joining(","));
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }	
    public boolean isValid(String password)
        {
        	if (password.length() < 6)
        	{
        		return false;
        	}
        	else if (password.length() > 15)
        	{
        		return false;
        	}
        	else
        	{
        		return true;
        	}
        }
}
