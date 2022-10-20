$(document).ready(function(){ 

	// Scrollbar
	$(".scrollbar_chat").mCustomScrollbar({
		autoHideScrollbar:true
	});

	// Chat
	$('.reply_comment').on('click', function() {
		$('.box_chat').addClass('show_thread');
	});

	$('.nr_answers').on('click', function() {
		$('.box_chat').addClass('show_thread');
	});

	$('.close_thread').on('click', function() {
		$('.box_chat').removeClass('show_thread');
	});

	$('.input_message_chat input').keyup(function() {
	    if ($(this).val() == '@') { // check if value changed
	       $('.users_bar_chat').show();
	    } else {
	    	$('.users_bar_chat').hide();
	    }
	});

});


