package org.xzg.goods.api;

import com.xzg.library.config.infrastructure.model.CommonResponse;
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
@RestController
public class TestApi {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public CommonResponse<String> createOrder() {
        return CommonResponse.success("succcess");
    }
    @GetMapping("/test1")
    public String getName(){
        return "xx";
    }
}
