package org.choongang.member.constants;

import java.util.List;

public enum Job {
    PROFESSOR("교수"),
    DOCTOR("박사"),
    MASTER("석사"),
    ACADEMIC("학생"),
    RESEARCHER("연구원"),
    LIBRARIAN("사서"),
    UNIVERSITY_STAFF("대학직원"),
    TEACHER("교사"),
    PUBLIC_OFFICIAL("공무원"),
    GENERAL_MEMBER("일반인");

    private final String title;

    Job(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<String[]> getList() {
        return List.of(
            new String[] {PROFESSOR.name(), PROFESSOR.title},
                new String[] {DOCTOR.name(), DOCTOR.title},
                new String[] {MASTER.name(), MASTER.title},
                new String[] {ACADEMIC.name(), ACADEMIC.title},
                new String[] {RESEARCHER.name(), RESEARCHER.title},
                new String[] {LIBRARIAN.name(), LIBRARIAN.title},
                new String[] {UNIVERSITY_STAFF.name(), UNIVERSITY_STAFF.title},
                new String[] {TEACHER.name(), TEACHER.title},
                new String[] {PUBLIC_OFFICIAL.name(), PUBLIC_OFFICIAL.title},
                new String[] {GENERAL_MEMBER.name(), GENERAL_MEMBER.title}
        );
    }
}
