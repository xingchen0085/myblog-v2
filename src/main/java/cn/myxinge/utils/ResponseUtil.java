package cn.myxinge.utils;

import cn.myxinge.entity.User;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by XingChen on 2017/11/19.
 */
public class ResponseUtil {

    /**
     * 返回字符串 文本
     *
     * @param success
     * @param msg
     * @return
     */
    public static String returnMsg(boolean success, String msg) {
        JSONObject rtn = new JSONObject();
        rtn.put("success", success);
        rtn.put("message", msg);
        return rtn.toJSONString();
    }

    /**
     * 返回Json对象
     */
    public static JSONObject returnJson(boolean success, String msg) {
        JSONObject rtn = new JSONObject();
        rtn.put("success", success);
        rtn.put("message", msg);
        return rtn;
    }

    public static User loginUser(HttpServletRequest request) {
        Object loginU = request.getSession().getAttribute("loginU");
        if (null != loginU) {
            return (User) loginU;
        }
        return null;
    }

    /**
     * 清除seesion登录的用户
     */
    public static void replaceLogin(HttpServletRequest request, User login) throws Exception {
        Object loginU = request.getSession().getAttribute("loginU");
        if (null != loginU) {
            User user = (User) loginU;
            user = clonyObj(user, login);
            request.getSession().setAttribute("loginU", user);
        }
    }

    public static User clonyObj(User user, User newUser) throws Exception {
        Method[] methods = newUser.getClass().getMethods();
        for (Method m : methods) {
            if (m.getName().startsWith("set")) {
                String name = m.getName();
                String methodSuffix = name.substring(name.indexOf("set") + 3);
                Method u_method = user.getClass().getMethod("get" + methodSuffix);
                m.invoke(newUser, u_method.invoke(user, null));
            }
        }
        return newUser;
    }
}
