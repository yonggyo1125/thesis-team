package org.choongang.thesis.services;

import lombok.RequiredArgsConstructor;
import org.choongang.file.services.FileInfoService;
import org.choongang.thesis.constants.Category;
import org.choongang.thesis.controllers.RequestThesis;
import org.choongang.thesis.entities.Field;
import org.choongang.thesis.entities.Thesis;
import org.choongang.thesis.exceptions.ThesisNotFoundException;
import org.choongang.thesis.repositories.ThesisRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ThesisInfoService {
    private final ThesisRepository thesisRepository;
    private final FileInfoService fileInfoService;
    private final ModelMapper modelMapper;

    public Thesis get(Long tid) {
        Thesis item = thesisRepository.findById(tid).orElseThrow(ThesisNotFoundException::new);

        addInfo(item);

        return item;
    }

    public RequestThesis getForm(Long tid) {
        Thesis item = get(tid);
        RequestThesis form = modelMapper.map(item, RequestThesis.class);
        Category category = item.getCategory();
        form.setCategory(category == null ? null : category.name());

        List<Field> fields = item.getFields();
        if (fields != null && !fields.isEmpty()) {
            List<String> ids = fields.stream().map(Field::getId).toList();
            form.setFields(ids);
        }

        return form;
    }

    // 추가 정보 처리
    private void addInfo(Thesis item) {

    }
}
