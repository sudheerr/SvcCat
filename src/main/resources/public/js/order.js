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
            url:'json/order.json',
            dataSrc: ""
        },
      /*  scrollY:'70vh',
        scrollCollapse: true,*/
        scrollX: true,
        orderCellsTop: true,
        pageLength: 25,
      //  fixedColumns:true,
        columns: [
            { data: 'wricef', defaultContent: '' },
            { data: 'source', width: '120px' },
            { data: 'target'},
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
					//$(nTd).css('color', '#fff');
                  // $(nTd).css('textDecoration', 'underline');
                    $(nTd).css('cursor', 'pointer');
				}
			},
			className:'dt-right',
			"targets" : 12
		} , {
			fnCreatedCell : function(nTd,
					sData, oData, iRow, iCol) {
				if (sData > 0) {
					$(nTd).css('backgroundColor', '#ff3a33');
					//$(nTd).css('color', '#fff');
                    //$(nTd).css('textDecoration', 'underline');
                    $(nTd).css('cursor', 'pointer');
				}
			},
			className:'dt-right',
			"targets" : 8
		} ,{
			fnCreatedCell : function(nTd,
					sData, oData, iRow, iCol) {
				if (sData > 0) {
					$(nTd).css('backgroundColor', '#ff3a33');
					//$(nTd).css('color', '#fff');
                   // $(nTd).css('textDecoration', 'underline');
                    $(nTd).css('cursor', 'pointer');
				}
			},className:'dt-right',
			"targets" : 5
		},
		{	className:"dt-center",
            "render": function ( data, type, row ) {
            	if(data.indexOf('=')===0){
            		  return '<span class="glyphicon glyphicon-arrow-right"></span>';
            	}else{
            		 return '<span class="glyphicon glyphicon-arrow-left"></span>';
            	}
              
            },
            "targets": 9
        },{
            targets: 0,
            render: $.fn.dataTable.render.ellipsis(20)
        },{
            targets: 2,
            render: $.fn.dataTable.render.ellipsis(30)
        },{
            targets: 3,
            className:'dt-right',
            render: function ( data, type, row ) {
            	return '$' + data.toFixed(0).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
            }
        },{
            targets: 4,
            className:'dt-right',
            render: function ( data, type, row ) {
            	return '$' + data.toFixed(0).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
            }
        },{
            targets: 5,
            className:'dt-right',
            render: function ( data, type, row ) {
            	//return '$' + data.toFixed(0).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
            	var retStr = '$' + data.toFixed(0).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
            	if(data > 0){
            		return '<a href="./recon-orderDetail.html"  style="color:#FFFFFF; text-decoration:underline;">'+retStr+'</a>';
            	}else{
            		return retStr;
            	}
            }
        },{
            targets: 6,
            className:'dt-right',
            render: function ( data, type, row ) {
            	return '$' + data.toFixed(0).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
            }
        },{
            targets: 7,
            className:'dt-right',
            render: function ( data, type, row ) {
            	return '$' + data.toFixed(0).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
            }
        },{
            targets: 8,
            className:'dt-right',
            render: function ( data, type, row ) {
            	//return '$' + data.toFixed(0).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
            	var retStr = '$' + data.toFixed(0).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
            	if(data > 0){
            		return '<a  href="./recon-orderDetail.html"  style="color:#FFFFFF; text-decoration:underline;">'+retStr+'</a>';
            	}else{
            		return retStr;
            	}
            }
        },{
            targets: 10,
            className:'dt-right',
            render: function ( data, type, row ) {
            	return '$' + data.toFixed(0).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
            }
        },{
            targets: 11,
            className:'dt-right',
            render: function ( data, type, row ) {
            	return '$' + data.toFixed(0).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
            }
        },{
            targets: 12,
            className:'dt-right',
            render: function ( data, type, row ) {
            	var retStr = '$' + data.toFixed(0).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
            	if(data > 0){
            		return '<a  href="./recon-orderDetail.html" style="color:#FFFFFF; text-decoration:underline;">'+retStr+'</a>';
            	}else{
            		return retStr;
            	}
            }
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
        lengthChange: false,
        footerCallback: function ( row, data, start, end, display ) {
            var api = this.api(), data;

            // Remove the formatting to get integer data for summation
            var intVal = function ( i ) {
                return typeof i === 'string' ?
                    i.replace(/[\$,]/g, '')*1 :
                    typeof i === 'number' ?
                        i : 0;
            };
            
            for(var i=3;i<=12;i++){
            	if(i==9){
            		continue;
            	}
        	   var total = api
               .column( i )
               .data()
               .reduce( function (a, b) {
                   return intVal(a) + intVal(b);
               }, 0 );
        	   
        	   $( api.column( i ).footer() ).html('$'+  total.toFixed(0).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,"));
            }
            /*
            // Total over all pages
            total = api
                .column( 4 )
                .data()
                .reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
 
            // Total over this page
            pageTotal = api
                .column( 4, { page: 'current'} )
                .data()
                .reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
 
            // Update footer
            $( api.column( 4 ).footer() ).html(
                '$'+pageTotal +' ( $'+ total +' total)'
            );*/
        }
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
