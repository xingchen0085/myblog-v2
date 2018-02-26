package cn.myxinge.common.intercepter;

import cn.myxinge.entity.User;
import cn.myxinge.utils.HttpClientUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenxinghua on 2017/11/24.
 * 拦截器 拦截请求，获取请求者IP地址 ， 检查是否登录
 */
public class MyIntercepterHandler implements HandlerInterceptor {

    private String url_ipSave;

    MyIntercepterHandler() {
        LOG.info("init..");
        this.url_ipSave = "http://127.0.0.1:8081/admin/visit/save";
    }

    private Logger LOG = LoggerFactory.getLogger(MyIntercepterHandler.class);

    //进入前
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        LOG.info("Request: " + httpServletRequest.getRequestURL());


        final String ip = getIp(httpServletRequest);

        if (null == ip) {
            LOG.error("IP查询出错，未知错误。");
            return true;
        }

        //存入session||检查session中是否存在
        String visitIp = (String) httpServletRequest.getSession().getAttribute("visitIp");
        if (null == visitIp || !visitIp.equals(ip)) {
            httpServletRequest.getSession().setAttribute("visitIp", ip);
            saveIp(ip);
        }

        return true;
    }

    //controller之后
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //检查是否登录
        Object loginU = httpServletRequest.getSession().getAttribute("loginU");
        if (loginU != null) {
            User u = (User) loginU;
            String name = u.getName();
            if (!StringUtils.isEmpty(name) && name.length() > 10) {
                name = name.substring(0, 10) + "...";
                u.setName(name);
            }
            if (null == name) {
                u.setName("无名氏");
            }
            //加入reqquest的值中
            httpServletRequest.setAttribute("loginU", loginU);
        }
    }

    //响应后
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * IP地址
     *
     * @param request
     * @return
     */
    private String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        LOG.info("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
//            LOG.info("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
//            LOG.info("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
//            LOG.info("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//            LOG.info("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
//            LOG.info("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
//            LOG.info("getRemoteAddr ip: " + ip);
        }
        LOG.info("获取客户端IP: " + ip);

        //本机：几乎不可能执行到这里哦
       /* if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            //获取本机外网IP
            String rtn = HttpClientUtil.get("https://ipip.yy.com/get_ip_info.php");
            if (rtn != null && rtn.contains("{") && rtn.contains("}")) {
                int start = rtn.indexOf("{");
                int end = rtn.indexOf("}") + 1;
                String result = rtn.substring(start, end);
                JSONObject Ipaddress = JSONObject.parseObject(result);
                return Ipaddress.getString("cip");
            }
        }*/
        return ip;
    }

    /**
     * IP地址
     */
    private String getAdress(String ip) {

        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            return "本机";
        }

        JSONObject address = null;
        //IP地址
        try {
            String rtn = HttpClientUtil.get("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=" + ip);
            address = JSONObject.parseObject(rtn);

            String country = (String) JSONPath.eval(address, "$.country");
            String province = (String) JSONPath.eval(address, "$.province");
            String city = (String) JSONPath.eval(address, "$.city");

            if (country == null) {
                return "无法联网解析该IP地址";
            }

            return country + "." + province + "." + city;

        } catch (Exception e) {
            LOG.error("IP解析错误:IP = " + ip);
        }

        return "该IP解析失败";
    }

    /**
     * 存储IP
     *
     * @param ip
     */
    private void saveIp(final String ip) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String address = getAdress(ip);
                Date visittime = new Date();

                Map data = new HashMap<String, String>();
                data.put("address", address);
                data.put("ip", ip);
                data.put("visittime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(visittime));
                String rtn = HttpClientUtil.post(url_ipSave, data, "utf-8");
                if (rtn == null || !rtn.contains("success")) {
                    LOG.error("IP保存/更新出错: IP = " + ip);
                }
            }
        });

        thread.start();
        LOG.info("线程" + thread.getName() + ": 正在存储IP信息");
    }
}














