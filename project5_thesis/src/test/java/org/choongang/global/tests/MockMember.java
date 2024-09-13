package org.choongang.global.tests;

import org.choongang.member.constants.Authority;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
//@WithSecurityContext
public @interface MockMember {
    long seq() default 1L;
    String gid() default "testgid";
    String email() default "user01@test.org";
    String job() default "테스트 직업";
    String password() default "_aA123456";
    String userName() default "사용자01";
    String mobile() default "01010001000";
    Authority authority() default Authority.USER;
}
