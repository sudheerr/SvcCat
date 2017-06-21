$(document).ready(function() {
    // Inserting row to filtering
    $('#serviceTable thead tr#filterrow th').each(function() {
        $(this).html('<div class="rounded"><input style="width:100%" type="text"/></div>');
    });

    var dialog = $("#dialog2").dialog({
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
                table.button('2').trigger();
            }
        }]
    });

    dialog.data("uiDialog")._title = function(title) {
        title.html(this.options.title);
    };

    dialog.dialog('option', 'title', '<i class="fa fa-info-circle" aria-hidden="true"></i> Information');
    var gridURL = ServiceCatalog.getContextPath() + '/webapi/interfaces';
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
                data: 'serviceName',
                width: '200px'
            },
            {
                data: 'source',
                width: '90px'
            },
            {
                data: 'target',
                width: '90px'
            },
            {
                data: 'taxonomy',
                width: '120px'
            },
            {
                data: 'purpose',
                width: '260px'
            },
            {
                defaultContent: '',
                width: '80px'
            }, //TODO
            {
                defaultContent: '',
                width: '110px'
            }, //TODO
            {
                data: 'serviceType',
                width: '80px'
            },
            {
                data: 'transportType',
                defaultContent: '',
                width: '70px'
            },
            {
                defaultContent: '',
                width: '60px'
            }, //TODO
            {
                data: 'serviceStatusDesc',
                width: '70px'
            },
            {
                defaultContent: '',
                width: '60px'
            },
            {
                data: 'serviceId',
                width: '80px'
            }
        ],
        columnDefs: [{
                "render": function(data, type, row) {
                    return '<a target="_blank" href="interface-details.html?interfaceId=' + row.serviceId + '">' + data + '</a>';
                },
                "targets": 0
            }, {
                targets: 4,
                render: $.fn.dataTable.render.ellipsis(80)
            },
            {
                targets: 12,
                className: 'dt-right'
            }
        ],
        language: {
            info: '<strong>_START_</strong>-<strong>_END_</strong> of <strong>_TOTAL_</strong>',
            infoFiltered: '(filtered from _MAX_ total entries)',
            infoPostFix: '',
            paginate: {
                next: "<i class='glyphicon glyphicon-menu-right'></i>",
                previous: "<i class='glyphicon glyphicon-menu-left'></i>"
            }
        },
        lengthChange: false
    });

    new $.fn.dataTable.Buttons(serviceTable, {
        buttons: [{
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
            titleAttr: 'Export to Excel',
            text: '<span class="fa fa-file-excel-o"></span>',
            action: function() {
                $('#dialog2').dialog('open');
            }
        }],
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
    $("#filterrow input").on('keyup change', function() {
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

});