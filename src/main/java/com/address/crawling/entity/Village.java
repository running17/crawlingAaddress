package com.address.crawling.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author running
 * 村、居委会
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Village extends BaseEntity {

    /**
     * 城乡分类代码
     */
    private String typeCode;

    /**
     * 乡镇编码
     */
    private String townCode;

    /**
     * 下级地址
     */
    private String path;

}
