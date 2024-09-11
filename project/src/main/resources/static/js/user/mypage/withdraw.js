$(document).ready(function() {
	$(".edit-btn").on('click', function(){
		if(!confirm("정말 탈퇴하시겠습니까?")){
			return;
		}
		
		$.ajax({
			url: '/user/mypage/withdrawal', // 비밀번호 변경 요청을 처리할 서버 엔드포인트
	       	type: 'POST',
	       	contentType: 'application/json',
	       	success: function(response) {
				
			},
			error: function(xhr) {
			   	// 에러 시 처리 로직
		       	alert('현재 서버에 문제가 있습니다. \n 잠시 후 다시 시도해 주세요');
			   	console.log(xhr);
		       	// 추가적인 에러 처리 로직을 여기에 작성할 수 있습니다.
	   		}
	
		});
	});
});