package com.xzg.goods.api;

import com.xzg.library.config.infrastructure.model.CommonResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
public class OrderController {


    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public CommonResponse<String> createOrder() {
        return CommonResponse
                .success("");
    }

}
