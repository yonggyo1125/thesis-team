package org.choongang.thesis.exceptions;

import org.choongang.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class FieldNotFoundException extends CommonException {
    public FieldNotFoundException() {
        super("NotFound.field", HttpStatus.BAD_REQUEST);
        setErrorCode(true);
    }
}
