package com.lombardrisk.pages.collateral;


import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import com.lombardrisk.pojo.StartupDashboard;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class StartupDashboardPage extends PageBase {
    public StartupDashboardPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void saveStartupDashboard() throws Exception{
    	element("db.save").click();
    }
    
    public void resetStartDashboard() throws Exception{
    	element("db.reset").click();
    }
    
    
    public void setStartupDashboard(StartupDashboard sdb) throws Exception{
    	if (sdb.getFeedData() != null){
    		if (sdb.getFeedData().getFxRates() != null){
    			if (sdb.getFeedData().getFxRates().isTickWhenCompleted() != null
    					&& sdb.getFeedData().getFxRates().isTickWhenCompleted()){
    				element("db.fxRates").check(sdb.getFeedData().getFxRates().isTickWhenCompleted());
    			}
    		}
    		if (sdb.getFeedData().getInterestRates() != null){
    			if (sdb.getFeedData().getInterestRates().isTickWhenCompleted() != null
    					&& sdb.getFeedData().getInterestRates().isTickWhenCompleted()){
    				element("db.interestRates").check(sdb.getFeedData().getInterestRates().isTickWhenCompleted());
    			}
    		}
    		if (sdb.getFeedData().getTradeDetails() != null){
    			if (sdb.getFeedData().getTradeDetails().isTickWhenCompleted() != null
    					&& sdb.getFeedData().getTradeDetails().isTickWhenCompleted()){
    				element("db.tradeDetails").check(sdb.getFeedData().getTradeDetails().isTickWhenCompleted());
    			}
    		}
    		if (sdb.getFeedData().getAssetPricesIfRequired() != null){
    			if (sdb.getFeedData().getAssetPricesIfRequired().isTickWhenCompleted() != null
    					&& sdb.getFeedData().getAssetPricesIfRequired().isTickWhenCompleted()){
    				element("db.assetPrices").check(sdb.getFeedData().getAssetPricesIfRequired().isTickWhenCompleted());
    			}
    		}
    	}
    	if (sdb.getHasTradeMarketDataLoadedSuccessfully() != null){
    		if (sdb.getHasTradeMarketDataLoadedSuccessfully().getCheckTaskManagerFeedStatusManagerForDetails() != null){
    			if (sdb.getHasTradeMarketDataLoadedSuccessfully().getCheckTaskManagerFeedStatusManagerForDetails().isTickWhenCompleted()
    					&& sdb.getHasTradeMarketDataLoadedSuccessfully().getCheckTaskManagerFeedStatusManagerForDetails().isTickWhenCompleted()){
    				element("db.checkTaskManager").check(sdb.getHasTradeMarketDataLoadedSuccessfully().getCheckTaskManagerFeedStatusManagerForDetails().isTickWhenCompleted());
    			}
    		}
    	}
    	if (sdb.getReviewCallsAndOtherActionsOfTheDay() != null){
    		if (sdb.getReviewCallsAndOtherActionsOfTheDay().getReviewTheDashboard() != null){
    			if (sdb.getReviewCallsAndOtherActionsOfTheDay().getReviewTheDashboard().isTickWhenCompleted() != null
    					&& sdb.getReviewCallsAndOtherActionsOfTheDay().getReviewTheDashboard().isTickWhenCompleted()){
    				element("db.reviewTheDashboard").check(sdb.getReviewCallsAndOtherActionsOfTheDay().getReviewTheDashboard().isTickWhenCompleted());
    			}
    		}
    		if (sdb.getReviewCallsAndOtherActionsOfTheDay().getApproveAllAgreementsAndStatementsForToday() != null){
    			if (sdb.getReviewCallsAndOtherActionsOfTheDay().getApproveAllAgreementsAndStatementsForToday().isTickWhenCompleted() != null
    					&& sdb.getReviewCallsAndOtherActionsOfTheDay().getApproveAllAgreementsAndStatementsForToday().isTickWhenCompleted()){
    				element("db.approveAllAgreementsAndStatements").check(sdb.getReviewCallsAndOtherActionsOfTheDay().getApproveAllAgreementsAndStatementsForToday().isTickWhenCompleted());
    			}
    		}
    		if (sdb.getReviewCallsAndOtherActionsOfTheDay().getListAllCallsForTheDay() != null){
    			if (sdb.getReviewCallsAndOtherActionsOfTheDay().getListAllCallsForTheDay().isTickWhenCompleted() != null
    					&& sdb.getReviewCallsAndOtherActionsOfTheDay().getListAllCallsForTheDay().isTickWhenCompleted()){
    				element("db.listAllCallsForTheDay").check(sdb.getReviewCallsAndOtherActionsOfTheDay().getListAllCallsForTheDay().isTickWhenCompleted());
    			}
    		}
    		if (sdb.getReviewCallsAndOtherActionsOfTheDay().getReviewTheValuesAndCallPositions() != null){
    			if (sdb.getReviewCallsAndOtherActionsOfTheDay().getReviewTheValuesAndCallPositions().isTickWhenCompleted() != null
    					&& sdb.getReviewCallsAndOtherActionsOfTheDay().getReviewTheValuesAndCallPositions().isTickWhenCompleted()){
    				element("db.reviewTheValuesAndCallPositions").check(sdb.getReviewCallsAndOtherActionsOfTheDay().getReviewTheValuesAndCallPositions().isTickWhenCompleted());
    			}
    		}
    		if (sdb.getReviewCallsAndOtherActionsOfTheDay().getRunTheDailyExposuresReportAndSaveResultsInTheRequiredOutputFormat() != null){
    			if (sdb.getReviewCallsAndOtherActionsOfTheDay().getRunTheDailyExposuresReportAndSaveResultsInTheRequiredOutputFormat().isTickWhenCompleted() != null
    					&& sdb.getReviewCallsAndOtherActionsOfTheDay().getRunTheDailyExposuresReportAndSaveResultsInTheRequiredOutputFormat().isTickWhenCompleted()){
    				element("db.runTheDailyExposureReport").check(sdb.getReviewCallsAndOtherActionsOfTheDay().getRunTheDailyExposuresReportAndSaveResultsInTheRequiredOutputFormat().isTickWhenCompleted());
    			}
    		}
    	}
    	if (sdb.getMakingAndReceivingCalls() != null){
    		if (sdb.getMakingAndReceivingCalls().getCallsAndRecallsGenerateOutgoingRequests() != null){
    			if (sdb.getMakingAndReceivingCalls().getCallsAndRecallsGenerateOutgoingRequests().isTickWhenCompleted() != null
    					&& sdb.getMakingAndReceivingCalls().getCallsAndRecallsGenerateOutgoingRequests().isTickWhenCompleted()){
    				element("db.callsAndRecalls").check(sdb.getMakingAndReceivingCalls().getCallsAndRecallsGenerateOutgoingRequests().isTickWhenCompleted());
    			}
    		}
    		if (sdb.getMakingAndReceivingCalls().getBookIncomingCollateralIntoTheStatement() != null){
    			if (sdb.getMakingAndReceivingCalls().getBookIncomingCollateralIntoTheStatement().isTickWhenCompleted() != null
    					&& sdb.getMakingAndReceivingCalls().getBookIncomingCollateralIntoTheStatement().isTickWhenCompleted()){
    				element("db.bookIncomingCollateral").check(sdb.getMakingAndReceivingCalls().getBookIncomingCollateralIntoTheStatement().isTickWhenCompleted());
    			}
    		}
    		if (sdb.getMakingAndReceivingCalls().getReturnsAndDeliveryRespondToIncomingRequests() != null){
    			if (sdb.getMakingAndReceivingCalls().getReturnsAndDeliveryRespondToIncomingRequests().isTickWhenCompleted() != null
    					&& sdb.getMakingAndReceivingCalls().getReturnsAndDeliveryRespondToIncomingRequests().isTickWhenCompleted()){
    				element("db.returnsAndDelivery").check(sdb.getMakingAndReceivingCalls().getReturnsAndDeliveryRespondToIncomingRequests().isTickWhenCompleted());
    			}
    		}
    	}
    	if (sdb.getProcessingTheCollateralBookings() != null){
    		if (sdb.getProcessingTheCollateralBookings().getOutstandingCollateralBookings() != null){
    			if (sdb.getProcessingTheCollateralBookings().getOutstandingCollateralBookings().isTickWhenCompleted() != null 
    					&& sdb.getProcessingTheCollateralBookings().getOutstandingCollateralBookings().isTickWhenCompleted()){
    				element("db.outstandingCollateralBookings").check(sdb.getProcessingTheCollateralBookings().getOutstandingCollateralBookings().isTickWhenCompleted());
    			}
    		}
    		if (sdb.getProcessingTheCollateralBookings().getClickTheWordCASHOrBONDSToSeeAllCollateralCashAndBondTypeEntriesInTheNextPage() != null){
    			if (sdb.getProcessingTheCollateralBookings().getClickTheWordCASHOrBONDSToSeeAllCollateralCashAndBondTypeEntriesInTheNextPage().isTickWhenCompleted() != null 
    					&& sdb.getProcessingTheCollateralBookings().getClickTheWordCASHOrBONDSToSeeAllCollateralCashAndBondTypeEntriesInTheNextPage().isTickWhenCompleted()){
    				element("db.clickTheWordCash").check(sdb.getProcessingTheCollateralBookings().getClickTheWordCASHOrBONDSToSeeAllCollateralCashAndBondTypeEntriesInTheNextPage().isTickWhenCompleted());
    			}
    		}
    		if (sdb.getProcessingTheCollateralBookings().getProcessCollateralStatus() != null){
    			if (sdb.getProcessingTheCollateralBookings().getProcessCollateralStatus().isTickWhenCompleted() != null 
    					&& sdb.getProcessingTheCollateralBookings().getProcessCollateralStatus().isTickWhenCompleted()){
    				element("db.processCollateralStatus").check(sdb.getProcessingTheCollateralBookings().getProcessCollateralStatus().isTickWhenCompleted());
    			}
    		}
    		if (sdb.getProcessingTheCollateralBookings().getByEndOfDayCollateralEntriesShouldReachTheFinalStatusOfPendingSettlement() != null){
    			if (sdb.getProcessingTheCollateralBookings().getByEndOfDayCollateralEntriesShouldReachTheFinalStatusOfPendingSettlement().isTickWhenCompleted() != null 
    					&& sdb.getProcessingTheCollateralBookings().getByEndOfDayCollateralEntriesShouldReachTheFinalStatusOfPendingSettlement().isTickWhenCompleted()){
    				element("db.byEndOfDay").check(sdb.getProcessingTheCollateralBookings().getByEndOfDayCollateralEntriesShouldReachTheFinalStatusOfPendingSettlement().isTickWhenCompleted());
    			}
    		}
    	}
    	if (sdb.getEndOfDayReporting() != null){
    		if (sdb.getEndOfDayReporting().getRunTheEndOfDayDailyExposuresReportAndSaveResultsInTheRequiredFormat() != null){
    			if (sdb.getEndOfDayReporting().getRunTheEndOfDayDailyExposuresReportAndSaveResultsInTheRequiredFormat().isTickWhenCompleted()
    					&& sdb.getEndOfDayReporting().getRunTheEndOfDayDailyExposuresReportAndSaveResultsInTheRequiredFormat().isTickWhenCompleted()){
    				element("db.runTheEndOfDay").check(sdb.getEndOfDayReporting().getRunTheEndOfDayDailyExposuresReportAndSaveResultsInTheRequiredFormat().isTickWhenCompleted());
    			}
    		}
    		if (sdb.getEndOfDayReporting().getRunTheAssetSettlementReportWithFilterSetToPendingSettlementAndPassToBackOfficeForProcessing() != null){
    			if (sdb.getEndOfDayReporting().getRunTheAssetSettlementReportWithFilterSetToPendingSettlementAndPassToBackOfficeForProcessing().isTickWhenCompleted()
    					&& sdb.getEndOfDayReporting().getRunTheAssetSettlementReportWithFilterSetToPendingSettlementAndPassToBackOfficeForProcessing().isTickWhenCompleted()){
    				element("db.runTheAssetSettlement").check(sdb.getEndOfDayReporting().getRunTheAssetSettlementReportWithFilterSetToPendingSettlementAndPassToBackOfficeForProcessing().isTickWhenCompleted());
    			}
    		}
    	}
    }

    
    
    
    
    
    
    
    
    
    
    
}
