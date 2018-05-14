package data;

public class Data
{
    public static final String TESTCASE_URL = "target/classes/testcase.xlsx";
    public static final String[] TESTCASE_KEYNAME = {"前置条件","测试方法和步骤","测试数据","校验过程","测试工具","纳入测试"};
    public static final String KEYWORD_URL = "target/classes/keyword.xlsx";
    public static final String NUMBERSPLIT_REGEX ="\\d\\.";
    public static final String SENTENCE_REGEX = "\\;\\;";
    public static final String SENTENCE_DATA_REGEX = "\\:\\:";
    public static final String LOGIN_PAGE = "https://mail.163.com/";
    public static final int IMPLICITY_TIME = 5;
    public static final int ROWHEAD_INDEX = 10;
    public static final String SHEETHEAD_STRING = "测试大纲";
    public static final String SHEETEND_STRING = "流程测试用例";

    //启动浏览器驱动配置
    public static final String WEBDRIVER_TYPE = "chrome";
    public static final String DRIVER_URL = "F:\\driver\\chromedriver.exe";
    //航站人员账号密码
    public static final String USERNAME_1 = "laosiji005@163.com";
    public static final String password_1 = "a6461889";
}
