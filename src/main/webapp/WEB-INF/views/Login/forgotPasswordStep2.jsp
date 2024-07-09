<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>WORKWAVE:비밀번호찾기-2</title>
        <link href="/assets/css/forgotPasswordStep2.css" rel="stylesheet" />
        <link rel="icon" href="/assets/img/workwave_logo.png" />
    </head>

    <body>
        <div class="container">
            <h2></h2>
            <form id="myForm" onsubmit="return validateForm()">
                <div class="password-change-title">비밀번호 찾기</div><br />
                <div class="input-group">
                    <i class="fas fa-user"></i>
                    <input type="text" id="userName" name="userName" placeholder="이름" required>
                </div>
                <div class="input-group">
                    <i class="fas fa-user"></i>
                    <input type="text" id="employeeId" name="employeeId" placeholder="사번" required>
                </div>
                <div class="input-group">
                    <i class="fas fa-envelope"></i>
                    <input type="email" id="userEmail" name="userEmail" placeholder="이메일" required>
                </div>
                <input type="button" value="확인" onclick="validateForm()">
            </form>
            <div class="back-link">
                <a href="/login">로그인으로 돌아가기</a>
            </div>

            <!-- The Modal -->
            <div id="myModal" class="modal">
                <!-- Modal content -->
                <div class="modal-content">
                    <span class="close">&times;</span>
                    <p id="modal-message"></p>
                    <form id="passwordForm" action="changePassword" method="post">
                        <div class="password-change-title">비밀번호 찾기</div><br />
                        <div class="input-group">
                            <i class="fas fa-lock"></i>
                            <input type="password" id="newPassword" name="newPassword" placeholder="새 비밀번호" required>
                        </div>
                        <div class="input-group">
                            <i class="fas fa-lock"></i>
                            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="비밀번호 확인"
                                required>
                        </div>
                        <input type="submit" value="비밀번호 변경" onclick="return checkPassword()">
                    </form>
                </div>
                <!-- 비밀번호 불일치 모달 -->
                <div id="passwordMismatchModal" class="modal">
                    <div class="modal-content">
                        <span class="close">&times;</span>
                        <p>새 비밀번호와 비밀번호 확인이<br /> 일치하지 않습니다.<br />다시 확인해주세요.</p>
                    </div>
                </div>
            </div>
        </div>
        <!-- <script type="module" src="/assets/js/forgotPasswordStep2.js"></script> -->
    </body>
    <script>
        // Get the modal
        var modal = document.getElementById('myModal');

        // Get the <span> element that closes the modal
        var span = document.getElementsByClassName("close")[0];

        // When the user clicks the button, open the modal
        document.getElementById('myForm').addEventListener('submit', function (e) {
            e.preventDefault(); // Prevent the form from submitting
            modal.style.display = "block";
        });

        // When the user clicks on <span> (x), close the modal
        span.onclick = function () {
            modal.style.display = "none";
        }

        // When the user clicks anywhere outside of the modal, close it
        window.onclick = function (event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }

        function validateForm() {
            // input data
            var userName = document.getElementById('userName').value;
            var employeeId = document.getElementById('employeeId').value;
            var userEmail = document.getElementById('userEmail').value;
            // console.log('🙏[입력내용] userName:' + userName + ' employeeId:' + employeeId + ' userEmail: ' + userEmail);
            // console.log('✌🏻result= ' + '${resultUser}');
            modal.style.display = "block";

            // 문자열 분해 [db data]
            const db_uid = `${resultUserId}`;
            const db_eid = `${resultEmpId}`;
            const db_uEmail = `${resultEmail}`;
            const db_uName = `${resultName}`;

            // console.log("☂️찐 =  " + db_uid + " " + db_eid + " " + db_uEmail + " " + db_uName);

            if (userName === db_uName && employeeId === db_eid && userEmail === db_uEmail) {
                document.getElementById('passwordForm').style.display = "block";
                document.getElementById('modal-message').innerText = '';
                $modalBox = document.querySelector('.modal-content');
                $modalBox.style.backgroundColor = "#3E48A1";
            }
            else {
                document.getElementById('passwordForm').style.display = "none";
                document.getElementById('modal-message').innerText = '정보 불일치!\n다시 확인해주세요.';
                $modalBox = document.querySelector('.modal-content');
                $modalBox.style.backgroundColor = "#973554";
            }
            return true; // For demonstration purposes, always returns true
        }

        function checkPassword() {

            const db_uid = `${resultUserId}`;

            var newPassword = document.getElementById('newPassword').value;
            var confirmPassword = document.getElementById('confirmPassword').value;
            var mismatchModal = document.getElementById('passwordMismatchModal');
            var modalContent = mismatchModal.querySelector('.modal-content p');
            var $modalBox = document.querySelector('.modal-content');

            // 비밀번호 길이 검사 (4~12자)
            if (newPassword.length < 4 || newPassword.length > 12) {
                showPasswordError("비밀번호는 4~12자리여야 합니다.", "#973554");
                return false;
            }

            // 특수문자 포함 검사
            var specialCharsRegex = /[!@#$%^&*(),.?":{}|<>]/;
            if (!specialCharsRegex.test(newPassword)) {
                showPasswordError("비밀번호에는 특수문자가 포함되어야 합니다.", "#973554");
                return false;
            }

            // 새 비밀번호와 비밀번호 확인 일치 검사
            if (newPassword !== confirmPassword) {
                showPasswordError("새 비밀번호와 비밀번호 확인이<br/> 일치하지 않습니다.<br/>다시 확인해주세요.", "#973554");
                return false;
            }

            //-->
            // console.log("👽전송 폼->" + db_uid + " " + newPassword);
            // 비밀번호 변경 요청
            fetch('/changePassword', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    userId: db_uid, // 수정된 부분
                    password: newPassword
                })
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        showPasswordError("비밀번호가 성공적으로 변경되었습니다.", "#006400");
                        setTimeout(() => {
                            window.location.href = "/login"; // 2초 후 로그인 페이지로 리다이렉트
                        }, 2000);
                    } else {
                        showPasswordError("비밀번호 변경에 실패했습니다. 다시 시도해주세요.", "#973554");
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    showPasswordError("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.", "#973554");
                });

            return false; // 폼 기본 제출 방지

            //<--

            function showPasswordError(message, backgroundColor) {
                modalContent.innerHTML = message;
                mismatchModal.style.display = "block";
                const $passowrdMismatchModal = document.getElementById('passwordMismatchModal');
                //   #passwordMismatchModal   외부 배경
                const $content = document.querySelector('#passwordMismatchModal .modal-content');
                $content.style.backgroundColor = backgroundColor;

                // 모달 닫기 버튼 이벤트 리스너
                var closeBtn = mismatchModal.getElementsByClassName("close")[0];
                closeBtn.onclick = function () {
                    mismatchModal.style.display = "none";
                }

                // 모달 외부 클릭 시 닫기
                window.onclick = function (event) {
                    if (event.target == mismatchModal) {
                        mismatchModal.style.display = "none";
                    }
                }
            }
        } //changePassword (end) 


        ///////////////////
        //new
        // function checkPassword() {
        //     if (checkPasswordValidity()) {
        //         return true;
        //     } else {
        //         return false;
        //     }
        // }

        //테스트 버전의 코드 하단
        // function checkPasswordValidity() {
        //     var newPassword = document.getElementById('newPassword').value;
        //     var confirmPassword = document.getElementById('confirmPassword').value;

        //     // 비밀번호 길이 검사 (4~12자)
        //     if (newPassword.length < 4 || newPassword.length > 12) {
        //         // document.getElementById('passwordForm').style.display = "none";
        //         var mismatchModal = document.getElementById('passwordMismatchModal');
        //         mismatchModal.innerText = '비밀번호는 4~12자리여야 합니다.';
        //         mismatchModal.style.display = "block";

        //         $modalBox = document.querySelector('.modal-content');
        //         $modalBox.style.backgroundColor = "#973554";
        //         // alert("비밀번호는 4~12자리여야 합니다.");
        //         return false;
        //     }

        //     // 특수문자 포함 검사
        //     var specialCharsRegex = /[!@#$%^&*(),.?":{}|<>]/;
        //     if (!specialCharsRegex.test(newPassword)) {
        //         // alert("비밀번호에는 특수문자가 포함되어야 합니다.");
        //         // document.getElementById('passwordForm').style.display = "none";
        //         var mismatchModal = document.getElementById('passwordMismatchModal');
        //         mismatchModal.innerText = '비밀번호에는 특수문자가 포함되어야 합니다.';
        //         mismatchModal.display = "block";

        //         $modalBox = document.querySelector('.modal-content');
        //         $modalBox.style.backgroundColor = "#973554";
        //         return false;
        //     }

        //     // 새 비밀번호와 비밀번호 확인 일치 검사
        //     if (newPassword !== confirmPassword) {
        //         var mismatchModal = document.getElementById('passwordMismatchModal');
        //         mismatchModal.style.display = "block";

        //         var closeBtn = mismatchModal.getElementsByClassName("close")[0];
        //         closeBtn.onclick = function () {
        //             mismatchModal.style.display = "none";
        //         }
        //         window.onclick = function (event) {
        //             if (event.target == mismatchModal) {
        //                 mismatchModal.style.display = "none";
        //             }
        //         }
        //         return false;
        //     }

        //     return true;
        // }
    </script>

    </html>