package org.choongang.member.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.FileInfo;
import org.choongang.global.entities.BaseEntity;
import org.choongang.member.constants.Authority;
import org.choongang.member.constants.Gender;
import org.choongang.member.constants.Job;
import org.choongang.thisis.entities.Interests;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    private Long seq;

    @Column(length = 45, nullable = false)
    private String gid;

    @Column(length = 65, unique = true, nullable = false)
    private String email;

    @Column(length = 65, nullable = false)
    private String password;

    @Column(length = 40, nullable = false)
    private String userName;

    @Column(length = 15, nullable = false)
    private String mobile;

    @Column(nullable = false)
    private LocalDate birth;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(length = 20)
    private String memMajor;//전공

    @Column(length = 20)
    private String memMinor;//부전공

    @Transient
    private List<Interests> interests;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Authority authorities;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Job job;

    @Transient
    private FileInfo profileImage;
}
