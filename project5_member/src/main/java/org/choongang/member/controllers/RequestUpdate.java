package org.choongang.member.controllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class RequestUpdate {
    private String email;

    @NotBlank
    private String userName;

    @Size(min=8)
    private String password;

    private String confirmPassword;

    private String mobile;

    private String job;

    private String gender;

    private List<String> authority;

    private List<String> belongings;

    private List<String> interests;
}