package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.SettlementStatusSummary;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;




/**
 * @author Kenny Wang
 */
public final class SettlementStatusSummaryPage extends PageBase {

    public SettlementStatusSummaryPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }


    public void returnToFilter() throws Exception{
    	element("settlementStatusSummary.returnToFliter").click();
    }
    
    public void enterExternalExposuresPage() throws Exception{
    	element("settlementStatusSummary.returnToEE").click();
    }
    
    public void enterExposureManagermentPage() throws Exception{
    	element("settlementStatusSummary.returnToEM").click();
    }
    
    public void enterInternalReviewsPage() throws Exception{
    	element("settlementStatusSummary.returnToIR").click();
    }
    
    public void back() throws Exception{
    	element("settlementStatusSummary.back").click();
    }
    	
    public void enterAssetHoldingDetailPage(SettlementStatusSummary settlementStatusSummary) throws Exception{
    	if (settlementStatusSummary.getAssetClass() != null){
            element("settlementStatusSummary.assetClassName",settlementStatusSummary.getAssetClass().getRealValue()).click();
    	}
        if (settlementStatusSummary.getAssetType() != null){
            element("settlementStatusSummary.assetTypeName",settlementStatusSummary.getAssetType().getRealValue()).click();
        }
    }
    
    public void approveAllSystemDraft() throws Exception{
    	element("settlementStatusSummary.approveAllSystemDraft").click();
        if(alert().isPresent())
            alert().accept();
        waitThat().jQuery().toBeInactive();
        waitThat().document().toBeReady();
    }
    
    public void approveAllPending() throws Exception{
    	element("settlementStatusSummary.approveAllPending").click();
        if(alert().isPresent())
            alert().accept();
        waitThat().jQuery().toBeInactive();
        waitThat().document().toBeReady();
    }
    
    public void approveAllAuthorised() throws Exception{
    	element("settlementStatusSummary.approveAllAuthorised").click();
        if(alert().isPresent())
            alert().accept();
        waitThat().jQuery().toBeInactive();
        waitThat().document().toBeReady();
    }
    
    public void approveAllPendingRelease() throws Exception{
    	element("settlementStatusSummary.approveAllPendingRelease").click();
        if(alert().isPresent())
            alert().accept();
        waitThat().jQuery().toBeInactive();
        waitThat().document().toBeReady();
    }

    public void assertApproveButton(String buttonValue, boolean isExisted) throws Exception{
        assertThat("settlementStatusSummary.approveButton", buttonValue).displayed().isEqualTo(isExisted);
    }

    public void assertSettlementStatusSummary(SettlementStatusSummary settlementStatusSummary) throws Exception {

        if (settlementStatusSummary.getAssetType() != null){
            if (settlementStatusSummary.isReceived() != null)
                assertThat("settlementStatusSummary.received","td/a='"+settlementStatusSummary.getAssetType().getRealValue()+"'").displayed().isEqualTo(settlementStatusSummary.isReceived());

            if (settlementStatusSummary.isDelivery() != null)
                assertThat("settlementStatusSummary.delivery","td/a='"+settlementStatusSummary.getAssetType().getRealValue()+"'").displayed().isEqualTo(settlementStatusSummary.isDelivery());

            if (settlementStatusSummary.isSystemDraft() != null)
                assertThat("settlementStatusSummary.system.draft","td/a='"+settlementStatusSummary.getAssetType().getRealValue()+"'").displayed().isEqualTo(settlementStatusSummary.isSystemDraft());

            if (settlementStatusSummary.isPending() != null)
                assertThat("settlementStatusSummary.pending","td/a='"+settlementStatusSummary.getAssetType().getRealValue()+"'").displayed().isEqualTo(settlementStatusSummary.isPending());

            if (settlementStatusSummary.isQuery() != null)
                assertThat("settlementStatusSummary.query","td/a='"+settlementStatusSummary.getAssetType().getRealValue()+"'").displayed().isEqualTo(settlementStatusSummary.isQuery());

            if (settlementStatusSummary.isAuthorised() != null)
                assertThat("settlementStatusSummary.authorised","td/a='"+settlementStatusSummary.getAssetType().getRealValue()+"'").displayed().isEqualTo(settlementStatusSummary.isAuthorised());

            if (settlementStatusSummary.isPendingSettlement() != null)
                assertThat("settlementStatusSummary.pending.settlement","td/a='"+settlementStatusSummary.getAssetType().getRealValue()+"'").displayed().isEqualTo(settlementStatusSummary.isPendingSettlement());

            if (settlementStatusSummary.isOutstandingSettlement() != null)
                assertThat("settlementStatusSummary.outstanding.settlement","td/a='"+settlementStatusSummary.getAssetType().getRealValue()+"'").displayed().isEqualTo(settlementStatusSummary.isOutstandingSettlement());

            if (settlementStatusSummary.isCancelledOrFailed() != null)
                assertThat("settlementStatusSummary.cancelled.failed","td/a='"+settlementStatusSummary.getAssetType().getRealValue()+"'").displayed().isEqualTo(settlementStatusSummary.isCancelledOrFailed());
        }
    }
}
