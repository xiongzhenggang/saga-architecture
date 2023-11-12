package com.xzg.authentication.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * security中需要一个
 * UserDetails类来定义用户账户的行为.这个是用户鉴权的关键.主要有账户,密码,权限,用户状态等等.在下面
 */
@Builder
@AllArgsConstructor
public class UserAccount  implements UserDetails {

    private User user;
    /**
     * 该用户拥有的授权，譬如读取权限、修改权限、增加权限等等
     */
    private Collection<GrantedAuthority> authorities = new HashSet<>();

    public UserAccount(User user) {
        super();
        this.user = user;
        authorities.add(new SimpleGrantedAuthority(Role.ADMIN.name()));
    }


    /**
     * 获取用户的权限
     * 这里是根据角色枚举的权限来获取的(静态的而非从数据库动态读取)
     * @return 用户权限列表
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
    /**
     * 获取用户密码
     * 主要是用来指定你的password字段
     * @return 用户密码
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * 获取用户账号
     * 这里使用email做为账号
     * @return 用户账号
     */
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    /**
     * 账号是否未过期,下面的这个几个方法都是用来指定账号的状态的,因为该项目是一个Demo,所以这里全部返回true
     * @return true 未过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否未锁定
     * @return true 未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否未过期
     * @return true 未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账号是否激活
     * @return true 已激活
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}