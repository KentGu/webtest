package com.lombardrisk.pages.collateral;


import com.lombardrisk.pojo.Message;
import com.lombardrisk.pojo.SettlementStatusDetail;
import com.lombardrisk.pojo.StringType;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.locator.Locator;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;
import java.util.List;




/**
 * @author Kenny Wang
 */
public final class SettlementStatusPage extends PageBase {

    public SettlementStatusPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setNetFormat(SettlementStatusDetail ssd) throws Exception{
    	if (ssd.getNetFormat() != null){
    		element("settlementStatus.netFormat").selectByVisibleText(ssd.getNetFormat().getRealValue());
    	}
    }
    
    public void tickSettlementStatusSearchResult(SettlementStatusDetail ssd) throws Exception{
    	StringBuffer xpath = new StringBuffer("//tr");
//    	if (ssd.getAssetClass() != null){
//    		xpath.append("[td='"+ssd.getAssetClass().getRealValue()+"']");
//    	}
//    	if (ssd.getAssetType() != null){
//    		xpath.append("[td='"+ssd.getAssetType().getRealValue()+"']");
//    	}
    	//tr[td/font[contains(text(),'QTPPrincipal')]]//input[@id='_chk']

    	if (ssd.getPrincipal() != null){
    		xpath.append("[td='").append(ssd.getPrincipal().getRealValue()).append("']");
    	}
    	//tr[td/font[contains(text(),'qtp_sn_1453872878620')]]//input[@id='_chk']
    	if (ssd.getCounterparty() != null){
    		xpath.append("[td='").append(ssd.getCounterparty().getRealValue()).append("']");
    	}
    	//tr[td/font[contains(text(),'qtp_desc_1453872569557')]]//input[@id='_chk']
    	if (ssd.getAgreementDescription() != null){
    		xpath.append("[td='").append(ssd.getCounterparty().getRealValue()).append("']");
    	}
//    	if (ssd.getBusinessLine() != null){
//    		xpath.append("[td='"+ssd.getBusinessLine().getRealValue()+"']");
//    	}
//    	if (ssd.getInstrumentId() != null){
//    		xpath.append("[td='"+ssd.getInstrumentId().getRealValue()+"']");
//    	}
//    	if (ssd.getTicker() != null){
//    		xpath.append("[td='"+ssd.getTicker().getRealValue()+"']");
//    	}
//    	if (ssd.getDenomCcy() != null){
//    		xpath.append("[td='"+ssd.getDenomCcy().getRealValue()+"']");
//    	}
//    	if (ssd.getCreationDate() != null){
//    		xpath.append("[td='"+ssd.getCreationDate().getRealValue()+"']");
//    	}
//    	if (ssd.getSettlementDate() != null){
//    		xpath.append("[td='"+ssd.getSettlementDate().getRealValue()+"']");
//    	}
//    	if (ssd.getParAmount() != null){
//    		xpath.append("[td='"+ssd.getParAmount().getRealValue()+"']");
//    	}
//    	if (ssd.getCollateralValue() != null){
//    		xpath.append("[td='"+ssd.getCollateralValue().getRealValue()+"']");
//    	}
//    	if (ssd.getMarketValue() != null){
//    		xpath.append("[td='"+ssd.getMarketValue().getRealValue()+"']");
//    	}
//    	if (ssd.getMovement() != null){
//    		xpath.append("[td='"+ssd.getMovement().getRealValue()+"']");
//    	}
//    	if (ssd.getStatus() != null){
//    		xpath.append("[td='"+ssd.getStatus().getRealValue()+"']");
//    	}
//    	if (ssd.getCreator() != null){
//    		xpath.append("[td='"+ssd.getCreator().getRealValue()+"']");
//    	}
//    	if (ssd.getBookingSource() != null){
//    		xpath.append("[td='"+ssd.getBookingSource().getRealValue()+"']");
//    	}
//    	if (ssd.getRegion() != null){
//    		xpath.append("[td='"+ssd.getRegion().getRealValue()+"']");
//    	}
//    	if (ssd.getGroup() != null){
//    		xpath.append("[td='"+ssd.getGroup().getRealValue()+"']");
//    	}
    	element("settlementStatus.resultTick",xpath.toString()).click();
    }
    
    public void editSettlementStatusSearchResult(SettlementStatusDetail ssd ) throws Exception{
    	StringBuffer xpath = new StringBuffer("//tr");
//    	if (ssd.getAssetClass() != null){
//    		xpath.append("[td='"+ssd.getAssetClass().getRealValue()+"']");
//    	}
//    	if (ssd.getAssetType() != null){
//    		xpath.append("[td='"+ssd.getAssetType().getRealValue()+"']");
//    	}
    	//tr[td/font[contains(text(),'QTPPrincipal')]]//img[@title='edit']
    	if (ssd.getPrincipal() != null){
    		xpath.append("[td/font[contains(text(),'").append(ssd.getPrincipal().getRealValue()).append("')]]");
    	}
    	//tr[td/font[contains(text(),'QTPPrincipal')]][td/font[contains(text(),'QTPCounterparty')]]//img[@title='edit']
    	if (ssd.getCounterparty() != null){
    		xpath.append("[td/font[contains(text(),'").append(ssd.getCounterparty().getRealValue()).append("')]]");
    	}
    	//tr[td/font[contains(text(),'QTPPrincipal')]][td/font[contains(text(),'QTPCounterparty')]][td/font[contains(text(),'agreement32467-2')]]//img[@title='edit']
    	if (ssd.getAgreementDescription() != null){
    		xpath.append("[td[normalize-space(font)='").append(ssd.getAgreementDescription().getRealValue()).append("' or normalize-space(.)='").append(ssd.getAgreementDescription().getRealValue()).append("']]");
    	}
//    	if (ssd.getBusinessLine() != null){
//    		xpath.append("[td='"+ssd.getBusinessLine().getRealValue()+"']");
//    	}
//    	if (ssd.getInstrumentId() != null){
//    		xpath.append("[td='"+ssd.getInstrumentId().getRealValue()+"']");
//    	}
//    	if (ssd.getTicker() != null){
//    		xpath.append("[td='"+ssd.getTicker().getRealValue()+"']");
//    	}
//    	if (ssd.getDenomCcy() != null){
//    		xpath.append("[td='"+ssd.getDenomCcy().getRealValue()+"']");
//    	}
//    	if (ssd.getCreationDate() != null){
//    		xpath.append("[td='"+ssd.getCreationDate().getRealValue()+"']");
//    	}
//    	if (ssd.getSettlementDate() != null){
//    		xpath.append("[td='"+ssd.getSettlementDate().getRealValue()+"']");
//    	}
//    	if (ssd.getParAmount() != null){
//    		xpath.append("[td='"+ssd.getParAmount().getRealValue()+"']");
//    	}
//    	if (ssd.getCollateralValue() != null){
//    		xpath.append("[td='"+ssd.getCollateralValue().getRealValue()+"']");
//    	}
//    	if (ssd.getMarketValue() != null){
//    		xpath.append("[td='"+ssd.getMarketValue().getRealValue()+"']");
//    	}
//    	if (ssd.getMovement() != null){
//    		xpath.append("[td='"+ssd.getMovement().getRealValue()+"']");
//    	}
//    	if (ssd.getStatus() != null){
//    		xpath.append("[td='"+ssd.getStatus().getRealValue()+"']");
//    	}
//    	if (ssd.getCreator() != null){
//    		xpath.append("[td='"+ssd.getCreator().getRealValue()+"']");
//    	}
//    	if (ssd.getBookingSource() != null){
//    		xpath.append("[td='"+ssd.getBookingSource().getRealValue()+"']");
//    	}
//    	if (ssd.getRegion() != null){
//    		xpath.append("[td='"+ssd.getRegion().getRealValue()+"']");
//    	}
//    	if (ssd.getGroup() != null){
//    		xpath.append("[td='"+ssd.getGroup().getRealValue()+"']");
//    	}
    	element("settlementStatus.resultEdit",xpath.toString()).click();
    }
    
    public void tickSettlementStatusSearchResults(List<SettlementStatusDetail> list) throws Exception{
    	if (list != null && list.size() >0){
    		for(SettlementStatusDetail ssd : list){
    			tickSettlementStatusSearchResult(ssd);
    		}
    	}
    }
    
    public void tickAll() throws Exception{
    	element("settlementStatus.tickAll").click();
    }
    
    public void approveAllSystemDraft(List<Message> handles) throws Exception{
    	element("settlementStatus.approveAllSystemDraft").click();
    }
    
    public void approveAllPending() throws Exception{
    	element("settlementStatus.approveAllPending").click();
    }
    
    public void approveAllAuthorised() throws Exception{
    	element("settlementStatus.approveAllAuthorised").click();
    }
    
    public void approveAllPendingRelease() throws Exception{
    	element("settlementStatus.approveAllPendingRelease").click();
    }

    public void approveAllTicked() throws Exception{
    	element("settlementStatus.approveAllTicked").click();

    }

	public  void  assertNetTableInSettlementStatusDetail() throws Exception {
		assertThat("settlementStatus.net.table").present().isFalse();
	}

	public  void  assertPreNetTableInSettlementStatusDetail() throws Exception {
		assertThat("settlementStatus.prenet.table").present().isFalse();
	}


	public void assertSettlementStatusDetail(SettlementStatusDetail settlementStatusDetail, SettlementStatusDetail checkSettlementStatusDetail) throws Exception {
		if (settlementStatusDetail.getResultId() == null || (settlementStatusDetail.getResultId() != null && settlementStatusDetail.getResultId().getRealValue().isEmpty())){
			setSettlementStatusDetailId(settlementStatusDetail);
		}
		if (settlementStatusDetail.isIsRed() != null && settlementStatusDetail.isIsRed()){
			assertThat("settlementStatus.check.asset.type").cssValueOf("color").isEqualToIgnoringCase("rgb(255,0,0)");//#ff0000 red
		}
		if (checkSettlementStatusDetail.getAssetType() != null){
			assertThat("settlementStatus.check.asset.type",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getAssetType().getRealValue());
		}
		if (checkSettlementStatusDetail.getPrincipal() != null){
			assertThat("settlementStatus.check.principal",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getPrincipal().getRealValue());
		}
		if (checkSettlementStatusDetail.getCounterparty() != null){
			assertThat("settlementStatus.check.counterparty",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getCounterparty().getRealValue());
		}
		if (checkSettlementStatusDetail.getAgreementDescription() != null){
			assertThat("settlementStatus.check.agreement.description",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getAgreementDescription().getRealValue());
		}

		if (checkSettlementStatusDetail.getBusinessLine() != null){
			assertThat("settlementStatus.check.business.line",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getBusinessLine().getRealValue());
		}

		if (checkSettlementStatusDetail.getTicker() != null){
			assertThat("settlementStatus.check.ticker",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getTicker().getRealValue());
		}

		if (checkSettlementStatusDetail.getDenomCcy() != null){
			assertThat("settlementStatus.check.denom.ccy",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getDenomCcy().getRealValue());
		}

		if (checkSettlementStatusDetail.getResultId() != null){
			assertThat("settlementStatus.check.id",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getResultId().getRealValue());
		}

		if (checkSettlementStatusDetail.getCreationDate() != null){
			assertThat("settlementStatus.check.creation.date",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getCreationDate().getRealValue());
		}

		if (checkSettlementStatusDetail.getSettlementDate() != null){
			assertThat("settlementStatus.check.settlement.date",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getSettlementDate().getRealValue());
		}

		if (checkSettlementStatusDetail.getParAmount()!= null){
			assertThat("settlementStatus.check.par.amount",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getParAmount().getRealValue());
		}


		if (checkSettlementStatusDetail.getCollateralValue() != null){
			assertThat("settlementStatus.check.collateral.value",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getCollateralValue().getRealValue());
		}


		if (checkSettlementStatusDetail.getMarketValue() != null){
			assertThat("settlementStatus.check.market.value",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getMarketValue().getRealValue());
		}

		if (checkSettlementStatusDetail.getMovement() != null){
			assertThat("settlementStatus.check.movement",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getMovement().getRealValue());
		}
		if (checkSettlementStatusDetail.getStatus() != null){
			assertThat("settlementStatus.check.status",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getStatus().getRealValue());
		}


		if (checkSettlementStatusDetail.getCreator() != null){
			assertThat("settlementStatus.check.creator",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getCreator().getRealValue());
		}


		if (checkSettlementStatusDetail.getBookingSource() != null){
			assertThat("settlementStatus.check.booking.source",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getBookingSource().getRealValue());
		}

		if (checkSettlementStatusDetail.getRegion() != null){
			assertThat("settlementStatus.check.region",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getRegion().getRealValue());
		}
		if (checkSettlementStatusDetail.getGroup() != null){
			assertThat("settlementStatus.check.group",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getGroup().getRealValue());
		}
		if (checkSettlementStatusDetail.getEventCategory() != null){
			assertThat("settlementStatus.check.event.category",settlementStatusDetail.getResultId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
					checkSettlementStatusDetail.getEventCategory().getRealValue());
		}
	}

	public void assertApproveButton(String buttonValue, boolean isExisted) throws Exception{
		assertThat("settlementStatus.approveButton", buttonValue).displayed().isEqualTo(isExisted);
	}

	private void setSettlementStatusDetailId(SettlementStatusDetail settlementStatusDetail) throws Exception{
		StringBuffer attributes = new StringBuffer("//tr");
		Method[] methods = settlementStatusDetail.getClass().getDeclaredMethods();
		for (Method method : methods){
			if (method.getName().startsWith("get") && !method.getName().equals("getName") && !method.getName().equals("getAssetClass")&&
					method.invoke(settlementStatusDetail) != null && method.getReturnType() == StringType.class){
				StringType value = (StringType) method.invoke(settlementStatusDetail);
				if (!method.getName().equals("getAssetClass") && !method.getName().equals("getStatus")){
					attributes.append("[td/font[contains(text(),'").append(value.getRealValue()).append("')]]");
				}else{
					attributes.append("[td/div[contains(text(),'").append(value.getRealValue()).append("')]]");
				}
			}
		}
		Locator locator = locator("settlementStatus.id",attributes.toString());
		String id = element("settlementStatus.id",attributes.toString()).getInnerText().trim();
		if (element(locator).isDisplayed()){
			if (settlementStatusDetail.getResultId() == null){
				settlementStatusDetail.setResultId(new StringType("0"));
			}else if (settlementStatusDetail.getResultId().getRealValue().isEmpty()) {
				settlementStatusDetail.getResultId().setValue("0");
			}
			settlementStatusDetail.getResultId().setValue(id);
		}
	}

    public void handleAlters(List<Message> handles) {
		PageHelper.handleAlters(handles);
	}
}
