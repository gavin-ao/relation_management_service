package data.driven.erm.common;

/**
 * @author 何晋凯
 * @date 2018/06/05
 */
public class Constant {
    /** redis默认失效时间：半小时 **/
    public final static int REDIS_DEFAULT_EXPIRE_SECONDS = 30 * 60;
    /** 微信获取用户信息时，获取后传入后台的消耗时间最大允许为 3分钟，3分钟后判断该信息为假数据 **/
    public final static int USER_INFO_MAX_INTERVAL = 3 * 60;
    /** 文件上传路径 **/
    public static String FILE_UPLOAD_PATH;
    /** 微信二维码生成临时目录 **/
    public static String WXQRCODE_TEMP_FILE_FOLDER = "tempwxqrcode/";
    /** 静态资源访问路径 **/
    public static String STATIC_FILE_PATH = "/static/file/";
    /**电商的url**/
    public final static String SHOP_HOST_PATH = "http://shop.xinkebao.cn";//主机地址可以为域名
    /**电商静态资源范围路径**/
    public static String SHOP_FILE_PATH=SHOP_HOST_PATH+"/files/";//图片文件预览下载地址，'/'表示cms-admin项目根路径，如为admin则此值为HOST_PATH+"/admin/"
    public static final String SESSIONID_COOKIE_NAME = "SESSIONID";
    /** mysql日期格式字符串 **/
    public static final String mysql_day_format = "%Y-%m-%d";
    public static final String mysql_hour_format = "%Y-%m-%d %H";

    public void setFileUploadPath(String fileUploadPath) {
        FILE_UPLOAD_PATH = fileUploadPath;
    }
}
