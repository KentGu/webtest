package com.lombardrisk.pages.systemadmin;

import com.lombardrisk.pojo.BusinessFrequency;
import com.lombardrisk.pojo.FrequencyType;
import com.lombardrisk.pojo.StringType;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;


public final class BusinessFrequencyPage extends PageBase {

    public BusinessFrequencyPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void searchFrequency(BusinessFrequency businessFrequency) throws Exception {
        if (businessFrequency.getFrequencyName() != null){
            element("sf.frequency.name.select").selectByVisibleText(businessFrequency.getFrequencyName().getRealValue());
        }
    }


    public void addBusinessFrequency(BusinessFrequency businessFrequency) throws Exception {
        element("sf.frequency.add").click();
    }

    public void inputBusinessFrequency(BusinessFrequency businessFrequency) throws Exception {
        if (businessFrequency.getFrequencyName() != null){
            element("sf.frequency.name.input").input(businessFrequency.getFrequencyName().getRealValue());
        }
        if (businessFrequency.getFrequencyName() != null){
            element("sf.frequency.type").selectByVisibleText(businessFrequency.getFrequency().value());
        }
    }

    public void saveBusinessFrequency() throws Exception {
        element("sf.frequency.save").click();
    }

    public void editBusinessFrequecy(BusinessFrequency businessFrequency) throws Exception {
        String xpath = getXpath(businessFrequency);
        element("sf.frequency.edit",xpath).click();
    }
    public void deleteBusinessFrequency(BusinessFrequency businessFrequency) throws Exception {
        String xpath = getXpath(businessFrequency);
        element("sf.frequency.delete",xpath).click();
    }
    //tr[td='Daily'][td='Daily']//img[@alt='Edit Business Frequency']
    public String getXpath(BusinessFrequency businessFrequency) throws Exception {
        StringBuffer xpath = new StringBuffer("//tr");
        Method[] methods = businessFrequency.getClass().getDeclaredMethods();

        for (Method method : methods){
            if ((method.getName().equals("getFrequencyName")
                    || method.getName().equals("getFrequency"))
                    && method.invoke(businessFrequency) != null){
                if (method.getName().equals("getFrequencyName")){
                    StringType v = (StringType) method.invoke(businessFrequency);
//                    xpath.append("[td='"+v.getRealValue()+"'");
                    xpath.append("[td='").append(v.getRealValue()).append("'");
                }else{
                   FrequencyType frequencyType = (FrequencyType) method.invoke(businessFrequency);
//                    xpath.append("[td='"+frequencyType.value()+"']");
                    xpath.append("[td='").append(frequencyType.value()).append("']");
                }
            }
        }
        return xpath.toString();
    }

}
