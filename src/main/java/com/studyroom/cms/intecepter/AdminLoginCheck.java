package com.studyroom.cms.intecepter;


import com.studyroom.cms.entity.Administrator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studyroom.cms.result.Const;
import com.studyroom.cms.result.Result;
import com.studyroom.cms.result.ResultCodeEnum;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;


@Component
public class AdminLoginCheck implements HandlerInterceptor {

    /**
     *
     * this is login check
     * 在请求到达controller之前
     *     //return true : 可以通过拦截器（不拦截）
     *     //return false : 拦截请求
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {

        System.out.println("=====prehandle");
        //return false;
        HttpSession session = request.getSession();
        Administrator admin = (Administrator) session.getAttribute(Const.CURRENT_USER_ADMIN);
        if(admin!=null){
            //admin exist, return true
            return true;
        }
        //用户未登录 重写response
        try {

            //防止乱码
            response.reset();
            response.addHeader("Content-Type","application/json;charset=utf-8");

            PrintWriter printWriter = response.getWriter();
            Result result = Result.fail(ResultCodeEnum.NOTLOGIN);

//            //对象转json
//            ObjectMapper objectMapper = new ObjectMapper();
//            String info = objectMapper.writeValueAsString(serverResponse);
//            printWriter.write(info);
//            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * posthandle
     * after deal with request
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("=====posthandle");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }


    /**
     * 客户端接收到服务端的响应之后
     * client get the server message
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("=====afterCompletion");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
