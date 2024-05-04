package com.xzg.goods.entity;

import com.xzg.goods.exception.GoodsStockLimitExceededException;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * <b>项目名称： </b>:saga-architecture
 * <b>Class name</b>: GoodsStock
 * </p>
 * <p>
 * <b>Class description</b>:
 *
 * </p>
 * <p>
 * <b>Author</b>: xiongzhenggang
 * </p>
 * <b>Change History</b>:<br/>
 * <p>
 *
 * <pre>
 * Date          Author       Revision     Comments
 * ----------    ----------   --------     ------------------
 * 12/20/2023   xiongzhenggang        1.0          Initial Creation
 *
 * </pre>
 *
 * @author xiongzhenggang
 * @date 12/20/2023
 * </p>
 */
@Data
@Entity
@Table(name="GOODS_INFO")
@Access(AccessType.FIELD)
public class Goods {

    @Id
    @GeneratedValue
    private Long id;

    private String goodsName;

    private Integer stock;
    private BigDecimal unitPrice;
    private String desc;
    private String url;
    private Long createBy;
    private Long updateBy;
    private LocalDateTime updateTime;

    private LocalDateTime createTime;
    /**
     * 库存是否足够
     */
    public void reserveGoodsStock(Integer goodsTotal) {
        if (this.stock >=goodsTotal) {
            this.stock = this.stock -goodsTotal;
        } else {
            throw new GoodsStockLimitExceededException();
        }
    }
}
