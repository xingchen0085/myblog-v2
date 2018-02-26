package cn.myxinge.service.impl;

import cn.myxinge.entity.User;
import cn.myxinge.service.UserService;
import cn.myxinge.utils.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by XingChen on 2017/12/20.
 */
@Service
public class UserServiceImpl implements UserService {
    @Value("${url_reg}")
    private String url_reg;

    @Value("${url_log}")
    private String url_log;

    @Value("${url_confirm}")
    private String url_confirm;

    @Value("${url_uploadUserAvatar}")
    private String url_uploadUserAvatar;

    @Value("${url_regThreepart}")
    private String url_regThreepart;

    @Value("${url_userInfo}")
    private String url_userInfo;

    @Value("${url_updateUser}")
    private String url_updateUser;

    @Value("${url_resetPwd}")
    private String url_resetPwd;

    @Value("${url_resetP}")
    private String url_resetP;

    @Override
    public String reg(User user) {

        //加密
        Md5Hash md5Hash = new Md5Hash(user.getPwd(), user.getEmail(), 2);

        //注册需要哪些信息？邮箱，密码
        Map<String, String> map = new HashMap<String, String>();
        map.put("email", user.getEmail());
        map.put("pwd", md5Hash.toHex());
        map.put("name", user.getName());

        return HttpClientUtil.post(url_reg, map, "utf-8");
    }

    @Override
    public String log(User user) {
        return HttpClientUtil.get(url_log.concat("?email=").concat(user.getEmail()).concat("&pwd=").concat(user.getPwd()));
    }

    @Override
    public String confirm(User user) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("email", user.getEmail());
        map.put("confirm_id", user.getConfirm_id());
        String rtn = HttpClientUtil.post(url_confirm, map, "utf-8");
        return rtn;
    }

    @Override
    public String uploadUserAvatar(String image, Integer usreId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("image", image);
        map.put("userId", usreId + "");
        return HttpClientUtil.post(url_uploadUserAvatar, map, "utf-8");
    }

    @Override
    public String regThreepart(User user) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("login", user.getLogin());
        map.put("html_url", user.getHtml_url());
        map.put("email", user.getEmail());
        map.put("name", user.getName());
        map.put("avatar_url", user.getAvatar_url());
        map.put("created_at", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getCreated_at()));
        map.put("updated_at", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getUpdated_at()));
        map.put("state", user.getState());
        return HttpClientUtil.post(url_regThreepart, map, "utf-8");
    }

    @Override
    public User userInfo(String login) {
        String rtn = HttpClientUtil.get(url_userInfo + "?login=" + login);

        User user = JSONObject.parseObject(rtn, User.class);

        return user;
    }

    @Override
    public String updateUser(User user) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", user.getId() + "");
        map.put("login", user.getLogin());
        map.put("email", user.getEmail());
        map.put("pwd", user.getPwd());
        map.put("html_url", user.getHtml_url());
        map.put("avatar_url", user.getAvatar_url());
        map.put("name", user.getName());
        map.put("created_at", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getCreated_at()));
        map.put("updated_at", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getCreated_at()));
        map.put("confirm_id", user.getConfirm_id());
        map.put("state", user.getState());
        map.put("isxing", user.getIsxing());
        return HttpClientUtil.post(url_updateUser, map, "utf-8");
    }

    @Override
    public String resetPwd(String re_mail) {
        return HttpClientUtil.get(url_resetPwd + "?re_mail=" + re_mail);
    }

    @Override
    public String resetP(String resetid, String pwd) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("login", resetid);
        map.put("pwd", pwd);
        return HttpClientUtil.post(url_resetP, map, "utf-8");
    }
}


