$(document).ready(function() {
    // URL 파라미터에서 type 값을 가져옵니다.
    const urlParams = new URLSearchParams(window.location.search);
    const type = urlParams.get('type');
	
    // type에 따라 초기 탭을 설정합니다.
    if (type === 'password') {
        $("#pwTab").addClass("active");
        $("#idTab").removeClass("active");
        $("#pw-form-container").show();
        $("#id-form-container").hide();
		$("#title").text("비밀번호 찾기");
		$("#pw-reset-container").hide();
		
    } else {
        $("#idTab").addClass("active");
        $("#pwTab").removeClass("active");
        $("#id-form-container").show();
        $("#pw-form-container").hide();
		$("#title").text("아이디 찾기");
		$("#pw-reset-container").hide();
    }

    $("#idTab").click(function() {
        $(this).addClass("active");
        $("#pwTab").removeClass("active");
        $("#id-form-container").show();
        $("#pw-form-container").hide();
		$(".error-message").hide();
		$("#title").text("아이디 찾기");
		$("#pw-reset-container").hide();
		$("#show-find-container").hide()
    });

    $("#pwTab").click(function() {
        $(this).addClass("active");
        $("#idTab").removeClass("active");
        $("#pw-form-container").show();
        $("#id-form-container").hide();
		$(".error-message").hide();
		$("#title").text("비밀번호 찾기");
		$("#pw-reset-container").hide();
		$("#show-find-container").hide()
    });
	
	// 아이디 찾기 폼 제출
	// 아이디 찾기일 경우
	// req1은 이름, req2는 이메일
	 $("#id-form").submit(function(e) {
		e.preventDefault();
		
		// 아이디 찾기 폼에서 value
		let $idFormName = $("#id-form input[placeholder='이름']").val();
		let $idFormEmail = $("#id-form input[placeholder='이메일']").val();
		
		
		// 유효성 검사
		if(!$idFormName || !$idFormEmail){
			$(".error-message").html("빈칸이 존재합니다.<br> 모두 입력하신후 다시 시도해주시길 바랍니다.").show();
			return;
		}
		$(".error-message").hide();
		

     	$.ajax({
        	url: '/find',
	        type: 'POST',
	        data: $(this).serialize(),
	        success: function(response) {
				if(response.message ==="emptyIdError"){		
		             $(".error-message").html("잘못 입력하셨습니다<br>해당하는 정보가 존재하지 않습니다.").show();
				}else {
					$(".error-message").hide();
					$("#id-form-container").hide();
					$("#show-find-container").show();
					$("#id-find-result").val(response.id).show();
				}
	         },
	         error: function() {
	             $(".error-message").text("아이디 찾기에 실패했습니다.").show();
	         }
	     });
	 });
	 
	 
	 let userId = "";
     // 비밀번호 찾기 폼 제출
	 // 비밀번호 찾기일 경우 
	 // req1은 아이디
	 // req2는 이메일
     $("#pw-form").submit(function(e) {
         e.preventDefault();
		 
		 // 비밀번호 찾기 폼에서 value
 		let $pwdFormId = $("#pw-form-container input[placeholder='아이디']").val();
 		let $pwdFormEmail = $("#pw-form-container input[placeholder='이메일']").val();
		 		
		if(!$pwdFormId || !$pwdFormEmail){
			$(".error-message").html("빈칸이 존재합니다.<br> 모두 입력하신후 다시 시도해주시길 바랍니다.").show();
			return;
		}
		$(".error-message").hide();
		 
         $.ajax({
             url: '/find',
             type: 'POST',
             data: $(this).serialize(),
			 success: function(response) {
			     if(response.message == 'emptyPwdError'){
			         $(".error-message").html("잘못 입력하셨습니다<br>해당하는 정보가 존재하지 않습니다.").show();
			     } else {
			         $(".error-message").hide();
			         $("#pw-form-container").hide();
			         $("#pw-reset-container").show();
			         $("#title").text("비밀번호 변경");
			         userId = response.userId;
			     }
			 },
             error: function() {
                 $(".error-message").html("비밀번호 찾기에 실패했습니다.").show();
             }
         });
     });
	
	// 비밀번호 재설정 폼 제출
	$("#pw-reset-form").submit(function(e) {
	    e.preventDefault();
	
	    let newPassword = $("input[name='newPassword']").val();
    	let confirmPassword = $("input[name='confirmPassword']").val();	
	
		
		
		// 정규표현식
		let re = /^(?!.*\\s)(?=.*[a-z])(?=.*\d)[a-z\d]{8,20}$/;
		
		if(!re.test(newPassword) || !re.test(confirmPassword)){
			$(".error-message").html("비밀번호는 영어 소문자와 숫자를 최소 한 글자씩 포함하고 8~20자여야 하며,<br> 공백을 포함할 수 없습니다.").show();
    	    return;
		}
		
        if (!newPassword || !confirmPassword) {
	        $(".error-message").html("빈칸이 존재합니다.<br> 모두 입력하신후 다시 시도해주시길 바랍니다.").show();
    	    return;
   	 	}
        
    	if (newPassword !== confirmPassword) {
        	$(".error-message").html("비밀번호가 일치하지 않습니다.<br> 다시 확인해주시길 바랍니다.").show();
       		return;
        }
        $(".error-message").hide();
		

		
		$dataForm = {
			"newPassword" : newPassword,
			"confirmPassword":confirmPassword,
			"userId" : userId
		}

		$.ajax({
            url: '/reset-password',
            type: 'POST',
			contentType:"application/json; charset=urf-8",
            data: JSON.stringify($dataForm),
            success: function(response) {
				switch(response.message){
					case 'success':
						alert("비밀번호가 성공적으로 변경되었습니다.");
	                    location.href = '/login';
						break;
					case 'WrongApproach':
						$(".error-message").html("잘못된 접근입니다.").show();
						location.href = '/login';
						break;
					case 'differentPwd':
						$(".error-message").html("비밀번호가 일치하지 않습니다.<br> 다시 확인해주시길 바랍니다.").show();
						break;
					case 'samePwdError':
						$(".error-message").html("현재 비밀번호와 같습니다.").show();
						break;

				}
            },
            error: function() {
                $(".error-message").html("비밀번호 변경에 실패했습니다.").show();
            }
        });
    });
});