package com.workflowManager.common.boardpage;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

// 페이지 화면 렌더링에 필요한 정보들을 계산
@Getter
@ToString
@EqualsAndHashCode
public class PageMaker {

    // 한 화면에 페이지를 몇개씩 배치할 것인지??
    private static final int PAGE_COUNT = 10;

    // 페이지 시작번호와 끝번호
    private int begin, end, finalPage;

    // 이전, 다음버튼 활성화 여부
    private boolean prev, next;

    // 총 게시물 수
    private int totalCount;

    // 현재 페이지 정보
    private Page pageInfo;

    public PageMaker(Page page, int totalCount) {
        this.pageInfo = page;
        this.totalCount = totalCount;
        makePageInfo();
    }

    // 페이지 생성에 필요한 데이터를 만드는 알고리즘
    private void makePageInfo() {

        // 1. end값을 계산
        /*
            지금 사용자가 7페이지를 보고 있다면
            페이지 구간: 1 ~ 10 구간

            지금 사용자가 24페이지를 보고 있다면
            페이지 구간: 21 ~ 30 구간

            // 5개씩 페이지를 배치하는 경우는
            7page :  6 ~ 10
            24page :  21 ~ 25

            // 공식: (올림 (현재 사용자가 위치한 페이지넘버 / 한 화면에 보여줄 페이지 수)) * 한 화면에 보여줄 페이지 수
         */

        this.end = (int) (Math.ceil((double) pageInfo.getPageNo() / PAGE_COUNT) * PAGE_COUNT);

        // 2. begin
        this.begin = this.end - PAGE_COUNT + 1;

        // 3. 마지막 페이지 구간에서 end값 보정
        /*
            총 게시물이 237개이고 한 화면에 게시물을 10개씩 배치하고 있다면

            1 ~ 10페이지 구간 : 게시물이 총 100개
            11 ~ 20페이지 구간 : 게시물이 총 100개
            21 ~ 30페이지 구간 : 게시물이 총 37개

            -> 과연 마지막 구간에서 end값이 30이 맞는가?
            -> 실제로는 24로 보정되어야 함.

            // 마지막 페이지 번호를 구하는 공식

            게시물이 351개 한페이지당 게시물 10개씩 배치
            끝페이지 번호?  36페이지

            올림(총 게시물 수 / 한 페이지당 배치할 게시물 수)

         */

        this.finalPage = (int) Math.ceil((double) totalCount / pageInfo.getAmount());

        // 마지막 구간에서 end값을 finalPage으로 보정
        if (finalPage < this.end) {
            this.end = finalPage;
        }

        // 4. 이전버튼 활성화 여부
        this.prev = begin != 1;

        // 5. 다음 버튼 활성화 여부
        this.next = this.end < finalPage;

    }

}

