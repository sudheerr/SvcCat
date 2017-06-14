$(document).ready(function() {
	
	$body = $("body");
    $(document).on({
        ajaxStart: function() { $body.addClass("loading");    },
        ajaxStop: function() { $body.removeClass("loading"); }    
    });
    
    // Inserting row to filtering
    $('#serviceTable thead tr#filterrow th').each(function() {
        $(this).html('<div class="rounded"><input style="width:100%" type="text"/></div>');
    });	
    
    var serviceTable = $('#serviceTable').DataTable({
        ajax: {
            url:'json/test.json',
            dataSrc: ""
        },
        scrollY:'70vh',
        scrollCollapse: true,
        scrollX: true,
        orderCellsTop: true,
        pageLength: 25,
        fixedColumns:true,
        columns: [
            { data: 'release'},
            { data: 'wricef', defaultContent: '' },
            { data: 'source', width: '120px' },
            { data: 'target'},
            { data: 'svcName'},
            { data: 'totalSRC',  defaultContent: '' },
            { data: 'srcSuccess',  defaultContent: '' },
            { data: 'srcErrors', defaultContent: '' },
            { data: 'totalEIS', defaultContent: '' },
            { data: 'eisSuccess', defaultContent: '' },
            { data: 'eisErrors', defaultContent:''},
            { data: 'flowDir', defaultContent: '' },
            { data: 'totalSAP',  defaultContent: '' },
            { data: 'sapSuccess',  defaultContent: '' },
            { data: 'sapErrors', defaultContent: '' }            
        ],
		columnDefs : [{
			fnCreatedCell : function(nTd,
					sData, oData, iRow, iCol) {
				if (sData > 0) {
					$(nTd).css('backgroundColor', '#ff3a33');
					$(nTd).css('color', '#fff');
                    $(nTd).css('textDecoration', 'underline');
                    $(nTd).css('cursor', 'pointer');
				}
			},
			className:'dt-right',
			"targets" : 14
		} , {
			fnCreatedCell : function(nTd,
					sData, oData, iRow, iCol) {
				if (sData > 0) {
					$(nTd).css('backgroundColor', '#ff3a33');
					$(nTd).css('color', '#fff');
                    $(nTd).css('textDecoration', 'underline');
                    $(nTd).css('cursor', 'pointer');
				}
			},
			className:'dt-right',
			"targets" : 10
		} ,{
			fnCreatedCell : function(nTd,
					sData, oData, iRow, iCol) {
				if (sData > 0) {
					$(nTd).css('backgroundColor', '#ff3a33');
					$(nTd).css('color', '#fff');
                    $(nTd).css('textDecoration', 'underline');
                    $(nTd).css('cursor', 'pointer');
				}
			},className:'dt-right',
			"targets" : 7
		},
		{	className:"dt-center",
            "render": function ( data, type, row ) {
            	if(data.indexOf('=')===0){
            		  return '<span class="glyphicon glyphicon-arrow-right"></span>';
            	}else{
            		 return '<span class="glyphicon glyphicon-arrow-left"></span>';
            	}
              
            },
            "targets": 11
        },{
            targets: 2,
            render: $.fn.dataTable.render.ellipsis(20)
        },{
            targets: 4,
            render: $.fn.dataTable.render.ellipsis(30)
        },{
            targets: 5,
            className:'dt-right'
        },{
            targets: 6,
            className:'dt-right'
        },{
            targets: 9,
            className:'dt-right'
        },{
            targets: 10,
            className:'dt-right'
        },{
            targets: 12,
            className:'dt-right'
        },{
            targets: 13,
            className:'dt-right'
        },{
            targets: 14,
            className:'dt-right'
        }
		],
        language: {
            info:           "<strong>_START_</strong>-<strong>_END_</strong> of <strong>_TOTAL_</strong>",
            infoFiltered:   "(filtered from _MAX_ total entries)",
            infoPostFix:    "",
            paginate: {
                next: "<i class='glyphicon glyphicon-menu-right'></i>",
                previous: "<i class='glyphicon glyphicon-menu-left'></i>"
            }
        },
        lengthChange: false
    });

    var buttons = new $.fn.dataTable.Buttons(serviceTable, {
        buttons: [{
                titleAttr: 'Toggle Filter',
                text: '<span class="glyphicon glyphicon-filter"></span>',
                action: function() {
                    $('#filterrow').toggle();
                }
            },{
                titleAttr: 'Clear ALL Filters',
                text: '<span class="glyphicon glyphicon-remove-circle"></span>',
                action: function() {
                    $('#filterrow').find('input').each(function(index, input) { $(input).val(''); });
                    serviceTable.columns().search('').draw();
                    $(serviceTable.columns().header()).removeClass('appliedFilter');
                }
            }, {
                extend: 'excelHtml5',
                titleAttr: 'Export to Excel',
                text: '<span class="glyphicon glyphicon-download-alt"></span>',
                exportOptions: {
                    modifier: {
                        page: 'current'
                    }
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
    $("#filterrow input").on('keyup change', function() {
        var column  = serviceTable.column($(this).parent().parent().index() + ':visible');
        column.search(this.value).draw();
        var header = $(column.header());
        if(!this.value){
            header.removeClass('appliedFilter');
        }else{
            header.addClass('appliedFilter');
        }
        
    });


    $("#serviceTable_length").on('change', function() {
        serviceTable.page.len( $(this).val() ).draw();
    });

});
