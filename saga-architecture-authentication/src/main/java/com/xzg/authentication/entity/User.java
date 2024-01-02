package com.xzg.authentication.entity;

import com.xzg.authentication.entity.enums.RoleEnums;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * security中需要一个
 * UserDetails类来定义用户账户的行为.这个是用户鉴权的关键.主要有账户,密码,权限,用户状态等等.在下面
 * @author xiongzhenggang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AUTH_USER")
public class User extends BaseEntity {

    // 密码字段不参与序列化（但反序列化是参与的）、不参与更新（但插入是参与的）
    // 这意味着密码字段不会在获取对象（很多操作都会关联用户对象）的时候泄漏出去；
    // 也意味着此时“修改密码”一类的功能无法以用户对象资源的接口来处理（因为更新对象时密码不会被更新），需要单独提供接口去完成
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @Column(updatable = false)
    private String password;

    @NotEmpty(message = "用户姓名不允许为空")
    private String username;


    @Pattern(regexp = "1\\d{10}", message = "手机号格式不正确")
    private String telephone;

    @Email(message = "邮箱格式不正确")
    private String email;

    private String location;
    /**
     * 角色枚举
     */
    @Enumerated(EnumType.STRING)
    private RoleEnums role;
//    /**
//     * 用户关联的Token
//     * 这里面使用了jpa的一对多映射
//     */
//    @OneToMany(mappedBy = "user")
//    private List<Token> tokens;

}