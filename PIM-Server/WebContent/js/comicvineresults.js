
	$.getJSON('http://127.0.0.01:8080/PIM-Server/ComicVineController', function(data) {
	
      		var head="<div class='bs-example table-responsive'><table class='table table-striped table-hover' id='sub-table'>";
	        var tr="<thead><tr>";
	        var td1="<th><b>Name</b></th>";
	        var td2="<th><b>Issue Count</b></th>";
	        var td3="<th><b>Resource Type</b></th></tr>";
	
	        $("#table").append(head+tr+td1+td2+td3+"</thead><tbody>"); 
	        
	        for (var i in data.COMICVINE) {
	            var tr="<tr><div class='row'>";
		        var td1="<td><div class ='span8'>"+data.COMICVINE[i].name+"</div></td>";
		        var td2="<td><div class ='span2'>"+data.COMICVINE[i].count_of_issues+"</div></td>";
		        var td3="<td><div class ='span2'>"+data.COMICVINE[i].resource_type+"</div></td></div></tr>";
		
		        $("#sub-table").append(tr+td1+td2+td3); 
	        }
	        $("#table").append("</tbody></table></div>");
	  });
