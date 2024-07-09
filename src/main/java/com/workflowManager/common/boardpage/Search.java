package com.workflowManager.common.boardpage;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@EqualsAndHashCode
public class Search extends Page {

    // 검색어, 검색조건
    private String keyword, type;

    public Search() {
        this.keyword = "";
    }
}
