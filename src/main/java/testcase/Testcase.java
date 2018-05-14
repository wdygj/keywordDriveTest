package testcase;

public class Testcase
{
    private String proCondition;
    private String procession;
    private String testData;
    private String expected;
    private String testTool;
    private boolean isTest;
    public boolean isTest()
    {
        return isTest;
    }

    public void setIsTest(boolean test)
    {
        isTest = test;
    }

    public String getTestTool()
    {
        return testTool;
    }

    public void setTestTool(String testTool)
    {
        this.testTool = testTool;
    }

    public void setProCondition(String proCondition)
    {
        this.proCondition = proCondition;
    }

    public void setProcession(String procession)
    {
        this.procession = procession;
    }

    public void setTestData(String testData)
    {
        this.testData = testData;
    }

    public void setExpected(String expected)
    {
        this.expected = expected;
    }

    public String getProCondition()
    {
        return proCondition;
    }

    public String getProcession()
    {
        return procession;
    }

    public String getExpected()
    {
        return expected;
    }

    public String getTestData()
    {
        return testData;
    }

    public void setTest(boolean test)
    {
        isTest = test;
    }
}
