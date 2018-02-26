package cn.myxinge.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Example;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by XingChen on 2017/12/10.
 * <p>
 * Redis key 生成规则：根据 类+方法+形参数据（用+号连接）
 * <p>
 * 操作Redis
 */
public class JedisUtil {
    private static JedisPool pool;

    //静态代码初始化池配置

    static {


        //创建jedis池配置实例

        JedisPoolConfig config = new JedisPoolConfig();

        //设置池配置项值

        config.setMaxTotal(3000);

        config.setMaxIdle(1000);

        config.setMaxWaitMillis(3000);

        config.setTestOnBorrow(true);

        config.setTestOnReturn(true);

        //根据配置实例化jedis池
        pool = new JedisPool(config, "127.0.0.1", 6379);
    }

    /**
     * 获得jedis对象
     */

    public static Jedis getJedisObject() {
        return pool.getResource();
    }


    /**
     * 归还jedis对象
     */

    public static void recycleJedisOjbect(Jedis jedis) {
        pool.returnResource(jedis);
    }

    /**
     * 缓存
     */
    public static String cachData(String key, String value) throws Exception {
        Jedis jedis = getJedisObject();
        String rtn = null;
        try {
            rtn = jedis.set(key, value);
            recycleJedisOjbect(jedis);
        } catch (Exception e) {
            recycleJedisOjbect(jedis);
            throw e;
        }
        return rtn;
    }

    /**
     * 取出
     */
    public static String getDataFromRedis(String key) throws Exception {
        Jedis jedis = getJedisObject();
        String rtn = null;
        try {
            rtn = jedis.get(key);
            recycleJedisOjbect(jedis);
        } catch (Exception e) {
            recycleJedisOjbect(jedis);
            throw e;
        }
        return rtn;
    }

    public static String flushall() {
        Jedis jedis = getJedisObject();
        String s = jedis.flushAll();
        recycleJedisOjbect(jedis);
        return s;
    }
}








