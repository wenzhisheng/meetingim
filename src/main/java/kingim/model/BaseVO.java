package kingim.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @author dameizi
 * @description TODO
 * @dateTime 2019-05-05 20:02
 * @className kingim.model.BaseVo
 */
public class BaseVO implements Serializable {

    private static final long serialVersionUID = 463474369223737316L;

    /** 创建时间 */
    @Column(name = "gmt_create")
    private Date gmtCreate;
    /** 修改时间 */
    @Column(name = "gmt_modified")
    private Date gmtModified;
    /** 开始时间 */
    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss" ,iso=DateTimeFormat.ISO.DATE_TIME)
    private Date startTime;
    /** 结束时间 */
    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss",iso=DateTimeFormat.ISO.DATE_TIME)
    private Date endTime;
    /** 批量操作数组 */
    @Transient
    private Integer[] ids;

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }
}
