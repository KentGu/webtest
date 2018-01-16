package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.CollateralPosition;
import com.lombardrisk.pojo.ExposureStatement;
import com.lombardrisk.pojo.StringType;
import com.lombardrisk.util.Biz;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;

/**
 * Created by mengli huang on 17/03/2017.
 */
public class ExposureStatementPage extends PageBase{
    public ExposureStatementPage(IWebDriverWrapper webDriverWrapper){
        super(webDriverWrapper);
    }

    private void assertCollateralPosition(CollateralPosition position) throws Exception{
        StringBuilder xpath = new StringBuilder();
        xpath.append("//table[tbody/tr/td[@class='titleHeader'][contains(text(),'Collateral Position')]]/following-sibling::table//table[@class='pagetext']/tbody/tr");
        Method[] methods = position.getClass().getDeclaredMethods();
        for (Method method:methods){
            if (method.getName().startsWith("get") && !method.getName().equals("getId")
                    && !method.getName().equals("getRef") && !method.getName().equals("getName")
                    && method.invoke(position) != null){
                String value;
                String columnName = Biz.getColumnName(method.getName().substring(3), "(?<!^)(?=[A-Z])");
                if (columnName.contains("Or"))
                    columnName = columnName.replace(" Or ","/");
                if (columnName.contains("Fx"))
                    columnName=columnName.replace("Fx","FX");
                if (columnName.contains("Local"))
                    columnName=columnName.replace(" Local","(Local)");
                if (method.getReturnType().equals(StringType.class)) {
                    value = ((StringType) method.invoke(position)).getRealValue();
                    if (!columnName.equals("Collateral Value")){
//                        xpath.append("[td[count(//th[@text()='"+columnName+"']/preceding-sibling::th)+1][text()='"+value+"']]");
                        xpath.append("[td[count(//th[@text()='").append(columnName).append("']/preceding-sibling::th)+1][text()='").append(value).append("']]");
                    }else {
//                        xpath.append("[td[count(//th[contains(text(),'" + columnName + "')]/preceding-sibling::th)+1][text()='" + value + "']]");
                        xpath.append("[td[count(//th[contains(text(),'").append(columnName).append("')]/preceding-sibling::th)+1][text()='").append(value).append("']]");
                    }
                }
            }
        }
        assertThat("ag.eligible.breached.rule.detail",xpath.toString()).displayed();
    }

    public void assetExposureStatementInfo(ExposureStatement statement) throws Exception{
        if (statement.getCollateralPosition()!=null && !statement.getCollateralPosition().isEmpty())
            for (CollateralPosition position:statement.getCollateralPosition())
                assertCollateralPosition(position);
    }
}
