// 修改easyUI默认验证提示
$.fn.validatebox.defaults.rules.email.message = '请输入有效的电子邮件地址';
// easyUI拓展验证
	$.extend($.fn.validatebox.defaults.rules, {
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
                return /^1\d{10}$/i.test(value);
            },
            message: '手机号码格式不正确'
        }
	});

	$.extend($.fn.validatebox.defaults.rules, {
	    checkAccount: {
			validator: function(value, param){
				var flag = false;
				$.ajax({
					method: 'POST',
					url: '/platform/expert/checkAccount',
					contentType: "application/json",
			        dataType: 'json',
			        async: false,
			        data: JSON.stringify({id: $(param[0]).val(), telephone: value}),
			        success: function(data){
			        	flag = data;
			        }
				});
				return flag;
			},
			message: '专家账号已存在'
	    },
	});


	boolean save(Participant participant, MultipartFile file, HttpServletRequest request, FilePath filePath);

	@Override
	public boolean save(Participant participant, MultipartFile file, HttpServletRequest request, FilePath filePath) {
		if(!file.isEmpty()){
			String imageUrl = UploadUtil.uploadFile(filePath.getParticipantPath(), request, file,filePath.getRootPath());
			participant.setImageUrl(imageUrl);
			return this.insert(participant);
		}
		return false;
	}

	