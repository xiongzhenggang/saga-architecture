package com.xzg.goods.dao;

import com.xzg.goods.entity.Goods;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * <b>项目名称： </b>:saga-architecture
 * <b>Class name</b>: GoodsDao
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
@Repository
public class GoodsDao {
    private GoodsRepository goodsRepository;

    /**
     *
     * @param id
     * @return
     */
    public Goods findById(Long id){
        return goodsRepository.findById(id)
                .orElseThrow(() ->
                new IllegalArgumentException(String.format("Goods with id=%s is not found", id)));
    }

    /**
     * 保存
     * @param goods
     */
    public void saveGoods(Goods goods){
        goodsRepository.save(goods);
    }
}
