package cn.myxinge.service.impl;

import cn.myxinge.entity.BoardMsg;
import cn.myxinge.service.BoardMsgService;
import cn.myxinge.utils.HttpClientUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenxinghua on 2017/11/23.
 */
@Service
public class BoardServiceImpl implements BoardMsgService {

    @Value("${url_msgSave}")
    private String url_msgSave;
    @Value("${url_msgList}")
    private String url_msgList;
    @Value("${url_delboardmsg}")
    private String url_delboardmsg;

    @Override
    public String save(BoardMsg boardMsg) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user.id", boardMsg.getUser().getId() + "");
        map.put("text", boardMsg.getText());
        String rtn = HttpClientUtil.post(url_msgSave, map, "utf-8");

        return rtn;
    }

    @Override
    public JSONObject boardList(Integer page, Integer rows) {
        JSONObject json = null;
        Map<String, String> map = new HashMap<String, String>();
        map.put("page", page + "");
        map.put("rows", rows + "");
        String rtn = HttpClientUtil.post(url_msgList, map, "utf-8");
        try {
            json = JSONObject.parseObject(rtn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (json == null) {
            return null;
        }
        return json;
    }

    @Override
    public List<BoardMsg> all() {
        String rtn = HttpClientUtil.get(url_msgList);
        List<BoardMsg> list = null;
        try {
            list = JSONArray.parseArray(rtn, BoardMsg.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void del(Integer id) {
        HttpClientUtil.post(url_delboardmsg + "/" + id, new HashMap<String, String>(), "utf-8");
    }
}


