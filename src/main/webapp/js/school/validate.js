// 修改easyUI默认验证提示
//$.fn.validatebox.defaults.rules.email.message = '请输入有效的电子邮件地址';
// easyUI拓展验证
	/*$.extend($.fn.validatebox.defaults.rules, {
	    checkEmail: {
			validator: function(value, param){
				var flag = false;
				$.ajax({
					method: 'POST',
					url: '/platform/expert/checkEmail',
					contentType: "application/json",
			        dataType: 'json',
			        async: false,
			        data: JSON.stringify({id: $(param[0]).val(), email: value}),
			        success: function(data){
			        	flag = data;
			        }
				});
				return flag;
			},
			message: 'Email已存在'
	    },
	    mobile: {// 验证手机号码
            validator: function (value) {
                return /^(13|15|18)\d{9}$/i.test(value);
            },
            message: '手机号码格式不正确'
        }
	});*/

	$.extend($.fn.validatebox.defaults.rules, {
	    checkUnitSitPhone: {
			validator: function(value, param){
				var flag = false;
				$.ajax({
					method: 'POST',
					url: '/platform/school/checkUnitSitPhone',
					contentType: "application/json",
			        dataType: 'json',
			        async: false,
			        data: JSON.stringify({id: $(param[0]).val(), unitSitPhone: value}),
			        success: function(data){
			        	flag = data;
			        	/*$(".this_textarea input").attr("disabled","disabled");*/
			        }
				});
				//$(".this_textarea input").attr("disabled","disabled");
				return flag;
			},
			message: '单位座机号已存在'
	    },
	    sitmobile: {// 验证手机号码
            validator: function (value) {
                return /^0\d{2,3}-[1-9]\d{6,7}$/i.test(value);
            },
            message: '座机号码格式不正确'
        }
	});
	
	$.extend($.fn.validatebox.defaults.rules, {
		checkUnitTelPhone: {
			validator: function(value, param){
				var flag = false;
				$.ajax({
					method: 'POST',
					url: '/platform/school/checkUnitTelPhone',
					contentType: "application/json",
			        dataType: 'json',
			        async: false,
			        data: JSON.stringify({id: $(param[0]).val(), unitTelPhone: value}),
			        success: function(data){
			        	flag = data;
			        }
				});
				return flag;
			},
			message: '单位手机号已存在'
	    },
	    mobile: {// 验证手机号码
            validator: function (value) {
                return /^1\d{10}$/i.test(value);
            },
            message: '手机号码格式不正确'
        }
	});
	
	$.extend($.fn.validatebox.defaults.rules, {
	    checkPreSitPho: {
			validator: function(value, param){
				var flag = false;
				$.ajax({
					method: 'POST',
					url: '/platform/school/checkPreSitPho',
					contentType: "application/json",
			        dataType: 'json',
			        async: false,
			        data: JSON.stringify({id: $(param[0]).val(), preSitPho: value}),
			        success: function(data){
			        	flag = data;
			        }
				});
				return flag;
			},
			message: '校长座机号已存在'
	    },
	    sitmobile: {// 验证手机号码
            validator: function (value) {
                return /^0\d{2,3}-[1-9]\d{6,7}$/i.test(value);
            },
            message: '座机号码格式不正确'
        }
	});
	
	$.extend($.fn.validatebox.defaults.rules, {
		checkPrePhone: {
			validator: function(value, param){
				var flag = false;
				$.ajax({
					method: 'POST',
					url: '/platform/school/checkPrePhone',
					contentType: "application/json",
			        dataType: 'json',
			        async: false,
			        data: JSON.stringify({id: $(param[0]).val(), prePhone: value}),
			        success: function(data){
			        	flag = data;
			        }
				});
				return flag;
			},
			message: '校长联系电话已存在'
	    },
	    mobile: {// 验证手机号码
            validator: function (value) {
                return /^1\d{10}$/i.test(value);
            },
            message: '手机号码格式不正确'
        }
	});
	
	$.extend($.fn.validatebox.defaults.rules, {
	    checkInfreceiSitP: {
			validator: function(value, param){
				var flag = false;
				$.ajax({
					method: 'POST',
					url: '/platform/school/checkInfreceiSitP',
					contentType: "application/json",
			        dataType: 'json',
			        async: false,
			        data: JSON.stringify({id: $(param[0]).val(), infreceiSitP: value}),
			        success: function(data){
			        	flag = data;
			        }
				});
				return flag;
			},
			message: '信息对接任人座机号已存在'
	    },
	    sitmobile: {// 验证手机号码
            validator: function (value) {
                return /^0\d{2,3}-[1-9]\d{6,7}$/i.test(value);
            },
            message: '座机号码格式不正确'
        }
	});

	$.extend($.fn.validatebox.defaults.rules, {
		checkInfreceiphone: {
			validator: function(value, param){
				var flag = false;
				$.ajax({
					method: 'POST',
					url: '/platform/school/checkInfreceiphone',
					contentType: "application/json",
			        dataType: 'json',
			        async: false,
			        data: JSON.stringify({id: $(param[0]).val(), infreceiphone: value}),
			        success: function(data){
			        	flag = data;
			        }
				});
				return flag;
			},
			message: '信息对接任人电话已存在'
	    },
	    mobile: {// 验证手机号码
            validator: function (value) {
                return /^1\d{10}$/i.test(value);
            },
            message: '手机号码格式不正确'
        }
	});
	
