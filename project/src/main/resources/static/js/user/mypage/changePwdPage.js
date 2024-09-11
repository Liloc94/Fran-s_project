$(document).ready(function() {
   	$('#changePwdBtn').on('click', function() {
			
	let $currentPassword = $('#currentPassword');
	let $newPassword = $('#newPassword');
	let $confirmPassword = $('#confirmPassword');
   
	let reg = /^(?!.*\\s)(?=.*[a-z])(?=.*\d)[a-z\d]{8,20}$/;

	var currentPassword = $currentPassword.val().trim();
	var newPassword = $newPassword.val().trim();
	var confirmPassword = $confirmPassword.val().trim();
			   
			  
   	if(currentPassword == null || currentPassword == ''){
		alert("빈칸을 모두 입력한 후 다시 시도주세요");
		$currentPassword.focus();
		return;
   	}
	   
   	// 비밀번호 유효성 검사
   	if(!reg.test(currentPassword)){
		alert("비밀번호는 영어 소문자와 숫자를 최소 한 글자씩 포함하고 8~20자여야 하며,<br> 공백을 포함할 수 없습니다.");
		$currentPassword.focus();
		return;
   	}
			   
   	if(newPassword == null || newPassword == ''){
		alert("빈칸을 모두 입력한 후 다시 시도주세요");
		$newPassword.focus();
		return;
   	}
	// 비밀번호 유효성 검사
   	if(!reg.test(newPassword)){
		alert("비밀번호는 영어 소문자와 숫자를 최소 한 글자씩 포함하고 8~20자여야 하며,<br> 공백을 포함할 수 없습니다.");
		$newPassword.focus();
		return;
  	}
	
	if(confirmPassword == null || confirmPassword == ''){
		alert("빈칸을 모두 입력한 후 다시 시도주세요");
 		$confirmPassword.focus();
 		return;
	}
   	// 비밀번호 유효성 검사
   	if(!reg.test(confirmPassword)){
		alert("비밀번호는 영어 소문자와 숫자를 최소 한 글자씩 포함하고 8~20자여야 하며,<br> 공백을 포함할 수 없습니다.");
		$confirmPassword.focus();
		return;
   	}
			   
			   
   	// 비밀번호 일치 여부 확인
   	if (newPassword !== confirmPassword) {
       alert('변경할 비밀번호와 확인 비밀번호가 일치하지 않습니다.');
	   $newPassword.focus();
       return;
   	}
	
	let dataForm = {
		currentPassword: currentPassword,
		newPassword: newPassword,	
		confirmPassword : confirmPassword
	}

   	$.ajax({
       	url: '/user/mypage/reset-password', // 비밀번호 변경 요청을 처리할 서버 엔드포인트
       	type: 'POST',
       	contentType: 'application/json',
       	data: JSON.stringify(dataForm),
       	success: function(response) {
           	switch(response.message){
				case 'success':
					alert("비밀번호가 성공적으로 변경되었습니다.");
					location.href = "/user/mypage"
					break;
				case 'Wrong Pwd':
					alert("현재비밀번호가 일치하지않습니다.");
					$currentPassword.focus();
					break;
				case 'Wrong Approcah':
					location.href = '/access-denied';
					break;		
				case 'Wrong Request':
					alert("빈칸을 모두 입력해주세요.");
					if(currentPassword == null || currentPassword == ''){
						$currentPassword.focus();
					}else if(newPassword == null || newPassword == ''){
						$newPassword.focut();
					}else if(confirmPassword == null || confirmPassword == ''){
						$confirmPassword.focus();
					}
					break;
				case 'Not Valid cpwd':
					alert('비밀번호는 영어 소문자와 숫자를 최소 한 글자씩 포함하고 8~20자여야 하며, 공백을 포함할 수 없습니다.');
					$currentPassword.focus();
					break;
				case 'Not Valid npwd':
					alert('비밀번호는 영어 소문자와 숫자를 최소 한 글자씩 포함하고 8~20자여야 하며, 공백을 포함할 수 없습니다.');
					$newPassword.focus();
					break;
				case 'Not Valid cfpwd':
					alert('비밀번호는 영어 소문자와 숫자를 최소 한 글자씩 포함하고 8~20자여야 하며, 공백을 포함할 수 없습니다.');
					$confirmPassword.focus();
					break;
				case 'Diff Pwd':
					alert("변경할 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
					$confirmPassword.focus();
					break;
				case 'Same Pwd':
					alert("현재 비밀번호와 같습니다.");
					newPassword = '';
					confirmPassword = '';
					$newPassword.focus();
					break;
	   			}
       		},
		   	error: function(xhr) {
			   	// 에러 시 처리 로직
		       	alert('비밀번호 변경 중 오류가 발생했습니다');
			   	console.log(xhr);
		       	// 추가적인 에러 처리 로직을 여기에 작성할 수 있습니다.
       		}
   		});
	});
});