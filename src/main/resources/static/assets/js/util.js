// 디바운싱 함수 정의
// : 기존 타이머 취소-> 새로운 타이머 호출
// : 지정된 시간 후 콜백함수 호출(=먼저 처리 후 호출하여 동작!)
export function debounce(callback, wait) {
  let timeout;
  return (...args) => {
    clearTimeout(timeout);
    timeout = setTimeout(() => callback(...args), wait);
  };
}
