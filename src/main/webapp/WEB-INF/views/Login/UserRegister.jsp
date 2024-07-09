<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <!DOCTYPE html>
    <html>

    <head>
      <meta charset="UTF-8" />
      <title>workwave:sign-up</title>
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@4.0.1/reset.min.css" />
      <link href="https://fonts.google.com/specimen/Roboto" rel="stylesheet" />
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css"
        integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
      <link href="/assets/css/UserRegister.css" rel="stylesheet" />
      <link rel="icon" href="/assets/img/workwave_logo.png" />
    </head>

    <body>
      <div class="container wrap">
        <div class="row">
          <div>
            <div class="card">
              <div class="card-body">
                <form action="/join" name="join" id="joinForm" method="post" enctype="multipart/form-data">
                  <div class="logo-container">
                    <img src="/assets/img/workwave_logo_Background Removal.png" alt="Logo" class="logo">
                    <span class="logo-text">WorkWave</span>
                  </div>

                  <div class="profile">
                    <div class="thumbnail-box">
                      <img class="profile-img" src="/assets/img/image-add.png" alt="프로필 썸네일" />
                    </div>

                    <label id="profile-label" for="profile-img">프로필 이미지 추가</label>

                    <input type="file" id="profile-img" accept="image/*" name="profileImg" />
                  </div>
                  <table>
                    <tr>
                      <td>
                        <div class="form-group">
                          <label for="userId"><i class="fas fa-user"></i><strong>아이디를 입력해주세요.</strong></label>
                          <input type="text" name="userId" id="userId" class="signUpInputStyle" maxlength="14"
                            required="required" aria-required="true" placeholder="숫자와 영어로 4-14자" />
                          <span id="idChk"></span>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class="form-group">
                          <label for="password"><i class="fas fa-lock"></i><strong>비밀번호를 입력해주세요.</strong></label>
                          <input type="password" size="17" maxlength="20" id="password" name="password"
                            class="signUpInputStyle" maxlength="20" required="required" aria-required="true"
                            placeholder="영문과 특수문자를 함한 최소 8자" />
                          <span id="pwChk"></span>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class="form-group">
                          <label for="password_check"><i class="fas fa-lock"></i><strong>비밀번호를 재확인해주세요.</strong></label>
                          <input type="password" size="17" maxlength="20" id="password_check" name="pw_check"
                            class="signUpInputStyle" maxlength="20" required="required" aria-required="true"
                            placeholder="비밀번호가 일치해야합니다." />
                          <span id="pwChk2"></span>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class="form-group">
                          <label for="userName"><i class="fas fa-user"></i><strong>이름을 입력해주세요.</strong></label>
                          <input type="text" name="userName" id="userName" class="signUpInputStyle" maxlength="6"
                            required="required" aria-required="true" placeholder="한글로 최대 6자" />
                          <span id="nameChk"></span>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class="form-group">
                          <label for="userEmail"><i class="fas fa-envelope"></i><strong>이메일을 입력해주세요.</strong></label>
                          <input type="email" name="userEmail" id="userEmail" class="signUpInputStyle"
                            required="required" aria-required="true" placeholder="ex) abc@mvc.com" />
                          <span id="emailChk"></span>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class="form-group">
                          <label for="departmentId"><i class="fas fa-building"></i><strong>부서를 선택해주세요.</strong></label>
                          <select class="signUpInputStyle2 select_department" id="departmentId" name="departmentId"
                            required>
                            <option value=""></option>
                            <c:forEach items="${dList}" var="department">
                              <option value="${department.departmentId}">
                                ${department.departmentName}
                              </option>
                            </c:forEach>
                          </select>
                          <span id="departmentChk"></span>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class="form-group">
                          <label for="employeeId"><i class="fas fa-id-card-alt"></i><strong>사원 ID를
                              작성해주세요.</strong></label>
                          <input type="text" class="signUpInputStyle" id="employeeId" name="employeeId"
                            placeholder="P000" required />
                          <span id="employeeIdChk"></span>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class="form-group">
                          <label for="userPosition"><i class="fas fa-user-tie"></i><strong>직책을 입력해주세요.</strong></label>
                          <input type="text" class="signUpInputStyle" id="userPosition" name="userPosition"
                            placeholder="사원,대리,과장,차장..." required />
                          <span id="userPositionChk"></span>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <input type="button" value="회원가입" class="btn-join" id="signup-btn" />
                      </td>
                    </tr>
                  </table>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
      <script type="module" src="/assets/js/UserRegister.js"></script>
      <script>
        document.addEventListener("DOMContentLoaded", function () {
          const $profile = document.querySelector(".profile-img");
          const $fileInput = document.getElementById("profile-img");

          $profile.addEventListener("click", (e) => {
            $fileInput.click();
          });

          $fileInput.addEventListener("change", (e) => {
            console.log("file changed!");
            const fileData = $fileInput.files[0];
            const reader = new FileReader();
            reader.readAsDataURL(fileData);

            reader.onloadend = (e) => {
              const $img = document.querySelector(".thumbnail-box img");
              const $profileLabel = document.getElementById("profile-label");
              $img.src = reader.result;
              $profileLabel.style.display = "none";
            };
          });
        });
      </script>
    </body>

    </html>