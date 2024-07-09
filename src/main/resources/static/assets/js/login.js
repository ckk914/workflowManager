document
  .getElementById("loginForm")
  .addEventListener("submit", function (event) {
    event.preventDefault(); // 폼 제출 기본 동작 막기

    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    // 로그인 처리 로직 추가
    // 예: AJAX 요청을 통해 서버에 로그인 정보 전송 및 응답 처리

    // 예시 코드: 로그인 실패 시 에러 메시지 표시
    var errorMessage = document.getElementById("error-message");
    errorMessage.textContent = "아이디 또는 비밀번호가 잘못되었습니다.";
  });


