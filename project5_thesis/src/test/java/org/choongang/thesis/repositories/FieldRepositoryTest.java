package org.choongang.thesis.repositories;

import org.choongang.thesis.entities.Field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class FieldRepositoryTest {
    @Autowired
    FieldRepository fieldRepository;

    @Test
    @DisplayName("필드 추가")
    void test() {
        List<String[]> items = Arrays.asList(
            new String[]{"ET-ComputerScience", "공학 및 기술", "컴퓨터 과학"},
            new String[]{"ET-ElectricalEngineering", "공학 및 기술", "전자공학"},
            new String[]{"ET-MechanicalEngineering", "공학 및 기술", "기계공학"},
            new String[]{"ET-CivilEngineering", "공학 및 기술", "토목공학"},
            new String[]{"ET-ChemicalEngineering", "공학 및 기술", "화학공학"},
            new String[]{"ET-MaterialsScience", "공학 및 기술", "재료공학"},

            new String[]{"MH-ClinicalMedicine", "의학 및 보건학", "임상의학"},
            new String[]{"MH-Nursing", "의학 및 보건학", "간호학"},
            new String[]{"MH-Pharmacy", "의학 및 보건학", "약학"},
            new String[]{"MH-PublicHealth", "의학 및 보건학", "공중보건"},
            new String[]{"MH-VeterinaryScience", "의학 및 보건학", "수의학"},
            new String[]{"MH-BiomedicalScience", "의학 및 보건학", "의생명과학"},

            new String[]{"HU-Philosophy", "인문학", "철학"},
            new String[]{"HU-History", "인문학", "역사학"},
            new String[]{"HU-Linguistics", "인문학", "언어학"},
            new String[]{"HU-Literature", "인문학", "문학"},
            new String[]{"HU-ReligiousStudies", "인문학", "종교학"},
            new String[]{"HU-Arts", "인문학", "예술"},

            new String[]{"SS-Psychology", "사회과학", "심리학"},
            new String[]{"SS-Economics", "사회과학", "경제학"},
            new String[]{"SS-Sociology", "사회과학", "사회학"},
            new String[]{"SS-PoliticalScience", "사회과학", "정치학"},
            new String[]{"SS-Anthropology", "사회과학", "인류학"},
            new String[]{"SS-Law", "사회과학", "법학"},
            new String[]{"SS-Education", "사회과학", "교육학"},

            new String[]{"AE-AgriculturalScience", "농업 및 생명공학", "농업학"},
            new String[]{"AE-EnvironmentalScience", "농업 및 생명공학", "환경과학"},
            new String[]{"AE-Ecology", "농업 및 생명공학", "생태학"},
            new String[]{"AE-Forestry", "농업 및 생명공학", "임학"},
            new String[]{"AE-FisheriesScience", "농업 및 생명공학", "수산학"},
            new String[]{"AE-FoodScience", "농업 및 생명공학", "식품과학"},

            new String[]{"BE-BusinessAdministration", "경영 및 경제", "경영학"},
            new String[]{"BE-Marketing", "경영 및 경제", "마케팅"},
            new String[]{"BE-Finance", "경영 및 경제", "재무학"},
            new String[]{"BE-Accounting", "경영 및 경제", "회계학"},
            new String[]{"BE-InternationalBusiness", "경영 및 경제", "국제경영"},
            new String[]{"BE-IndustrialEngineering", "경영 및 경제", "산업공학"},

            new String[]{"AD-VisualArts", "예술 및 디자인", "시각예술"},
            new String[]{"AD-Music", "예술 및 디자인", "음악"},
            new String[]{"AD-Design", "예술 및 디자인", "디자인"},
            new String[]{"AD-Architecture", "예술 및 디자인", "건축학"},
            new String[]{"AD-TheatrePerformingArts", "예술 및 디자인", "연극 및 공연예술"},
            new String[]{"AD-FilmStudies", "예술 및 디자인", "영화학"}
        );

        for (String[] FieldData : items) {
            Field field = new Field();
            field.setId(FieldData[0]);
            field.setName(FieldData[1]);
            field.setSubfield(FieldData[2]);

            fieldRepository.saveAndFlush(field);
        }
    }
}
