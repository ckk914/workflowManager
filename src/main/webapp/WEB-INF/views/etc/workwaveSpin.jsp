<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>WorkWave 3D Rotating Text</title>
        <style>
            body {
                margin: 0;
                height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
                background-color: #f0f0f0;
                overflow: hidden;
                perspective: 1000px;
            }

            #container {
                transform-style: preserve-3d;
                animation: rotate 12s ease-in-out infinite;
            }

            #workwave {
                font-size: 48px;
                font-weight: bold;
                transform-style: preserve-3d;
            }

            #workwave span {
                display: inline-block;
                position: relative;
                transform-style: preserve-3d;
                transition: color 0.5s ease;
            }

            @keyframes rotate {

                0%,
                100% {
                    transform: rotateY(60deg);
                }

                50% {
                    transform: rotateY(-60deg);
                }
            }
        </style>
    </head>

    <body>
        <div id="container">
            <div id="workwave"></div>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const text = document.getElementById('workwave');
                const letters = "workwave".split('');
                const radius = 200; // 원의 반지름

                letters.forEach((letter, i) => {
                    const span = document.createElement('span');
                    span.textContent = letter;
                    const angle = (i / letters.length) * Math.PI * 2; // 각 글자의 각도
                    const x = Math.sin(angle) * radius;
                    const z = Math.cos(angle) * radius;
                    span.style.transform = `translate3d(${x}px, 0, ${z}px) rotateY(${angle}rad)`;
                    text.appendChild(span);

                    // 3D text effect
                    span.style.textShadow = `
                    1px 1px 1px #999,
                    2px 2px 1px #999,
                    3px 3px 1px #999,
                    4px 4px 1px #999
                `;
                });

                // 색상 변경 애니메이션
                function updateColor() {
                    const animationDuration = 12000; // 12초 (CSS의 animation 주기와 일치)
                    const progress = (Date.now() % animationDuration) / animationDuration;
                    const spans = text.getElementsByTagName('span');

                    let color;
                    if (progress < 0.33) {
                        color = '#522073';
                    } else if (progress < 0.66) {
                        color = '#8416F2';
                    } else {
                        color = '#7021BF';
                    }

                    for (let span of spans) {
                        span.style.color = color;
                    }

                    requestAnimationFrame(updateColor);
                }

                updateColor();
            });
        </script>
    </body>

    </html>