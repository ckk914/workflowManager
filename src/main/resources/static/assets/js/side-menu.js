// //버튼의 요소 노드 취득
// const menuBtn = document.querySelector('header .menu-open');
// const closeBtn = document.querySelector('.gnb .close');

// const gnb = document.querySelector('.gnb');

// //클릭 이벤트 생성
// menuBtn.addEventListener('click', () => {
//   gnb.classList.add('on');
// });

// closeBtn.addEventListener('click', () => {
//   gnb.classList.remove('on');
// });

document.addEventListener("DOMContentLoaded", function () {
  const menuOpen = document.querySelector("header .menu-open");
  const gnb = document.querySelector(".gnb");
  const closeBtn = document.querySelector(".gnb .close");

  // 메뉴를 열기 또는 닫기 위한 클릭 이벤트
  menuOpen.addEventListener("click", function (event) {
    event.stopPropagation();
    if (gnb.classList.contains("on")) {
      gnb.classList.add("close");
      gnb.classList.remove("on");
    } else {
      gnb.classList.add("on");
      gnb.classList.remove("close");
    }
  });

  // 메뉴를 닫기 위한 클릭 이벤트
  closeBtn.addEventListener("click", function (event) {
    event.stopPropagation();
    gnb.classList.add("close");
    gnb.classList.remove("on");
  });

  // 다른 영역을 클릭했을 때 메뉴를 닫기 위한 이벤트
  document.addEventListener("click", function (event) {
    if (!gnb.contains(event.target) && gnb.classList.contains("on")) {
      gnb.classList.add("close");
      gnb.classList.remove("on");
    }
  });

  // gnb 내부를 클릭했을 때 이벤트 전파 방지
  gnb.addEventListener("click", function (event) {
    event.stopPropagation();
  });
});
