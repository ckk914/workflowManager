//package com.workwave.repository;
//
//import com.workwave.entity.LunchMateBoard;
//import lombok.RequiredArgsConstructor;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class LunchMateBoardRepositoryImpl implements LunchMateBoardRepository {
//
//    private final JdbcTemplate template;
//
//    @Override
//    public List<LunchMateBoard> findAll() {
//        String sql = "SELECT * FROM Lunch_posts";
//        return template.query(sql, (rs, rowNum) -> new LunchMateBoard(rs));
//    }
//
//    @Override
//    public boolean save(LunchMateBoard lunchMateBoard) {
//        String sql = "INSERT INTO Lunch_posts " +
//                "(title, content, writer) " +
//                "VALUES (?,?,?)";
//        return template.update(sql,
//                board.getTitle(),
//                board.getContent(),
//                board.getWriter()) == 1;
//
//
////
////        private String lunchPostTitle; // 글 제목
////        private String eatTime; // 식사 일정
////        private String lunchLocation; // 식당 위치 (식당명)
////        private String lunchMenuName; // 메뉴 이름
////        private int lunchAttendees; // 최대 모집인원 수
////
////        public LunchMateBoard toEntity() {
////            LunchMateBoard lmb = new LunchMateBoard();
////            lmb.setLunchPostTitle(this.lunchPostTitle);
////            lmb.setEatTime(this.eatTime);
////            lmb.setLunchLocation(this.lunchLocation);
////            lmb.setLunchMenuName(this.lunchMenuName);
////            lmb.setLunchAttendees(this.lunchAttendees);
////
////            return lmb;
////    }
//
//    }
//}
