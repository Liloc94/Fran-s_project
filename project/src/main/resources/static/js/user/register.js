$(document).ready(function() {
	
	// 모든 항목에 정상적으로 수행되었는지 확인하기 위한 객체
	let registerFlag = {
		"idCheck" : false,
		"emailCheck" : false,
		"certificationEMail" : false,
		"nicknameCheck" : false,
		"passwordCheck" : false,
		"nameCheck" : false
	};
	
	// 아이디 중복검사
	$('#userId').on('blur', function() {
	        var userId = $(this).val();
			let message = "아이디는 5자~20자의 영어 소문자와 숫자의 조합만 가능합니다."
			
	        if (userId) {
				if(!validateValue('id', userId)){
					$('#duplication-id').text(message).show();
					// 아이디 입력칸 포커스
					$('#userId').focus();
					return;
				};
	            checkDuplicate('id', userId);
	        };
	    });
		
	$('#userEmailLocalPart').on('blur', function(){
		$('#userEmailDomain').focus();
	})

    // 이메일 중복 검사
    $('#userEmailDomain').on('blur', function() {
		const localPart = $('#userEmailLocalPart').val();
		const domain = $('#userEmailDomain').val();
		const fullEmail = localPart + domain;
		let message = "이메일 형식이 아닙니다.";
		$('#certification-number-container').hide();
		
        if (fullEmail) {
			if(!validateValue('email',fullEmail)){
				$('#duplication-email').text(message).show();
				$('#userEmailLocalPart').focus();
				return;
			};
            checkDuplicate('email', fullEmail);
        };
    });
	
	// 인증번호 버튼을 눌렀을 때
	$('#send-btn').on('click', function(){
		if(!registerFlag['emailCheck']){
			$('#duplication-email').text("이메일을 제대로 선택해주세요.").show();
			return;
		}
		$('#duplication-email').hide();
		$('.certification-number-container').show();
		const localPart = $('#userEmailLocalPart').val();
		const domain = $('#userEmailDomain').val();
		const userEmail = localPart + domain;
		$.ajax({
           type: 'POST',
           url: '/send-certification-email', // 서버 측 API 엔드포인트
           contentType: 'application/json',
           data: JSON.stringify({
               userEmail: userEmail
           }),
           success: function(response) {
               if (response.result === 'success') {
                   $('#email-send-info').text(response.message).show()
                   // 인증 번호 입력을 위한 UI 등을 활성화할 수 있습니다.
               } else {
                   alert('인증 메일 발송에 실패하였습니다.');
               }
           },
           error: function(xhr, status, error) {
               alert('서버 오류: ' + error); // 오류 발생 시 처리
           }
       });
	})
	
	
	// 이메일 인증을 눌렀을 때
	$('#confirm-btn').on('click', function() {
			const localPart = $('#userEmailLocalPart').val();
			const domain = $('#userEmailDomain').val();
	        var userEmail = localPart + domain; // 사용자의 이메일 주소를 여기에 설정
	        var certificationNumber = $('#certification-number').val(); // 사용자가 입력한 인증 번호를 여기에 설정
			registerFlag["certificationEMail"] = false;
			$('#duplication-email').hide();
			$('#email-send-info').hide();
			
	        $.ajax({
	            type: 'POST',
	            url: '/verify-email', // 서버 측 API 엔드포인트
	            contentType: 'application/json',
	            data: JSON.stringify({
	                userEmail: userEmail,
	                certificationNumber: certificationNumber
	            }),
	            success: function(response) {
	                if (response.result === 'success') {
	                    registerFlag["certificationEMail"] = true;
	                } else {
						$('#duplication-email').html("인증번호가 다릅니다.<br>다시입력해주세요.").show();
	                    // 인증 실패 시 할 작업
	                }
	            },
	            error: function(xhr, status, error) {
					$('#duplication-email').html("서버에 문제가 생겼습니다<br>잠시 후 시도해주세요.").show();
	            }
	        });
	    });	
	// 닉네임 중복검사
	$('#userNickname').on('blur', function() {
	    var userNickname = $(this).val();
		let message = "닉네임는 한글 2자~6자 또는 영어 소문자 4자~12자입니다."
		
	    if (userNickname) {
			if(!validateValue('nickname', userNickname)){
				$('#duplication-nickname').text(message).show();
				$('#userNickname').focus();
				return;
			};
	        checkDuplicate('nickname', userNickname);
	    };
	});
	
	// 비밀번호와 비밀번호 확인 일치 검사
	$('input[name="checkPwd"]').on('blur', function(){
		let pwd = $('#pwd').val();
		let checkPwd = $(this).val();
		let message = '';
		
		if(!pwd === checkPwd){
			message = '비밀번호와 비밀번호 확인이 다릅니다.';
			$('#check_pwd').text(message).show();
			registerFlag["passwordCheck"] = false;
			return;
		};
		registerFlag["passwordCheck"] = true;
		$('#check_pwd').text(message).hide();
	});
	
	// 비밀번호입력칸에 왔을 때 비밀번호 조건 알려주기
	$('#pwd').on('focus', function(){
		let message = '비밀번호는 8자~ 20자의 영어 소문자와 숫자의 조합만 가능합니다.';
		$('#check_pwd').text(message).show();
	});
	
	// 비밀번호 유효성 검사
	$('#pwd').on('blur', function(){
		let pwd = $(this).val();
		if(!validateValue('password', pwd)){
			let message = '비밀번호는 8자~ 20자의 영어 소문자와 숫자의 조합만 가능합니다.';
			$('#check_pwd').text(message).show();
			$('pwd').focus();
			registerFlag["passwordCheck"] = false;
			return;
		}else{
			$('#check_pwd').hide();
		};
		registerFlag["passwordCheck"] = true;
	});
	
	// 이름 유효성 검사
	$('input[name="userName"]').on('blur', function(){
		let name = $(this).val();
		if(!validateValue('name', name)){
			let message = '이름은 2자~6자의 한글만 가능합니다.';
			$('#check-name').text(message).show();
			$(this).focus();
			registerFlag["nameCheck"] = false;
			return;
		}else{
			$('#check-name').hide();
		}
		registerFlag["nameCheck"] = true;
	})
		
		

    // 중복 검사 함수
    function checkDuplicate(type, value) {
        $.ajax({
            url: '/api/check-duplicate', // 실제 서버의 중복 검사 API 주소로 변경해야 합니다
            method: 'POST',
            data: { type: type, value: value },
	
            success: function(response) {
				//console.log(response.isDuplicate)
				var message = '';
				// 중복된 값이 있을 때
                if (!response.isDuplicate) {
					// 타입이 id일때
					if(type === 'id'){
						message='이미 사용 중인 아이디입니다.';
						// registerFlag의 idCheck를 false로 바꾼다
						registerFlag["idCheck"] = false;
						// 아이디 오류 출력 div의 메세지를 넣어서 보여준다
						$('#duplication-id').text(message).show();
						// 아이디 입력칸 포커스
						$('#userId').focus();
					}else if(type==='email'){
						message = '이미 사용 중인 이메일입니다.';
						registerFlag["emailCheck"] = false;
						$('#duplication-email').text(message).show();
						$('#userEmailLocalPart').focus();
					}else if(type==='nickname'){
						message = '이미 사용중인 닉네임입니다.';
						registerFlag["nicknameCheck"] = false;
						$('#duplication-nickname').text(message).show();
						$('#userNickname').focus();
					}

               }else{
					if (type === 'nickname') {
				            $('#duplication-nickname').hide();
				        	registerFlag["nicknameCheck"] = true;
						
				     	} else {
				            if (type === 'id') {
				                registerFlag["idCheck"] = true;
								$('#duplication-id').hide();
				            } else if (type === 'email') {
				                registerFlag["emailCheck"] = true;
								$('#duplication-email').hide();
				            }
				       }
				   }
            },
            error: function() {
                alert('중복 검사 중 오류가 발생했습니다. 다시 시도해 주세요.');
            }
        });
    }
		
	// 회원가입 버튼을 눌렀을 경우 작동하는 이벤트
	// 회원가입에 성공했을 때 {"result":"success"}
    $('#register-form button.height-50').on('click', function(event) {
        event.preventDefault();
		
		let allChecksPassed = true;
		
		$('#duplication').text('').hide();
		$('#duplication-nickname').text('').hide();
		$('#check_pwd').text('').hide();

		$.each(registerFlag, function(index, element) {
		    if (!element) {
		        allChecksPassed = false;
				// 각 필드에 대한 경고 메시지 표시
	            switch (index) {
	                case 'idCheck':
	                    $('#duplication-id').text('아이디를 확인해 주세요.').show();
						$('#userId').focus();
	                    break;
						
	                case 'emailCheck':
	                    $('#duplication-email').text('이메일을 확인해 주세요.').show();
						$('#userEmailLocalPart').focus();
	                    break;
						
	                case 'nicknameCheck':
	                    $('#duplication-nickname').text('닉네임을 확인해 주세요.').show();
						$('#userNickname').focus();
	                    break;
						
	                case 'passwordCheck':
	                    $('#check_pwd').text('비밀번호를 확인해 주세요.').show();
						$('#pwd').focus();
	                    break;
						
	                case 'nameCheck':
	                    $('#check-name').text('이름을 확인해 주세요.').show();
						$('input[name="userName"]').focus();
	                    break;
					case 'certificationEMail':
	                    $('#duplication-email').text('이메일 인증을 진행해주세요.').show();
						$('#userEmailLocalPart').focus();
	                    break;
					
	            }
		    }
		});

		if (!allChecksPassed) {
		    return;
		}
		
        var $form = $('#register-form');
		//console.log($form.serialize());
        $.ajax({
            type: $form.attr('method'),
            url: $form.attr('action'),
            data: $form.serialize(),
            success: function(response) {
				//alert(response.result);
				switch(response.result){
					case 'success':
						alert("회원가입에 성공하셨습니다.");
						// 기본적인 리디렉션 URL
		                var url = '/login'; 
		                window.location.href = url;
						break;
						
					case 'empthIdError':
						$('#duplication-id').text(response.message).show();
						$('#userId').focus();
						break;
						
					case 'idInvalidError':
						$('#duplication-id').text(response.message).show();
						$('#userId').focus();
						break;
						
					case 'EmailInvalidError':
						$('#duplication-email').text(response.message).show();
						$('#userEmailLocalPart').focus();
	                    break;     
						
					case 'emptyPwdError':
						$('#check_pwd').text(response.message).show();
						$('input[name="userPwd"]').focus();
						break;
						
					case 'mismatchPwdError':
						$('#check_pwd').text(response.message).show();
						$('input[name="userPwd"]').focus();
						break;
						
					case 'pwdInvalidError':
						$('#check_pwd').text(response.message).show();
						$('input[name="userPwd"]').focus();
					    break;
						
					case 'emptyNicknameError':
						$('#check-name').text(response.message).show();
						$('input[name="userName"]').focus();
	                    break;
						
					case 'nicknameInvalidError':
						$('#check-name').text(response.message).show();
						$('input[name="userName"]').focus();
	                    break;
					}
            },
			error: function(xhr, status, error) {
		        //console.log(xhr.status + ": " + xhr.responseText); // 오류 상세 정보 로깅
		        alert("서버에 문제가 생겼습니다. 잠시후 시도해 주세요.");
		    }
        });
    });
	
	// 유효성 검사
	function validateValue(type, value) {
		let re = "";
		if(type === 'id'){
			re = /^(?!.*admin)[a-z0-9]{5,20}$/;
		}else if(type ==='email'){
			re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
		}else if(type ==='nickname'){
			re = /^(?:(?:[가-힣]{2,6})|(?:[a-z]{4,12}))(?!.*\b(?:관리자|운영자|admin)\b).*$/;
		}else if(type==='password'){
			re = /^(?=.*[a-z])(?=.*\d)[a-z\d]{8,20}$/;
		}else if(type==='name'){
			re = /^[가-힣]{2,6}$/;
		}
		return re.test(value);
    }
});
