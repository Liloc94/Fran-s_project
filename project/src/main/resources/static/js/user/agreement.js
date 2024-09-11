$(document).ready(function() {
		
	$('#agreeForm').on('submit', function(event){
		event.preventDefault();
		let agreeTerms = $('#agreeTerms').is(':checked');
		let agreePrivacy = $('#agreePrivacy').is(':checked');
		
		$('.error-message').hide();
		$.ajax({
            type: 'POST',
            url: $(this).attr('action'),
            data: {agreeTerms: agreeTerms, agreePrivacy: agreePrivacy},
            success: function(response) {
                switch(response.result) {
                    case 'Not AgreeTerms':
                        $('.error-message').text(response.message).show();
                        break;
                    case 'Not AgreePrivacy':
                        $('.error-message').text(response.message).show();
                        break;
                    case 'success':
                        window.location.href = "/register";
                        break;
                }
            },
            error: function(xhr) {
                $('.error-message').text('서버 오류가 발생했습니다.').show();
            }
        });
	})
});