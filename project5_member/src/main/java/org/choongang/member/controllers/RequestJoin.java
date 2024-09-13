package org.choongang.member.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestJoin {

    /* 회원공통 */
    private String gid = UUID.randomUUID().toString();

    @NotBlank
    @Email
    private String email;

    @NotBlank @Size(min=8)
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String userName;

    @NotBlank
    private String mobile;

    @NotNull
    @Past
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birth;

    @NotBlank
    private String job;

    private String gender; // 커맨드 객체에는 enum상수 사용 불가 -> String으로 바꿔주기

    private String memMajor; // 커맨드 객체에는 enum상수 사용 불가 -> String으로 바꿔주기

    private String memMinor;

    private List<String> interests; // 커맨드 객체에는 enum상수 사용 불가 -> String으로 바꿔주기

    @AssertTrue
    private boolean agree;
}
