package com.workflowManager.entity.board;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {
    private int noticeId;
    private String userId;
    private String noticeTitle;
    private String noticeContent;
    private LocalDateTime noticeCreatedAt;
    private LocalDateTime noticeUpdatedAt;
    private String attachment;
    private int noticeColorIndexId;
    private LocalDateTime teamEventDate;
    private String departmentId;
}
