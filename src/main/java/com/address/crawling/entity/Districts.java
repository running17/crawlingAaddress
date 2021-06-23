package com.address.crawling.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author running
 * 区域数据集合
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Districts extends BaseEntity {


    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 等级
     */
    private Integer level;
}
