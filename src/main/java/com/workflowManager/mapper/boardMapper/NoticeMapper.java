package com.workflowManager.mapper.boardMapper;

import com.workflowManager.entity.board.Notice;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeMapper {
    boolean save(Notice notice);
}
