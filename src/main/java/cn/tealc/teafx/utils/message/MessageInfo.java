package cn.tealc.teafx.utils.message;

import javafx.util.Duration;

/**
 * 用于自定义的消息包装类
 */
public class MessageInfo {
    public static final Duration DEFAULT_SHOW_TIME = Duration.seconds(3.0);
    public static final Duration LONG_SHOW_TIME = Duration.seconds(5.0);
    public static final Duration LONG_PLUS_SHOW_TIME = Duration.seconds(7.0);
    public static final String SUCCESS = "Success";
    public static final String ERROR = "Error";
    public static final String INFO = "Info";
    public static final String WARNING = "Warning";

    private MessageType type;
    private String title;
    private String message;
    private Boolean autoClose; //自动关闭
    private Duration showTime;//持续显示时间，仅在autoClose = true生效


    public static MessageInfo warning(String message) {
        return MessageInfo.warning(WARNING, message);
    }

    public static MessageInfo error(String message) {
        return MessageInfo.error(ERROR, message);
    }

    public static MessageInfo success(String message) {
        return MessageInfo.success(SUCCESS, message);
    }

    public static MessageInfo info(String message) {
        return MessageInfo.info(INFO, message);
    }

    public static MessageInfo warning(String message, boolean autoClose) {
        return new MessageInfo(MessageType.WARNING, WARNING, message, autoClose);
    }

    public static MessageInfo error(String message, boolean autoClose) {
        return new MessageInfo(MessageType.ERROR, ERROR, message, autoClose);
    }

    public static MessageInfo success(String message, boolean autoClose) {
        return new MessageInfo(MessageType.SUCCESS, SUCCESS, message, autoClose);
    }

    public static MessageInfo info(String message, boolean autoClose) {
        return new MessageInfo(MessageType.INFO, INFO, message, autoClose);
    }


    public static MessageInfo warning(String title, String message) {
        return new MessageInfo(MessageType.WARNING, title, message);
    }

    public static MessageInfo error(String title, String message) {
        return new MessageInfo(MessageType.ERROR, title, message);
    }

    public static MessageInfo success(String title, String message) {
        return new MessageInfo(MessageType.SUCCESS, title, message);
    }

    public static MessageInfo info(String title, String message) {
        return new MessageInfo(MessageType.INFO, title, message);
    }

    public MessageInfo(MessageType type, String title, String message) {
        this(type, title, message, true);
    }

    public MessageInfo(MessageType type, String title, String message, Boolean autoClose) {
        this(type, title, message, autoClose, DEFAULT_SHOW_TIME);
    }

    public MessageInfo(MessageType type, String title, String message, Boolean autoClose, Duration showTime) {
        this.type = type;
        this.title = title;
        this.message = message;
        this.autoClose = autoClose;
        this.showTime = showTime;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getAutoClose() {
        return autoClose;
    }

    public void setAutoClose(Boolean autoClose) {
        this.autoClose = autoClose;
    }

    public Duration getShowTime() {
        return showTime;
    }

    public void setShowTime(Duration showTime) {
        this.showTime = showTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
