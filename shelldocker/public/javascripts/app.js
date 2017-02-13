var main = function() {
	var x = 1;
		
	$('#addbutton').click(function() {
		x += 1;
		$('.tbodyclass').append("<tr style='height:50px' id=x>" +
				"<td>" +
				"<select class='form-control selectpicker' name='"+x+"select'>" +
				"<option>Master</option>" +
				"<option>Worker</option>" +
				"<option>Storage</option>" +
				"</select>" +
				"</td>" +
				"<td><input class='form-control' id='ipaddress' name='"+x+"ipaddress' placeholder='IP Address' style='text-align:center;'></td>" +
				//modify by yuha
			//	"<td><input class='form-control' name='"+x+"sshport' placeholder='SSH Port' style='text-align:center;'></td>" +
			//	"<td><input class='form-control' name='"+x+"interface' placeholder='Interface' style='text-align:center;'></td>" +						
				"<td><input class='form-control' id='password' name='"+x+"password' placeholder='Password' style='text-align:center;'></td>" +							
				"</tr>");
		
	});	
	$('#install-button').click(function(){
		var obj = new Object();
		var test="";
//		var masterArr = new Array();
//		var workerArr = new Array();
//		var storageArr = new Array();
		var masterObj=new Object();
		var workerObj=new Object();
		var storageObj=new Object();
		var sshPort = $('#sshport').val()
		var interfaceStr= $('#interface').val()
		var tbl = $('#table tr').map(function(i) {
			var tmp= new Object()
			var str
			tmp.ip=$(this).find('#ipaddress').val()
			tmp.pw=$(this).find('#password').val()
			
			if($(this).find('select').val()=="Master"){
//				masterArr.push(tmp)
				masterObj[i]=tmp
			}
			else if($(this).find('select').val()=="Worker"){
//				workerArr.push(tmp)
				workerObj[i]=tmp
			}
			else{
//				storageArr.push(tmp)
				storageObj[i]=tmp
			}
			
		}).get()
		
		
		obj.masters=JSON.stringify(masterObj)
		obj.workers=JSON.stringify(workerObj)
		obj.storages=JSON.stringify(storageObj)
		obj.sshPort=sshPort
		obj.interfaceStr=interfaceStr
//		alert(JSON.stringify(obj))
////		
		
		
//		alert(test)
//		alert(JSON.stringify(obj))
		$.ajax({
//			processData: false,
		    type: "POST",
		    crossOrigin: true,
		    data: obj,
//		    data: {
//		    	"masters":JSON.stringify(masterArr),
//		    	"wokrers":JSON.stringify(workerArr),
//		    	"storages":JSON.stringify(storageArr),
//		    	"sshport":sshPort.toString(),
//		    	"interfaceStr":interfaceStr.toString()		    	
//		    },
		    url: "/install",		    
		    success: function(response) {
		    	
		    }
		});
	})
};
$(document).ready(main);