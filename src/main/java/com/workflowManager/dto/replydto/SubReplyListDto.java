package com.workflowManager.dto.replydto;

import lombok.*;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class SubReplyListDto {

    private List<SubReplyDetailDto> subReplies;

}
