package org.choongang.thesis.services;

import lombok.RequiredArgsConstructor;
import org.choongang.file.services.FileUploadDoneService;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.thesis.constants.ApprovalStatus;
import org.choongang.thesis.constants.Category;
import org.choongang.thesis.controllers.RequestThesis;
import org.choongang.thesis.controllers.ThesisApprovalRequest;
import org.choongang.thesis.entities.Field;
import org.choongang.thesis.entities.Thesis;
import org.choongang.thesis.entities.VersionLog;
import org.choongang.thesis.exceptions.ThesisNotFoundException;
import org.choongang.thesis.repositories.FieldRepository;
import org.choongang.thesis.repositories.ThesisRepository;
import org.choongang.thesis.repositories.VersionLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional // Thesis_Field 엔티티에 담기위해 추가함
public class ThesisSaveService {
    private final ThesisRepository thesisRepository;
    private final FieldRepository fieldRepository;
    private final FileUploadDoneService uploadDoneService;
    private final VersionLogRepository versionLogRepository;

    private final MemberUtil memberUtil;

    public void save(RequestThesis form) {

        String mode = Objects.requireNonNullElse(form.getMode(), "register");
        Long tid = form.getTid();

        Thesis thesis = null;
        if (mode.equals("update") && tid != null) { // 수정
            thesis = thesisRepository.findById(tid).orElseThrow(ThesisNotFoundException::new);
            //수정 전 논문상태
            String beforeState = thesis.toString();


            saveVersion(thesis, form.getMajorVersion(), form.getMinorVersion(), beforeState, form.toString());
        } else { // 추가
            thesis = new Thesis();
            thesis.setGid(form.getGid());

            if (memberUtil.isLogin()) {
                Member member = memberUtil.getMember();
                thesis.setEmail(member.getEmail());
                thesis.setUserName(member.getUserName());
                thesis.setApprovalStatus(ApprovalStatus.PENDING);
            }
            saveVersion(thesis, 1, 0,null, form.toString());
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
            thesis.setApprovalStatus(form.getApprovalStatus()); // 관리자가 승인 상태를 변경할 수 있음

            //반려하게된다면
            if(form.getApprovalStatus() == ApprovalStatus.REJECTED){
                thesis.setRejectedReason(form.getRejectedReason());
            }
        }

        thesis.setToc(form.getToc());
        thesis.setLanguage(form.getLanguage());
        thesis.setCountry(form.getCountry());

        /* fields 항목 처리 */
        List<String> ids = form.getFields();
        List<Field> fields = null;
        if (ids != null && !ids.isEmpty()) {
            fields = fieldRepository.findAllById(ids);
        }
        thesis.setFields(fields);
        /* 추가, 수정 공통 처리 S */

        thesisRepository.saveAndFlush(thesis);

        // 파일 업로드 완료 처리
        uploadDoneService.process(thesis.getGid());
    }
    //재제출입니다
    @Transactional
    public void resubmitThesis(Long thesisId, RequestThesis form) {
        Thesis thesis = thesisRepository.findById(thesisId)
                .orElseThrow(ThesisNotFoundException::new);


        thesis.setApprovalStatus(ApprovalStatus.PENDING);
        thesis.setRejectedReason(null); // 반려 사유 초기화
        save(form);
    }
    //버전관리
    private void saveVersion(Thesis thesis, int major, int minor,String beforeState ,String afterState) {
        VersionLog versionLog = VersionLog.builder()
                .thesis(thesis)
                .major(major)
                .minor(minor)
                .before(beforeState)
                .after(afterState)
                .build();
        versionLogRepository.saveAndFlush(versionLog);
    }

    //논문들 선택 권한 수정
    @Transactional
    public void saveTheses(List<ThesisApprovalRequest.ThesisApprovalItem> theses) {

//    if (!memberUtil.isAdmin()) {
//        throw new AdminNotFoundException();
//    }
        List<Thesis> thesisList = new ArrayList<>();

        for (ThesisApprovalRequest.ThesisApprovalItem item : theses) {
            Thesis thesis = thesisRepository.findById(item.getThesisId())
                    .orElseThrow(ThesisNotFoundException::new);


            thesis.setApprovalStatus(item.getApprovalStatus());
            thesisList.add(thesis);
        }

        thesisRepository.saveAllAndFlush(thesisList);
    }
}
