$(function() {
	var tabsSwiper = new Swiper('.tabSwiper', {
		speed: 500,
		autoHeight: true,
		// observer: true,
		// observeParents: true,
		onSlideChangeStart: function() {
			$(".titList .active").removeClass('active');
			$(".titList li").eq(tabsSwiper.activeIndex).addClass('active');
		}
	});
	$(".titList li").on('touchstart mousedown', function(e) {
		e.preventDefault();
		$(".titList .active").removeClass('active');
		$(this).addClass('active');
		tabsSwiper.slideTo($(this).index());
	});
	$(".titList a").click(function(e) {
		e.preventDefault();
	});



	$('.questionList li .questionTit').click(function() {
		if ($(this).next('.answer').is(':hidden')) {
			$(this).find('img').addClass('rotate');
			$(this).next('.answer').show();
		} else {
			$(this).find('img').removeClass('rotate');
			$(this).next('.answer').hide();
		}

	});
	var h = $(window).height() - $('.titList').height();
	$('.hidden').css('height', h + 'px');
	$('.tabSwiper').css('height', h + 'px');


});