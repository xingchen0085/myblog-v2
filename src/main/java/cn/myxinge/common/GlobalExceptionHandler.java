package cn.myxinge.common;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by XingChen on 2017/11/20.
 * 统一异常处理类
 */
@Controller
public class GlobalExceptionHandler extends BasicErrorController {

    public GlobalExceptionHandler(){
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }
    public GlobalExceptionHandler(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
    }

    public GlobalExceptionHandler(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }

    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        int status = response.getStatus();
        if(404 == status){
            mv.setViewName("/404.html");
        }else {
            mv.setViewName("/50x.html");
        }
        return mv;
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        return super.error(request);
    }

}
