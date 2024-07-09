document.addEventListener("DOMContentLoaded", function () {
  const $selectYear = document.getElementById("year");
  const currentYear = new Date().getFullYear();

  for (let year = currentYear; year >= 2020; year--) {
    const option = document.createElement("option");
    option.value = year;
    option.innerText = year + "년";
    $selectYear.appendChild(option);
  }

  const $selectMonth = document.getElementById("month");

  for (let month = 1; month <= 12; month++) {
    const option = document.createElement("option");
    option.value = month;
    option.innerText = month + "월";
    $selectMonth.appendChild(option);
  }
});

document.addEventListener("DOMContentLoaded", function () {
  const dateElements = document.querySelectorAll(".regDate");

  dateElements.forEach(function (element) {
    const regDate = new Date(element.getAttribute("data-reg-date"));
    const formattedDate = regDate.toLocaleDateString("ko-KR", {
      year: "numeric",
      month: "long",
      day: "numeric",
    });
    element.textContent = `${formattedDate}`;
  });
});

document.addEventListener("DOMContentLoaded", function () {
  let currentPage = window.location.href;

  let match = currentPage.match(/pageNo=(\d+)/);
  let currentPageNo = "";
  if (match) {
    currentPageNo = match[1];
  }

  let pageLinks = document.querySelectorAll(".pagination li[data-page-num]");

  pageLinks.forEach(function (li) {
    let dataPageNum = li.getAttribute("data-page-num");
    if (dataPageNum === currentPageNo) {
      li.classList.add("active"); 
    }
  });
});


document.querySelector('.favorite-list').addEventListener('click', async (event) => {
  event.preventDefault(); // 기본 동작 방지

  try {
    const response = await fetch('/traffic-rank'); 
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    const htmlContent = await response.text();

    
    const modalContainer = document.createElement('div');
    modalContainer.classList.add('modal-container');

    const modalContent = document.createElement('div');
    modalContent.classList.add('modal-content');

    modalContent.innerHTML = `
      <div class="modal-header">
        <a href="#" class="close-modal">&times;</a>
      </div>
      <div class="modal-body">
        ${htmlContent}
      </div>
    `;

    modalContainer.appendChild(modalContent);
    document.body.appendChild(modalContainer);

    // 모달 닫기 이벤트 처리
    modalContainer.querySelector('.close-modal').addEventListener('click', () => {
      document.body.removeChild(modalContainer);
    });

    // 외부를 클릭하여 모달 닫기
    window.onclick = function(event) {
      if (event.target == modalContainer) {
        document.body.removeChild(modalContainer);
      }
    };
  } catch (error) {
    console.error('Error fetching modal content:', error);
  }
});
