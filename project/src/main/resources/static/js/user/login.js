$(document).ready(function() {
	
	$('#logo').on('click', function(){
		location.href='/';
	})
	
    $('#loginForm').on('submit', function(event) {
        event.preventDefault();
		
		let $userId = $('input[name="userId"]');
		let $password = $('input[name="userPwd"]');
		let userIdValue = $userId.val();
		let passwordValue = $password.val();
		// 유효성 검사 플래그
		let isValid = true;

		// 유효성 검사 메시지 숨기기
		$('#loginError').hide();

		// userId 필드가 비어 있는지 확인
		if (!userIdValue) {
		    $('#loginError').text("아이디를 입력해주세요.").show();
		    isValid = false;
			return;
		}

		// password 필드가 비어 있는지 확인
		if (!passwordValue) {
		    $('#loginError').text("비밀번호를 입력해주세요.").show();
		    isValid = false;
			return;
		}

		// 유효성 검사 실패 시 함수 종료
		if (!isValid) {
		    return;
		}
		
		
		
        var $form = $(this);
        $.ajax({
            type: $form.attr('method'),
            url: $form.attr('action'),
            data: $form.serialize(),
            success: function(response) {
				alert("환영합니다");
				// 로그인 성공 시 이전 페이지로 이동
				var url = '/'; // 기본적인 리디렉션 URL
				var savedRequest = localStorage.getItem('spring-security-redirect');
				/* alert(savedRequest); */
				if (savedRequest) {
				    url = savedRequest; // SavedRequest에서 가져온 URL로 대체
				    localStorage.removeItem('spring-security-redirect'); // 사용한 SavedRequest는 삭제
				}
				window.location.href = url; // 리디렉션
            },
			error: function(xhr) {
			    $('#loginError').text(xhr.responseText).show();
			}
        });
    });
});