package com.lombardrisk.pages.marketdata;

import com.lombardrisk.pojo.FxRate;
import com.lombardrisk.pojo.FxRateSet;
import com.lombardrisk.pojo.RateType;
import com.lombardrisk.pojo.StringType;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;

public final class FXRatesPage extends PageBase {

    public FXRatesPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void addNewRateSet() throws Exception {
        element("md.rateset.add").click();
    }

    public void addFxRate(FxRate fxRate) throws Exception{
        element("md.fxrate.add").click();
        int row = element("md.fxrate.record.row").getNumberOfMatches();
        if (fxRate.getRiskCurrency() != null){
            element("md.fxrate.risk.ccy", String.valueOf(row - 1)).selectByVisibleText(fxRate.getRiskCurrency().getRealValue());
        }
        if (fxRate.getBid() != null){
            element("md.fxrate.bid", String.valueOf(row - 1)).input(fxRate.getBid().getRealValue());
        }
        if (fxRate.getOffer() != null){
            element("md.fxrate.offer", String.valueOf(row - 1)).input(fxRate.getOffer().getRealValue());
        }
        if (fxRate.getRateType() != null){
            element("md.fxrate.type", String.valueOf(row - 1)).selectByVisibleText(fxRate.getRateType().value());
        }
    }

    /**
     * add FX Rate
     *
     * @param fxRates
     */
    public void addFxRates(List<FxRate> fxRates) throws Exception {
        for (FxRate fxRate : fxRates) {
            addFxRate(fxRate);
        }
    }

    public void save() throws Exception{
        element("md.fxrate.save").click();
    }

    public void openFxRateSet(FxRateSet frs) throws Exception{
        if (frs.getFxRateSetName() != null){
            element("md.fxRateName",frs.getFxRateSetName().getRealValue()).click();
        }
    }

    public void editFxRateSetInfo(FxRateSet frs) throws Exception{
        openFxRateSet(frs);
        inputFxRateSetInfo(frs);
        editPermissions(frs);
    }

    public void editFxRate(FxRate orifxRate, FxRate newfxRate) throws Exception{
        String xpath = getXpath(orifxRate);
        if(newfxRate.getRiskCurrency() != null)
            element("md.fxrate.edit.risk.ccy", xpath).selectByVisibleText(newfxRate.getRiskCurrency().getRealValue());
        if(newfxRate.getBid() != null)
            element("md.fxrate.edit.bid", xpath).input(newfxRate.getBid().getRealValue());
        if(newfxRate.getOffer() != null)
            element("md.fxrate.edit.offer", xpath).input(newfxRate.getOffer().getRealValue());
        if(newfxRate.getRateType() != null)
            element("md.fxrate.edit.type", xpath).selectByVisibleText(newfxRate.getRateType().value());
    }

    /**
     * edit permissions
     *
     * @param rs
     */
    public void editPermissions(FxRateSet rs) throws Exception {
        if (rs.getFxUpdatePermission() != null && !rs.getFxUpdatePermission().isEmpty()) {
            element("md.permision.edit").click();
            element("md.rateset.permission").deselectAll();
            for (StringType permission : rs.getFxUpdatePermission()) {
                element("md.rateset.permission").selectByVisibleText(permission.getRealValue());
            }
            element("md.permision.save").click();
        }
    }

    /**
     * delete rate set
     */
    public void deleteRateSet(FxRateSet frs) throws Exception {
//        if (frs.isRemove() != null && frs.isRemove()) {
//            element("md.delete").click();
//        }
        element("md.delete").click();
        PageHelper.handleAlter(frs.getAlertHandling());
    }

    public void inputFxRateSetInfo(FxRateSet frs) throws Exception{
        if (frs.isOverRide() != null){
            element("md.override").check(frs.isOverRide());
        }
        if (frs.getDate() != null){
            element("md.date").input(frs.getDate().getRealValue());
        }
        if (frs.getTime() != null){
            element("md.time").input(frs.getTime().getRealValue());
        }
    }

    public void setNewFxRateSet(FxRateSet frs) throws Exception{
        openFxRateSet(frs);
        inputFxRateSetInfo(frs);
        if(frs.getFxRate() != null && !frs.getFxRate().isEmpty())
            addFxRates(frs.getFxRate());
    }

    private String getXpath(FxRate fxRate) throws Exception {
        StringBuffer xpath = new StringBuffer();
        Method[] methods = fxRate.getClass().getDeclaredMethods();

        for(Method method : methods){
            if(method.getReturnType() == StringType.class
                    && method.invoke(fxRate) != null){
                StringType v = (StringType) method.invoke(fxRate);
                StringBuilder value = new StringBuilder(v.getRealValue());
                if (method.getName().equals("getBid") || method.getName().equals("getOffer")) {
                    if(value.substring(value.toString().indexOf('.') + 1).length() < 6){
                        int i = 6 - value.substring(value.toString().indexOf('.') + 1).length();
                        while (i > 0){
                            value.append("0");
                            i--;
                        }
                    }
                }
                xpath.append("[td/input[@value='").append(value.toString()).append("']]");
            }else if(method.getReturnType() == RateType.class
            && method.invoke(fxRate) != null){
                RateType rateType = (RateType) method.invoke(fxRate);
                xpath.append("[td//option[contains(text(),'").append(rateType.value()).append("') and @selected='selected']");
            }
        }
        return xpath.toString();
    }

    public void assertFxRateSet(FxRateSet fxRateSet) throws Exception{
        openFxRateSet(fxRateSet);
        if(fxRateSet.getDate() != null)
            assertThat("md.date").attributeValueOf("value").isEqualToIgnoringWhitespace(fxRateSet.getDate().getRealValue());
        if(fxRateSet.getFxUpdatePermission() != null && !fxRateSet.getFxUpdatePermission().isEmpty()){
            StringBuffer strFxUpdatePermission = new StringBuffer();
            for(StringType stringType : fxRateSet.getFxUpdatePermission()){
//                strFxUpdatePermission.append(stringType.getRealValue() + ",");
                strFxUpdatePermission.append(stringType.getRealValue()).append(",");
            }
            strFxUpdatePermission.deleteCharAt(strFxUpdatePermission.length()-1);
            assertThat("md.update.permision").innerText().isEqualToIgnoringWhitespace("FX Update Permissions : " + strFxUpdatePermission.toString());
        }
        if(fxRateSet.getTime() != null)
            assertThat("md.time").attributeValueOf("value").isEqualToIgnoringWhitespace(fxRateSet.getTime().getRealValue());
        if(fxRateSet.getFxRate() != null && !fxRateSet.getFxRate().isEmpty()){
            for(FxRate fxRate : fxRateSet.getFxRate()){
                assertThat("md.fx.rate.row", getFxRateRow(fxRate)).displayed().isTrue();
            }
        }
    }

    private String getFxRateRow(FxRate fxRate) {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,##0.000000");
        StringBuffer xpath = new StringBuffer();
        if(fxRate.getBaseCurrency() != null) {
//            xpath.append("[td[1][text()[1]='" + fxRate.getBaseCurrency().getRealValue() + "']]");
            xpath.append("[td[1][text()[1]='").append(fxRate.getBaseCurrency().getRealValue()).append("']]");
        }
        if(fxRate.getRiskCurrency() != null) {
//            xpath.append("[td/select[contains(@name,'foreignCurrencyId')]/option[@selected='selected' and text()='" + fxRate.getRiskCurrency().getRealValue() + "']]");
            xpath.append("[td/select[contains(@name,'foreignCurrencyId')]/option[@selected='selected' and text()='").append(fxRate.getRiskCurrency().getRealValue()).append("']]");
        }
        if(fxRate.getBid() != null) {
            String strBid = format.format(Float.parseFloat(fxRate.getBid().getRealValue()));
//            xpath.append("[td/input[contains(@name,'bid') and @value='" + strBid + "']]");
            xpath.append("[td/input[contains(@name,'bid') and @value='").append(strBid).append("']]");
        }
        if(fxRate.getOffer() != null) {
            String strOffer = format.format(Float.parseFloat(fxRate.getOffer().getRealValue()));
//            xpath.append("[td/input[contains(@name,'offer') and @value='" + strOffer + "']]");
            xpath.append("[td/input[contains(@name,'offer') and @value='").append(strOffer).append("']]");
        }
        if(fxRate.getRateType() != null) {
//            xpath.append("[td/select[contains(@name,'type')]/option[@selected='selected' and text()='" + fxRate.getRateType().value() + "']]");
            xpath.append("[td/select[contains(@name,'type')]/option[@selected='selected' and text()='").append(fxRate.getRateType().value()).append("']]");
        }
        return xpath.toString();
    }
}
