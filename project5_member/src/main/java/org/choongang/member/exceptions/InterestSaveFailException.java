package org.choongang.member.exceptions;

import org.choongang.global.exceptions.CommonException;

public class InterestSaveFailException extends CommonException {
    public InterestSaveFailException() {
        super("Interest.Fail");
        setErrorCode(true);
    }
}
