package kingim.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "group_message")
public class GroupMessage extends BaseVO implements Serializable {

    private static final long serialVersionUID = 5095345139572696939L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    /**
     * 发消息的人的id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 群id
     */
    @Column(name = "group_id")
    private Integer groupId;

    /**
     * 消息内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 发送时间
     */
    @Column(name = "send_time")
    private String sendTime;

    /**
     * 是否已读，1是0否
     */
    @Column(name = "is_read")
    private Integer isRead;

    /**
     * 是否删除，1是0否
     */
    @Column(name = "is_del")
    private Integer isDel;

    /**
     * 是否撤回，1是0否
     */
    @Column(name = "is_back")
    private Integer isBack;

    /** 信息标识 */
    @Column(name = "cid")
    private String cid;

    @Transient
    private String avatar;

    @Transient
    private String nickName;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取消息内容
     *
     * @return content - 消息内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置消息内容
     *
     * @param content 消息内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取发送时间
     *
     * @return send_time - 发送时间
     */
    public String getSendTime() {
        return sendTime;
    }

    /**
     * 设置发送时间
     *
     * @param sendTime 发送时间
     */
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取是否已读，1是0否
     *
     * @return is_read - 是否已读，1是0否
     */
    public Integer getIsRead() {
        return isRead;
    }

    /**
     * 设置是否已读，1是0否
     *
     * @param isRead 是否已读，1是0否
     */
    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    /**
     * 获取是否删除，1是0否
     *
     * @return is_del - 是否删除，1是0否
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * 设置是否删除，1是0否
     *
     * @param isDel 是否删除，1是0否
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    /**
     * 获取是否撤回，1是0否
     *
     * @return is_back - 是否撤回，1是0否
     */
    public Integer getIsBack() {
        return isBack;
    }

    /**
     * 设置是否撤回，1是0否
     *
     * @param isBack 是否撤回，1是0否
     */
    public void setIsBack(Integer isBack) {
        this.isBack = isBack;
    }
}