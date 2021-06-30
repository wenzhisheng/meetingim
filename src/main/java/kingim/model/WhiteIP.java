package kingim.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author dameizi
 * @description IP白名单
 * @dateTime 2019-04-28 21:27
 * @className kingim.model.WhiteIP
 */
@Table(name = "white_ip")
public class WhiteIP extends BaseVO implements Serializable {

    private static final long serialVersionUID = 5132362516794180630L;

    /** 自增主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;
    /** ip */
    @Column(name = "ip")
    private String ip;
    /** 是否启用（0：禁用 1：启用） */
    @Column(name = "status")
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
