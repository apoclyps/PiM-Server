
	$.getJSON('http://127.0.0.01:8080/PIM-Server/AmazonController', function(data) {
	
      		var head="<div class='bs-example table-responsive'><table class='table table-striped table-hover' id='sub-table'>";
	        var tr="<thead><tr>";
	        var td1="<th><b>Title</b></th>";
	        var td2="<th><b>ASIN</b></th>";
	        var td3="<th><b>Resource Type</b></th></tr>";
	
	        $("#table").append(head+tr+td1+td2+td3+"</thead><tbody>"); 
      
	        for (var i in data.Amazon) {
	             var tr="<tr>";
	        var td1="<td>"+data.Amazon[i].title+"</td>";
	        var td2="<td>"+data.Amazon[i].asin+"</td>";
	        var td3="<td>"+data.Amazon[i].resource_type+"</td></tr>";
	
	        $("#sub-table").append(tr+td1+td2+td3); 
	        }
	        $("#table").append("</tbody></table></div>");
	  });
