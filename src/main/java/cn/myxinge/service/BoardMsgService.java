package cn.myxinge.service;

import cn.myxinge.entity.BoardMsg;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by chenxinghua on 2017/11/23.
 */
public interface BoardMsgService {
    String save(BoardMsg boardMsg);

    JSONObject boardList(Integer page, Integer size);

    List<BoardMsg> all();

    void del(Integer id);
}
