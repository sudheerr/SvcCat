var AppUtil;
AppUtil = (function() {

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
            ServiceCatalog.readTextFile(data['sampleRequest'], formControls[0]);
        }
        if (data['sampleResponse']) {
            ServiceCatalog.readTextFile(data['sampleResponse'], formControls[1]);
        }
        formControls[2].textContent = data['comments'];
    }

    function renderSVCDetails(data) {
        renderServiceData(data);
        renderVersionData(data);
        renderUsageData(data);
    }

    return {
        renderDetails: renderSVCDetails
    };
})();

$(document).ready(function(AppUtil) {
    var interfaceId = ServiceCatalog.getURLParameter('interfaceId');
    var url = ServiceCatalog.getContextPath() + '/webapi/interface/' + interfaceId;

    $.ajax({
        url: url,
        dataType: 'json'
    }).done(function(data) {
        if (data) {
            AppUtil.renderDetails(data);
        }
    });

}(AppUtil));