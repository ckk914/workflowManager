import { validateInput } from "./validate.js";
import { debounce } from "./util.js";

// 폼과 회원가입 버튼 요소를 가져옴
const form = document.getElementById("joinForm");
const signupButton = document.getElementById("signup-btn");
//-->

// 각 필드에 대한 정보 배열 (id, 유효성 검증 함수, 에러 메시지 표시 요소, 초기 유효 상태)
const fields = [
  {
    id: "userId",
    validator: validateInput.account,
    errorElement: "idChk",
    valid: false,
  },
  {
    id: "password",
    validator: validateInput.password,
    errorElement: "pwChk",
    valid: false,
  },
  {
    id: "password_check",
    validator: (value) =>
      validateInput.passwordCheck(
        value,
        document.getElementById("password").value
      ),
    errorElement: "pwChk2",
    valid: false,
  },
  {
    id: "userName",
    validator: validateInput.name,
    errorElement: "nameChk",
    valid: false,
  },
  {
    id: "userEmail",
    validator: validateInput.email,
    errorElement: "emailChk",
    valid: false,
  },
  {
    id: "employeeId",
    validator: validateInput.employeeId,
    errorElement: "employeeIdChk",
    valid: false,
  },
  {
    id: "userPosition",
    validator: validateInput.userPosition,
    errorElement: "userPositionChk",
    valid: false,
  },
];

// 버튼 상태를 업데이트하는 함수
const updateButtonState = () => {
  // 모든 valid가 true인지 확인
  const isFormValid = fields.every((field) => field.valid);
  if (isFormValid) {
    signupButton.disabled = false;
    signupButton.style.backgroundColor = "orangered"; // 활성화된 버튼 배경색
  } else {
    signupButton.disabled = true;
    signupButton.style.backgroundColor = "lightgray"; // 비활성화된 버튼 배경색
  }
};

// 입력 필드의 유효성을 검사하고 에러 메시지를 업데이트하는 함수
const validateField = async (field) => {
  const $input = document.getElementById(field.id);
  const $errorSpan = document.getElementById(field.errorElement);
  const isValid = await field.validator($input.value);
  console.log(isValid);

  if (isValid.valid) {
    $input.style.borderColor = "skyblue";
    $errorSpan.innerHTML = '<b class="success">[사용가능합니다.]</b>';
    $errorSpan.style.color = "#228B22";
    field.valid = true;
  } else {
    $input.style.borderColor = "red";
    $errorSpan.innerHTML = `<b class="warning">[${isValid.message}]</b>`;
    $errorSpan.style.color = "#FF6347";
    field.valid = false;
  }
  updateButtonState();
};

// 각 필드에 이벤트 리스너 추가
fields.forEach((field) => {
  const $input = document.getElementById(field.id);
  $input.addEventListener(
    "keyup",
    debounce(() => {
      validateField(field);
      // 비밀번호 필드가 변경될 때 비밀번호 확인 필드의 유효성도 다시 검사
      if (field.id === "password") {
        const passwordCheckField = fields.find(
          (f) => f.id === "password_check"
        );
        validateField(passwordCheckField);
      }
    }, 500)
  );
});

//<--
// 회원가입 버튼 클릭 이벤트 리스너 추가
signupButton.addEventListener("click", (e) => {
  e.preventDefault();
  // console.log("form: ", form);
  const chk = etc_check(); // Changed 'boolean' to 'const'
  if (chk) {
    form.submit();
  } else {
    return;
  }
});

const etc_check = () => {
  const $select = document.getElementById("departmentId"); // Added 'const'
  if ($select.value === "") {
    const $errorSpan = document.getElementById("departmentChk");
    $errorSpan.innerHTML = `<b class="warning">[부서를 선택해주세요.]</b>`;
    return false;
  }
  return true;
};

// 페이지 로드 시 초기 버튼 상태 업데이트
updateButtonState();
