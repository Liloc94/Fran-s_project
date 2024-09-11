$(document).ready(function() {
    $('form').on('submit', function(event) {
        event.preventDefault();
        
		$form = $('#pwd-form');
		
		$(".error-message").hide();
		
        $.ajax({
			type: $form.attr('method'),
			url: $form.attr('action'),
			data: $form.serialize(),
            success: function(response) {
                if (response.message === 'success') {
                    window.location.href = '/user/mypage';
                } else {
					$(".error-message").text(response.message).show();
                    
                }
            },
            error: function() {
                alert('비밀번호 확인 중 오류가 발생했습니다.');
            }
        });
    });
});
