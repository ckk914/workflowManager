package com.workflowManager.service.boardservice;

import com.workflowManager.common.boardpage.Search;
import com.workflowManager.dto.boarddto.BoardDetailDto;
import com.workflowManager.dto.boarddto.BoardListDto;
import com.workflowManager.dto.boarddto.BoardUpdateDto;
import com.workflowManager.dto.boarddto.BoardWriteDto;
import com.workflowManager.entity.board.Board;
import com.workflowManager.mapper.boardMapper.BoardMapper;
import com.workflowManager.util.LoginUtil;
import com.workflowManager.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    @Autowired
    private final BoardMapper boardMapper;

    public boolean save(BoardWriteDto dto, HttpSession session) {

        // 비밀번호 해싱
        String rawPassword = dto.getBoardPassword();
        String hashedPassword = PasswordUtil.hashPassword(rawPassword);

        Board b = dto.toEntity();
        b.setUserId(LoginUtil.getLoggedInUserAccount(session));
        b.setBoardPassword(hashedPassword); // 해싱된 비밀번호 설정

        log.info("Saving Board Entity: {}", b);

        return boardMapper.save(b);
    }

    public List<BoardListDto> findAll(Search page) {
        // boardMapper.findAll()이 List<Board>를 반환한다고 가정
        List<Board> boards = boardMapper.findAll(page);

        // Board 객체를 BoardListDto 객체로 변환
        return boards.stream()
                .map(board -> {
                    // 첫 번째 이미지 태그 추출
                    String firstImageTag = getFirstImageTag(board.getBoardContent());

                    return BoardListDto.builder()
                            .boardId(board.getBoardId())
                            .boardTitle(board.getBoardTitle())
                            .boardContent(firstImageTag) // 첫 번째 이미지 태그로 설정
                            .userId(board.getUserId())
                            .boardNickname(board.getBoardNickname())
                            .boardCreatedAt(board.getBoardCreatedAt())
                            .replyCount(board.getReplyCount())
                            .viewCount(board.getViewCount())
                            .likes(board.getLikes())
                            .dislikes(board.getDislikes())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public BoardDetailDto findOne(int boardId) {

        Board b = boardMapper.findOne(boardId);// Board 객체를 가져옴

        log.info(String.valueOf(b));

        if (b != null) {

            return BoardDetailDto.builder()
                    .boardId(b.getBoardId())
                    .userId(b.getUserId())
                    .boardContent(b.getBoardContent())
                    .boardTitle(b.getBoardTitle())
                    .viewCount(b.getViewCount())
                    .boardNickname(b.getBoardNickname())
                    .boardPassword(b.getBoardPassword())
                    .likes(b.getLikes())
                    .dislikes(b.getDislikes())
                    .boardCreatedAt(b.getBoardCreatedAt())
                    .boardUpdatedAt(b.getBoardUpdatedAt())
                    .build();

        }

        return null; // 조회된 데이터가 없으면 null 반환
    }

    // 게시물의 갯수를 확인
    public int boardListCount(Search page) {

        return boardMapper.count(page);
    }

    // 게시물 삭제
    public boolean delete(int boardId) {

        return boardMapper.delete(boardId);
    }

    // 게시물 수정
    public Boolean update(BoardUpdateDto dto) {

        Board one = boardMapper.findOne(dto.getBoardId());

        one.setBoardContent(dto.getNewContent());
        one.setBoardUpdatedAt(dto.getBoardUpdatedAt());

        log.info("upadte DTO: {}", one);

        return boardMapper.update(one);
    }

    public Boolean updateViewCount(int boardId, HttpSession session) {

        final long VIEW_COUNT_INTERVAL_MINUTES = 30;

        @SuppressWarnings("unchecked")
        Map<Integer, LocalDateTime> viewTimestamps
                = (Map<Integer, LocalDateTime>) session.getAttribute("viewTimestamps");
        if (viewTimestamps == null) {
            viewTimestamps = new HashMap<>();
            session.setAttribute("viewTimestamps", viewTimestamps);
        }

        LocalDateTime lastViewTime = viewTimestamps.get(boardId);
        LocalDateTime now = LocalDateTime.now();

        if (lastViewTime == null ||
                ChronoUnit.MINUTES.between(lastViewTime, now) >= VIEW_COUNT_INTERVAL_MINUTES) {

            if (boardMapper.updateViewCount(boardId)) {
                viewTimestamps.put(boardId, now);
                return true;

            }
        }

        return false;
    }

    public Boolean upLikeCount(int boardId) {


        boolean upLikeCount = boardMapper.upLikeCount(boardId);

        return upLikeCount;
    }

    public Boolean downLikeCount(int boardId) {


        boolean downLikeCount = boardMapper.downLikeCount(boardId);

        return downLikeCount;

    }

    public Boolean upDislikeCount(int boardId) {

        boolean upDislikeCount = boardMapper.upDislikeCount(boardId);

        return upDislikeCount;
    }

    public Boolean downDislikeCount(Integer boardId) {

        boolean downDislikeCount = boardMapper.downDislikeCount(boardId);

        return downDislikeCount;

    }

    private String getFirstImageTag(String content) {
        if (content == null || content.isEmpty()) {
            return "";
        }

        // 첫 번째 <img> 태그를 찾기 위한 정규 표현식 (style 속성 제외)
        Pattern pattern = Pattern.compile("<img[^>]*src=[\"']([^\"']+)[\"'][^>]*>");
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            String imgTag = matcher.group(0);
            // style 속성을 제거하기 위한 정규 표현식
            imgTag = imgTag.replaceAll("\\s*style=[\"'][^\"']*[\"']", "");
            return imgTag;
        }

        return ""; // 이미지 태그가 없으면 빈 문자열 반환
    }
}
