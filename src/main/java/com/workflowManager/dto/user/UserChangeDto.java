package com.workflowManager.dto.user;


import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserChangeDto {
    private String userId;
    private String password;


    public UserChangeDto toEntity() {

        return UserChangeDto.builder()
                .password(this.password)
                .userId(this.userId)
                .build();
    }

}
