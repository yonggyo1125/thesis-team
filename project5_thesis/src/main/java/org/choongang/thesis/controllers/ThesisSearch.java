package org.choongang.thesis.controllers;

import lombok.Data;
import org.choongang.global.CommonSearch;

import java.util.List;

@Data
public class ThesisSearch extends CommonSearch {
    private List<String> category;
    private List<String> fields;
    private List<String> email; // 회원 이메일(로그인 ID)
}
