package com.xhpower.education.common;

	
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.xhpower.education.utils.R;
@Controller
public class BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(BaseController.class);
	
	@ExceptionHandler(Exception.class)
    public Object exp(Exception e) {  
        Class<?> c = e.getClass();
    	log.info("API error", e);
    	return R.error(e.getMessage());
	
		
    }  
}
