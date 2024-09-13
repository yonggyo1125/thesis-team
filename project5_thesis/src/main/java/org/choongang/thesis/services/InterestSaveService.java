package org.choongang.thesis.services;

import lombok.RequiredArgsConstructor;
import org.choongang.global.Utils;
import org.choongang.global.exceptions.BadRequestException;
import org.choongang.thesis.entities.Interests;
import org.choongang.thesis.repositories.FieldRepository;
import org.choongang.thesis.repositories.InterestsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InterestSaveService {
    private final InterestsRepository interestsRepository;
    private final Utils utils;
    private final FieldRepository fieldRepository;

    //    관심사 저장
    public void save(List<Interests> _interests, String email) {
        if (_interests.isEmpty()) {
            throw new BadRequestException(utils.getMessage("NotBlank.Interests"));
        }
        if (!StringUtils.hasText(email)) {
            throw new BadRequestException(utils.getMessage("NotBlank.Email"));
        }
        List<Interests> list = interestsRepository.findAllByEmail(email);
        if (!list.isEmpty()) {//기존 관심사가 존재하는 경우
            interestsRepository.deleteByEmail(email);//삭제 후
            interestsRepository.flush();
        }
        _interests.forEach(interestsRepository::saveAndFlush);

    }
}
