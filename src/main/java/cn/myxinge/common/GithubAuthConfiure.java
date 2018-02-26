package cn.myxinge.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by chenxinghua on 2017/12/20.
 */
//加上注释@Component，可以直接其他地方使用@Autowired来创建其实例对象
@Component
@ConfigurationProperties(prefix = "db")
@PropertySource("classpath:/auth.properties")
public class GithubAuthConfiure {
    private String github_client_id;
    private String github_client_secret;
    private String github_redirect_uri_code;
    private String github_redirect_uri;
    private String github_oauth_url;
    private String github_auth_token;
    private String github_user_info;

    public String getGithub_client_id() {
        return github_client_id;
    }

    public void setGithub_client_id(String github_client_id) {
        this.github_client_id = github_client_id;
    }

    public String getGithub_client_secret() {
        return github_client_secret;
    }

    public void setGithub_client_secret(String github_client_secret) {
        this.github_client_secret = github_client_secret;
    }

    public String getGithub_redirect_uri_code() {
        return github_redirect_uri_code;
    }

    public void setGithub_redirect_uri_code(String github_redirect_uri_code) {
        this.github_redirect_uri_code = github_redirect_uri_code;
    }

    public String getGithub_redirect_uri() {
        return github_redirect_uri;
    }

    public void setGithub_redirect_uri(String github_redirect_uri) {
        this.github_redirect_uri = github_redirect_uri;
    }

    public String getGithub_oauth_url() {
        return github_oauth_url;
    }

    public void setGithub_oauth_url(String github_oauth_url) {
        this.github_oauth_url = github_oauth_url;
    }

    public String getGithub_auth_token() {
        return github_auth_token;
    }

    public void setGithub_auth_token(String github_auth_token) {
        this.github_auth_token = github_auth_token;
    }

    public String getGithub_user_info() {
        return github_user_info;
    }

    public void setGithub_user_info(String github_user_info) {
        this.github_user_info = github_user_info;
    }
};















