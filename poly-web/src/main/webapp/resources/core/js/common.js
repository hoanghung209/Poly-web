/**
 * 
 */

function menu_animated_line() {
        menu_animated_line_first_pos();
        $('.header_main_menu_wrapper .header-menu>li').hover(function() {
            $('.magic_line').removeClass('line_visible');
            $(this).find('.magic_line').addClass('line_visible');
        }, function() {
            $('.magic_line').removeClass('line_visible');
            menu_animated_line_first_pos();
        });
    }
    var appendOnce = 0;

    function menu_animated_line_first_pos() {
        appendOnce++;
        var hasCurrentMenuItem = false;
        var li_anchestor = 'undefined';
        $('.header_main_menu_wrapper .header-menu>li').each(function() {
            if (appendOnce == 1) {
                var maxLineWidth = 0;
                maxLineWidth = $(this).width();
                $(this).append('<div class="magic_line "></div>');
                $(this).find('.magic_line').css({
                    'max-width': maxLineWidth + 'px',
                })
            }
            if ($(this).hasClass('current-menu-item') || $(this).hasClass('current-menu-parent')) {
                hasCurrentMenuItem = true;
                li_anchestor = $(this);
            }
        });
        if (hasCurrentMenuItem  && li_anchestor != 'undefined') {
            var lineFirstW = li_anchestor.innerWidth() - 28;
            var lineFirstPos = li_anchestor.position().left + 14;
            $(li_anchestor).find('.magic_line').addClass('line_visible');
        }
    }
 function mobileMenu() {
        $(".header-menu-mobile .header-menu > li.menu-item-has-children > a").after('<span class="arrow"><i class="fa fa-angle-right"></i></span>');
        $('.header-menu-mobile .header-menu .arrow').click(function() {
            $(this).toggleClass('active');
            $(this).closest('li').toggleClass('opened');
            $(this).closest('li').find('> ul.sub-menu').slideToggle(300);
        })
        $(".header-menu-mobile .header-menu > li.menu-item-has-children > a").click(function(e) {
            if ($(this).attr('href') == '#') {
                e.preventDefault();
                $(this).closest('li').find(' > ul.sub-menu').slideToggle(300);
                $(this).closest('li').toggleClass('opened');
                $(this).closest('li').find('.arrow').toggleClass('active');
            }
        });
    }

function AccordionMenu() {

	  $(".menudrop .menu-item-has-children > a").before('<span class="arrow"><i class="fa fa-plus-circle"></i></span>');
	  $(".menudrop .opened>.arrow").addClass('active');
	  
        $('.menudrop .menu-item-has-children>.arrow').click(function() {
            $(this).toggleClass('active');
            $(this).parent().removeClass('mclose').toggleClass('opened');
            $(this).parent().find('ul.mlevelchild').first().slideToggle(300);
				$(this).parent().siblings().removeClass('opened').addClass('mclose');
			$('.mclose').find( '.mlevelchild' ).slideUp("slow");
        })
        $(".menudrop .menu-item-has-children>a").click(function(e) {
            if ($(this).attr('href') == '#') {
                e.preventDefault();
                $(this).parent().find('ul.mlevelchild').first().slideToggle(300);
           $(this).parent().removeClass('mclose').toggleClass('opened');
                $(this).parent().find('.arrow').first().toggleClass('active');
					$(this).parent().siblings().removeClass('opened').addClass('mclose');
			$('.mclose').find( '.mlevelchild' ).slideUp("slow");
            }
        });
		
	
	}

function timline(){
	$('.timeline-article .content-left-container').hover(function() {
					$(this).toggleClass('lhopened');
					$(this).parent().find('.content-right-container').toggleClass('lhclosed');
					
					});
				$('.timeline-article .content-right-container').hover(function() {
					$(this).toggleClass('lhopened');
					$(this).parent().find('.content-left-container').toggleClass('lhclosed');
					
					});
				$('.timeline-article .content-left-container .lhxemthem a').click(function() {
					$(this).toggleClass('lhopened');
					$(this).parent().find('.content-right-container').toggleClass('lhclosed');
					
					});
				$('.timeline-article .content-right-container .lhxemthem a').click(function() {
					$(this).toggleClass('lhopened');
					$(this).parent().find('.content-left-container').toggleClass('lhclosed');
					
					});
             
	
	
	}

$(document).ready(function($) {
	
	   menu_animated_line();
        mobileMenu();
	AccordionMenu();
	timline();
	});


var stickyHeader;
var stickyHeaderOriginPos;
var stickyHeaderHeight = 0;
 $(window).load(function() {
          
            if ($('#header').hasClass('sticky_header')) {
                stickyHeader = $('#header.sticky_header .header_default');
                stickyHeaderOriginPos = stickyHeader.offset().top;
                stickyHeaderHeight = stickyHeader.height() + 25;
                sticky_header();
            };
            $(window).scroll(function() {
                sticky_header();
            });
            //woo_gallery_carousel();
            //composerRTL();
        });

function sticky_header() {
        if ($('#header').hasClass('sticky_header')) {
            var currentPos = $(window).scrollTop();
            if ($('.slider').length < 1) {
                if (currentPos > stickyHeaderOriginPos - 1) {
                    stickyHeader.addClass('fixed');
                    $('#header').css({
                        'padding-bottom': stickyHeaderHeight + 'px'
                    });
                    if ($('#header').hasClass('transparent_header')) {
                         $('.logo_transparent_static').addClass('hidden').removeClass('visible');
                        $('.logo_colored_fixed').addClass('visible').removeClass('hidden');
                    }
                } else {
                    stickyHeader.removeClass('fixed');
                    $('#header').css({
                        'padding-bottom': 0
                    });
                    if ($('#header').hasClass('transparent_header')) {
                      
                        $('.logo_transparent_static').addClass('visible').removeClass('hidden');
                        $('.logo_colored_fixed').addClass('hidden').removeClass('visible');
                    }
                }
            } else {
                if ($('#header').hasClass('transparent_header')) {
                    sliderHeight = $('.slider').height();
                    if (currentPos > stickyHeaderOriginPos + sliderHeight - 200) {
                        stickyHeader.addClass('fixed_invisible_top');
                    } else {
                        stickyHeader.removeClass('fixed_invisible_top');
                    }
                    if (currentPos > stickyHeaderOriginPos + sliderHeight - 45) {
                        stickyHeader.addClass('fixed');
                        $('#header').css({
                            'padding-bottom': stickyHeaderHeight + 'px'
                        });
                        if ($('#header').hasClass('transparent_header')) {
                     
                            $('.logo_transparent_static').addClass('hidden').removeClass('visible');
                            $('.logo_colored_fixed').addClass('visible').removeClass('hidden');
                        }
                    } else {
                        stickyHeader.removeClass('fixed');
                        $('#header').css({
                            'padding-bottom': 0
                        });
                        if ($('#header').hasClass('transparent_header')) {
                        
                            $('.logo_transparent_static').addClass('visible').removeClass('hidden');
                            $('.logo_colored_fixed').addClass('hidden').removeClass('visible');
                        }
                    }
                } else {
                    if (currentPos > stickyHeaderOriginPos - 1) {
                        stickyHeader.addClass('fixed');
                        $('#header').css({
                            'padding-bottom': stickyHeaderHeight + 'px'
                        });
                        if ($('#header').hasClass('transparent_header')) {
                    
                            $('.logo_transparent_static').addClass('hidden').removeClass('visible');
                            $('.logo_colored_fixed').addClass('visible').removeClass('hidden');
                        }
                    } else {
                        stickyHeader.removeClass('fixed');
                        $('#header').css({
                            'padding-bottom': 0
                        });
                        if ($('#header').hasClass('transparent_header')) {
                      
                            $('.logo_transparent_static').addClass('visible').removeClass('hidden');
                            $('.logo_colored_fixed').addClass('hidden').removeClass('visible');
                        }
                    }
                }
            }
        }
    }
	

	