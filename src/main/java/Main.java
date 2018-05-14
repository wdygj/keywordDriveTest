
/*
import data.Data;
import common.CommonTool;
import page.Pages;
import keyword.Keyword;
import testcase.Testcase;
import testcase.TestcaseDocument;

import java.lang.reflect.Method;

public class Main
{
    static Testcase testcase = new Testcase();
    static Keyword keyword = new Keyword();
    static TestcaseDocument testcaseOp = new TestcaseDocument(testcase);

    static int rowCurrent = -1;
    static Pages pagOpe = new Pages();
    public static void main(String[] args)
    {
        String[][][] testCaseData = testcase.rCaseExcel(Data.TESTCASE_URL);
        String[][] keywordData = keyword.readExcel(Data.KEYWORD_URL);
        keyword.setKeyword(keywordData);
        pagOpe.runWebsite();
        try
        {
            for (int i = 0; i < testCaseData.length; i++)
            {
                testcase.setKeyword(testCaseData, i);
                for (int j = 0; j < testCaseData[i].length; j++)
                {
                    String[] testData =  CommonTool.numberSplit(testcase.getTestData()[j], Data.NUMBERSPLIT_REGEX);
                    int parameterSign = 0;
                    for (int k = 0; k < testCaseData[i][j].length; k++)
                    {
                        keyword.setColumCurrent(k);
                        String cellText = testCaseData[i][j][k];
                        String proWeb = testcase.getproConditionWeb(cellText);
                        keyword.setConditionsign(proWeb);
                        main.start(cellText,testData,parameterSign);
                        pagOpe.exit();
                    }
                }
            }
        } catch (Exception e)
        {
            pagOpe.exit();
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void start(String cell, String[] testData, int parameterSign) throws Exception
    {
        String[] text = CommonTool.numberSplit(cell, Data.NUMBERSPLIT_REGEX);
        for (int i = 0; i < text.length; i++)
        {
            String methodCode = keyword.searchMethodCode(text[i]);
            String objectCode = keyword.searchObjectCode(text[i]);
            String[] splitMethodCode = methodCode.split( Data.SENTENCE_REGEX);
            String[] findCondition = objectCode.split( Data.SENTENCE_REGEX);
            Class pageOpe = pagOpe.getClass();
            for (int j = 0; j < splitMethodCode.length; j++)
            {
                Object obj =null;
                Method operationMethod = keyword.reflectMethod(pageOpe,splitMethodCode[j]);
                int paCount = operationMethod.getParameterCount();
                switch (paCount)
                {
                    case 1:obj=operationMethod.invoke(pagOpe, findCondition[j]);break;
                    case 2:obj=operationMethod.invoke(pagOpe, findCondition[j],testData[parameterSign]);++parameterSign;break;
                }

            }
        }
    }

}
*/