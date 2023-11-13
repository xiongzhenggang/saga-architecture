package com.xzg.authentication.entity;

import com.xzg.authentication.entity.enums.TokenType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "toke")
public class Token extends BaseEntity {
    private  boolean expired;
    private  boolean revoked;
    private Integer userId;
    @Column(length = 5000)
    private String tokenStr;
    private TokenType tokenType;

}
