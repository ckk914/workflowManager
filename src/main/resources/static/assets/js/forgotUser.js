import { validateInput } from "./validate.js";
import { debounce } from "./util.js";

const $inputUserId = document.getElementById("userId");
const $errorMessage = document.getElementById("error-message");
const $forgotSubmit = document.getElementById("forgot-submit");

const handleInputChange = debounce((e) => {
  const value = $inputUserId.value;
  validateInput.account(value).then((result) => {
    console.log(result.message);
    if (result.message !== undefined) {
      if (result.message === "아이디가 중복되었습니다.") {
        $errorMessage.innerHTML =
          '<i class="fas fa-check-circle" style="color: forestgreen;"></i>' +
          "아이디가 확인되었습니다.\n 찾기를 진행하세요";
        $errorMessage.style.color = "forestgreen";
        $forgotSubmit.disabled = false;
      } else {
        $errorMessage.innerHTML =
          '<i class="fas fa-exclamation-circle" style="color: #ff7f00;"></i> ' +
          result.message;
        $errorMessage.style.color = "#ff7f00";
        $forgotSubmit.disabled = true;
      }
    } else {
      $errorMessage.innerHTML =
        '<i class="fas fa-exclamation-circle" style="color: #ff7f00;"></i> ' +
        "아이디가 존재하지 않습니다.";
      $errorMessage.style.color = "#ff7f00";
      $forgotSubmit.disabled = true;
    }
  });
}, 500);

$inputUserId.addEventListener("keyup", handleInputChange);

// $input.addEventListener("keyup", (e) => {
//   console.log($input.value);

//   const result = validateInput.account($input.value).then((result) => {
//     console.log(result.message);
//   });
// });
