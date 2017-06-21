var ServiceCatalog = {};

ServiceCatalog.getContextPath = function() {
    return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
};

ServiceCatalog.getURLParameter = function(sParam) {
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] === sParam) {
            return sParameterName[1];
        }
    }
};

$(document).ready(function($) {

    $body = $('body');
    $(document).on({
        ajaxStart: function() {
            $body.addClass('loading');
        },
        ajaxStop: function() {
            $body.removeClass('loading');
        }
    });

    //After loading the header page, Fetch user details and show it on the webpage, won't work in
    //local environment as user details are not available.
    $("#header").load("header.html", function() {
        var userDetUrl = ServiceCatalog.getContextPath() + '/webapi/user';
        $.ajax({
            url: userDetUrl,
        }).done(function(data) {
            if (data) {
                $('#user-name-label').text(data.userName);
            }
        });
    });

    $("#footer").load("footer.html");

    // browser window scroll (in pixels) after which the "back to top" link is shown
    var offset = 200,
        //browser window scroll (in pixels) after which the "back to top" link opacity is reduced
        offset_opacity = 1200,
        //duration of the top scrolling animation (in ms)
        scroll_top_duration = 700,
        //grab the "back to top" link
        $back_to_top = $('.cd-top');

    //hide or show the "back to top" link
    $(window).scroll(function() {
        ($(this).scrollTop() > offset) ? $back_to_top.addClass('cd-is-visible'): $back_to_top.removeClass('cd-is-visible cd-fade-out');
        if ($(this).scrollTop() > offset_opacity) {
            $back_to_top.addClass('cd-fade-out');
        }
    });

    //smooth scroll to top
    $back_to_top.on('click', function(event) {
        event.preventDefault();
        $('body,html').animate({
            scrollTop: 0,
        }, scroll_top_duration);
    });
});