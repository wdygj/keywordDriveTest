package page;

import java.util.Map;

public class Page
{
    String title;
    Map<String,String> pageName;
    Map<String,String> Element;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Map<String, String> getPageName()
    {
        return pageName;
    }

    public void setPageName(Map<String, String> pageName)
    {
        this.pageName = pageName;
    }

    public Map<String, String> getElement()
    {
        return Element;
    }

    public void setElement(Map<String, String> element)
    {
        Element = element;
    }
}
