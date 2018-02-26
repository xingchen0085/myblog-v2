package cn.myxinge.controller;

import cn.myxinge.entity.BoardMsg;
import cn.myxinge.entity.User;
import cn.myxinge.service.BoardMsgService;
import cn.myxinge.utils.ResponseUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by chenxinghua on 2017/11/23.
 */
@Controller
@RequestMapping("/board")
public class BoardMsgController {

    private Logger LOG = LoggerFactory.getLogger(BoardMsgController.class);

    @Autowired
    private BoardMsgService boardMsgService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JSONObject sendMsg(BoardMsg boardMsg, HttpServletRequest request) {
        if (null == boardMsg) {
            return ResponseUtil.returnJson(false, "失败，留言为空");
        }
        JSONObject rtnJson = null;
        try {
            User user = ResponseUtil.loginUser(request);
            if (null == user) {
                return ResponseUtil.returnJson(false, "失败，请登录");
            }
            boardMsg.setUser(user);
            String rtn = boardMsgService.save(boardMsg);
            rtnJson = JSONObject.parseObject(rtn);

        } catch (Exception e) {
            LOG.error("留言存储异常", e);
            return rtnJson;
        }
        return rtnJson;
    }

    /**
     * 留言分页返回-初始化
     */
    @RequestMapping("/initBoard")
    public String initBoard(Integer page, Integer rows, Model m) {
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 6;
        }
        JSONObject jsonObject = boardList(page, rows);
        m.addAttribute("boardCount", JSONPath.eval(jsonObject, "$.total"));
        m.addAttribute("boardList", JSONPath.eval(jsonObject, "$.rows"));
        m.addAttribute("page", page);
        m.addAttribute("rows", rows);
        return "/contact";
    }

    /**
     * 留言分页返回、JSON
     */
    @RequestMapping("/about")
//    @ResponseBody
    public String list(Integer page, Integer rows, Model m) {
//        if (page == null) {
//            page = 1;
//        }
//        if (rows == null) {
//            rows = 6;
//        }
//        return boardList(page, rows);

        //更改时间： 12-27 :取消留言分页

        List<BoardMsg> all = boardMsgService.all();
        if (null != all) {
            m.addAttribute("boardmsgList", all);
            m.addAttribute("total", all.size());
        }
        return "about";
    }

    @RequestMapping(value = "/del/{id}", method = RequestMethod.POST)
    public String del(@PathVariable Integer id) {
        if (id == null) {
            return null;
        }
        boardMsgService.del(id);
        return "success";
    }

    /**
     * 分页查询
     */
    private JSONObject boardList(Integer page, Integer rows) {
        JSONObject jsonObject = boardMsgService.boardList(page, rows);
        Object eval = JSONPath.eval(jsonObject, "$.rows");

        JSONArray rowsArr = new JSONArray();
        if (eval instanceof JSONArray) {
            rowsArr = (JSONArray) eval;
        } else {
            rowsArr.add(eval);
        }

        for (Object j : rowsArr) {
            JSONObject json = (JSONObject) j;
            JSONPath.set(json, "$.createtime", toDate(JSONPath.eval(json, "$.createtime")));
        }
        return jsonObject;
    }


    private Date toDate(Object eval) {
        if (eval == null) {
            return null;
        }
        Long time = Long.parseLong(String.valueOf(eval));
        Date date = new Date();
        date.setTime(time);
        return date;
    }
}









