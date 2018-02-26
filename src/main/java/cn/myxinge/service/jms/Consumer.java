package cn.myxinge.service.jms;

import cn.myxinge.utils.JedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by chenxinghua on 2017/12/14.
 */
@Component
public class Consumer {
    private Logger LOG = LoggerFactory.getLogger(Consumer.class);
    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = "redisFlush.queue")
    public void receiveQueue(String text) {
        LOG.info("Consumer收到的报文为:"+text+",开始清空redis缓存");
        String rtn = JedisUtil.flushall();
        LOG.info("Redis清空结果：" + rtn);
    }
}
