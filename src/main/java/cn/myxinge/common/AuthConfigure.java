package cn.myxinge.common;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by XingChen on 2017/11/28.
 */
//加上注释@Component，可以直接其他地方使用@Autowired来创建其实例对象
@Component
@ConfigurationProperties(prefix = "db")
@PropertySource("classpath:/db.properties")
public class AuthConfigure {
    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
