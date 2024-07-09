package com.workflowManager.entity.schedule;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class ColorIndex {
    private int colorIndexId;
    private String colorCode;
    private String colorName;
}
