package com.workflowManager.common.lunchpage;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class LunchPage {
    private int pageNo; // 클라이언트가 요청한 페이지번호
    private int amount; // 클라이언트가 요청한 한 페이지당 게시물 목록 수

    public LunchPage() {
        this.pageNo = 1;
        this.amount = 3;
    }

    public void setPageNo(int pageNo) {
        if (pageNo < 1 || pageNo > Integer.MAX_VALUE) {
            this.pageNo = 1;
            return;
        }
        this.pageNo = pageNo;
    }

    public void setAmount(int amount) {
        if (amount < 3 || amount > 30) {
            this.amount = 3;
            return;
        }
        this.amount = amount;
    }

    public int getPageStart() {
        return (this.pageNo - 1) * this.amount;
    }
}


