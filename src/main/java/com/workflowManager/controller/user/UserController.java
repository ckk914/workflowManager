package com.workflowManager.controller.user;

import com.workflowManager.dto.DepartmentNameDto;

import com.workflowManager.dto.user.JoinDto;
import com.workflowManager.dto.user.LoginDto;
import com.workflowManager.dto.user.UserChangeDto;
import com.workflowManager.dto.user.findUserDto;
import com.workflowManager.entity.User;
import com.workflowManager.service.LoginResult;
import com.workflowManager.service.UserService;
import com.workflowManager.util.FileUtil;
import com.workflowManager.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    @Value("${project.base.dir}")
    private String rootPath;
    private final UserService userService;

    @GetMapping("/")

    public String list(Model model, HttpSession session) {
        String userId = LoginUtil.getLoggedInUserAccount(session);
//        String userName = LoginUtil.getLoggedInUser(session).getNickName();
//        String departmentId = LoginUtil.getLoggedInDepartmentId(session);
//        String departmentName = userService.findOneDepartmentName(departmentId);
        if (userId == null) {
            // 세션에 로그인 정보가 없을 경우, 로그인 페이지로 리다이렉트 또는 홈 화면 표시
            log.info("userId 가 없습니다. {}", session.getAttribute("userId"));
            model.addAttribute("userId", null);
            model.addAttribute("departmentId", null);
            model.addAttribute("departmentName", null);
            model.addAttribute("userName", null);
            return "indexHome";  // indexHome.jsp에서 로그인을 유도하는 기능
        }
        try {
            String userName = LoginUtil.getLoggedInUser(session).getNickName();
            String departmentId = LoginUtil.getLoggedInDepartmentId(session);
            String departmentName = userService.findOneDepartmentName(departmentId);

            model.addAttribute("userId", userId);
            model.addAttribute("departmentId", departmentId);
            model.addAttribute("departmentName", departmentName);
            model.addAttribute("userName", userName);
        } catch (Exception e) {
            log.error("로그인한 유저 정보를 JSON에서 가져올 수 없습니다. ", e);
        }
        return "indexHome";
    }

    //로그인 페이지 이동  Get
    @GetMapping("/login")
    public String login() {


        return "/Login/login";
    }

//    @PostMapping("/login")
@PostMapping({"/login", "/"})
    public String signIn(LoginDto dto,
                         RedirectAttributes ra,                    //리다이렉트 할때 쓰는 전송 객체
                         HttpServletRequest request,        //세션 사용 목적⭐️
                         HttpServletResponse response) {

        log.info("/members/sign-in POST");
        log.debug("🖐️Login parameter: {}", dto);

        // 세션 얻기⭐️
        HttpSession session = request.getSession();

        //세션을 서비스에게 보냄
        LoginResult result = userService.authenticate(dto, session, response);

        log.debug("조회결과:result: {}",result);

        //        model.addAttribute("result", result); // (X)
        //리다이렉트 할때 쓰는 전송 객체⭐️
        //ㄴ 리다이렉트 객체를 써야 리다이렉트 페이지 까지 전송된다.~!
        ra.addFlashAttribute("result", result);

        if (result == LoginResult.SUCCESS) {
            //리다이렉트가 있는지 확인해본다.
            String redirect = (String) session.getAttribute("redirect");
            if (redirect != null) {
                session.removeAttribute("redirect");

                return "redirect:" + redirect;
            }

            return "redirect:/"; // 로그인 성공시
        }

        return "redirect:/login";
    }



    //회원 가입 페이지 이동 Get
    @GetMapping("/join")
    public String join(Model model) {
        //켜기 전에 부서 넘겨주기
        List<DepartmentNameDto> dList = userService.findDepartmentName();
//        System.out.println("dList = " + dList);
        model.addAttribute("dList", dList);
        return "/Login/UserRegister";
    }

    //회원가입 처리 POST
    @PostMapping("/join")
    public String signUp(@Validated JoinDto dto) {
        log.info("welcome~ /join POST ");

        File root = new File(rootPath);
        log.debug("root: {}", root);
        if (!root.exists()) root.mkdirs(); //폴더 없으면 생성

        log.debug("parameter: {}", dto);
        // 프로필 사진 추출
        MultipartFile profileImage = dto.getProfileImg();
        String profilePath = null;
        if (!profileImage.isEmpty()) {
            log.debug("attached profile image name: {}", profileImage.getOriginalFilename());
            // 서버에 업로드 후 업로드 경로 반환
            profilePath = FileUtil.uploadFile(rootPath, profileImage);
            log.debug("profilePath:{}", profilePath);
        }

        boolean flag = userService.join(dto, profilePath);

        return flag ? "redirect:/login" : "redirect:/join";
    }

    // 아이디, 이메일 중복검사 비동기 요청 처리
    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity<?> check(String type, String keyword) {
        boolean flag = userService.checkIdentifier(type, keyword);
        log.debug("{} 중복체크 결과? {}", type, flag);
        return ResponseEntity
                .ok()
                .body(flag);
    }

    //로그 아웃 처리
    @GetMapping("/member/logout")
    public String logOut(
            HttpServletRequest request
            ,HttpServletResponse response){  //세션 가져오기.(스프링이 알아서 해줌)

        log.debug("삭제 시작~!");
        //세션에서 로그인 기록 삭제
        HttpSession session = request.getSession();
        //자동 로그인 상태인지 확인
        if(LoginUtil.isAutoLogin(request)) {
            //쿠키를 제거하고, db에도 자동 로그인 관련 데이터를 원래대로 해놓음
            userService.autoLoginClear(request, response);

        }
        session.removeAttribute("login");
        //세션을 초기화 (reset)
        session.invalidate();  //무효화
        //홈으로 보내기
        return "redirect:/";

    }

    //비밀번호 찾기! 🧤
    @GetMapping("/forgot-password")
        public String forgotPassword(){

        return "/Login/forgotPassword";
        }

    @PostMapping("/forgot-password")
    public String checkIdExists(findUserDto dto,
                                RedirectAttributes ra                   //리다이렉트 할때 쓰는 전송 객체
                                ){
                log.info("welcome~ /join POST ");
        System.out.println("🧤dto = " + dto);
        //검색 !
        User resultUser = userService.findOneUser(dto);
        ra.addFlashAttribute("resultUserId", resultUser.getUserId());
        ra.addFlashAttribute("resultEmpId",resultUser.getEmployeeId());
        ra.addFlashAttribute("resultEmail",resultUser.getUserEmail());
        ra.addFlashAttribute("resultName",resultUser.getUserName());
//        System.out.println("🧤resultUser = " + resultUser);

        //리다이렉트 할때 쓰는 전송 객체⭐️
        //ㄴ 리다이렉트 객체를 써야 리다이렉트 페이지 까지 전송된다.~!
        ra.addFlashAttribute("resultUser", resultUser);
        System.out.println("resultUser = " + resultUser);
        //유저 조회 성공!
        if (resultUser != null)
        {
//            System.out.println("🏆 resultUser = " + resultUser);
            //리다이렉트가 있는지 확인해본다.
//            String redirect = (String) session.getAttribute("redirect");
//            if (redirect != null) {
////                session.removeAttribute("redirect");
////
////                return "redirect:" + redirect;
//            }

            return "redirect:/forgotPassword2"; // 로그인 성공시
        }

        return "redirect:/forgot-password";
    }

        @GetMapping("/forgotPassword2")
        public String forgotPassword2(RedirectAttributes ra){
            // ra 객체를 사용하여 데이터 전달
//            System.out.println("rarara = " + ra); //공백임
//            ra.addFlashAttribute("resultUser", ra);
            //일단은 resultUser 데이터가 전달된다.!
            return "/Login/forgotPasswordStep2";
        }
        @PostMapping("/forgotPassword2")
        public String forgotPassword2After(RedirectAttributes ra, Model model){
//            System.out.println("👽ra = " + ra);
//            System.out.println("🛠️model = " + model);

            // 모달창을 띄우기 위해 모달 관련 데이터를 모델에 추가
//            model.addAttribute("showModal", true);
//            model.addAttribute("modalMessage", "Form submitted successfully!");
//
        return "/login";
        }





    @PostMapping("/changePassword")
    @ResponseBody
            public ResponseEntity<Map<String, Object>> changePassword(@RequestBody UserChangeDto userchangedto) {
//                System.out.println("🙏1");
//        System.out.println("userchangedto = " + userchangedto);

                // 비밀번호 변경 로직 구현
                boolean isChanged = changePasswordInDatabase(userchangedto);
                System.out.println("🫡isChanged = " + isChanged);
                Map<String, Object> response = new HashMap<>();
                response.put("success", isChanged);
//
                if (isChanged) {
                    return ResponseEntity.ok(response);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
            } // end changePassword

            private boolean changePasswordInDatabase(UserChangeDto dto) {
                // 실제 데이터베이스에 비밀번호 변경 로직 구현
                userService.updatePassword(dto);
                return true; // 임시로 true 반환
            }
        } //end UserControllerß



