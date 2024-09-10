package org.choongang.thesis.controllers;

import lombok.Data;
import org.choongang.global.CommonSearch;

import java.util.List;

@Data
public class ThesisSearch extends CommonSearch {
    private List<String> category;
    private List<String> fields;
}
