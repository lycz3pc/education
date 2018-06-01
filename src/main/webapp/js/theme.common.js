function praise(element, id, num, flag) {
	$.ajax({
		type: "post",
		url: "/api/theme/praise",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(GetPraiseData(id)),
        dataType: "json",
        success: function (data, status) {
        	if (data.code == "1") {
    			if (data.result == "1") {
    				element.className = "current";
    				if (flag) {
    					element.innerHTML = "<i></i>" + (num);
    				} else {
    					element.innerHTML = "<i></i>" + (num + 1);
    				}
    			} else {
    				element.className = "";
    				if (flag) {
    					element.innerHTML = "<i></i>" + (num - 1);
    				} else {
    					element.innerHTML = "<i></i>" + (num);
    				}
    			}
    		}
        }
	});
}

function praise1(element, id, num, flag) {
	$.ajax({
		type: "post",
		url: "/api/theme/praise",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(GetPraiseData(id)),
        dataType: "json",
        success: function (data, status) {
        	if (data.code == "1") {
    			if (data.result == "1") {
    				element.className = "current";
    				if (flag) {
    					element.innerHTML = "<i></i>" + (num);
    				} else {
    					element.innerHTML = "<i></i>" + (num + 1);
    				}
    			} else {
    				element.className = "zan";
    				if (flag) {
    					element.innerHTML = "<i></i>" + (num - 1);
    				} else {
    					element.innerHTML = "<i></i>" + (num);
    				}
    			}
    		}
        }
	});
}

function collection(element, id, num, flag) {
	$.ajax({
		type: "post",
		url: "/api/theme/collection",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(GetCollectionData(id)),
        dataType: "json",
        success: function (data, status) {
        	if (data.code == "1") {
    			if (data.result == "0") {
    				element.className = "";
    				if (flag) {
    					element.innerHTML = "<i></i>" + (num - 1);
    				} else {
    					element.innerHTML = "<i></i>" + (num);
    				}
    			} else {
    				element.className = "current";
    				if (flag) {
    					element.innerHTML = "<i></i>" + (num);
    				} else {
    					element.innerHTML = "<i></i>" + (num + 1);
    				}
    			}
    		}
        }
	});
}

function getSid(){
	var sid = window.js.getSessionId();
	//var sid = "e5bd70d646104fdbae0e538a806e0cb5";
	return sid;
}

function enter() {
    return true;
}

function GetPraiseData(id) {
    var json = {
        sid: getSid(),
        themeId: id
    };
    return json;
}

function GetCollectionData(id) {
    var json = {
        sid: getSid(),
        informid: id
    };
    return json;
}