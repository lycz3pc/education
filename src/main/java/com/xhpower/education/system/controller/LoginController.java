package com.xhpower.education.system.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.xhpower.education.common.BaseController;
import com.xhpower.education.utils.R;
import com.xhpower.education.utils.ShiroUtils;
import com.xhpower.plugins.common.security.MD5;

@Controller("admin.loginController")
public class LoginController extends BaseController {

    private Logger log = Logger.getLogger(LoginController.class);

    @Autowired
    private Producer producer;

    @RequestMapping("/captcha")
    public void captcha(HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        // 生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);
        // 保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);

    }

    /**
     * 
     * @Title: login
     * @Description: 登陆
     * @param username
     * @param password
     * @param captcha
     * @return
     * @throws IOException 设定文件
     * @author lisf
     * @return R 返回类型
     * @throws
     */
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    @ResponseBody
    public R login(HttpServletRequest request, String username, String password, String captcha) throws IOException {
        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);

        if (!captcha.equalsIgnoreCase(kaptcha)) { return R.error("验证码不正确"); }

        try {
            Subject subject = ShiroUtils.getSubject();
            // sha256加密
            // password = new Sha256Hash(password).toHex();
            // md5加密
            UsernamePasswordToken token = new UsernamePasswordToken(username, MD5.encrypt(password));
            subject.login(token);
        } catch (ExcessiveAttemptsException e) {
            return R.error("登录频繁,请稍后再试");
        } catch (UnknownAccountException e) {
            return R.error("用户或密码不正确");
        } catch (IncorrectCredentialsException e) {
            return R.error("用户或密码不正确");
        } catch (LockedAccountException e) {
            return R.error(e.getMessage());
        } catch (AuthenticationException e) {
            return R.error(e.getMessage());
        }
        log.info("login success");

        return R.success();
    }

    /**
     * 
     * @Title: logout
     * @Description: 注销
     * @return 设定文
     * @author lisf
     * @return String 返回类型
     * @throws
     */
    @RequestMapping("/logout")
    public String logout() {
        ShiroUtils.logout();
        return "redirect:/admin/login.html";
    }

    @RequestMapping("/sys/getAdmin")
    @ResponseBody
    public R getUser() {
        return R.success().put("admin", ShiroUtils.getAdminEntity());
    }

}
