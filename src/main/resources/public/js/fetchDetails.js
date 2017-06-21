var AppUtil = (function() {

    function renderServiceData(data) {
        var details = $('#details');

        var formControls = details.find('.form-control');
        formControls[0].textContent = data['svcName'];
        formControls[1].textContent = data['status'];
        formControls[2].textContent = data['operations'];
        formControls[3].textContent = data['svcDescription'];
        formControls[4].textContent = data['taxonomy'];
        formControls[5].textContent = data['provider'];
        formControls[6].textContent = data['consumerName'];
    }

    function renderVersionData(data) {
        var versionDetails = $('#versionDetails');

        var formControls = versionDetails.find('.form-control');
        formControls[0].textContent = data['versionId'];
        formControls[1].textContent = data['serviceType'];
        formControls[2].textContent = data['serviceFrequency'];
        formControls[3].textContent = data['transportType'];
        formControls[4].textContent = data['dataType'];

        formControls[5].textContent = data['integrationType'];
        formControls[6].innerHTML = '<a href="' + data["designDocUrl"] + '">' + data['designDoc'] + '</a>';
        formControls[7].innerHTML = '<a href=""></a>';
        //formControls[8].textContent = data['comments'];

        $('#flowDiagram').attr('src', 'images/' + data['versionImgUrl']);
    }

    function renderUsageData(data) {
        var svcUsage = $('#svcUsage');
        var formControls = svcUsage.find('.form-control');
        formControls[0].textContent = data['sampleRequest'];
        formControls[1].textContent = data['sampleResponse'];
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
    var svcId = ServiceCatalog.getURLParameter('svcId');
    var version = ServiceCatalog.getURLParameter('version');
    var url = ServiceCatalog.getContextPath() + '/webapi/service/' + svcId + '/version/' + version;

    $.ajax({
        url: url,
        dataType: 'json'
    }).done(function(data) {
        if (data) {
            AppUtil.renderDetails(data);
        }
    });

}(AppUtil));