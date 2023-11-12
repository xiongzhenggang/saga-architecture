package com.xzg.authentication.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.authention.entity
 * @className: BaseEntity
 * @author: xzg
 * @description: TODO
 * @date: 10/11/2023-下午 10:41
 * @version: 1.0
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
    