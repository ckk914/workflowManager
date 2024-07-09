package com.workflowManager.common.traffic;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class myPageMaker {

    // 한 화면에 페이지를 몇개씩 배치할 것인가?
    private static final int PAGE_COUNT = 5;

    // 페이지 시작번호와 끝번호
    private int begin, end, finalPage;

    private boolean prev, next;

    private int totalCount;

    @Setter
    private String userId;

    // 현재 페이지 정보
    private myInfoPage pageInfo;

    public myPageMaker(myInfoPage page, int totalCount){
        this.pageInfo = page;
        this.totalCount =totalCount;
        makePageInfo();
    }


    private void makePageInfo(){


        this.end = (int) (Math.ceil(pageInfo.getPageNo()/(double) PAGE_COUNT)*PAGE_COUNT);

        this.begin = this.end - PAGE_COUNT + 1;

        finalPage = (int) Math.ceil((double) totalCount / pageInfo.getAmount());

        if (finalPage < this.end) {
            this.end = finalPage;
        }

        this.prev = begin != 1;

        this.next = this.end < finalPage;
    }
}
