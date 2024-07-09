<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>WORKWAVE:비밀번호 변경</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css"
            integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w=="
            crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="icon" href="/assets/img/workwave_logo.png" />
        <style>
            body {
                font-family: 'NEXON Lv1 Gothic OTF', 'Roboto', sans-serif;
                background-color: #181A33;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }

            .container {
                background-color: #3E489F;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 8px 32px 0 rgba(0, 0, 0, 0.3);
                width: 300px;
            }

            h2 {
                color: #FFFFFF;
                text-align: center;
                margin-bottom: 20px;
                font-size: 24px;
            }

            .input-group {
                position: relative;
                margin-bottom: 15px;
            }

            .input-group i {
                position: absolute;
                left: 10px;
                top: 50%;
                transform: translateY(-50%);
                color: #FFFFFF;
            }

            input[type="text"] {
                width: 257px;
                padding: 10px 10px 10px 35px;
                border: none;
                border-radius: 5px;
                background-color: #a595b1;
                color: #FFFFFF;
                font-size: 14px;
            }

            input[type="text"]::placeholder {
                color: rgba(255, 255, 255, 0.7);
            }

            input[type="submit"] {
                width: 100%;
                padding: 10px;
                border: none;
                border-radius: 5px;
                background-color: #7B317C;
                color: #FFFFFF;
                cursor: pointer;
                font-size: 16px;
                transition: background-color 0.3s;
            }

            input[type="submit"]:hover {
                background-color: #973554;
            }

            .back-link {
                text-align: center;
                margin-top: 15px;
            }

            .back-link a {
                color: #FFFFFF;
                text-decoration: none;
                font-size: 14px;
            }

            .error-message {
                color: #FF6B6B;
                font-size: 14px;
                margin-top: 5px;
                text-align: center;
            }

            input[type="submit"]:disabled {
                background-color: #f5f5f5;
                /* 배경색 변경 */
                color: #999;
                /* 글자색 변경 */
                cursor: not-allowed;
                /* 마우스 커서 변경 */
                border-color: #ccc;
                /* 테두리 색상 변경 */
                opacity: 0.7;
                /* 투명도 조절 */
            }

            /*  */
            .back-link a {
                color: white;
                text-decoration: none;
                font-weight: normal;
            }

            .back-link a:hover {
                color: #a699c8;
                text-decoration: underline;
                font-weight: bold;
            }
        </style>
        <script type="module" src="/assets/js/forgotUser.js"></script>
    </head>

    <body>
        <div class="container">
            <h2>WORKWAVE</h2>
            <form action="/forgot-password" method="post">
                <div class="input-group">
                    <i class="far fa-user-circle"></i>
                    <input type="text" id="userId" name="userId" placeholder="아이디" required>
                </div>
                <div id="error-message" class="error-message"></div>
                <br /><br />
                <input id="forgot-submit" type="submit" value="확인" disabled>
            </form>


            <div class="back-link">
                <a href="/login">로그인으로 돌아가기</a>
            </div>
        </div>

    </body>

    <script>
        // document.getElementById("userId").addEventListener("input", async function () {
        //     const value = this.value;
        //     const isAvailable = await checkAvailability("account", value);
        //     console.log('isAvailable: ', isAvailable);
        //     console.log('value: ', value);
        // });
        // const isAvailable = await checkAvailability("account", value);

        // const errorMessage = "${error-message}";
        // if (errorMessage !== "") {
        //     console.log('errorMessage: ', errorMessage);
        //     document.getElementById("error-message").innerHTML = errorMessage;
        // }
    </script>

    </html>