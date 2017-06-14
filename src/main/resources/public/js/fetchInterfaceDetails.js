var AppUtil;
AppUtil = (function() {

    function readTextFile(url, element) {
        var rawFile = new XMLHttpRequest();
        rawFile.open("GET", url, false);
        rawFile.onreadystatechange = function() {
            if (rawFile.readyState === 4) {
                if (rawFile.status === 200 || rawFile.status === 0) {
                    element.textContent = rawFile.responseText;
                }
            }
        };
        rawFile.send(null);
    }

    function getURLParameter(sParam) {
        var sPageURL = window.location.search.substring(1);
        var sURLVariables = sPageURL.split('&');
        for (var i = 0; i < sURLVariables.length; i++) {
            var sParameterName = sURLVariables[i].split('=');
            if (sParameterName[0] === sParam) {
                return sParameterName[1];
            }
        }
    };

    function renderServiceData(data) {
        var details = $('#details');

        var formControls = details.find('.form-control');
        formControls[0].textContent = data['serviceName'];
        formControls[1].textContent = data['serviceStatusDesc'];
        formControls[3].textContent = data['purpose'];
        formControls[4].textContent = data['useCase'];
        formControls[5].textContent = data['taxonomy'];
        formControls[6].textContent = data['source'];
        formControls[7].textContent = data['target'];

    }

    function renderVersionData(data) {
        var versionDetails = $('#versionDetails');

        var formControls = versionDetails.find('.form-control');
        formControls[0].textContent = '1.0';
        formControls[1].textContent = data['serviceType'];
        formControls[2].textContent = data['frequency'];
        formControls[3].textContent = data['transportType'];
        formControls[4].textContent = data['dataType'];

        formControls[5].textContent = data['integrationType'];
        formControls[6].innerHTML = '<a href="' + data['designDoc'] + '">Design Document URL</a>';
        /*formControls[7].innerHTML  = '<a href="#">' + data['provConsMapping'] + '</a>';*/
        /*formControls[8].textContent = data['comments'];*/

        if (data['contextDiagram']) {
            $('#flowDiagram').attr('src', 'use_cases/' + data['contextDiagram']);
        }
    }

    function renderUsageData(data) {
        var svcUsage = $('#svcUsage');
        var formControls = svcUsage.find('.form-control');
        if (data['sampleRequest']) {
            readTextFile(data['sampleRequest'], formControls[0]);
        }
        if(data['sampleResponse']){
            readTextFile(data['sampleResponse'], formControls[1]);
        }
        formControls[2].textContent = data['comments'];
    }


    function renderSVCDetails(data) {
        renderServiceData(data);
        renderVersionData(data);
        renderUsageData(data);
    }

    return {
        getURLParameter: getURLParameter,
        renderDetails: renderSVCDetails
    };
})();

$(document).ready(function(AppUtil) {

    $body = $("body");
    $(document).on({
        ajaxStart: function() { $body.addClass("loading"); },
        ajaxStop: function() { $body.removeClass("loading"); }
    });

    var interfaceId = AppUtil.getURLParameter('interfaceId');
    var url = ServiceCatalog.getContextPath()+'/webapi/interface/' + interfaceId;

    $.ajax({
            url: url,
            dataType: 'json'
        })
        .done(function(data) {
            if (data) {
                AppUtil.renderDetails(data);
            }
        });
    var userDetUrl = ServiceCatalog.getContextPath()+'/webapi/user';
    $.ajax({
        url: userDetUrl,
    }).done(function (data) {
        if (console && console.log) {
            console.log("User :", data);

        }
        if(data){
            $('#user-name-label').text(data.userName);
        }
    });
}(AppUtil));
