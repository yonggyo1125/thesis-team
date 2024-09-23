//package org.choongang.file.services;
//
//import org.choongang.member.MemberUtil;
//import org.choongang.note.entities.Note;
//import org.choongang.note.repositories.NoteRepository;
//import org.choongang.note.services.NoteInfoService;
//import org.junit.jupiter.api.BeforeEach;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class FileInfoServiceTest {
//
//    @Autowired
//    NoteInfoService infoService;
//
//    @Autowired
//    NoteRepository repository;
//
//    @Mock
//    MemberUtil memberUtil;
//
//    @BeforeEach
//    void setUp() {
//        for (int i = 0; i < 10; i++) {
//            Note note = new Note();
//            note.setNid("그룹 id" + i);
//
//        }
//    }
//}
