$(document).ready(function() {
    // Inserting row to filtering
    $('#serviceTable thead tr#filterrow th').each(function() {
        $(this).html('<div class="rounded"><input style="width:100%" type="text"/></div>');
    });

    var fileUploadURL = ServiceCatalog.getContextPath() + '/webapi/upload';
    var fileupload = $('#fileupload').fileupload({
        autoUpload: false,
        url: fileUploadURL,
        replaceFileInput: false,
        add: function(e, data) {
            var btnUpload = $('#btnUpload');
            $('#fileupload_wrapper > input').val(data.files[0].name);

            btnUpload.removeAttr('disabled').removeClass('ui-state-disabled');
            btnUpload.off('click').on('click', function() {
                data.submit();
                btnUpload.attr('disabled', 'disabled').addClass('ui-state-disabled');
            });
        },
        done: function(e, data) {
            $('#wrapper2').hide();
            if (data.result.success) {
                $('#formMsg > span:first').addClass('glyphicon-ok-circle');
            } else {
                $('#formMsg > span:first').addClass('glyphicon-warning-sign');
            }

            $('#formMsg > span:last').text(data.result.message);

            if (data.result.details) {
                $('#formStatusMsg').html(data.result.details);
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            $('#wrapper2').hide();
            $('#formMsg > span:first').addClass('glyphicon-warning-sign');
            $('#formMsg > span:last').text("An error occurred in the upload : " + errorThrown);
        }
    });

    $('#dialog').dialog({
        autoOpen: false,
        modal: true,
        height: 200,
        width: 440,
        buttons: [{
                text: "Upload",
                id: "btnUpload",
                disabled: true,
                click: function() {
                    $(this).dialog('close');
                }
            },{
                text: "Cancel",
                click: function() {
                    $(this).dialog('close');
                }
            }
        ],
        open: function(event, ui) {
            $('#wrapper2').show();
            $("#btnUpload").attr('disabled', 'disabled').addClass('ui-state-disabled');
            $("#formStatusMsg").html('');
        },
        close: function() {
            $('#fileupload_wrapper > input').val('');
            $('#fileupload').val('');
            $('#formMsg > span:first').removeClass('glyphicon-warning-sign glyphicon-ok-circle');
            $('#formMsg > span:last').text('');
        }
    });

    var dialog3 = $("#dialog3").dialog({
        autoOpen: false,
        modal: true,
        height: 150,
        width: 400,
        dialogClass: "no-close",
        buttons: [{
            text: "Ok",
            click: function() {
                $(this).dialog('close');
            }
        }]
    });
    dialog3.data("uiDialog")._title = function(title) {
        title.html(this.options.title);
    };

    dialog3.dialog('option', 'title', '<i class="fa fa-info-circle" aria-hidden="true"></i> Information');

    var dialog2 = $("#dialog2").dialog({
        autoOpen: false,
        modal: true,
        height: 180,
        width: 400,
        dialogClass: "no-close",
        buttons: [{
            text: "Ok",
            click: function() {
                var table = $('#serviceTable').DataTable();
                $(this).dialog('close');
                table.button('0').trigger();
            }
        }]
    });

    dialog2.data("uiDialog")._title = function(title) {
        title.html(this.options.title);
    };

    dialog2.dialog('option', 'title', '<i class="fa fa-info-circle" aria-hidden="true"></i> Information');

    var gridURL = ServiceCatalog.getContextPath() + '/webapi/results';
    var serviceTable = $('#serviceTable').DataTable({
        ajax: {
            url: gridURL,
            dataSrc: ''
        },
        scrollY: '70vh',
        scrollCollapse: true,
        scrollX: true,
        orderCellsTop: true,
        pageLength: 25,
        fixedColumns: true,
        columns: [{
                data: 'svcName',
                width: '220px',
                name: 'first'
            },{
                data: 'provider',
                defaultContent: ''
            },{
                data: 'versionId'
            },{
                data: 'consumerName',
                width: '190px'
            },{
                data: 'domainName',
                width: '126px'
            },{
                data: 'canonicalDataModel',
                width: '154px',
                defaultContent: ''
            },{
                data: 'svcDescription',
                width: '260px'
            },{
                data: 'svcOperations',
                width: '180px',
                defaultContent: ''
            },{
                data: 'networkScope',
                width: '114px',
                defaultContent: ''
            },{
                data: 'eventType',
                width: '78px',
                defaultContent: ''
            },{
                data: 'protocol',
                width: '220px',
                defaultContent: ''
            },{
                data: 'svcSecurity',
                width: '140px',
                defaultContent: ''
            },{
                data: 'providerSLA',
                width: '90px',
                defaultContent: ''
            },{
                data: 'tiersSLA',
                width: '68px',
                defaultContent: ''
            },{
                data: 'consumerCLA',
                width: '108px',
                defaultContent: ''
            },{
                data: 'status',
                defaultContent: ''
            },{
                data: 'initiative',
                defaultContent: ''
            }
        ],
        columnDefs: [{
            "render": function(data, type, row) {
                return '<a target="_blank" href="service-details.html?svcId=' + row.svcId + '&version=' + row.versionId + '">' + data + '</a>';
            },
            "targets": [0,2]
        }, {
            "render": function(data, type, row) {
                if (data && data.indexOf('.') > -1) {
                    var path = ServiceCatalog.getContextPath();
                    return '<a target="_blank" href="' + path + '/xsd/' + data + '">' + data + '</a>';
                } else {
                    return data;
                }
            },
            "targets": 5
        }, {
            targets: [3,6,7,10,11],
            render: $.fn.dataTable.render.ellipsis(40)
        }],
        language: {
            info: '<strong>_START_</strong>-<strong>_END_</strong> of <strong>_TOTAL_</strong>',
            infoFiltered: '(filtered from _MAX_ total entries)',
            infoPostFix: ' (Consumer Count)',
            paginate: {
                next: "<i class='glyphicon glyphicon-menu-right'></i>",
                previous: "<i class='glyphicon glyphicon-menu-left'></i>"
            }
        },
        lengthChange: false,
        rowsGroup: [
            'first:name', 0, 1, 2
        ]
    });

    new $.fn.dataTable.Buttons(serviceTable, {
        buttons: [{
                /*
                 * This button is hidden through css and is triggered through next button.
                 * (To show additional information popup before downloading)
                 */
                extend: 'excelHtml5',
                exportOptions: {
                    modifier: {
                        page: 'current'
                    }
                }
            }, {
                titleAttr: 'Toggle Filter',
                text: '<span class="glyphicon glyphicon-filter"></span>',
                action: function() {
                    $('#filterrow').toggle();
                }
            }, {
                titleAttr: 'Clear ALL Filters',
                text: '<span class="glyphicon glyphicon-remove"></span>',
                action: function() {
                    $('#filterrow').find('input').each(function(index, input) {
                        $(input).val('');
                    });
                    serviceTable.columns().search('').draw();
                    $(serviceTable.columns().header()).removeClass('appliedFilter');
                }
            }, {
                titleAttr: 'Export to Excel',
                text: '<span class="fa fa-file-excel-o"></span>',
                action: function() {
                    if (isSafari()) {
                        $('#dialog3').dialog('open');
                    } else {
                        $('#dialog2').dialog('open');
                    }
                }
            }, {
                titleAttr: 'Upload file',
                text: '<span class="fa fa-upload"></span>',
                action: function() {
                    $('#dialog').dialog('open');
                }
            },
            {
                titleAttr: 'Download Excel Template',
                text: '<span class="fa fa-download"></span>',
                action: function() {
                    var url = ServiceCatalog.getContextPath() + '/Upload_Template.xlsx';
                    window.open(url, '_blank');
                }
            }
        ],
        dom: {
            container: {
                tag: 'span',
                className: 'pull-right svcBtn'
            },
            buttonContainer: {
                tag: 'span'
            },
            button: {
                tag: 'a',
                className: 'btn'
            }
        }
    }).container().appendTo($('#serviceTableHeader'));

    // Apply the filter
    $("#filterrow").find("input").on('keyup change', function() {
        var column = serviceTable.column($(this).parent().parent().index() + ':visible');
        column.search(this.value).draw();
        var header = $(column.header());
        if (!this.value) {
            header.removeClass('appliedFilter');
        } else {
            header.addClass('appliedFilter');
        }
    });

    $("#serviceTable_length").on('change', function() {
        serviceTable.page.len($(this).val()).draw();
    });

    $('#fileupload_wrapper').on('click', function() {
        $('#fileupload').click();
    });

    function isSafari() {
        var ua = navigator.userAgent.toLowerCase();
        if (ua.indexOf('safari') !== -1) {
            return ua.indexOf('chrome') <= -1;
        }
        return false;
    }

});