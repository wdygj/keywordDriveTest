import common.Check;
import common.Common;
import data.Data;
import keyword.Keyword;
import keyword.KeywordDoc;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import page.Pages;
import testcase.Testcase;
import testcase.TestcaseDoc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.NoSuchElementException;

public class Operator
{
    static KeywordDoc keywordDocument;
    static TestcaseDoc testcaseDocument;
    static Pages pages;
    static WebDriver driver;
    public Operator()
    {
        keywordDocument = new KeywordDoc();
        testcaseDocument = new TestcaseDoc();
        pages = new Pages();
    }

    public static void main(String[] args) throws Exception
    {
        try
        {
            boolean excpected = false;
            Operator operator = new Operator();
            operator.initialData(keywordDocument, testcaseDocument, pages);
            List<Keyword> keys = keywordDocument.getKeys();
            List<Testcase> testcases = testcaseDocument.getTestcases();
            driver =pages.getDriver();
            for (int i = 0; i < testcases.size(); i++)
            {
                int currentRow = keywordDocument.getCurrentRow();
                if (operator.isSetKeywordDoc(testcases, i))
                {
                    keywordDocument.setSpecificData(keys.get(currentRow));
                    keywordDocument.setCurrentRow(currentRow + 1);
                }
                Testcase testcase = testcases.get(i);
                if (testcase.isTest() && testcase.getTestTool().equals("自动化")) //如果纳入测试不为“是”或测试工具栏不为自动化，那么将跳过这条测试
                {
                    testcaseDocument.setSpecificData(testcase);
                    String[] pros = testcaseDocument.getProconditions();
                    String[] proces = testcaseDocument.getProccesions();
                    operator.runSentence(pros, 0);
                    operator.runSentence(proces, 2);
                    excpected = operator.checkExpected(testcaseDocument.getExcpecteds());
                    operator.complecteExcel(excpected);
                }
            }
        }
        catch (Exception e)
        {
            e.getMessage();
            e.printStackTrace();
        }
        finally
        {
            if (pages != null && driver != null)
            {
                pages.exit();
            }
        }
    }

    private void complecteExcel(boolean excpected)
    {
    }

    private void runSentence(String[] str, int type) throws Exception
    {
        Thread.sleep(2000);
        int index = 0;
        for (int i = 0; i < str.length; i++)
        {
            String opCode = keywordDocument.matchCode(str[i].substring(0, 2), 1);
            Check.isNotNull(opCode, "Can't find methodCode");
            String[] opCodes = opCode.split(Data.SENTENCE_REGEX);
            String find = keywordDocument.matchCode(str[i].substring(2, str[i].length()), type);
            Check.isNotNull(find, "Can't find methodCode");
            String[] finds = find.split(Data.SENTENCE_REGEX);
            String[] data = new String[2];
            String[] testData = testcaseDocument.getSpecific_testData();
            for (int j = 0; j < opCodes.length; j++)
            {
                if (type == 0)
                {
                    data = finds[j].split(Data.SENTENCE_DATA_REGEX);
                } else
                {
                    data[0] = finds[j];
                    data[1] = testData[index];//这里报错的话说明本身表里没有写数据但是方法确要求两个参数，检查表和方法看问题在哪
                }
                Method method = keywordDocument.matchMethod(Pages.class, opCodes[j]);
                //执行方法
                int parameterCount = method.getParameterCount();
                index = (type == 0&&parameterCount==2) ? index : index + 1;
                try{invokeMehtod(pages,method,parameterCount,data);}
                catch (NoSuchElementException|InvocationTargetException e)
                {
                List<WebElement> iframes = driver.findElements(By.xpath("//iframe"));
                int iframe_Size = iframes.size();
                int sig = -1;       //用于跳出for循环
                int k = 0;
                for (k = 0; k < iframe_Size; k++)   //执行方法并遍历文档的iframe一个一个找
                {
                    try{invokeMehtod(pages,method,parameterCount,data);}
                    catch (NoSuchElementException|InvocationTargetException a)
                    {
                        driver.switchTo().defaultContent();
                        driver.switchTo().frame(iframes.get(k));++sig;
                    }
                    if (sig!=k){break;}
                }
                Check.isNotEquals(k,iframe_Size,"can't find such Element");
            }
        }
    }
    }

    private boolean checkExpected(String[] specific_excpected)
    {
        return false;
    }

    private void initialData(KeywordDoc keywordDocument, TestcaseDoc testcaseDocument, Pages pages) throws Exception
    {
        keywordDocument.initial(Data.KEYWORD_URL);
        testcaseDocument.initial(Data.TESTCASE_URL);
        pages.setup();
    }

    private boolean isSetKeywordDoc(List<Testcase> keywords, int index) throws Exception
    {
        boolean result = false;
        Testcase keyword_Now = keywords.get(index);
        if (index != 0)
        {
            String proCondition_Now = keyword_Now.getProCondition();
            String page_Now=getPositionFromString(proCondition_Now);
            String proCondition_provious = keywords.get(index - 1).getProCondition();
            String page_provious = getPositionFromString(proCondition_provious);
            if (!page_Now.equals(page_provious))
            {
                result = true;
            }
        } else
        {
            result = true;
        }
        return result;
    }
    private String getPositionFromString(String text) throws Exception
    {
        String[] strings = Common.numberSplit(text, Data.TestCase_STARTPOSITION_REGEX,Data.TestCase_NUMBERSPLIT_REGEX);
        String page = "";
        for (int i = 0; i < strings.length; i++)
        {
            int indexOf = strings[i].indexOf("界面");
            if (indexOf>-1){page=strings[i];break;}
        }
        if (page.equals("")){throw new Exception("Can't find '界面'");}
        return  page;
    }
    private void invokeMehtod(Pages pages,Method method,int paramCount,String[] data) throws Exception
    {
            switch (paramCount)
            {
                case 1:
                    method.invoke(pages, data[0]);
                    break;
                case 2:
                    method.invoke(pages, data[0], data[1]);
                    break;
                default:
                    throw new NoSuchMethodException("run Process no such method!");
            }

    }
}
