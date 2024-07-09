package com.workflowManager.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum AccessLevel {
    USER("일반회원",1),
    ADMIN("관리자회원",2);

    private String desc;                //권한 설명
    private int authNumber;         //권한 번호
}