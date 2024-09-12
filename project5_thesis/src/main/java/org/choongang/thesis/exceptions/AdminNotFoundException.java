package org.choongang.thesis.exceptions;

import org.choongang.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class AdminNotFoundException extends CommonException {
    public AdminNotFoundException() {
        super("NotFound.admin", HttpStatus.BAD_REQUEST);
        setErrorCode(true);
    }
}

