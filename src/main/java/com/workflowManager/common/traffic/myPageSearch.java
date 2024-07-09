package com.workflowManager.common.traffic;

import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode
public class myPageSearch {

    // 검색어와 검색조건
    private String keyword, type;

    public myPageSearch(){
        this.keyword = "";
    }

}
