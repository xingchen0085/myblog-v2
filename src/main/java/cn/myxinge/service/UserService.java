package cn.myxinge.service;

import cn.myxinge.entity.User;

/**
 * Created by XingChen on 2017/12/20.
 */
public interface UserService {
    public String reg(User user);

    public String log(User user);

    String confirm(User user);

    String uploadUserAvatar(String image, Integer usreId);

    String regThreepart(User user);

    User userInfo(String login);

    String updateUser(User user);

    //发邮件
    String resetPwd(String re_mail);

    //重置
    String resetP(String resetid, String pwd);
}
