package com.xzg.goods.api;

import com.xzg.goods.entity.Goods;
import com.xzg.goods.service.GoodsService;
import com.xzg.library.config.infrastructure.auth.ApiHeader;
import com.xzg.library.config.infrastructure.configuration.EnableResponseBodyWrap;
import com.xzg.library.config.infrastructure.model.CommonResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * <b>项目名称： </b>:saga-architecture
 * <b>Class name</b>: OrderController
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
 * 12/13/2023   xiongzhenggang        1.0          Initial Creation
 *
 * </pre>
 *
 * @author xiongzhenggang
 * @date 12/13/2023
 * </p>
 */
@RestController
@EnableResponseBodyWrap
public class OrderController {

    @Resource
    GoodsService goodsService;
    @ApiHeader
    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public CommonResponse<String> createOrder()  {
        Goods goods = new Goods();
        goods.setGoodsName(UUID.randomUUID().toString());
        goods.setStock(1);
        goods.setUnitPrice(new BigDecimal(1));
        goods.setCreateTime(LocalDateTime.now());
        goodsService.saveGoods(goods);
//        Thread.sleep(1000);
        return CommonResponse
                .success("");
    }

}
