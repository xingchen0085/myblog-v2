package cn.myxinge.controller;

import cn.myxinge.entity.Blog;
import cn.myxinge.entity.Comments;
import cn.myxinge.service.BlogService;
import cn.myxinge.service.CommentsService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by chenxinghua on 2017/11/9.
 */
@Controller
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentsService commentsService;
    private Logger LOG = LoggerFactory.getLogger(BlogController.class);

    @RequestMapping("/blog/{url}")
    public String showBlog(@PathVariable String url, Model model, HttpServletResponse rps) throws UnsupportedEncodingException {
        JSONObject json = blogService.getBlog(url);
        Object eval = JSONPath.eval(json, "$.curBlog");
        if (null == eval) {
            rps.setStatus(404);
            return "/error";
        }
        Blog curBlog = JSONObject.parseObject(String.valueOf(eval), Blog.class);
        List<Comments> comments = commentsService.listByBlog(curBlog.getId());

        model.addAttribute("comments", comments);
        model.addAttribute("total", comments.size());
        model.addAttribute("blog", curBlog);
//        model.addAttribute("preBlog", (JSONObject) JSONPath.eval(json, "$.preAndNext.preBlog"));
//        model.addAttribute("nextBlog", (JSONObject) JSONPath.eval(json, "$.preAndNext.nextBlog"));
        return "detail";
    }

    @RequestMapping("/blog/content")
    @ResponseBody
    public String content(Model model, String sysyUrl) throws UnsupportedEncodingException {
        if (sysyUrl == null) {
            return null;
        }
        String content = blogService.getBlogContent(sysyUrl);
        return new String(content.getBytes("ISO-8859-1"), "UTF-8");
    }

//    @RequestMapping("/blog/comments")
//    @ResponseBody
//    public Page<Comments> comments(Model model, Integer blogId) throws UnsupportedEncodingException {
//        if (blogId == null) {
//            return null;
//        }
//        Page<Comments> comments = commentsService.listByBlog(blogId);
//        return comments;
//    }

    @RequestMapping("/")
    public String indexBlog(Model model) {
        return pageHandler(model, 1, 5);
    }

    @RequestMapping("blog/pe/{page}")
    public String pageHandler(Model model, @PathVariable Integer page, Integer rows) {
        if (null == page) {
            page = 1;
        }
        if (null == rows) {
            rows = 5;
        }

        JSONObject json = pageBlog(null, page, rows);
        Long eval = Long.parseLong(String.valueOf(JSONPath.eval(json, "$.total")));
        Object content = JSONPath.eval(json, "$.rows");
        if (null != eval) {
            model.addAttribute("total", eval);
        }
        if (null != content) {
            if (content instanceof JSONArray) {
                if (((JSONArray) content).size() != 0) {
                    List<Blog> blogs = JSONArray.parseArray(String.valueOf(content), Blog.class);
                    model.addAttribute("blogs", blogs);
                } else {
                    model.addAttribute("blogs", new ArrayList<Blog>());
                }
            }
        }
        //当前页
        model.addAttribute("curPage", page);
        //最大页
        Long totalPage = eval % rows == 0 ? eval / rows : (eval / rows) + 1;
        model.addAttribute("totalPage", totalPage);

        return "/index";
    }

    @RequestMapping("/pe/archives")
    public String archives(Model model) {
        try {
            String rtn = blogService.archives();
            JSONArray jsonArray = JSONObject.parseArray(rtn);
            model.addAttribute("archivesList", jsonArray);
        } catch (Exception e) {
            LOG.error("归档信息获取失败，发生异常：", e);
        }

        return "blogs";
    }


    private JSONObject pageBlog(Blog blog, @PathVariable Integer page, Integer rows) {

        JSONObject json = blogService.pageBlog(page, rows);
        return json;
    }
}




