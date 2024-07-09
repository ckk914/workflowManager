//package com.workwave.controller;
//
//import com.workwave.dto.DepartmentNameDto;
//import com.workwave.dto.user.JoinDto;
//import com.workwave.dto.user.LoginDto;
//import com.workwave.service.LoginResult;
//import com.workwave.service.UserService;
//import com.workwave.util.FileUtil;
//import com.workwave.util.LoginUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.File;
//import java.util.List;
//
//@Controller
//@RequestMapping("/")
//@Slf4j
//@RequiredArgsConstructor
//public class HomeController {
//
//    private final UserService userService;
//
//    @GetMapping("/")
//    public String home() {
//        return "indexHome"; // 홈 인덱스 파일은 indexHome.jsp 입니다.
//    }
//
//    // 로그인 페이지 이동  Get
//    @GetMapping("/login")
//    public String login() {
//        return "/Login/login";
//    }
//
//    @PostMapping({"/login", "/"})
//    public String signIn(LoginDto dto,
//                         RedirectAttributes ra,                    //리다이렉트 할때 쓰는 전송 객체
//                         HttpServletRequest request,        //세션 사용 목적⭐️
//                         HttpServletResponse response) {
//
//        log.info("/members/sign-in POST");
//        log.debug("🖐️Login parameter: {}", dto);
//
//        // 세션 얻기⭐️
//        HttpSession session = request.getSession();
//
//        //세션을 서비스에게 보냄
//        LoginResult result = userService.authenticate(dto, session, response);
//
//        log.debug("조회결과:result: {}", result);
//
//        ra.addFlashAttribute("result", result);
//
//        if (result == LoginResult.SUCCESS) {
//            //리다이렉트가 있는지 확인해본다.
//            String redirect = (String) session.getAttribute("redirect");
//            if (redirect != null) {
//                session.removeAttribute("redirect");
//
//                return "redirect:" + redirect;
//            }
//
//            return "redirect:/"; // 로그인 성공시
//        }
//
//        return "redirect:/login";
//    }
//
//    //회원 가입 페이지 이동 Get
//    @GetMapping("/join")
//    public String join(Model model) {
//        //켜기 전에 부서 넘겨주기
//        List<DepartmentNameDto> dList = userService.findDepartmentName();
//        model.addAttribute("dList", dList);
//        return "/Login/UserRegister";
//    }
//
//    //회원가입 처리 POST
//    @PostMapping("/join")
//    public String signUp(@Validated JoinDto dto) {
//        log.info("welcome~ /join POST ");
//
//        File root = new File(rootPath);
//        log.debug("root: {}", root);
//        if (!root.exists()) root.mkdirs(); //폴더 없으면 생성
//
//        log.debug("parameter: {}", dto);
//        // 프로필 사진 추출
//        MultipartFile profileImage = dto.getProfileImg();
//        String profilePath = null;
//        if (!profileImage.isEmpty()) {
//            log.debug("attached profile image name: {}", profileImage.getOriginalFilename());
//            // 서버에 업로드 후 업로드 경로 반환
//            profilePath = FileUtil.uploadFile(rootPath, profileImage);
//            log.debug("profilePath:{}", profilePath);
//        }
//
//        boolean flag = userService.join(dto, profilePath);
//
//        return flag ? "redirect:/login" : "redirect:/join";
//    }
//
//    // 아이디, 이메일 중복검사 비동기 요청 처리
//    @GetMapping("/check")
//    @ResponseBody
//    public ResponseEntity<?> check(String type, String keyword) {
//        boolean flag = userService.checkIdentifier(type, keyword);
//        log.debug("{} 중복체크 결과? {}", type, flag);
//        return ResponseEntity
//                .ok()
//                .body(flag);
//    }
//
//    // 로그 아웃 처리
//    @GetMapping("/member/logout")
//    public String logOut(
//            HttpServletRequest request,
//            HttpServletResponse response) {  //세션 가져오기.(스프링이 알아서 해줌)
//
//        log.debug("삭제 시작~!");
//        //세션에서 로그인 기록 삭제
//        HttpSession session = request.getSession();
//        //자동 로그인 상태인지 확인
//        if (LoginUtil.isAutoLogin(request)) {
//            //쿠키를 제거하고, db에도 자동 로그인 관련 데이터를 원래대로 해놓음
//            userService.autoLoginClear(request, response);
//        }
//        session.removeAttribute("login");
//        //세션을 초기화 (reset)
//        session.invalidate();  //무효화
//        //홈으로 보내기
//        return "redirect:/";
//    }
//}