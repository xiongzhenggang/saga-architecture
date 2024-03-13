package com.xzg.goods.controller;

import com.xzg.goods.entity.Goods;
import com.xzg.goods.service.GoodsService;
import com.xzg.library.config.infrastructure.configuration.EnableResponseBodyWrap;
import com.xzg.library.config.infrastructure.model.CommonResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * <b>项目名称： </b>:saga-architecture
 * <b>Class name</b>: TestApi
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
@EnableResponseBodyWrap
@RestController
@RequestMapping("/goods")
public class GoodsApi {

    @Resource
    private GoodsService goodsService;

    //    @ApiHeader
    @GetMapping("/{goodsId}")
    public CommonResponse<Goods> getById(@PathVariable("goodsId") Long goodsId){
        return CommonResponse.success(goodsService.getGoodsById(goodsId));
    }

    @GetMapping("/{goodsId}")
    public CommonResponse<Goods> createGoods(@PathVariable("goodsId") Long goodsId){
        return CommonResponse.success(goodsService.getGoodsById(goodsId));
    }
    @GetMapping("/{goodsId}")
    public CommonResponse<Goods> updateGoods(@PathVariable("goodsId") Long goodsId){
        return CommonResponse.success(goodsService.getGoodsById(goodsId));
    }
}
