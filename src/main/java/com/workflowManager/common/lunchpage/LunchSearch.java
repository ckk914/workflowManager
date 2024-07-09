package com.workflowManager.common.lunchpage;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@EqualsAndHashCode
public class LunchSearch extends LunchPage {
    // 검색어, 검색조건
    private String keyword, type;

    public LunchSearch() {
        this.keyword = "";
    }
}
