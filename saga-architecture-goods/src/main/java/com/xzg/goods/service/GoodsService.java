package com.xzg.goods.service;

import com.xzg.goods.dao.GoodsDao;
import com.xzg.goods.entity.Goods;
import com.xzg.library.config.infrastructure.common.exception.BusinessException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * <b>项目名称： </b>:saga-architecture
 * <b>Class name</b>: GoodsService
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
 * 12/29/2023   xiongzhenggang        1.0          Initial Creation
 *
 * </pre>
 *
 * @author xiongzhenggang
 * @date 12/29/2023
 * </p>
 */
@Service
@Slf4j
public class GoodsService {
    @Resource
    private GoodsDao goodsDao;

    public Goods getGoodsById(Long goodsId){
        return goodsDao.findById(goodsId).orElseThrow(()->BusinessException.builder()
                .message("not fund goods")
                .build());
    }
    public void saveGoods(Goods goods){
        log.info("save goods:{}",goods);
        goodsDao.saveGoods (goods);

    }
}
