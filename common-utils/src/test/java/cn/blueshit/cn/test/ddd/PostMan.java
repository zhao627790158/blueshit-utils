package cn.blueshit.cn.test.ddd;

/**
 * Created by zhaoheng on 18/10/16.
 */
public class PostMan {

    private Object mail;

    /**
     * 发送工具
     */
    private MailTool tool;

    public PostMan(Object mail, MailTool tool) {
        this.mail = mail;
        this.tool = tool;
    }

    private boolean sendMail() {
        return tool.send(mail);
    }


    interface MailTool {
        boolean send(Object mail);

    }
}
