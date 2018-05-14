package common;

public class Check
{
    public static void isGreater(int actual,int expected,String message,int isEqual) throws Exception
    {
        switch (isEqual)
        {
            case 0:if (actual<=expected){throw new Exception(message);}break;
            case 1:if (actual<expected){throw new Exception(message);}break;
        }
    }
    public static void isLesser(int actual, int expected, String message, int isEqual) throws Exception
    {
        switch (isEqual)
        {
            case 0:if (actual>=expected){throw new Exception(message);}break;
            case 1:if (actual>expected){throw new Exception(message);}break;
        }
    }
    public static void isEquals(int actual,int expected,String message) throws Exception
    {
        if (actual!=expected){throw new Exception(message);}
    }
    public static void isNotEquals(int actual,int expected,String message) throws Exception
    {
        if (actual==expected){throw new Exception(message);}
    }
    public static  void isNull(Object actual,String message) throws Exception
    {
        if (actual!=null){throw new Exception(message);}
    }
    public static  void isNotNull(Object actual,String message) throws Exception
    {
        if (actual==null){throw new Exception(message);}
    }
}
