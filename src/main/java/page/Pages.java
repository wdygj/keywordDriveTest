package page;

import common.Common;
import data.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.CORBA.TIMEOUT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;


public class Pages
{
    private WebDriver driver = null;
    private List<Page> pages;
    private static Logger logger = LogManager.getLogger(page.Pages.class.getName());
    private WebElement iframe_Now = null;

    //初始化方法
    public void setup()
    {
        pages = new ArrayList<>();
        try
        {
            driver = Common.chooseWebdriver(Data.WEBDRIVER_TYPE);
            driver.manage().timeouts().implicitlyWait(Data.IMPLICITY_TIME, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            turnHome();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    //页面操作方法
    public void turnHome()
    {
        driver.get(Data.LOGIN_PAGE);
    }
    public  void send(String find, String text)
    {
        WebElement element = driver.findElement(By.xpath(find));
        if (element.isEnabled())
        {
            element.clear();
            element.sendKeys(text);
            logger.info("input " + text + "\n\t");
        }
        else
        {
            String errorMessage = element.getText() + " is unenabled, please check the status";
            logger.info(errorMessage + "\n\t");
        }
    }

    public  void click(String find)
    {
        WebElement element = driver.findElement(By.xpath(find));
        element.click();
        logger.info(element.getText() + " is be clicked \n\t");
    }

    public  void submit(String find)
    {
        WebElement element = driver.findElement(By.xpath(find));
        element.submit();
        logger.info("submited \n\t");
    }

    public  void checkbox(String find)
    {
        WebElement element = driver.findElement(By.xpath(find));
        if (!element.isSelected())
        {
            element.click();
            logger.info(" click to selected the "+ element.getText()+"\n\t");
        }else
            {
            logger.info(element.getText()+"This element has been seleted \n\t");
            }
    }
    public void select(String find,String value) throws NoSuchElementException
    {
        WebElement element = driver.findElement(By.xpath(find));
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }
    public void deSelect(String find,String value) throws NoSuchElementException
    {
        WebElement element = driver.findElement(By.xpath(find));
        Select select = new Select(element);
        select.deselectByVisibleText(value);
    }
    public void get(String arg)
    {
        driver.get(arg);
    }
    public void exit()
    {
        driver.quit();
    }
    public void login(String type) throws Exception
    {
        String username = null;
        String password = null;
        int integer = Integer.valueOf(type);
        switch (integer)
        {
            case 1:;username=Data.USERNAME_1;password=Data.password_1;break;
            default:throw new Exception("Login Type is not find");
        }
        send("//input[@name=\"email\"]",username);
        send("//input[@name=\"password\"]",password);
        click("//a[@id=\"dologin\"]");
    }
    public void delay(int second) throws InterruptedException
    {
        driver.wait(second);
    }
    //GeterAndSeter
    public WebDriver getDriver()
    {
        return driver;
    }

    public void setDriver(WebDriver driver)
    {
        this.driver = driver;
    }

    public List<Page> getPages()
    {
        return pages;
    }

    public void setPages(List<Page> pages)
    {
        this.pages = pages;
    }
}
