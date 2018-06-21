package common;

import data.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

public class Common
{
    public Common()
    {
    }
    public static WebDriver chooseWebdriver(String browserName) throws Exception
    {
        WebDriver driver = null;
        if (browserName.equalsIgnoreCase("chrome"))
        {
            System.setProperty("webdriver.chrome.driver",Data.DRIVER_URL);
            System.setProperty("webdriver.chrome.bin", "C:\\Program Files (x86)\\Google Chrome\\chrome.exe");
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox"))
        {
            System.setProperty("webdriver.gecko.drivers", "E:\\seleniumForJava\\geckodriverv-0.19.exe");
            System.setProperty("webdriver.firefox.bin", "E:\\Firefox1\\firefox.exe");
            driver = new FirefoxDriver();
        } else
        {
            throw new Exception("cann't find browserName");
        }
        return driver;
    }
//发送QQ邮件
    public static boolean QQEmailSend(final String sender, String receiver, final String authentication, String text)
    {
        // 创建一个Property文件对象
        Properties props = new Properties();
        // 设置邮件服务器的信息，这里设置smtp主机名称
        props.put("mail.smtp.host", "smtp.qq.com");
        // 设置socket factory 的端口
        props.put("mail.smtp.socketFactory.port", "465");
        // 设置socket factory
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        // 设置需要身份验证
        props.put("mail.smtp.auth", "true");
        // 设置SMTP的端口，QQ的smtp端口是25
        props.put("mail.smtp.port", "25");
        // 身份验证实现
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                // 第二个参数，就是我QQ开启smtp的授权码
                return new PasswordAuthentication(sender, authentication);
            }
        });
        try
        {
            // 创建一个MimeMessage类的实例对象
            Message message = new MimeMessage(session);
            // 设置发件人邮箱地址
            message.setFrom(new InternetAddress(sender));
            // 设置收件人邮箱地址
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            // 设置邮件主题
            message.setSubject("测试发送邮件");
            // 创建一个MimeBodyPart的对象，以便添加内容
            BodyPart messageBodyPart1 = new MimeBodyPart();
            // 设置邮件正文内容
            messageBodyPart1.setText(text);
            // 创建另外一个MimeBodyPart对象，以便添加其他内容
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            // 设置邮件中附件文件的路径
            String filename = ".\\test-output\\emailable-report.html";
            // 创建一个datasource对象，并传递文件
            DataSource source = new FileDataSource(filename);
            // 设置handler
            messageBodyPart2.setDataHandler(new DataHandler(source));
            // 加载文件
            messageBodyPart2.setFileName(filename);
            // 创建一个MimeMultipart类的实例对象
            Multipart multipart = new MimeMultipart();
            // 添加正文1内容
            multipart.addBodyPart(messageBodyPart1);
            // 添加正文2内容
            multipart.addBodyPart(messageBodyPart2);
            // 设置内容
            message.setContent(multipart);
            // 最终发送邮件
            Transport.send(message);
        } catch (MessagingException e)
        {
            return false;
        }
        return true;
    }
//根据传入的行进行列的字符串匹配，返回字符串所在列数
    public static int[] findKeyWordCell(XSSFRow row, String[] rowName) throws Exception
    {
        int cellCount = row.getPhysicalNumberOfCells();
        if (cellCount <= 0)
        {
            throw new IOException("sheetCount style mistake！");
        }
        int[] keyCellposition = new int[rowName.length];
        int a = 0;
        for (int i = 0; i < cellCount - 1; i++)
        {
            String cellValue =getCellValue(row.getCell(i));
            for (int j = 0; j < rowName.length; j++)
            {
                if (cellValue.equals(rowName[j]))
                {
                    keyCellposition[a] = i;
                    ++a;
                    break;
                }
            }
        }
        return keyCellposition;
    }
//根据某一列是否为空来判断实际的行数
    public static int getActualRowCount(Sheet sheet, int startRow, int cell) throws Exception
    {
        int a = sheet.getPhysicalNumberOfRows();
        int rowCount = 0;
            for (int i = startRow; i < a; i++)
            {
                String cellValue = getCellValue(sheet.getRow(i).getCell(cell));
                if (cellValue.equals("")){break;}
                ++rowCount;
            }
        if (rowCount <= 0) {throw new NullPointerException("rowCount lower 0");}
        return rowCount;
    }
    //对于用数字序号例如：1.a 2.b 3.c这样的数据进行分割，返回数据实体，例如之前的{a,b,c}数组
    public static String[] numberSplit(String text, int startPosition,String regex)
    {
        Pattern p = Pattern.compile(regex);
        boolean hasNumber = p.matcher(text).find();
        if (text.length()>startPosition&&hasNumber)
        {
            text = text.substring(startPosition, text.length());
            text = text.replace("\n", "");
        }
        String[] a = text.split(regex);
        return a;
    }
    public static int getSheetIndexByName(XSSFWorkbook sheets, String Str) throws Exception
    {
        int result = -1;
        int index = sheets.getNumberOfSheets();
        for (int i = 0; i < index; i++)
        {
            String sheetName = sheets.getSheetAt(i).getSheetName();
            if (sheetName.equals(Str))
            {
                result = i;
            }
        }
        Check.isNotEquals(result,-1,"Can't find "+Str+",please check!");
        return result;
    }
    public static String getCellValue(Cell cell) throws Exception
    {
        String result;
        if (cell!=null)
        {
        CellType cellTypeEnum = cell.getCellTypeEnum();
            switch (cellTypeEnum)
            {
                case STRING:
                    result = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    result = String.valueOf(cell.getNumericCellValue());
                    break;
                case BLANK:
                    result = "";
                    break;
                case BOOLEAN:
                    result = cell.getBooleanCellValue() ? "是" : "否";
                    break;
                case FORMULA:
                    result = getFormulaValue(cell);
                    break;
                case _NONE:
                    result = "";
                    break;
                default:
                    throw new Exception("can't read this type:" + cellTypeEnum.toString());
            }
        }else {result = "";}
        return result;
    }
    private static String getFormulaValue(Cell cell)
    {
        String s;
        try
        {
            s = String.valueOf(cell.getNumericCellValue());
        }catch(Exception e)
        {
            s=String.valueOf(cell.getStringCellValue());
        }
        return s;
    }
}
