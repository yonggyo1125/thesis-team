package org.choongang.thesis.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.services.FileInfoService;
import org.choongang.global.ListData;
import org.choongang.global.Pagination;
import org.choongang.member.MemberUtil;
import org.choongang.thesis.constants.ApprovalStatus;
import org.choongang.thesis.constants.Category;
import org.choongang.thesis.controllers.RequestThesis;
import org.choongang.thesis.controllers.ThesisSearch;
import org.choongang.thesis.entities.Field;
import org.choongang.thesis.entities.QThesis;
import org.choongang.thesis.entities.Thesis;
import org.choongang.thesis.exceptions.ThesisNotFoundException;
import org.choongang.thesis.repositories.FieldRepository;
import org.choongang.thesis.repositories.ThesisRepository;
import org.choongang.thesisAdvance.services.UserLogService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
@Transactional
public class ThesisInfoService {
    private final ThesisRepository thesisRepository;
    private final FileInfoService fileInfoService;
    private final WishListService wishListService;
    private final HttpServletRequest request;
    private final ModelMapper modelMapper;
    private final MemberUtil memberUtil;
    private final FieldRepository fieldRepository;
    private final UserLogService userLogService;


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

    /**
     * 논문 목록
     *
     * @param search
     * @return
     */
    public ListData<Thesis> getList(ThesisSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;

        /* 검색 처리 S */
        BooleanBuilder andBuilder = new BooleanBuilder();
        QThesis thesis = QThesis.thesis;
        String sopt = search.getSopt();
        String skey = search.getSkey();

        LocalDate sDate = search.getSDate(); //검색 시작일
        LocalDate eDate = search.getEDate(); //검색 종료일

        List<String> category = search.getCategory(); //카테고리
        List<String> fields = search.getFields(); //분류명
        List<String> email = search.getEmail(); //작성한 회원 이메일

        String title = search.getTitle(); //제목
        String poster = search.getPoster(); //저자
        String thAbstract = search.getThAbstract(); //초록
        String publisher = search.getPublisher(); //발행기관
        String language = search.getLanguage(); //언어
        String country = search.getCountry(); //국가

        sopt = StringUtils.hasText(sopt) ? sopt : "ALL"; // 통합 검색이 기본

        sopt = sopt != null && StringUtils.hasText(sopt.trim()) ? sopt.trim() : "ALL";
        if (skey != null && StringUtils.hasText(skey.trim())) {
            /**
             * sopt
             *  ALL: 통합검색
             *  CATEGORY: 카테고리 -> 2차 가공 필요
             *  FIELDS: 분류명
             *  TITLE: 제목
             *  POSTER: 저자
             *  THABSTRACT: 초록
             *  PUBLISHER: 발행기관
             *  LANGUAGE: 언어
             *  COUNTRY: 국가
             *  고급 검색 기능으로 구현할 시 수정될 예정
             */

            skey = skey.trim();
            StringExpression expression = null;
            EnumExpression<Category> enumExpression = null;
            if (sopt.equals("ALL")) { // 통합 검색
                expression = thesis.title
                        .concat(String.valueOf(thesis.category))
                        .concat(String.valueOf(thesis.fields))
                        .concat(thesis.poster)
                        .concat(thesis.thAbstract)
                        .concat(thesis.publisher)
                        .concat(thesis.language)
                        .concat(thesis.country);
            } else if (sopt.equals("CATEGORY")) {
                enumExpression = thesis.category;
            } else if (sopt.equals("FIELD")) {

            } else if (sopt.equals("TITLE")) {
                expression = thesis.title;
            } else if (sopt.equals("POSTER")) {
                expression = thesis.poster;
            } else if (sopt.equals("TH-ABSTRACT")) {
                expression = thesis.thAbstract;
            } else if (sopt.equals("PUBLISHER")) {
                expression = thesis.publisher;
            } else if (sopt.equals("LANGUAGE")) {
                expression = thesis.language;
            } else if (sopt.equals("COUNTRY")) {
                expression = thesis.country;
            }

            if (expression != null) {
                andBuilder.and(expression.contains(skey));
            }

            if (enumExpression != null) {
                andBuilder.and(enumExpression.eq(Category.valueOf(skey)));
            }
        }
        //논문명 검색
        if (title != null && StringUtils.hasText(title.trim())) {
            andBuilder.and(thesis.title.contains(title));
        }
        //저자명 검색
        if (poster != null && StringUtils.hasText(poster.trim())) {
            andBuilder.and(thesis.poster.eq(poster));
        }
        //초록 검색 -> 초록은 검색해야 할 양이 많기 때문에 빼는 게 좋을 수도
        if (thAbstract != null && StringUtils.hasText(thAbstract.trim())) {
            andBuilder.and(thesis.thAbstract.contains(thAbstract));
        }
        //발행기관 검색
        if (publisher != null && StringUtils.hasText(publisher.trim())) {
            andBuilder.and(thesis.publisher.contains(publisher));
        }
        //언어 검색
        if (language != null && StringUtils.hasText(language.trim())) {
            andBuilder.and(thesis.language.eq(language));
        }
        //국가 검색
        if (country != null && StringUtils.hasText(country.trim())) {
            andBuilder.and(thesis.country.eq(country));
        }
        //field 검색
        if (fields != null && !fields.isEmpty()) {
            fieldRepository.findByIdIn(fields).forEach(i -> andBuilder.or(thesis.fields.contains(i)));
        }

        //논문 등록일 검색
        if (sDate != null) { //검색 시작일
            andBuilder.and(thesis.createdAt.goe(sDate.atTime(LocalTime.MIN)));
        }
        if (eDate != null) { //검색 종료일
            andBuilder.and(thesis.createdAt.loe(eDate.atTime(LocalTime.MAX)));
        }

        if (search.getApprovalStatus() != null) {
            ApprovalStatus approvalStatus = memberUtil.isAdmin() ? search.getApprovalStatus() : ApprovalStatus.APPROVED;
            andBuilder.and(thesis.approvalStatus.eq(approvalStatus)); // 승인 상태에 따른 필터링
        }

        //작성한 회원 이메일로 조회
        if (email != null && !email.isEmpty()) {
            andBuilder.and(thesis.email.in(email));
        }

        if (!memberUtil.isAdmin()) {
            andBuilder.and(thesis.visible.eq(true))
                    .and(thesis.approvalStatus.eq(ApprovalStatus.APPROVED));

        }
        /* 검색 처리 E */

        /* 고급 검색 처리 S */
        List<String> sopts = search.getSopts();
        List<String> skeys = search.getSkeys();
        List<String> operators = Objects.requireNonNullElse(search.getOperators(), new ArrayList<>());
        if (operators.size() > 0) {
            int index = operators.indexOf("OR");
            if (index == -1) operators.add("AND");
            else operators.add(index, "OR");
        } else {
            operators.add("AND");
        }

        if (sopts != null && !sopts.isEmpty()) {
            List<Map<String, BooleanExpression>> data = new ArrayList<>();
            for (int i = 0; i < sopts.size(); i++) {
                String _sopt = sopts.get(i);
                String _skey = skeys.get(i);
                String operator = operators.get(i);

                if (!StringUtils.hasText(_sopt) || !StringUtils.hasText(_skey)) continue;

                StringExpression expression = null;
                if (_sopt.equals("poster")) {
                    expression = thesis.poster;
                } else if (_sopt.equals("title")) {
                    expression = thesis.title;
                } else if (_sopt.equals("thAbstract")) {
                    expression = thesis.thAbstract;
                } else if (_sopt.equals("reference")) {
                    expression = thesis.reference;
                } else if (_sopt.equals("publisher")) {
                    expression = thesis.publisher;
                } else if (_sopt.equals("language")) {
                    expression = thesis.language;
                }

                BooleanExpression condition = expression.contains(_skey.trim());
                Map<String, BooleanExpression> c = new HashMap<>();
                c.put(operator, condition);
                data.add(c);
            }

            String prevOperator = "";
            BooleanBuilder orBuilder = new BooleanBuilder();
            int i = 0;
            for (Map<String, BooleanExpression> item : data) {
                for (Map.Entry<String, BooleanExpression> entry : item.entrySet()) {
                    String operator = entry.getKey();
                    BooleanExpression condition = entry.getValue();
                    if (prevOperator.equals("OR") &&  !operator.equals("OR")) {
                        andBuilder.and(orBuilder);

                        orBuilder = new BooleanBuilder();
                    }

                    if (operator.equals("NOT")) {
                        condition = condition.not();
                    }

                    if (operator.equals("AND") || operator.equals("NOT")) {
                        // 바로 다름 operator가 OR이면 orBuilder로 변경
                        String nextOperator = "";
                        try {
                             nextOperator = operators.get(i + 1);
                        } catch (Exception e) {}
                        if (nextOperator.equals("OR")) {
                            orBuilder.or(condition);
                        } else {
                            andBuilder.and(condition);
                        }
                    } else if (operator.equals("OR")) {
                        orBuilder.or(condition);
                    }

                    prevOperator = operator;
                    i++;
                }
            }
            if (prevOperator.equals("OR")) {
                andBuilder.and(orBuilder);
            }
        }

        /* 고급 검색 처리 E */

        // 정렬 처리 S, -> 목록 조회 처리 추가 필요함
        String sort = search.getSort();

        PathBuilder<Thesis> pathBuilder = new PathBuilder<>(Thesis.class, "thesis");
        OrderSpecifier orderSpecifier = null;
        Order order = Order.DESC;
        if (sort != null && StringUtils.hasText(sort.trim())) {
            //정렬항목_방향
            String[] _sort = sort.split("_");
            if (_sort[1].toUpperCase().equals("ASC")) {
                order = Order.ASC;
            }
            orderSpecifier = new OrderSpecifier(order, pathBuilder.get(_sort[0]));
        }

        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        orderSpecifiers.add(thesis.title.desc());
        if (orderSpecifier != null) {
            orderSpecifiers.add(orderSpecifier);
        }
        orderSpecifiers.add(thesis.createdAt.desc());
        //정렬 처리 E

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        //데이터 조회
        Page<Thesis> data = thesisRepository.findAll(andBuilder, pageable);

        long total = data.getTotalElements();

        Pagination pagination = new Pagination(page, (int) total, 10, limit, request);

        List<Thesis> items = data.getContent(); // 개수에 맞게 조회된 데이터
        items.forEach(this::addInfo);
        if(StringUtils.hasText(skey)){
            userLogService.save(skey);//검색한 키워드 저장
        }
        return new ListData<>(items, pagination);
    }

    //마이리스트
    public ListData<Thesis> getMyList(ThesisSearch search) {
        if (!memberUtil.isLogin()) {
            return new ListData<>();
        }
        String email = memberUtil.getMember().getEmail();
        search.setEmail(List.of(email));
        return getList(search);
    }

    /**
     * 내가 찜한 논문 목록
     * @param search
     * @return
     */
    public ListData<Thesis> getWishList(ThesisSearch search){
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 10 : limit;

        List<Long> tids = wishListService.getList(); //찜한 논문 목록 tid 가져오기
        if(tids == null || tids.isEmpty()){
            return new ListData<>();
        }

        QThesis thesis = QThesis.thesis;
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(thesis.tid.in(tids)); // 찜한 논문의 tid에 해당하는 논문만 조회

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<Thesis> data = thesisRepository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int) data.getTotalElements(), 10, limit, request);

        List<Thesis> items = data.getContent();

        return new ListData<>(items, pagination); // 결과 반환
    }

    // 추가 정보 처리
    private void addInfo(Thesis item) {
        Category category = item.getCategory();
        item.set_category(category == null ? null : category.getTitle());

        // 논문 파일
        List<FileInfo> files = fileInfoService.getList(item.getGid());
        item.setFileInfo(files == null || files.isEmpty() ? null : files.get(0));

        // 학문 분류 처리
        List<Field> fields = item.getFields();
        Map<String, String[]> _fields = fields == null || fields.isEmpty() ? null : fields.stream().collect(Collectors.toMap(Field::getId, f -> new String[]{f.getName(), f.getSubfield()}));

        item.set_fields(_fields);
    }
}
