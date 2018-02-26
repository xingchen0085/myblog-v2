package cn.myxinge.service.impl;

import cn.myxinge.entity.Blog;
import cn.myxinge.service.BlogService;
import cn.myxinge.utils.HttpClientUtil;
import cn.myxinge.utils.JedisUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenxinghua on 2017/11/9.
 */
@Service
public class BlogServiceImpl implements BlogService {

    private Logger LOG = LoggerFactory.getLogger(BlogServiceImpl.class);

    //根据Url返回博客对象
    @Value("${url_blogByUrl}")
    private String url_blogByUrl;

    @Value("${url_blogPage}")
    private String url_blogPage;

    @Value("${url_archives}")
    private String url_archives;

    @Override
    public JSONObject getBlog(String url) {

        String _url = url_blogByUrl.replace("*url*", url);
        String rtn = HttpClientUtil.get(_url);
        if (rtn == null) {
            LOG.error("博客不存在，或者后台系统出错");
            return null;
        }

        JSONObject rtnJson = null;
        try {
            rtnJson = JSONObject.parseObject(rtn);
        } catch (Exception e) {
            LOG.error("博客不存在，返回数据不是JSON", e);
        }

        //rtn返回值？   1.curBlog 当前博客  2.preAndNext Map:key -preBlog  -nextBlog

        return rtnJson;
    }

    @Override
    public List<Blog> allBlog() {
        return null;
    }

    @Override
    public String getBlogContent(String sysyUrl) {
        String pre = "https://www.myxinge.cn/";
        return HttpClientUtil.get(pre + sysyUrl);
    }

    @Override
    public JSONObject pageBlog(int page, int rows) {
        String rtn = HttpClientUtil.get(url_blogPage + "?page=" + page + "&rows=" + rows);
        try {
            JSONObject json = JSONObject.parseObject(rtn);

            return json;
        } catch (Exception e) {
            LOG.error("无法查到数据 或者返回的数据不是json", e);
        }
        return null;
    }

    @Override
    public String archives() {
        String rtn = HttpClientUtil.get(url_archives);
        return rtn;
    }
}












