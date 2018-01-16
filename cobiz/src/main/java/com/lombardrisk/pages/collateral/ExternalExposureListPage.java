package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.CallStatusType;
import com.lombardrisk.pojo.ExternalExposureList;
import com.lombardrisk.pojo.InternalReviewSummary;
import com.lombardrisk.pojo.StringType;
import com.lombardrisk.util.Biz;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;

/**
 * Created by jane zhang on 11/20/2017.
 */
public class ExternalExposureListPage extends PageBase {
    public ExternalExposureListPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void gotoTablink(ExternalExposureList externalExposureList) throws Exception{
        if (externalExposureList.getTablink() != null){
            element("eel.tablink",externalExposureList.getTablink().getRealValue()).click();
        }
        if (externalExposureList.getSubTablink() != null){
            if (externalExposureList.getSubTablink().getRealValue().equals("Calls")){
                element("eel.subTablink.withoutAndSymbol",externalExposureList.getSubTablink().getRealValue()).click();
            }else {
                element("eel.subTablink.withAndSymbol",externalExposureList.getSubTablink().getRealValue()).click();
            }
        }

    }

    public void  assertExternalExposureList(ExternalExposureList eel) throws Exception {
        if (eel.getVariation() != null && eel.getVariation().equalsIgnoreCase("noRecord")) {
            assertThat("eel.searchResult.message").displayed();
        } else {
            StringBuilder xpath = locateExternalExposureList(eel);
            if (eel.isApply() != null && ! eel.isApply()) {
                assertThat(locator("eel.event.row", xpath.toString())).displayed().isFalse();
            } else {
                assertThat(locator("eel.event.row", xpath.toString())).displayed().isTrue();
            }
        }

    }

    private StringBuilder locateExternalExposureList(ExternalExposureList eel) throws Exception {
        StringBuilder xpath = new StringBuilder();
        Method[] methods = eel.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get") && !method.getName().equals("getId")
                    && !method.getName().equals("getRef") && !method.getName().equals("getName")
                    && !method.getName().equals("getAlertHandling")
                    && method.invoke(eel) != null) {
                String columnName = Biz.getColumnName(method.getName().substring(3), "(?<!^)(?=[A-Z])");
                StringType stringType = null;
                String value = "";
                if (method.getReturnType().equals(StringType.class)) {
                    stringType = ((StringType) method.invoke(eel));
                    value = stringType.getRealValue();
                } else if (method.getReturnType().equals(CallStatusType.class))
                    value = ((CallStatusType) method.invoke(eel)).value();

                if (columnName.equalsIgnoreCase("Tablink")
                        || columnName.equalsIgnoreCase("Sub Tablink")
                        || columnName.equalsIgnoreCase("Variation")){
                }else {
                    xpath.append("[td[count(//th[text()='").append(columnName).append("']/preceding-sibling::th)+1]");
                    if (value != null && !value.equals("")) {
                        xpath.append("[contains(text(),'").append(value).append("')]");
                    }
                    xpath.append("]");
                }


            }
        }
        return xpath;

    }



}
