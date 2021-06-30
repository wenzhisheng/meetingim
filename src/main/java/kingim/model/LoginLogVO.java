package kingim.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author dameizi
 * @description TODO
 * @dateTime 2019-05-05 20:08
 * @className kingim.model.LoginLogVO
 */
@Table(name = "login_log")
public class LoginLogVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -4151290534231922758L;

    /** 自增主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    /** 账号 */
    @Column(name = "account")
    private String account;

    /** 登录IP */
    @Column(name = "login_ip")
    private String loginIp;

    /** 真实地址 */
    @Column(name = "real_address")
    private String realAddress;

    /** 登录时间 */
    @Column(name = "login_time")
    private Date loginTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getRealAddress() {
        return realAddress;
    }

    public void setRealAddress(String realAddress) {
        this.realAddress = realAddress;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
