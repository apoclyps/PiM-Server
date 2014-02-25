
	$.getJSON('http://127.0.0.01:8080/PIM-Server/ComicVineController', function(data) {
	
      		var head="<div class='bs-example table-responsive'><table class='table table-striped table-hover '>";
	        var tr="<thead><tr>";
	        var td1="<th><b>Name</b></th>";
	        var td2="<th><b>Issue Count</b></th>";
	        var td3="<th><b>Resource Type</b></th></tr>";
	
	        $("#table").append(head+tr+td1+td2+td3+"</thead><tbody>"); 
	        
	        for (var i in data) {
	             var tr="<tr>";
	        var td1="<td>"+data[i].name+"</td>";
	        var td2="<td>"+data[i].count_of_issues+"</td>";
	        var td3="<td>"+data[i].resource_type+"</td></tr>";
	
	        $("#table").append(tr+td1+td2+td3); 
	        }
	        $("#table").append("</tbody></table></div>");
	  });
