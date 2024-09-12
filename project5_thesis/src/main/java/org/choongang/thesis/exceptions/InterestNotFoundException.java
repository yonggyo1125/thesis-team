package org.choongang.thesis.exceptions;

import org.choongang.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class InterestNotFoundException extends CommonException {
    public InterestNotFoundException() {
        super("NotFound.interest", HttpStatus.BAD_REQUEST);
        setErrorCode(true);
    }
}
