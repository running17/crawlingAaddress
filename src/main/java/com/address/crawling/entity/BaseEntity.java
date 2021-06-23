package com.address.crawling.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author running
 * 通用父级
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 城市编码
     */
    private String code;

    /**
     * 城市名称
     */
    private String name;

}
