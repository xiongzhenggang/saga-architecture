@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiHeader {

    /**
     * api header是否必须
     * @return
     */
    boolean required() default true;
}

public final class ApiHeaderUtil {
    private static final ThreadLocal<HeaderUserModel> header = new ThreadLocal();

    public static HeaderUserModel getHeader() {
        return (HeaderUserModel)header.get();
    }

    public static void setHeader(HeaderUserModel headerModel) {
        header.set(headerModel);
    }

    public static void removeHeader() {
        header.remove();
    }

    private ApiHeaderUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}



@Slf4j
@Component
@Aspect
@Order(1)
public class ApiHeaderAop {

    public static final String HEADER_PORTAL_USER = "portalUser";

    @Autowired
    private ObjectMapper mapper;


    @Around(" @within(apiHeader)")
    public Object invokeWithIn(ProceedingJoinPoint point, ApiHeader apiHeader) throws Throwable {
        return invokeAround(point, apiHeader);
    }

    @Around("@annotation(apiHeader)")
    public Object invokeAround(ProceedingJoinPoint point, ApiHeader apiHeader) throws Throwable {
        // api header是否必须
        boolean required = apiHeader.required();

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request;
        if (requestAttributes != null) {
            Object requestObj = requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
            if (requestObj instanceof HttpServletRequest) {
                request = (HttpServletRequest) requestObj;

                String bizDto = request.getHeader(HEADER_PORTAL_USER);
                if (!StringUtils.hasText(bizDto) && required) {
                    throw new UnauthorizedException(SystemMessage.SYS_USER_PERMISSION_LIMIT);
                } else {
                    if (StringUtils.hasText(bizDto)) {
                        try {
                            HeaderUserModel userModel = mapper.readValue(bizDto, HeaderUserModel.class);
                            if (userModel == null) {
                                throw new UnauthorizedException(SystemMessage.SYS_USER_PERMISSION_LIMIT);
                            }

                            // 写header线程缓存
                            ApiHeaderUtil.setHeader(userModel);
                        } catch (JsonProcessingException e) {
                            log.warn("header {}: {}", HEADER_PORTAL_USER, bizDto);
                            log.warn("read portal user header error", e);
                            throw new UnauthorizedException(SystemMessage.SYS_USER_PERMISSION_LIMIT);
                        }
                    }
                }
            }
        }
        if (ApiHeaderUtil.getHeader() == null && required) {
            // 缺少用户信息, 抛出异常
            throw new UnauthorizedException(SystemMessage.SYS_USER_PERMISSION_LIMIT);
        }

        try {
            return point.proceed();
        } finally {
            // 清楚header线程缓存
            ApiHeaderUtil.removeHeader();
        }
    }
}



@Slf4j
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Component
public class FeignInterceptor implements RequestInterceptor {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void apply(RequestTemplate template) {
        HeaderUserModel header = ApiHeaderUtil.getHeader();
        if (header != null) {
            try {
                template.header(HEADER_PORTAL_USER, mapper.writeValueAsString(header));
            } catch (JsonProcessingException e) {
                log.warn("write header portalUser error : {}", header);
                log.warn("error", e);
            }
        }
    }
}



## feign config
@Slf4j
@Component
public class ResponseDecoder implements Decoder {

    private final static String RES_DATA = "result";
    public static final String COM_CMSR_MOS_COMMON_RESPONSE = "com.cmsr.cmp.gd.common.base.model";

    /**
     * 车享的默认返回包装值
     */
    private static final String TSOP_COMMON_RESPONSE = "com.cmsr.cmp.tsop2int.model.Cmp2ApiResponse";
    private static final String CMP2_COMMON_RESPONSE = "com.cmsr.mos.province.gz.busi.feign.model.Cmp2ApiResponse";
    private static final String RNR_API_RESPONSE = "com.cmsr.cmp.gd.rnr2int.model.RnrApiResponse";
    private static final String CMP2_CORE_RESPONSE = "com.cmsr.mos.province.gz.busi.feign.model.core.CoreResp";
    private static final String CPSP_YINHE_RESPONSE = "com.cmsr.cmp.gd.cpsp.yinhe.model.response.YhResponse";

    @Autowired
    JsonUtil jsonUtil;


    /**
     * 这里统一处理，根据状态码判断返回正常还是异常的，
     * 200返回正常的，其他状态码直接抛出异常
     */
    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        if (response.body() == null) {
            return response;
        }

        String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));

        log.info("bodyStr===========> {}", bodyStr);

        if (type.getTypeName().contains(COM_CMSR_MOS_COMMON_RESPONSE)
                || type.getTypeName().contains(TSOP_COMMON_RESPONSE)
                || type.getTypeName().contains(CMP2_COMMON_RESPONSE)
                || type.getTypeName().contains(RNR_API_RESPONSE)
                || type.getTypeName().contains(CMP2_CORE_RESPONSE)
                || type.getTypeName().contains(CPSP_YINHE_RESPONSE)) {
            log.info("decode COM_CMSR_MOS_COMMON_RESPONSE");
            return jsonUtil.json2obj(bodyStr, type);
        }

        try {
            JsonNode jsonNode = jsonUtil.getMapper().readTree(bodyStr);
            JsonNode statusNode = jsonNode.get("status");
            JsonNode messageNode = jsonNode.get("messages");
            JsonNode responseNode = jsonNode.get(RES_DATA);
            if (statusNode != null) {
                if (responseNode != null) {
                    String resData = responseNode.toPrettyString();
                    if (StringUtils.hasLength(resData)) {
                        return jsonUtil.json2obj(resData, type);
                    }
                } else {
                    return null;
                }
            }
        } catch (IOException e) {
            log.error("not json{}", bodyStr);
            //// TODO: 2021-07-20 需要校验此位置的返回是否正确
            return response;
        }
        return jsonUtil.json2obj(bodyStr, type);
    }


}


@Slf4j
@Configuration
public class ResponseErrorDecoder implements ErrorDecoder {

    private final static String MSG = "messages";
    private final static String CODE = "status";

    private final static String SERVICE_EX = "com.cmsr.common.exception.ServiceException";

    private final static String VALIDATE_EX= "com.cmsr.common.exception.ValidateException";



    @Override
    public Exception decode(String methodKey, Response response) {

        try {
            if (response.body() != null) {

                ObjectMapper mapper = new ObjectMapper();
                String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
                log.info("feign error response :"+bodyStr);
                Map<String,Object> result = mapper.readValue(bodyStr,
                        new TypeReference<Map<String,Object>>() {});
                if (result.get(CODE)==null||result.get(MSG)==null){
                    return FeignException.errorStatus(methodKey, response);
                }
                if (response.status()== HttpStatus.BAD_REQUEST.value()){
                    Class clazz = Class.forName(VALIDATE_EX);
                    return (Exception) clazz.getDeclaredConstructor(String.class).newInstance(result.get(CODE)+"|"+result.get(MSG));
                }else {
                    Class clazz = Class.forName(SERVICE_EX);
                    Object msg = result.get(MSG);
                    if(result.get(MSG).getClass().equals(ArrayList.class)){
                        msg = ((ArrayList)result.get(MSG)).get(0);
                    }
                    return (Exception) clazz.getDeclaredConstructor(String.class).newInstance(result.get(CODE)+"|"+msg);
                }

            }
 
        } catch (Exception e) {
            log.error("feign处理出错",e);
        }
        return FeignException.errorStatus(methodKey, response);
    }
}

public class JavaTimeModule extends SimpleModule {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String HH_MM_SS = "HH:mm:ss";

    public JavaTimeModule() {
        super(PackageVersion.VERSION);
        addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS)));
        addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(YYYY_MM_DD)));
        addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(HH_MM_SS)));

        addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS)));
        addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(YYYY_MM_DD)));
        addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(HH_MM_SS)));
    }
}


@Component
public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(JavaTimeModule.YYYY_MM_DD_HH_MM_SS));


        JavaTimeModule javaTimeModule = new JavaTimeModule();

        OBJECT_MAPPER.registerModule(javaTimeModule)
                .registerModule(new ParameterNamesModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> T json2obj(String jsonStr, Type targetType) {
        if (jsonStr == null || jsonStr.length() <= 0){
            return null;
        }
        try {
            JavaType javaType = TypeFactory.defaultInstance().constructType(targetType);
            return OBJECT_MAPPER.readValue(jsonStr, javaType);

        } catch (Exception e) {
            throw new IllegalArgumentException("将JSON转换为对象时发生错误:" + jsonStr, e);
        }
    }
    public  String object2Json(Object object) {
        try {
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            OBJECT_MAPPER.registerModule(javaTimeModule);
            return  OBJECT_MAPPER.writeValueAsString(object);
        }
        catch (Exception e) {
            return null;
        }
    }

    public ObjectMapper getMapper(){
        return  OBJECT_MAPPER;
    }
}


































































