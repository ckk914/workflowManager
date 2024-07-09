package com.workflowManager.dto.replydto;

import com.workflowManager.common.boardpage.PageMaker;
import com.workflowManager.dto.user.LoginUserInfoDto;
import lombok.*;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class ReplyListDto {

    @Setter
    private LoginUserInfoDto loginUser;
    private PageMaker pageInfo;
    private List<ReplyDetailDto> replies;

}
