package org.choongang.thesis.constants;

public enum Category {
    DOMESTIC("국내논문"),
    FOREIGN("해외논문");

    private final String title;

    Category(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
