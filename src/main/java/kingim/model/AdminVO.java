package kingim.model;

import javax.persistence.*;
import java.io.Serializable;

public class AdminVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 8380037582292182075L;

    /** 自增主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    /** 账号 */
    @Column(name = "account")
    private String account;

    /** 密码 */
    @Column(name = "password")
    private String password;

    /** 手机号 */
    @Column(name = "telphone")
    private String telPhone;

    /** 是否启用（ 0:未启用 1:已启用） */
    @Column(name = "is_enable")
    private Integer isEnable;

    @Transient
    private String status;
    @Transient
    private String currentAuthority;
    @Transient
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrentAuthority() {
        return currentAuthority;
    }

    public void setCurrentAuthority(String currentAuthority) {
        this.currentAuthority = currentAuthority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }
}