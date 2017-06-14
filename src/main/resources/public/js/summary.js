$(document).ready(function() {

    $body = $("body");
    $(document).on({
        ajaxStart: function() { $body.addClass("loading"); },
        ajaxStop: function() { $body.removeClass("loading"); }
    });

    var serviceTable = $('#serviceTable').DataTable({
        ajax: {
            url: 'json/summary2.json',
            dataSrc: ""
        },
        scrollY: '70vh',
        scrollCollapse: true,
        scrollX: true,
        paginate: false,
        bSort: false,
        orderCellsTop: true,
        fixedColumns: true,
        columns: [{
                className: 'details-control',
                orderable: false,
                data: null,
                defaultContent: '',
                width: '40px'
            },
            { data: 'message', width: '340px' },
            { data: 'count', },
            { data: 'countPer' },
            { data: 'sum' },
            { data: 'sumPer' }

        ],
        columnDefs: [{
            render: function(data, type, row) {
                return data + '%';
            },
            className: 'dt-right',
            targets: 3
        }, {
            render: function(data, type, row) {
                return data + '%';
            },
            className: 'dt-right',
            targets: 5
        }, {
            className: 'dt-right',
            targets: 2
        }, {
            render: function(data, type, row) {
                return '<b>$' + data + '</b>';
            },
            className: 'dt-right',
            targets: 4
        }],
        language: {
            info: "<strong>_START_</strong>-<strong>_END_</strong> of <strong>_TOTAL_</strong>",
            infoFiltered: "(filtered from _MAX_ total entries)",
            infoPostFix: "",
            paginate: {
                next: "<i class='glyphicon glyphicon-menu-right'></i>",
                previous: "<i class='glyphicon glyphicon-menu-left'></i>"
            }
        },
        lengthChange: false
    });

    var buttons = new $.fn.dataTable.Buttons(serviceTable, {
        buttons: [{
            extend: 'excelHtml5',
            titleAttr: 'Export to Excel',
            text: '<span class="glyphicon glyphicon-download-alt"></span>',
            exportOptions: {
                modifier: {
                    page: 'current'
                }
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



    // Add event listener for opening and closing details
    $('#serviceTable tbody').on('click', 'td.details-control', function() {
        var tr = $(this).closest('tr');
        var row = serviceTable.row(tr);

        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        } else {
            // Open this row
            //row.child('format(row.data())').show();
            tr.after('<tr><td ></td><td class=" details-control">&nbsp;&nbsp;&nbsp;TESTING</td><td ></td><td ></td><td ></td><td ></td></tr>');
            tr.addClass('shown');
        }
    });

});
