// franchise.js

// formatMoney 함수 정의
function formatMoney(amount) {
    let formatted = '';

    if (amount >= 100000000) { // 1억 이상
        let billions = Math.floor(amount / 100000000);
        let millions = Math.floor((amount % 100000000) / 10000);
        formatted = `${billions}억`;
        if (millions > 0) {
            formatted += `${millions.toLocaleString()}만`;
        }
    } else if (amount >= 10000) { // 1만 이상 1억 미만
        let millions = Math.floor(amount / 10000);
        let remainder = amount % 10000;
        formatted = `${millions.toLocaleString()}만`;
        if (remainder > 0) {
            formatted += `${remainder.toLocaleString()}`;
        }
    } else { // 1만 미만
        formatted = amount.toLocaleString();
    }

    return formatted + '원';
}

// 페이지가 로드될 때 실행되는 함수
window.onload = function() {
    console.log("Script loaded"); // 스크립트가 로드되었는지 확인

    let avgSalesElement = document.querySelector('.avg-sales');
    let startupCostElement = document.querySelector('.startup-cost');
    
    if(avgSalesElement && startupCostElement) {
        console.log("Elements found"); // 요소들을 찾았는지 확인
        
        let avgSales = parseInt(avgSalesElement.textContent.replace(/[^\d]/g, ''));
        let startupCost = parseInt(startupCostElement.textContent.replace(/[^\d]/g, ''));
        
        console.log("Avg Sales:", avgSales); // 파싱된 값 확인
        console.log("Startup Cost:", startupCost);

        avgSalesElement.textContent = formatMoney(avgSales);
        startupCostElement.textContent = formatMoney(startupCost);
    } else {
        console.log("Elements not found"); // 요소를 찾지 못했을 경우
    }
};