package cn.myxinge.service;

import cn.myxinge.entity.Comments;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by chenxinghua on 2017/12/27.
 */
public interface CommentsService {
    public void add(Comments comments);

    public List<Comments> listByBlog(Integer id);

    void del(Integer id);
}
