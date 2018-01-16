package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.*;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class RequestTypeAndAssetSelectionPage extends PageBase {
    public RequestTypeAndAssetSelectionPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    
    
    public void enterNext() throws Exception{
    	element("ls.next").clickByJavaScript();
    }

    public void enterLast() throws Exception{
        element("ls.last").click();
    }

    public void enterNext(Message handle) throws Exception{
        element("ls.next").click();
        if(handle != null)
            PageHelper.handleAlter(handle);
        waitThat().document().toBeReady();
    }

    public void bookAll(EligibleAssetSelection eligibleAssetSelection) throws Exception{
        if(eligibleAssetSelection.isBookAll() != null && eligibleAssetSelection.isBookAll())
            element("rtas.book.all").clickByJavaScript();
    }

    public void enterNextStage() throws Exception{
        while(element("rtas.title").isDisplayed()){
            alert().disable();
            enterNext();
            alert().enable();
        }
    }

    public void goToCorrectPage(EligibleAssetSelection eligibleAssetSelection) throws Exception{
        alert().disable();
        if(element("letterSummary.title").isDisplayed())
            element("letterSummary.edit").click();
        if(element("ls.title").isDisplayed())
            enterNext();
        if(eligibleAssetSelection.getMovementType() != null) {
            if (!element("rtas.move.type").getInnerText().trim().equalsIgnoreCase(eligibleAssetSelection.getMovementType().getRealValue() + ":")) {
                System.out.println(element("rtas.move.type").getInnerText());
                enterNext();
                goToCorrectPage(eligibleAssetSelection);
            }
        }
        alert().enable();
    }
    
    public void enterLetterInstrumentSearchPage() throws Exception{
    	element("rtas.instrumentSearch").click();
    }

    public void editEligibleAsset(EligibleAsset eligibleAsset) throws Exception{
        element("rtas.edit", getEligibleAssetRow(eligibleAsset)).clickByJavaScript();
    }
    
    public void assertRequestTypeAndAssetSelectionPage(RequestTypeAndAssetSelection requestTypeAndAssetSelection) throws Exception{
        if(requestTypeAndAssetSelection.getPrincipal() != null)
            assertThat("rtas.principal").innerText().isEqualToIgnoringWhitespace(requestTypeAndAssetSelection.getPrincipal().getRealValue());
        if(requestTypeAndAssetSelection.getCounterparty() != null)
            assertThat("rtas.counterparty").innerText().isEqualToIgnoringWhitespace(requestTypeAndAssetSelection.getCounterparty().getRealValue());
        if(requestTypeAndAssetSelection.getDescription() != null)
            assertThat("rtas.description").innerText().isEqualToIgnoringWhitespace(requestTypeAndAssetSelection.getDescription().getRealValue());
        if(requestTypeAndAssetSelection.getRequestType() != null)
            assertThat("rtas.requesttype").innerText().isEqualToIgnoringWhitespace(requestTypeAndAssetSelection.getRequestType().getRealValue());
        if(requestTypeAndAssetSelection.getCounterpartyExposureAmount() != null)
            assertThat("rtas.cptyexpoamt").innerText().isEqualToIgnoringWhitespace(requestTypeAndAssetSelection.getCounterpartyExposureAmount().getRealValue());
        if(requestTypeAndAssetSelection.getAgreedAmount() != null)
            assertThat("rtas.agramt").innerText().isEqualToIgnoringWhitespace(requestTypeAndAssetSelection.getAgreedAmount().getRealValue());
        if(requestTypeAndAssetSelection.getTotalAmountBooked() != null)
            assertThat("rtas.tolamtbooked").innerText().isEqualToIgnoringWhitespace(requestTypeAndAssetSelection.getTotalAmountBooked().getRealValue());
  	}

  	public void assertEligibleAsset(MovementAsset movementAsset) throws Exception {
  	    if (movementAsset.getEligibleAsset() != null)
            assertThat("rtas.edit", getEligibleAssetRow(movementAsset.getEligibleAsset())).displayed().isTrue();
    }

  	private String getEligibleAssetRow(EligibleAsset eligibleAsset) throws Exception{
  	    StringBuffer xpath = new StringBuffer();
        Method[] methods = eligibleAsset.getClass().getDeclaredMethods();
        for(Method method : methods){
            if(method.getParameterTypes().length == 0
                    && method.invoke(eligibleAsset) != null
                    && method.getReturnType() == StringType.class
                    && !method.getName().equals("getAssetClassName")){
                StringType stringType = (StringType) method.invoke(eligibleAsset);
                if(method.getName().equals("getAssetTypeName")){
//                    xpath.append("[normalize-space(td/text()[2])='" + stringType.getRealValue() + "']");
                    xpath.append("[normalize-space(td/text()[2])='").append(stringType.getRealValue()).append("']");
                }else if(method.getName().contains("Amount")
                        || method.getName().contains("Value")
                        || method.getName().contains("Valuation")){
//                    xpath.append("[td[contains(.,'" + stringType.getRealValue() +"') and string-length(normalize-space(.))=string-length('" + stringType.getRealValue() + "')+1]]");
//                    xpath.append("[normalize-space(td)='" + stringType.getRealValue() + "']");
                }else{
//                    xpath.append("[td='" + stringType.getRealValue() + "']");
                    xpath.append("[td='").append(stringType.getRealValue()).append("']");
                }
            }
        }
        return xpath.toString();
    }

    public void searchInstrument(MovementAsset movementAsset) throws Exception{
        if(movementAsset.getInstrumentSelectionSearch() != null){
            InstrumentSelectionSearch instrumentSelectionSearch = movementAsset.getInstrumentSelectionSearch();
            if(instrumentSelectionSearch.getInstrumentId() != null){
                if(instrumentSelectionSearch.getInstrumentId().getFilter() != null)
                    element("rtas.instrumentid.search").input(instrumentSelectionSearch.getInstrumentId().getFilter().getRealValue());
                if(instrumentSelectionSearch.getInstrumentId().getLinkText() != null)
                    element("rtas.link.text", instrumentSelectionSearch.getInstrumentId().getLinkText().getRealValue()).click();
            }
            if(instrumentSelectionSearch.getAssetClass() != null){
                element("rtas.assetclass.search").input(instrumentSelectionSearch.getAssetClass().getRealValue());
                element("rtas.link.text", instrumentSelectionSearch.getAssetClass().getRealValue()).click();
            }
            if(instrumentSelectionSearch.getAssetType() != null){
                element("rtas.assettype.search").input(instrumentSelectionSearch.getAssetType().getRealValue());
                element("rtas.link.text", instrumentSelectionSearch.getAssetType().getRealValue()).click();
            }
            element("rtas.search.instrument").click();
        }
    }

    public void selectInstrument(MovementAsset movementAsset) throws Exception{
        if(movementAsset.getInstrumentSelectionSearchResult() != null){
            InstrumentSelectionSearchResult instrumentSelectionSearchResult = movementAsset.getInstrumentSelectionSearchResult();
            element("rtas.instrument.frame").switchTo();
            element("rtas.instrument.check", getInstrumentResultRow(instrumentSelectionSearchResult)).check(true);
            switchTo().defaultContent();
            element("rtas.instrument.add").click();
        }
    }

    private String getInstrumentResultRow(InstrumentSelectionSearchResult instrumentSelectionSearchResult) throws Exception{
        StringBuffer xpath = new StringBuffer();
        Method[] methods = instrumentSelectionSearchResult.getClass().getDeclaredMethods();
        for(Method method : methods){
            if(method.getName().startsWith("get")
                    && method.invoke(instrumentSelectionSearchResult) != null
                    && method.getReturnType() == StringType.class){
                StringType stringType = (StringType) method.invoke(instrumentSelectionSearchResult);
                if(!method.getName().equals("getValuation")) {
//                    xpath.append("[td='" + stringType.getRealValue() + "']");
                    xpath.append("[td='").append(stringType.getRealValue()).append("']");
                }
            }
        }
        return xpath.toString();
    }
}
