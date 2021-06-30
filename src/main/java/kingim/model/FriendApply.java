package kingim.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "friend_apply")
public class FriendApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 引用用户表主键
     */
    @Column(name = "from_user_id")
    private Integer fromUserId;

    /**
     * 引用用户表主键
     */
    @Column(name = "to_user_id")
    private Integer toUserId;

    /**
     * 附加说明
     */
    @Column(name = "remark")
    private String remark;

    @Column(name = "friend_type_id")
    private Integer friendTypeId;

    /**
     * 申请时间
     */
    @Column(name = "apply_time")
    private String applyTime;

    /**
     * 0：待处理，1：同意，2：拒绝
     */
    private Integer status;

    @Transient
    private User fromUser;

    @Transient
    private User toUser;

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

    /**
     * 获取引用用户表主键
     * @return from_user_id - 引用用户表主键
     */
    public Integer getFromUserId() {
        return fromUserId;
    }

    /**
     * 设置引用用户表主键
     * @param fromUserId
     *            引用用户表主键
     */
    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    /**
     * 获取引用用户表主键
     * @return to_user_id - 引用用户表主键
     */
    public Integer getToUserId() {
        return toUserId;
    }

    /**
     * 设置引用用户表主键
     * @param toUserId
     *            引用用户表主键
     */
    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    /**
     * 获取附加说明
     * @return remark - 附加说明
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置附加说明
     * @param remark
     *            附加说明
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取申请时间
     * @return apply_time - 申请时间
     */
    public String getApplyTime() {
        return applyTime;
    }

    /**
     * 设置申请时间
     * @param applyTime 申请时间
     */
    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }


    /**
     * @return status - 0：待处理，1：同意，2：拒绝
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status 0：待处理，1：同意，2：拒绝
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public Integer getFriendTypeId() {
        return friendTypeId;
    }

    public void setFriendTypeId(Integer friendTypeId) {
        this.friendTypeId = friendTypeId;
    }
}
