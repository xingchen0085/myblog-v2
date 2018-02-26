package cn.myxinge.service;

import cn.myxinge.entity.Blog;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by chenxinghua on 2017/11/9.
 */
public interface BlogService {

    /**
     * 根据url获取博客
     * @param url
     * @return
     */
    JSONObject getBlog(String url);

    /**
     * 获取所有博客
     * @return
     */
    List<Blog> allBlog();

    String getBlogContent(String sysyUrl);

     JSONObject pageBlog(int page, int rows);

    String archives();
}
















