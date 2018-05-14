package testcase;

import common.Check;
import common.Common;
import data.Data;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestcaseDoc
{
    private String[] proconditions;
    private String[] proccesions;
    private String[] testDatas;
    private String[] excpecteds;
    private String isAutoTests;
    private int currentRow;
    private List<Testcase> testcases;

    public void initial(String url)
    {
        try
        {
            testcases = new ArrayList<>();
            File excelFile = new File(url);
            FileInputStream file = new FileInputStream(excelFile);
            XSSFWorkbook sheets = new XSSFWorkbook(file);
            int sheetHead = Common.getSheetIndexByName(sheets,Data.SHEETHEAD_STRING)+1;
            int sheetEnd = Common.getSheetIndexByName(sheets,Data.SHEETEND_STRING)-1;
            Check.isGreater(sheetEnd,sheetHead,"sheetEnd lesser than head!",1);
            for (int i = sheetHead; i <= sheetEnd; i++)
            {
                XSSFSheet sheetAt = sheets.getSheetAt(i);
                XSSFRow row = sheetAt.getRow(Data.ROWHEAD_INDEX-1 );
                int[] keyWorkCell = Common.findKeyWordCell(row, Data.TESTCASE_KEYNAME);
                setTestcases(sheetAt, keyWorkCell);
            }
        } catch (Exception e)
        {
            e.getMessage();
            e.printStackTrace();
        }
    }
    private void setTestcases(XSSFSheet sheet, int[] cell) throws Exception
    {
        int rowCount = Common.getActualRowCount(sheet, 10, 2);
        String[][] keyWord = new String[rowCount][6];
        for (int i = 0; i < rowCount; i++)
        {
            XSSFRow row = sheet.getRow(i + 10);
            Testcase testcase = new Testcase();
            for (int j = 0; j < 6; j++)
            {
                    keyWord[i][j] = Common.getCellValue(row.getCell(cell[j]));
            }
            testcase.setProCondition(keyWord[i][0]);
            testcase.setProcession(keyWord[i][1]);
            testcase.setTestData(keyWord[i][2]);
            testcase.setExpected(keyWord[i][3]);
            testcase.setTestTool(keyWord[i][4]);
            Boolean isTest = keyWord[i][5].equals("是")?true:false;
            testcase.setIsTest(isTest);
            testcases.add(testcase);
        }
    }
    public void setSpecificData(Testcase Tcase)
    {
        proconditions = Common.numberSplit(Tcase.getProCondition(),Data.NUMBERSPLIT_REGEX);
        proccesions = Common.numberSplit(Tcase.getProcession(),Data.NUMBERSPLIT_REGEX);
        testDatas = Common.numberSplit(Tcase.getTestData(),Data.NUMBERSPLIT_REGEX);
        excpecteds = Common.numberSplit(Tcase.getExpected(),Data.NUMBERSPLIT_REGEX);

    }
    //geterAndSeter
    public String getIsAutoTests()
    {
        return isAutoTests;
    }
    public void setIsAutoTests(String isAutoTests)
    {
        this.isAutoTests = isAutoTests;
    }
    public String[] getProconditions()
    {
        return proconditions;
    }

    public void setProconditions(String[] proconditions)
    {
        this.proconditions = proconditions;
    }

    public String[] getProccesions()
    {
        return proccesions;
    }

    public void setProccesions(String[] proccesions)
    {
        this.proccesions = proccesions;
    }
    //特殊化取用具体的数据
    public String getSpecific_testData(int index)
    {
        int length = testDatas.length;
        if (index<length)
        {
        return testDatas[index];
        }else
            {
                throw new IndexOutOfBoundsException("Index for get testData if out of Bounds!,Please Check again");
            }

    }

    public void setTestDatas(String[] testDatas)
    {
        this.testDatas = testDatas;
    }

    public String[] getExcpecteds()
    {
        return excpecteds;
    }

    public void setExcpecteds(String[] excpecteds)
    {
        this.excpecteds = excpecteds;
    }

    public int getCurrentRow()
    {
        return currentRow;
    }

    public void setCurrentRow(int currentRow)
    {
        this.currentRow = currentRow;
    }

    public List<Testcase> getTestcases()
    {
        return testcases;
    }

    public void setTestcases(List<Testcase> testcases)
    {
        this.testcases = testcases;
    }

    public String getproConditionWeb(String text)
    {
        String result = null;
        String[] split = Common.numberSplit(text, "\\d\\.");
        for (int i = 0; i < split.length; i++)
        {
            int index = split[i].indexOf("界面");
            if (index >= 0)
            {
                result = split[i].substring(2, index+2);
            }
        }
        if (result == null)
        {throw new NullPointerException("can't not find 界面");}
        return result;
    }
}
