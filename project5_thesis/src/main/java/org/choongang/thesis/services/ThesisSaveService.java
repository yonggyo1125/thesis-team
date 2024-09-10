package org.choongang.thesis.services;

import lombok.RequiredArgsConstructor;
import org.choongang.thesis.controllers.RequestThesis;
import org.choongang.thesis.repositories.ThesisRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ThesisSaveService {
    private final ThesisRepository thesisRepository;

    public void save(RequestThesis form) {

        String mode = Objects.requireNonNullElse(form.getMode(), "register");
        Long tid = form.getTid();

        if (mode.equals("update") && tid != null) {

        }
    }
}
