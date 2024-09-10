package org.choongang.thesis.services;

import lombok.RequiredArgsConstructor;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.thesis.constants.Category;
import org.choongang.thesis.controllers.RequestThesis;
import org.choongang.thesis.entities.Thesis;
import org.choongang.thesis.exceptions.ThesisNotFoundException;
import org.choongang.thesis.repositories.ThesisRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ThesisSaveService {
    private final ThesisRepository thesisRepository;
    private final MemberUtil memberUtil;

    public void save(RequestThesis form) {

        String mode = Objects.requireNonNullElse(form.getMode(), "register");
        Long tid = form.getTid();

        Thesis thesis = null;
        if (mode.equals("update") && tid != null) { // 수정
            thesis = thesisRepository.findById(tid).orElseThrow(ThesisNotFoundException::new);
        } else { // 추가
            thesis = new Thesis();
            thesis.setGid(form.getGid());
            if (memberUtil.isLogin()) {
                Member member = memberUtil.getMember();
                thesis.setEmail(member.getEmail());
                thesis.setUserName(member.getUserName());
            }
        }

        /* 추가, 수정 공통 처리 S */
        thesis.setCategory(Category.valueOf(form.getCategory()));
        thesis.setPoster(form.getPoster());
        thesis.setTitle(form.getTitle());
        thesis.setContributor(form.getContributor());
        thesis.setThAbstract(form.getThAbstract());
        thesis.setReference(form.getReference());
        thesis.setVisible(form.isVisible());

        if (memberUtil.isAdmin()) {
            thesis.setApproval(form.isApproval()); // 승인은 관리자인 경우만 가능
        }

        thesis.setToc(form.getToc());
        thesis.setLanguage(form.getLanguage());
        thesis.setCountry(form.getCountry());
        /* 추가, 수정 공통 처리 S */

        thesisRepository.saveAndFlush(thesis);
    }
}
