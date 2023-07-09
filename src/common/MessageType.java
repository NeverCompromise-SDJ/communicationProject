package common;

/**
 * 定义消息类型，在接口中定义了一些常量，不同常量的值表示不同的消息类型
 */
public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED = "1";//表示登录成功
    String MESSAGE_LOGIN_FAILED = "2";//表示登录失败
    String MESSAGE_COMMON = "3";//普通消息
    String MESSAGE_GET_ONLINE_LIST = "4";//要求返回在线用户列表
    String MESSAGE_RETURN_ONLINE_LIST = "5";//返回在线用户列表
    String MESSAGE_CLIENT_EXIT = "6";//客户端请求退出

}
