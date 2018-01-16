package com.lombardrisk.pages.collateral;

import com.lombardrisk.pages.AbstractCollinePage;
import com.lombardrisk.pojo.Dashboard;
import com.lombardrisk.pojo.StringType;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class DashboardPage extends AbstractCollinePage {
    public DashboardPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void show(Dashboard dashboard) throws Exception {
        if (dashboard.getExternalExposuresInformation() != null){
            if (dashboard.getExternalExposuresInformation().getCallsAndRecalls() != null){
                if (dashboard.getExternalExposuresInformation().getCallsAndRecalls().getEventType() != null){
                    element("dashboard.type.link",dashboard.getExternalExposuresInformation().getCallsAndRecalls().getEventType().getRealValue()).click();
                }
            }
            if (dashboard.getExternalExposuresInformation().getDeliveriesAndReturns() != null){
                if (dashboard.getExternalExposuresInformation().getDeliveriesAndReturns().getEventType() != null){
                    element("dashboard.type.link",dashboard.getExternalExposuresInformation().getDeliveriesAndReturns().getEventType().getRealValue()).click();
                }
            }
            if (dashboard.getExternalExposuresInformation().getNoCalls() != null){
                if (dashboard.getExternalExposuresInformation().getNoCalls().getEventType() != null){
                    element("dashboard.type.link",dashboard.getExternalExposuresInformation().getNoCalls().getEventType().getRealValue()).click();
                }
            }
            if (dashboard.getExternalExposuresInformation().getSubstitutions() != null){
                if (dashboard.getExternalExposuresInformation().getSubstitutions().getEventType() != null){
                    element("dashboard.type.link",dashboard.getExternalExposuresInformation().getSubstitutions().getEventType().getRealValue()).click();
                }
            }
        }

        if (dashboard.getInternalReviews() != null){
            if (dashboard.getInternalReviews().getEventType() != null){
                element("dashboard.type.link",dashboard.getInternalReviews().getEventType().getRealValue()).click();
            }
        }
        if (dashboard.getAgreementActions() != null){
            if (dashboard.getAgreementActions().getEventType() != null){
                element("dashboard.type.link",dashboard.getAgreementActions().getEventType().getRealValue()).click();
            }
        }
        if (dashboard.getCollateralSettlementSummary() != null){
            if (dashboard.getCollateralSettlementSummary().getAssetName() == null){
                if (dashboard.getCollateralSettlementSummary().getStatus() != null){
                    element("dashboard.type.link",dashboard.getCollateralSettlementSummary().getStatus().getRealValue()).click();
                }
            }else {
                element("dashboard.type.link",dashboard.getCollateralSettlementSummary().getAssetName().getRealValue()).click();
            }
        }
        if (dashboard.getInterestSummary() != null){
            if (dashboard.getInterestSummary().getInterestStatementsForLastMonth() != null){
                if (dashboard.getInterestSummary().getInterestStatementsForLastMonth().getEventType() != null){
                    element("",dashboard.getInterestSummary().getInterestStatementsForLastMonth().getEventType().getRealValue()).click();
                }
            }
            if (dashboard.getInterestSummary().getOutstandingMovementsUpToToday() != null){
                if (dashboard.getInterestSummary().getOutstandingMovementsUpToToday().getInterestMovement() == null){
                    if (dashboard.getInterestSummary().getOutstandingMovementsUpToToday().getStatus() != null){
                        element("dashboard.type.link",dashboard.getInterestSummary().getOutstandingMovementsUpToToday().getStatus().getRealValue()).click();
                    }else {
                        element("dashboard.type.link",dashboard.getInterestSummary().getOutstandingMovementsUpToToday().getInterestMovement().getRealValue()).click();
                    }
                }
            }
            if (dashboard.getApprovalsManagement() != null){
                if (dashboard.getApprovalsManagement().getEventType() != null){
                    element("dashboard.type.link",dashboard.getApprovalsManagement().getEventType().getRealValue()).click();
                }
            }
            if (dashboard.getTodayEvents() != null){
                if (dashboard.getTodayEvents().getEventType() != null){
                    element("dashboard.type.link",dashboard.getTodayEvents().getEventType().getRealValue()).click();
                }
            }
        }
    }

    public void expandCallAndRecallsForDetail() throws Exception {
        element("").click();
    }

    public void collapseCallAndRecallsForDetail() throws Exception {
        element("").click();
    }

    public void expandDeliveriesAndReturnsForDetail() throws Exception {
        element("").click();
    }

    public void collapaseDeliveriesAndReturnsForDetail() throws Exception {
        element("").click();
    }

    public void expandNoCallsForDetail() throws Exception {
        element("").click();
    }

    public void collapseNoCallsForDetail() throws Exception {
        element("").click();
    }

    public void expandSubstitutions() throws Exception {
        element("").click();
    }

    public void collapseSubstitutions() throws Exception {
        element("").click();
    }

    public void expandOutstandingEntriesUpToTodayForDetail() throws Exception {
        element("dashboard.expandOrCollapse").clickSmartly();
    }

    public void collapseOutstandingEntriesUpToTodayForDetail() throws Exception {
        element("dashboard.expandOrCollapse").clickSmartly();
    }

    public void expandOutstandingMovementsUpToTodayForDetail() throws Exception {
        element("").click();
    }

    public void collapseOutstandingMovementsUpToTodayForDetail() throws Exception {
        element("").click();
    }

    public void editDashboard(Dashboard dashboard) throws Exception {
        editRegion(dashboard);
        editGroup(dashboard);
        editLinkageSet(dashboard);
        editBusinessLine(dashboard);
    }

    public void editRegion(Dashboard dashboard) throws Exception {
        if (dashboard.getRegion() != null && dashboard.getRegion().size() > 0){
            for (StringType stringType : dashboard.getRegion()){
                element("dashboard.region").selectByVisibleText(stringType.getRealValue());
            }
        }
    }

    public void editGroup(Dashboard dashboard) throws Exception {
        if (dashboard.getGroup() != null && dashboard.getGroup().size() > 0){
            for (StringType stringType : dashboard.getGroup()){
                element("dashboard.group").selectByVisibleText(stringType.getRealValue());
            }
        }
    }

    public void editLinkageSet(Dashboard dashboard) throws Exception {
        if (dashboard.getLinkageSet() != null && dashboard.getLinkageSet().size() > 0){
            for (StringType stringType : dashboard.getLinkageSet()){
                element("dashboard.linkageSet").selectByVisibleText(stringType.getRealValue());
            }
        }
    }

    public void editBusinessLine(Dashboard dashboard) throws Exception {
        if (dashboard.getBusinessLine() != null && dashboard.getBusinessLine().size() > 0){
            for (StringType stringType : dashboard.getBusinessLine()){
                element("dashboard.businessLine").selectByVisibleText(stringType.getRealValue());
            }
        }
    }

    public void save(Dashboard dashboard) throws Exception {
        element("dashboard.save").click();
        PageHelper.handleAlters(dashboard.getAlertHandling());
    }
    public void reset() throws Exception {
        element("dashboard.reset").click();
    }

    public void assetDashboardInfo(Dashboard dashboard) throws Exception {
        if (dashboard.getCollateralSettlementSummary() != null && !dashboard.getCollateralSettlementSummary().isApply()) {
            element("dashboard.collateralSettlementSummary.status").getAllInnerTexts();
            assertThat("dashboard.collateralSettlementSummary.status").allInnerTexts().doesNotContain(dashboard.getCollateralSettlementSummary().getStatus().getRealValue());
        }
    }

    public void enterCollateralWorkflowSettlementStatus(Dashboard dashboard) throws Exception {
         if (dashboard.getCollateralSettlementSummary() != null)
             //element("workflowDashboard.asset.class2").click();
            element("dashboard.asset.class", dashboard.getCollateralSettlementSummary().getAssetName().getRealValue()).click();
        }




}
