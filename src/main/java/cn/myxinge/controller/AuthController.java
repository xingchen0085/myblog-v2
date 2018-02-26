package cn.myxinge.controller;

import cn.myxinge.entity.User;
import cn.myxinge.service.UserService;
import cn.myxinge.utils.HttpClientUtil;
import cn.myxinge.utils.ResponseUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenxinghua on 2017/12/20.
 * <p>
 * 处理用户认证  //暂用sessoin存储登录用户，过期时间为 ： 关闭浏览器
 */
@Controller
@RequestMapping("/u")
public class AuthController {

    @Autowired
    private UserService userService;

    private Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @Value("${github_client_id}")
    private String github_client_id;
    @Value("${github_client_secret}")
    private String github_client_secret;
    @Value("${github_auth_token}")
    private String github_auth_token;
    @Value("${github_redirect_uri}")
    private String github_redirect_uri;
    @Value("${github_user_info}")
    private String github_user_info;

    @RequestMapping("/auth")
    public String code(String code, String state, HttpServletRequest request, Model model, HttpServletResponse response) throws IOException {
        if (code == null) {
            model.addAttribute("loginMsg", "授权失败");
            return "/log";
        }

        //url处理
        String url_token = github_auth_token.replace("*client_id*", github_client_id)
                .replace("*client_secret*", github_client_secret)
                .replace("*code*", code)
                .replace("*redirect_uri*", github_redirect_uri);

        //发起请求并处理
        String token = HttpClientUtil.get(url_token);

        //token 处理
        if (token != null && token.startsWith("access_token")) {
            int end = token.indexOf("&");
            if (end == -1) {
                token = token.substring(0, token.length() - 1);
            } else {
                token = token.substring(0, end);
            }
        } else {
            model.addAttribute("loginMsg", "授权失败");
            return "/log";
        }

        //信息获取
        String userInfo = HttpClientUtil.get(github_user_info.concat("?").concat(token));
        if (userInfo == null) {
            model.addAttribute("loginMsg", "授权失败");
            return "/log";
        }

        //数据处理
        final User user = jsonHandler(userInfo);

        //数据库存储，不使用第三的ID
        String id = userService.regThreepart(user);

        if (StringUtils.isEmpty(id)) {
            model.addAttribute("loginMsg", "授权失败");
            return "/log";
        }

        User loginU = user;
        loginU.setId(Integer.parseInt(id));
        request.getSession().setAttribute("loginU", loginU);
        if ("/".equals(state)) {
            response.sendRedirect("/");
            return null;
        }
        return "redirect:" + state;
    }

    @RequestMapping("/reg")
    @ResponseBody
    public JSONObject reg(User user, String repwd) {
        if (null != user) {
            if (StringUtils.isEmpty(user.getEmail())) {
                return ResponseUtil.returnJson(false, "require email");
            }
            if (StringUtils.isEmpty(user.getPwd())) {
                return ResponseUtil.returnJson(false, "require password");
            }
            if (StringUtils.isEmpty(repwd)) {
                if (!repwd.equals(user.getPwd())) {
                    return ResponseUtil.returnJson(false, "password is not same");
                }
            }
        }
        //发送请求
        String rtn = userService.reg(user);
        if (!rtn.equals("1")) {
            return ResponseUtil.returnJson(false, rtn);
        }

        return ResponseUtil.returnJson(true, rtn);
    }

    @RequestMapping("/log")
    @ResponseBody
    public JSONObject log(User user, HttpServletRequest request) {
        try {
            if (null != user) {
                if (StringUtils.isEmpty(user.getEmail())) {
                    return ResponseUtil.returnJson(false, "require email");
                }
                if (StringUtils.isEmpty(user.getPwd())) {
                    return ResponseUtil.returnJson(false, "require password");
                }
            }

            //加密
            Md5Hash md5Hash = new Md5Hash(user.getPwd(), user.getEmail(), 2);

            user.setPwd(md5Hash.toHex());
            JSONObject rtnJson = JSONObject.parseObject(userService.log(user));
            User login = null;
            String msg = rtnJson.getString("success");
            if ("success".equals(msg)) {
                login = JSONObject.parseObject(rtnJson.getString("userInfo"), User.class);
            } else {
                return ResponseUtil.returnJson(false, msg);
            }

            request.getSession().setAttribute("loginU", login);

            return ResponseUtil.returnJson(true, "登陆成功");
        } catch (Exception e) {
            LOG.error("登录发生异常", e);
            return ResponseUtil.returnJson(false, "system error");
        }
    }

    @RequestMapping("/confirm")
    public String confirm(User user, Model model) {
        String message = null;
        try {
            if (null != user) {
                if (StringUtils.isEmpty(user.getEmail())) {
                    message = "邮箱未注册，激活失败！";
                }
                if (StringUtils.isEmpty(user.getConfirm_id())) {
                    message = "激活码为空，激活失败！";
                }
            }

            //查询并更新
            String rtn = userService.confirm(user);

            JSONObject jsonObject = JSONObject.parseObject(rtn);
            User c_user = null;
            if ("1".equals(jsonObject.getString("success"))) {
                message = "激活成功！";
                c_user = JSONObject.parseObject(jsonObject.getString("userInfo"), User.class);
                model.addAttribute("c_user", c_user);
            } else {
                message = jsonObject.getString("success");
            }

            model.addAttribute("message", message);
            return "confirm";
        } catch (Exception e) {
            LOG.error("激活发生异常", e);
            message = "激活失败,请稍候再试.";
            return "confirm";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().setAttribute("loginU", null);
        return "redirect:/";
    }

    @RequestMapping("/myself")
    public String myself(HttpServletRequest request, Model model) {
        Object loginU = request.getSession().getAttribute("loginU");
        if (null == loginU) {
            return "/log";
        }
        User login = (User) loginU;
        if (!StringUtils.isEmpty(login.getIsxing())) {
            return "/myself";
        } else {
            model.addAttribute("user", login);
            return "userInfo";
        }
    }

    @RequestMapping("/userInfo/{login}")
    public String userInfo(@PathVariable String login, HttpServletRequest request, Model model) {
        if (null == login) {
            return "/";
        }
        User user = userService.userInfo(login);
        model.addAttribute("user", user);
        return "userInfo";
    }


    @RequestMapping("/update")
    @ResponseBody
    public JSONObject update(User user, HttpServletRequest request, Model model) {

        Object loginU = request.getSession().getAttribute("loginU");

        if (null != loginU) {
            User u = (User) loginU;
            u = userNotNull(u, user);
            userService.updateUser(u);
            request.getSession().setAttribute("loginU", u);
            return ResponseUtil.returnJson(true, "资料更新成功");
        }
        return ResponseUtil.returnJson(true, "资料更新失败");
    }

    @RequestMapping("/changePwd")
    @ResponseBody


    public JSONObject changePwd(String pwd, String opwd, HttpServletRequest request, String repwd) {
        if (StringUtils.isEmpty(pwd) || StringUtils.isEmpty(opwd) || StringUtils.isEmpty(repwd)) {
            return ResponseUtil.returnJson(false, "修改失败");
        }

        if (!repwd.equals(pwd)) {
            return ResponseUtil.returnJson(false, "两次密码不一致，修改失败");
        }

        Object loginU = request.getSession().getAttribute("loginU");
        if (null != loginU) {
            User u = (User) loginU;
            //检测原密码是否正确，先登录即可
            //加密
            Md5Hash hash = new Md5Hash(opwd, u.getEmail(), 2);
            u.setPwd(hash.toHex());
            String rtn = userService.log(u);

            JSONObject jsonObject = JSONObject.parseObject(rtn);
            String isOk = jsonObject.getString("success");
            if ("success".equals(isOk)) {
                //登陆成功
                if (pwd.equals(opwd)) {
                    return ResponseUtil.returnJson(false, "密码不能和原密码一致");
                }
                //加密
                Md5Hash md5Hash = new Md5Hash(pwd, u.getEmail(), 2);
                u.setPwd(md5Hash.toHex());
                userService.updateUser(u);
                request.getSession().setAttribute("loginU", null);
                return ResponseUtil.returnJson(true, "密码更新成功");
            } else {
                return ResponseUtil.returnJson(false, "原密码错误，修改失败");
            }
        }
        return ResponseUtil.returnJson(true, "资料更新失败");
    }

    @RequestMapping("/resetPwd")
    @ResponseBody
    public JSONObject resetPwd(String re_mail) {
        if (StringUtils.isEmpty(re_mail)) {
            return ResponseUtil.returnJson(false, "邮箱为空");
        }
        String rtn = userService.resetPwd(re_mail);
        return JSONObject.parseObject(rtn);
    }

    @RequestMapping("/resetP")
    @ResponseBody
    public JSONObject resetP(String resetid, String pwd, String repwd) {
        if (StringUtils.isEmpty(resetid)) {
            return ResponseUtil.returnJson(false, "ID为空");
        }
        if (!StringUtils.isEmpty(repwd) && repwd.equals(pwd)) {
            String rtn = userService.resetP(resetid, pwd);
            return JSONObject.parseObject(rtn);
        }
        return ResponseUtil.returnJson(false, "密码不一致");
    }

    private User userNotNull(User byId, User user) {
        if (null != user.getPwd()) {
            byId.setPwd(user.getPwd());
        }
        if (null != user.getName()) {
            byId.setName(user.getName());
        }
        if (null != user.getHtml_url()) {
            byId.setHtml_url(user.getHtml_url());
        }
        return byId;
    }


    @RequestMapping("/uploadUserAvatar")
    @ResponseBody
    public String uploadUserAvatar(String image, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Object loginU = request.getSession().getAttribute("loginU");
        Integer usreId = null;
        String avatar = null;
        if (null != loginU) {
            usreId = ((User) loginU).getId();
        }
        if (usreId == null) {
            map.put("result", "失败");
            return new JSONObject(map).toJSONString();
        }
        String url = userService.uploadUserAvatar(image, usreId);
        map.put("result", "ok");
        map.put("url", url);

        ((User) loginU).setAvatar_url(url);
        request.getSession().setAttribute("loginU", ((User) loginU));
        return new JSONObject(map).toJSONString();
    }

    private User jsonHandler(String userInfo) {
        User user = null;
        try {
            user = JSONObject.parseObject(userInfo, User.class);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return user;
    }
}











