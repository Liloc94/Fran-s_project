$(document).ready(function() {
	
	let nicknameCheck = false;

	
	// 닉네임 중복검사
	$('input[name="nickname"]').on('blur', function(){
		
		userNickname = $(this).val();
		if (userNickname) {
				if(!validateValue(userNickname)){
					$('.error-message').text("닉네임는 한글 2자~6자 또는 영어 소문자 4자~12자입니다.").show();
					$(this).focus();
					return;
				};
			};
				
		$.ajax({
	        url: '/api/check-duplicate', // 실제 서버의 중복 검사 API 주소로 변경해야 합니다
	        method: 'POST',
	        data: { type: "nickname", value: userNickname },
	
	        success: function(response) {
				//console.log(response.isDuplicate)
				// 중복된 값이 있을 때
	            if (!response.isDuplicate) {
					// 타입이 id일때
					message = '이미 사용중인 닉네임입니다.';
					nicknameCheck = false;
					$('.error-message').text("이미 사용중인 닉네임입니다.").show();
					$('#userNickname').focus();

	
	           }else{
						$('.error-message').hide();
			        	nicknameCheck = true;
				   }
	        },
	        error: function() {
	            alert('중복 검사 중 오류가 발생했습니다. 다시 시도해 주세요.');
	        }
	    });
	})
	
	$("form").on("submit", function(e){
		e.preventDefault();
		
		let userNickname = $('input[name="nickname"]').val();
		 
		if(!nicknameCheck){
			$('.error-message').html("닉네임 검사를 진행해 주세요.<br> 닉네임 검사는 입력창을 벗어나면 자동으로 됩니다.").show();
			return;
		}
		 
		$(".error-message").hide();
		 
	 	$.ajax({
	         url: '/user/setNickname', // 실제 서버의 중복 검사 API 주소로 변경해야 합니다
	         method: 'POST',
	         data: {nickname : userNickname},

	         success: function(response) {
	 			//console.log(response.isDuplicate)
	 			// 중복된 값이 있을 때
	             if (response == 'Exist') {
	 				// 타입이 id일때
	 				message = '이미 사용중인 닉네임입니다.';
	 				nicknameCheck = false;
	 				$('.error-message').text("이미 사용중인 닉네임입니다.").show();
	 				$('#userNickname').focus();
					return;
	            }else if(response == 'Not Valid'){
					$('.error-message').text("닉네임는 한글 2자~6자 또는 영어 소문자 4자~12자입니다.").show();
					$(this).focus();
					return;
	 			}else if(response == 'success'){
					window.location.href="/";
					return;
				}
				
			},
	        error: function() {
				$('.error-message').html("서버에 문제가 생겼습니다<br>잠시후 다시 시도해주세요").show();
	        }
	    });
		 
		 
	})
	
	// 유효성 검사
	function validateValue(value) {
		let	re = /^(?:(?:[가-힣]{2,6})|(?:[a-z]{4,12}))(?!.*\b(?:관리자|운영자|admin)\b).*$/;
		return re.test(value);
    }
	
		
});