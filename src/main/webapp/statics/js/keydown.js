$(document).on("keydown",function(event){
	// 兼容FF和IE和Opera
	var theEvent = event || window.event;
	var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
	if (code == 13) {	 
		us_kerdown();
	}
})