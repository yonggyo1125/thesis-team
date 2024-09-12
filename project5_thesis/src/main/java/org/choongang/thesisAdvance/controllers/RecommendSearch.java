package org.choongang.thesisAdvance.controllers;

import lombok.Data;
import org.choongang.thesis.controllers.ThesisSearch;

import java.util.List;

@Data
public class RecommendSearch extends ThesisSearch {
    List<String> belongings;
}
