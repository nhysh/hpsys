package com.hp.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 
 * </p>
 *
 * @author 严敏
 * @since 2023-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_account")
@ApiModel(value="Account对象", description="")
public class Account implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "雇员id")
    private Integer empId;

    @ApiModelProperty(value = "0-禁用  1-启用")
    private Integer status;

    @ApiModelProperty(value = "权限")
    @TableField(exist = false)//数据库本身不存在
    private Collection<? extends GrantedAuthority> grantedAuthority;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthority;//用户所拥有的权限
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;//账号未过期
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;//账号未锁定
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;//证书未过期
    }

    @Override
    public boolean isEnabled() {
        return this.status==1?true:false; //账号是否可用
    }
}
