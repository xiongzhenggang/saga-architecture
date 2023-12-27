package com.xzg.goods.dao;

import com.xzg.goods.entity.Goods;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
    @Resource
    private GoodsRepository goodsRepository;

    /**
     *
     * @param id
     * @return
     */
    public Optional<Goods> findById(Long id){
        return goodsRepository.findById(id);
    }

    /**
     * 保存
     * @param goods
     */
    public void saveGoods(Goods goods){
        goodsRepository.save(goods);
    }
}
