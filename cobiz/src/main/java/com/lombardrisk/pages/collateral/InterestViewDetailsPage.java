package com.lombardrisk.pages.collateral;


import com.lombardrisk.pojo.DailyInterestDetail;
import com.lombardrisk.pojo.InterestReportDetails;
import com.lombardrisk.pojo.StringType;
import com.lombardrisk.util.Biz;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;
import java.text.DecimalFormat;


/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class InterestViewDetailsPage extends PageBase {
    public InterestViewDetailsPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    

    public void enterInterestManagerPage() throws  Exception{
        element("ivd.interest.manager").click();
    }

    public void assertInterestReportDetail(InterestReportDetails interestReportDetails) throws Exception{
        if(interestReportDetails.getPrincipal() != null)
            assertThat("ivd.detail.prin").innerText().isEqualToIgnoringWhitespace(interestReportDetails.getPrincipal().getRealValue());
        if(interestReportDetails.getCounterparty() != null)
            assertThat("ivd.detail.cpty").innerText().isEqualToIgnoringWhitespace(interestReportDetails.getCounterparty().getRealValue() + "  (" + interestReportDetails.getCounterparty().getRealValue() + ")");
        if(interestReportDetails.getDescription() != null)
            assertThat("ivd.detail.desc").innerText().isEqualToIgnoringWhitespace(interestReportDetails.getDescription().getRealValue());
        if(interestReportDetails.getAgreementExternalID() != null)
            assertThat("ivd.detail.agrexid").innerText().isEqualToIgnoringWhitespace(interestReportDetails.getAgreementExternalID().getRealValue());
        if(interestReportDetails.getCashAssetType() != null)
            assertThat("ivd.detail.assettype").innerText().isEqualToIgnoringWhitespace(interestReportDetails.getCashAssetType().getRealValue());
        if(interestReportDetails.getInterestSource() != null)
            assertThat("ivd.detail.intersource").innerText().isEqualToIgnoringWhitespace(interestReportDetails.getInterestSource().getRealValue());
        if(interestReportDetails.getCalculationMethod() != null)
            assertThat("ivd.detail.calmethod").innerText().isEqualToIgnoringWhitespace(interestReportDetails.getCalculationMethod().getRealValue());
        if(interestReportDetails.getPrincipalRate() != null)
            assertThat("ivd.detail.prinrate").innerText().isEqualToIgnoringWhitespace(interestReportDetails.getPrincipalRate().getRealValue());
        if(interestReportDetails.getCounterpartyRate() != null)
            assertThat("ivd.detail.cptyrate").innerText().isEqualToIgnoringWhitespace(interestReportDetails.getCounterpartyRate().getRealValue());
        if(interestReportDetails.getPrincipalSpread() != null)
            assertThat("ivd.detail.prinspread").innerText().isEqualToIgnoringWhitespace(interestReportDetails.getPrincipalSpread().getRealValue());
        if(interestReportDetails.getCounterpartySpread() != null)
            assertThat("ivd.detail.cptyspread").innerText().isEqualToIgnoringWhitespace(interestReportDetails.getCounterpartySpread().getRealValue());
        if(interestReportDetails.getWithholdingRate() != null)
            assertThat("ivd.detail.withholdrate").innerText().isEqualToIgnoringWhitespace(interestReportDetails.getWithholdingRate().getRealValue());
        if(interestReportDetails.getPeriodAmount() != null)
            assertThat("ivd.detail.periodamt").innerText().isEqualToIgnoringWhitespace(interestReportDetails.getPeriodAmount().getRealValue());
        if(interestReportDetails.getWithholdingAmount() != null)
            assertThat("ivd.detail.withholdamt").innerText().isEqualToIgnoringWhitespace(interestReportDetails.getWithholdingAmount().getRealValue());
        if(interestReportDetails.getNetInterestAmount() != null)
            assertThat("ivd.detail.netinteramt").innerText().isEqualToIgnoringWhitespace(interestReportDetails.getNetInterestAmount().getRealValue());
        if(interestReportDetails.getAgreedPeriodAmount() != null)
            assertThat("ivd.detail.agrperiodamt").innerText().isEqualToIgnoringWhitespace(interestReportDetails.getAgreedPeriodAmount().getRealValue());
        if(interestReportDetails.getAgreedWithholdingAmount() != null)
            assertThat("ivd.detail.agrwithholdamt").innerText().isEqualToIgnoringWhitespace(interestReportDetails.getAgreedWithholdingAmount().getRealValue());
        if(interestReportDetails.getAgreedNetInterestAmount() != null)
            assertThat("ivd.detail.agrnetinteramt").innerText().isEqualToIgnoringWhitespace(interestReportDetails.getAgreedNetInterestAmount().getRealValue());
        if(interestReportDetails.getAgreedWithholdingRate() != null)
            assertThat("ivd.detail.agrwithholdrate").innerText().isEqualToIgnoringWhitespace(interestReportDetails.getAgreedWithholdingRate().getRealValue());
        if(interestReportDetails.getDailyInterestDetails() != null && !interestReportDetails.getDailyInterestDetails().isEmpty()){
            for(DailyInterestDetail dailyInterestDetail : interestReportDetails.getDailyInterestDetails()){
                assertThat("ivd.daily.event.row", getDailyInterestDetailRow(dailyInterestDetail)).displayed().isTrue();
            }
        }
    }

    private String getDailyInterestDetailRow(DailyInterestDetail dailyInterestDetail) throws Exception{
        DecimalFormat format = new DecimalFormat();
        StringBuffer xpath = new StringBuffer();
        Method[] methods = dailyInterestDetail.getClass().getDeclaredMethods();
        for(Method method : methods) {
            if(method.getName().startsWith("get")
                    && method.invoke(dailyInterestDetail) != null
                    && method.getReturnType() == StringType.class) {
                String functionName, value;
                if(method.getName().contains("OpenBalance")
                        || method.getName().contains("CallRecall")
                        || method.getName().contains("CloseBalance")
                        || method.getName().contains("InterestAmount")
                        || method.getName().contains("AccumulatedOverPeriod")){
                    format.applyPattern("###,##0.00");
                    try {
                        value = format.format(Double.parseDouble(((StringType) method.invoke(dailyInterestDetail)).getRealValue()));
                    } catch (NumberFormatException nfe) {
                        value = ((StringType) method.invoke(dailyInterestDetail)).getRealValue();
                    }

                }else if(method.getName().contains("AgreementRate")){
                    format.applyPattern("###,##0.0000");
                    try {
                        value = format.format(Double.parseDouble(((StringType) method.invoke(dailyInterestDetail)).getRealValue()));
                    } catch (NumberFormatException nfe) {
                        value = ((StringType) method.invoke(dailyInterestDetail)).getRealValue();
                    }
                }else{
                    value = ((StringType) method.invoke(dailyInterestDetail)).getRealValue();
                }
                if(method.getName().contains("AccumulatedOverPeriod"))
                    functionName = "Accumulated over period";
                else
                    functionName = Biz.getColumnName(method.getName().substring(3), "(?<!^)(?=[A-Z])");
//                xpath.append("[td[count(//th[text()='" + functionName + "']/preceding-sibling::th)+1]='" + value + "']");
                xpath.append("[td[count(//th[text()='").append(functionName).append("']/preceding-sibling::th)+1]='").append(value).append("']");
            }
        }
        return xpath.toString();
    }
}
