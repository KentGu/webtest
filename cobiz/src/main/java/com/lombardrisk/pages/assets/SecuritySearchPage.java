package com.lombardrisk.pages.assets;

import com.lombardrisk.pojo.*;
import org.assertj.core.condition.Not;
import org.yiwan.webcore.test.TestCaseManager;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;
import java.text.DecimalFormat;

public final class SecuritySearchPage extends PageBase {

    public SecuritySearchPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void clear() throws Exception{
    	element("ss.instr.clear").click();
    }

    public void newInstrument(Instrument instr) throws Exception {
        element("ss.asset.class.new").selectByVisibleText(instr.getAssetClass().getRealValue());
        element("ss.instrument.new").click();
    }
    /**
     * @param search
     */
    public void searchInstrument(SecuritySearch search) throws Exception {
        if (search.getInstrumentId() != null){
            if (search.getInstrumentId().getFilter() != null)
                element("ss.instrument.filter").input(search.getInstrumentId().getFilter().getRealValue());
        }else if (search.getInstrument() != null) {
            if (search.getInstrument().getFilter() != null)
                element("ss.instrument.filter").input(search.getInstrument().getFilter().getRealValue());
        }
        if(search.getAssetClassification() !=null)
            element("ss.page.assetClassificaiton").selectByVisibleText(search.getAssetClassification().getRealValue());
        if(search.getAssetCategory() !=null)
            element("ss.page.assetCategory").selectByVisibleText(search.getAssetCategory().getRealValue());
        if(search.getAssetType() !=null)
            element("ss.asset.type").selectByVisibleText(search.getAssetType().getRealValue());
        if (search.getDescription() != null)
            if (search.getDescription().getFilter() != null)
                element("ss.desc.filter").input(search.getDescription().getFilter().getRealValue());
        if (search.getMatured() != null)
            element("ss.matured").selectByVisibleText(search.getMatured().value());
        if (search.getLock() != null)
            element("ss.selectLock.filter").selectByVisibleText(search.getLock().value());
        element("ss.search").click();
    }

    /**
     * click edit button enter Assets - Securities Data- Security Editor page
     *
     * @param result
     */
    public void editInstrument(SecuritySearchResult result) throws Exception {
    	if (result.getInstrument() != null){
//    	    if(element("ss.search.result", result.getInstrument().getRealValue()).getNumberOfMatches() > 1) {
//    	        List<IWebDriverWrapper.IWebElementWrapper> list = element("ss.search.result", result.getInstrument().getRealValue()).getAllMatchedElements();
//                for(int i=0; i<list.size(); i++){
//                    IWebDriverWrapper.IWebElementWrapper e = list.get(i);
//                    if(e.getInnerText().equals(result.getInstrument().getRealValue() + " ")){
//                        element("ss.search.result.edit", result.getInstrument().getRealValue()).getAllMatchedElements().get(i).clickByJavaScript();
//                        break;
//                    }else{
//                        String v = e.getInnerText();
//                        logger.debug(v);
//                    }
//                }
//            }else {
//                element("ss.search.result.edit", result.getInstrument().getRealValue()).clickByJavaScript();
//            }
            element("ss.search.result.edit", getInstrumentRow(result)).clickByJavaScript();
    	}
    }

    private String getInstrumentRow(SecuritySearchResult securitySearchResult) throws Exception {
        StringBuffer xpath = new StringBuffer();
        Method[] methods = securitySearchResult.getClass().getDeclaredMethods();
        for (Method method : methods){
            if ((method.getReturnType() == StringType.class
                    || method.getReturnType() == Boolean.class
                    || method.getReturnType() == boolean.class)
                    && method.getParameterTypes().length == 0
                    && method.invoke(securitySearchResult) != null){
                    if(method.getName().equals("isUse")){
                        if(!(boolean) method.invoke(securitySearchResult)){
                            xpath.append("[td/a/img[@title='Use Template']]");
                        }else{
                            xpath.append("[not(td/a/img[@title='Use Template'])]");
                        }
                    }else if(method.getName().equals("getInstrument")){
//                        xpath.append("[td[contains(font,'" + ((StringType)method.invoke(securitySearchResult)).getRealValue() +"') and string-length(font)=string-length('" + ((StringType)method.invoke(securitySearchResult)).getRealValue() + "')+1]]");
                        xpath.append("[td[contains(font,'").append(((StringType)method.invoke(securitySearchResult)).getRealValue())
                                .append("') and string-length(font)=string-length('").append(((StringType)method.invoke(securitySearchResult)).getRealValue()).append("')+1]]");
                    }else if(!method.getName().equals("getPrice")
                            && !method.getName().equals("getPriceDate")
                            && !method.getName().equals("getLastPriceChange")
                            && !method.getName().equals("getMaturityDate")
                            && !method.getName().equals("getStatus")){
                        if (method.getName().equals("getCounterparty")){

                        }else {
//                            xpath.append("[td[normalize-space(.)='" + ((StringType) method.invoke(securitySearchResult)).getRealValue() + "']]");
                            xpath.append("[td[normalize-space(.)='").append(((StringType) method.invoke(securitySearchResult)).getRealValue()).append("']]");
                        }
                    }
                }
        }
        return xpath.toString();
    }

    /**
     * @return export file name
     */
    public void exportCsv(StringType v) throws Exception {
        TestCaseManager.getTestCase().startTransaction("");
        TestCaseManager.getTestCase().setPrepareToDownload(true);
        element("ss.export.csv").click();
        TestCaseManager.getTestCase().stopTransaction();
        v.setValue(TestCaseManager.getTestCase().getDownloadFile());
    }

    /**
     * @return export file name
     */
    public void exportExcel(StringType v) throws Exception {
        TestCaseManager.getTestCase().startTransaction("");
        TestCaseManager.getTestCase().setPrepareToDownload(true);
        element("ss.export.excel").click();
        TestCaseManager.getTestCase().stopTransaction();
        v.setValue(TestCaseManager.getTestCase().getDownloadFile());
    }

    /**
     * @return export file name
     */
    public void exportXml(StringType v) throws Exception {
        TestCaseManager.getTestCase().startTransaction("");
        TestCaseManager.getTestCase().setPrepareToDownload(true);
        element("ss.export.xml").click();
        TestCaseManager.getTestCase().stopTransaction();
        v.setValue(TestCaseManager.getTestCase().getDownloadFile());
    }

    /**
     * @return export file name
     */
    public void exportPdf(StringType v) throws Exception {
        TestCaseManager.getTestCase().startTransaction("");
        TestCaseManager.getTestCase().setPrepareToDownload(true);
        element("ss.export.pdf").click();
        TestCaseManager.getTestCase().stopTransaction();
        v.setValue(TestCaseManager.getTestCase().getDownloadFile());
    }

    /**
     * click long_view button to view more information
     */
    public void longView() throws Exception {
        element("ss.long.view").click();
    }

    public void assertSecuritySearchResult(SecuritySearchResult securitySearchResult) throws Exception {
        StringBuffer xpath = new StringBuffer();
        Method[] methods = securitySearchResult.getClass().getDeclaredMethods();
        for (Method method : methods){
            if (method.getReturnType().equals(StringType.class) && method.invoke(securitySearchResult) != null){
                StringType stringType = (StringType) method.invoke(securitySearchResult);
                switch (method.getName()) {
                    case "getInstrument":
//                    xpath.append("[starts-with(td/font,'" + stringType.getRealValue() + "') and string-length(td/font)=string-length('" + stringType.getRealValue() + "')+1]");
                        xpath.append("[starts-with(td/font,'").append(stringType.getRealValue()).append("') and string-length(td/font)=string-length('").append(stringType.getRealValue()).append("')+1]");
                        break;
                    case "getPrice":
                        DecimalFormat format = new DecimalFormat();
                        format.applyPattern("0.0000000");
//                    xpath.append("[td[normalize-space(.)='" + format.format(Double.parseDouble(stringType.getRealValue())) + "']]");
                        xpath.append("[td[normalize-space(.)='").append(format.format(Double.parseDouble(stringType.getRealValue()))).append("']]");
                        break;
                    default:
//                    xpath.append("[td[normalize-space(.)='" + stringType.getRealValue() + "']]");
                        xpath.append("[td[normalize-space(.)='").append(stringType.getRealValue()).append("']]");
                        break;
                }
            }
        }

        if (securitySearchResult.isApply() != null && !securitySearchResult.isApply()) {
            assertThat("ss.result.assert", xpath.toString()).displayed().isFalse();

        } else {
            assertThat("ss.result.assert", xpath.toString()).displayed().isTrue();

        }


//        if (securitySearchResult.getAssetClass() != null){
//            assertThat("ss.result.asset.class").innerText().isEqualToIgnoringWhitespace(securitySearchResult.getAssetClass().getRealValue());
//        }
//        if (securitySearchResult.getAssetType() != null){
//            assertThat("ss.result.asset.type").innerText().isEqualToIgnoringWhitespace(securitySearchResult.getAssetType().getRealValue());
//        }
//        if (securitySearchResult.getMarket() != null){
//            assertThat("ss.result.market").innerText().isEqualToIgnoringWhitespace(securitySearchResult.getMarket().getRealValue());
//        }
//        if (securitySearchResult.getInstrument() != null){
//            assertThat("ss.result.instrument").innerText().isEqualToIgnoringWhitespace(securitySearchResult.getInstrument().getRealValue());
//        }
//        if (securitySearchResult.getCurrency() != null){
//            assertThat("ss.result.currency").innerText().isEqualToIgnoringWhitespace(securitySearchResult.getCurrency().getRealValue());
//        }
//        if (securitySearchResult.getPrice() != null){
//            assertThat("ss.result.price").innerText().isEqualToIgnoringWhitespace(securitySearchResult.getPrice().getRealValue());
//        }
//        if (securitySearchResult.getPriceDate() != null){
//            assertThat("ss.result.price.date").innerText().isEqualToIgnoringWhitespace(securitySearchResult.getPriceDate().getRealValue());
//        }
//        if (securitySearchResult.getLastPriceChange() != null){
//            assertThat("ss.result.last.price.change").innerText().isEqualToIgnoringWhitespace(securitySearchResult.getLastPriceChange().getRealValue());
//        }
//        if (securitySearchResult.getMaturityDate() != null){
//            assertThat("ss.result.maturity.date").innerText().isEqualToIgnoringWhitespace(securitySearchResult.getMaturityDate().getRealValue());
//        }
//        if (securitySearchResult.getIssuer() != null){
//            assertThat("ss.result.issuer").innerText().isEqualToIgnoringWhitespace(securitySearchResult.getIssuer().getRealValue());
//        }
//        if (securitySearchResult.getDescription() != null){
//            assertThat("ss.result.description").innerText().isEqualToIgnoringWhitespace(securitySearchResult.getDescription().getRealValue());
//        }
//        if (securitySearchResult.getStatus() != null){
//            assertThat("ss.result.status").innerText().isEqualToIgnoringWhitespace(securitySearchResult.getStatus().getRealValue());
//        }
//        if (securitySearchResult.getUser() != null){
//            assertThat("ss.result.use").innerText().isEqualToIgnoringWhitespace(securitySearchResult.getUser().getRealValue());
//        }
    }
    public void assertSecuritySearch(SecuritySearch Search) throws Exception {

        if (Search.isApply() != null && !Search.isApply()) {
                assertThat("ss.result.instrument").displayed().isTrue();
        } else {
            assertThat("ss.result.instrument").displayed().isFalse();
        }
    }


    public boolean isInstrumentExist(SecuritySearchResult securitySearchResult) throws Exception {
        return element("ss.search.result.edit", securitySearchResult.getInstrument().getRealValue()).isDisplayed();
    }
}
