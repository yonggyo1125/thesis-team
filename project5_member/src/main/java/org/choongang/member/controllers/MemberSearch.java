package org.choongang.member.controllers;

import lombok.Data;
import org.choongang.global.CommonSearch;

import java.util.List;

@Data
public class MemberSearch extends CommonSearch {
    /**
     * sopt 검색옵션
     * ALL - (통합검색) - email, userName
     * email - 이메일로 검색
     * userName - 이름으로 검색
     * job - 직업 구분
     */
    private String email;
    private String userName;
    private List<String> job;
}