package cn.myxinge.service.impl;

import cn.myxinge.entity.Comments;
import cn.myxinge.service.CommentsService;
import cn.myxinge.utils.HttpClientUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenxinghua on 2017/12/27.
 */
@Service
public class CommentsServiceImpl implements CommentsService {

    @Value("${url_addcomments}")
    private String url_addcomments;

    @Value("${url_listcomments}")
    private String url_listcomments;

    @Value("${url_delcomments}")
    private String url_delcomments;

    @Override
    public void add(Comments comments) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user.id", comments.getUser().getId() + "");
        map.put("fid", comments.getFid() + "");
        map.put("fcat", comments.getFcat());
        map.put("text", comments.getText());

        HttpClientUtil.post(url_addcomments, map, "utf-8");
    }

    @Override
    public List<Comments> listByBlog(Integer id) {
        String rtn = HttpClientUtil.get(url_listcomments + "?blogId=" + id);
        List<Comments> comments = null;
        if (null != rtn) {
            comments = JSONArray.parseArray(rtn, Comments.class);
        }
        return comments;
    }


    @Override
    public void del(Integer id) {
        HttpClientUtil.post(url_delcomments + "/" + id, new HashMap<String, String>(), "utf-8");
    }
}
