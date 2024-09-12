package org.choongang.thesis.controllers;

import lombok.Data;
import org.choongang.thesis.constants.ApprovalStatus;

import java.util.List;
@Data
public class ThesisApprovalRequest {

    private List<ThesisApprovalItem> theses; // 논문 ID와 승인 여부를 한 쌍으로 전달

    @Data
    public static class ThesisApprovalItem {
        private Long thesisId;
        private ApprovalStatus approvalStatus;
    }
}
