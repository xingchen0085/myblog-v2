package cn.myxinge.controller;

import cn.myxinge.entity.Comments;
import cn.myxinge.entity.User;
import cn.myxinge.service.CommentsService;
import cn.myxinge.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenxinghua on 2017/12/27.
 */
@Controller
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Comments comments, HttpServletRequest request) {
        User user = ResponseUtil.loginUser(request);
        if (null == user) {
            return "not login!";
        }
        comments.setUser(user);
        commentsService.add(comments);
        return "success";
    }

    @RequestMapping(value = "/del/{id}", method = RequestMethod.POST)
    public String del(@PathVariable Integer id) {
        if (id == null) {
            return null;
        }
        commentsService.del(id);
        return "success";
    }
}













