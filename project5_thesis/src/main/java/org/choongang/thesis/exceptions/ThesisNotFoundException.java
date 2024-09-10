package org.choongang.thesis.exceptions;

import org.choongang.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class ThesisNotFoundException extends CommonException {
    public ThesisNotFoundException() {
        super("NotFound.thesis", HttpStatus.BAD_REQUEST);
        setErrorCode(true);
    }
}
