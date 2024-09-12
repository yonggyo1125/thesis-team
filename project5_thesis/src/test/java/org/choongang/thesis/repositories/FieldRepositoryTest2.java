package org.choongang.thesis.repositories;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.choongang.thesis.entities.Field;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

@SpringBootTest
public class FieldRepositoryTest2 {
    @Autowired
    FieldRepository fieldRepository;

    @Test
    void excelToDB() {
        File file = new File("D:/data/researchField2.xlsx");
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
             OPCPackage opcPackage = OPCPackage.open(bis)) {

            XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 2; i <= 161; i++) {
                XSSFRow row = sheet.getRow(i);

                //System.out.println("cell 타입: " + cell.getCellType());
                //System.out.println("cell1 타입: " + cell1.getCellType());

                if (row != null) {
                    XSSFCell cell = row.getCell(0); // 대분류
                    XSSFCell cell1 = row.getCell(1); // 중분류
                    //System.out.println("대분류 : " + cell.getStringCellValue());
                    //System.out.println("중분류 : " + cell1.getStringCellValue());

                    String _name; // 대분류
                    String _subfield; // 중분류
                    String _id; // 학문별 분류 코드

                    if (cell!=null && cell.getCellType() != CellType.BLANK) {
                        _name = cell.getStringCellValue();
                    } else {
                        _name = "미분류";
                    }

                    if (cell!=null && cell1.getCellType() != CellType.BLANK){
                        _subfield = cell1.getStringCellValue();
                    } else {
                        _subfield = "미분류";
                    }

                    _id = cell.getStringCellValue() + "(" + cell1.getStringCellValue() + ")";
                    System.out.printf("id : %s, name : %s, sub : %s%n", _id, _name, _subfield);


                    Field field = new Field();
                    field.setId(_id);
                    field.setName(_name);
                    field.setSubfield(_subfield);

                    fieldRepository.saveAndFlush(field);

                } else {
                    System.out.println("행" + i + "가 null 입니다.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
