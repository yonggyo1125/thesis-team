package org.choongang.thesis.validators;

import org.choongang.thesis.controllers.RequestThesis;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class ThesisValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestThesis.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (errors.hasErrors()) {
            return;
        }

        RequestThesis form = (RequestThesis) target;
        Long tid = form.getTid();
        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "register";
        if(mode.equals("update") && (tid == null || tid < 1L)){
            errors.rejectValue("tid", "NotBlank");
        }
    }
}
