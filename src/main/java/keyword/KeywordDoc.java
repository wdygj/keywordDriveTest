package keyword;

import common.Common;
import data.Data;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class KeywordDoc
{
    private List<Keyword> keys;
    private String proConditions[];
    private String proConditionCodes[];
    private String operationKeys[];
    private String operationKeyCodes[];
    private String pageObjects[];
    private String pageObjectCodes[];
    private String expecteds[];
    private String expectedCodes[];
    private String commons[];
    private String commonCodes[];
    private int currentRow = 0;

    public void setSpecificData(Keyword key)
    {
        proConditions = Common.numberSplit(key.getProCondition(),Data.Key_STARTPOSITION_REGEX,Data.Key_NUMBERSPLIT_REGEX);
        proConditionCodes = Common.numberSplit(key.getProConditionCode(),Data.Key_STARTPOSITION_REGEX,Data.Key_NUMBERSPLIT_REGEX);
        operationKeys = Common.numberSplit(key.getOperationKey(),Data.Key_STARTPOSITION_REGEX,Data.Key_NUMBERSPLIT_REGEX);
        operationKeyCodes = Common.numberSplit(key.getOperationKeyCode(),Data.Key_STARTPOSITION_REGEX,Data.Key_NUMBERSPLIT_REGEX);
        pageObjects = Common.numberSplit(key.getPageObject(),Data.Key_STARTPOSITION_REGEX,Data.Key_NUMBERSPLIT_REGEX);
        pageObjectCodes = Common.numberSplit(key.getPageObjectCode(),Data.Key_STARTPOSITION_REGEX,Data.Key_NUMBERSPLIT_REGEX);
        expecteds = Common.numberSplit(key.getExpected(),Data.Key_STARTPOSITION_REGEX,Data.Key_NUMBERSPLIT_REGEX);
        expectedCodes = Common.numberSplit(key.getExpectedCode(),Data.Key_STARTPOSITION_REGEX,Data.Key_NUMBERSPLIT_REGEX);
        commons = Common.numberSplit(key.getCommon(),Data.Key_STARTPOSITION_REGEX,Data.Key_NUMBERSPLIT_REGEX);
        commonCodes = Common.numberSplit(key.getCommonCode(),Data.Key_STARTPOSITION_REGEX,Data.Key_NUMBERSPLIT_REGEX);
    }

    public void initial(String url) throws Exception
    {
            keys= new ArrayList<>();
            File excel = new File(url);
            FileInputStream file = new FileInputStream(excel);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheetAt = workbook.getSheetAt(0);
            sheetAt.setForceFormulaRecalculation(true);
            int rowCount = Common.getActualRowCount(sheetAt, 1, 0);
            for (int i = 0; i < rowCount; i++)
            {
                Keyword keyword = new Keyword();
                keyword.setProCondition(Common.getCellValue(sheetAt.getRow(i + 1).getCell(0)));
                keyword.setProConditionCode(Common.getCellValue(sheetAt.getRow(i + 1).getCell(1)));
                keyword.setOperationKey(Common.getCellValue(sheetAt.getRow(i + 1).getCell(2)));
                keyword.setOperationKeyCode(Common.getCellValue(sheetAt.getRow(i + 1).getCell(3)));
                keyword.setPageObject(Common.getCellValue(sheetAt.getRow(i + 1).getCell(4)));
                keyword.setPageObjectCode(Common.getCellValue(sheetAt.getRow(i + 1).getCell(5)));
                keyword.setExpected(Common.getCellValue(sheetAt.getRow(i + 1).getCell(6)));
                keyword.setExpectedCode(Common.getCellValue(sheetAt.getRow(i + 1).getCell(7)));
                keyword.setCommon(Common.getCellValue(sheetAt.getRow(1).getCell(8)));
                keyword.setCommonCode(Common.getCellValue(sheetAt.getRow(1).getCell(9)));
                keys.add(keyword);
            }
    }

    //将用例语句转化为程序所需要的Code,type三种类型：0=前置条件，1=页面操作，2=页面对象,3=预期结果,4=公共对象
    //没找到时返回Null
    public String matchCode(String text,int type)
    {
        String[] typeString = null;
        String[] codeString = null;
        String methodCode=null;
        switch (type)
        {
            case 0:typeString= proConditions;codeString= proConditionCodes;break;
            case 1:typeString= operationKeys;codeString= operationKeyCodes;break;
            case 2:typeString= pageObjects;codeString= pageObjectCodes;break;
            case 3:typeString= expecteds;codeString= expectedCodes;break;
            case 4:typeString= commons;codeString= commonCodes;break;
        }
        for (int i = 0; i < typeString.length; i++)
        {
            if(typeString[i].equals(text))
            {
                methodCode = codeString[i];
            }
        }
        if (methodCode==null&&type!=4)
        {
            methodCode = matchCode(text,4);
        }
        return methodCode;
    }

    public Method matchMethod(Class pageOpe, String methodName) throws ClassNotFoundException, NoSuchMethodException
    {
        Method method = null;
        try
        {
            method = pageOpe.getMethod(methodName, String.class);
        } catch (Exception e) {method = pageOpe.getMethod(methodName, String.class, String.class);}
        return method;
    }

    //GeterAndSeter

    public String[] getCommons()
    {
        return commons;
    }

    public void setCommons(String[] commons)
    {
        this.commons = commons;
    }

    public String[] getCommonCodes()
    {
        return commonCodes;
    }

    public void setCommonCodes(String[] commonCodes)
    {
        this.commonCodes = commonCodes;
    }

    public int getCurrentRow()
    {
        return currentRow;
    }

    public void setCurrentRow(int currentRow)
    {
        this.currentRow = currentRow;
    }

    public List<Keyword> getKeys()
    {
        return keys;
    }

    public void setKeys(List<Keyword> keys)
    {
        this.keys = keys;
    }

    public String[] getProConditions()
    {
        return proConditions;
    }

    public void setProConditions(String[] proConditions)
    {
        this.proConditions = proConditions;
    }

    public String[] getProConditionCodes()
    {
        return proConditionCodes;
    }

    public void setProConditionCodes(String[] proConditionCodes)
    {
        this.proConditionCodes = proConditionCodes;
    }

    public String[] getOperationKeys()
    {
        return operationKeys;
    }

    public void setOperationKeys(String[] operationKeys)
    {
        this.operationKeys = operationKeys;
    }

    public String[] getOperationKeyCodes()
    {
        return operationKeyCodes;
    }

    public void setOperationKeyCodes(String[] operationKeyCodes)
    {
        this.operationKeyCodes = operationKeyCodes;
    }

    public String[] getPageObjects()
    {
        return pageObjects;
    }

    public void setPageObjects(String[] pageObjects)
    {
        this.pageObjects = pageObjects;
    }

    public String[] getPageObjectCodes()
    {
        return pageObjectCodes;
    }

    public void setPageObjectCodes(String[] pageObjectCodes)
    {
        this.pageObjectCodes = pageObjectCodes;
    }

    public String[] getExpecteds()
    {
        return expecteds;
    }

    public void setExpecteds(String[] expecteds)
    {
        this.expecteds = expecteds;
    }

    public String[] getExpectedCodes()
    {
        return expectedCodes;
    }

    public void setExpectedCodes(String[] expectedCodes)
    {
        this.expectedCodes = expectedCodes;
    }
}
