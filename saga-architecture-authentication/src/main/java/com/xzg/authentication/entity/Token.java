package com.xzg.authentication.entity;

import com.xzg.authentication.entity.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AUTH_TOKEN")
public class Token extends BaseEntity {
    private  boolean expired;
    private  boolean revoked;
    private Integer userId;
    @Column(length = 5000)
    private String tokenStr;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

}
