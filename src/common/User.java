package common;

import java.io.Serializable;

/**
 * 表示一个用户信息
 */
public class User implements Serializable {
    private static final Long serialVersionUID = 1L;//序列化ID
    private String userId;//用户ID
    private String passwd;//用户密码

    public User() {
    }

    public User(String userId, String passwd) {
        this.userId = userId;
        this.passwd = passwd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
