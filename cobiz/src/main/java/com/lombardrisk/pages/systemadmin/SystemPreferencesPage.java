package com.lombardrisk.pages.systemadmin;

import com.lombardrisk.pojo.SystemPreference;
import com.lombardrisk.util.Biz;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.util.PropHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.io.File;


public final class SystemPreferencesPage extends PageBase {

    public SystemPreferencesPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void switchToMessagingTab() throws Exception{
        element("syspre.messaging").click();
    }

    public void switchToApplicationPreferenceTab() throws Exception{
        element("syspre.applicationPreferences").click();
    }

    public void switchToCommunicationTab() throws Exception{
        element("syspre.communication").click();
    }


    public void switchToLabelsTab() throws Exception{
        element("syspre.labels").click();
    }


    public void switchToEmailInformationTab() throws Exception{
        element("syspre.emailInformation").click();
    }

    public void setEmailInformation(SystemPreference sp) throws Exception{
        if (sp.getEmailInformation() != null){
            if (sp.getEmailInformation().getSmtpServerAddress() != null){
                element("emailInformation.smtpServerAddress").input(sp.getEmailInformation().getSmtpServerAddress().getRealValue());
            }
            if (sp.getEmailInformation().getSmtpServerPort() != null){
                element("emailInformation.smtpServerPort").input(sp.getEmailInformation().getSmtpServerPort().getRealValue());
            }
        }
    }

    public void setLabels(SystemPreference sp) throws Exception{
        if (sp.getLabels() != null){
            if (sp.getLabels().getLegalDisclaimerHeader() != null){
                element("labels.legalDisclaimerHeader").input(sp.getLabels().getLegalDisclaimerHeader().getRealValue());
            }
            if (sp.getLabels().getLegalDisclaimerFooter() != null){
                element("labels.legalDisclaimerFooter").input(sp.getLabels().getLegalDisclaimerFooter().getRealValue());
            }
            if (sp.getLabels().getHeaderFooterFontSize() != null){
                element("labels.headerOrFooterFontSize").setValue("").type(sp.getLabels().getHeaderFooterFontSize().getRealValue());
            }
            if (sp.getLabels().getFooterMarginSize() != null){
                element("labels.footerMarginSize").setValue("").type(sp.getLabels().getFooterMarginSize().getRealValue());
            }
            if (sp.getLabels().getReportingLogo() != null){
                String filePath = Biz.getWorkspace()+ PropHelper.getProperty("logo.path")+sp.getLabels().getReportingLogo().getRealValue();
                element("labels.reportingLogo").type(filePath.replace("/", File.separator));
            }
            if (sp.getLabels().getInstallationLogo() != null){
                String filePath = Biz.getWorkspace()+ PropHelper.getProperty("logo.path")+sp.getLabels().getInstallationLogo().getRealValue();
                element("labels.installationLogo").type(filePath.replace("/", File.separator));
            }
        }
    }

    public void setCommunication(SystemPreference sp) throws Exception{
        if (sp.getCommunication() != null){
            if (sp.getCommunication().getSmsGatewayEmailAddress() != null){
                element("communi.smsAddress").input(sp.getCommunication().getSmsGatewayEmailAddress().getRealValue());
            }
            if (sp.getCommunication().getSmsGatewayEmailSubject() != null){
                element("communi.smsSubject").input(sp.getCommunication().getSmsGatewayEmailSubject().getRealValue());
            }
            if (sp.getCommunication().getSmsGatewayBodyPrefix() != null){
                element("communi.smsMessage").input(sp.getCommunication().getSmsGatewayBodyPrefix().getRealValue());
            }
            if (sp.getCommunication().getTaskSchedulerEmailSender() != null){
                element("communi.taskSender").input(sp.getCommunication().getTaskSchedulerEmailSender().getRealValue());
            }
            if (sp.getCommunication().getTaskSchedulerEmailSubject() != null){
                element("communi.taskSubject").input(sp.getCommunication().getTaskSchedulerEmailSubject().getRealValue());
            }
            if (sp.getCommunication().getTaskSchedulerEmailMessage() != null){
                element("communi.taskMessage").input(sp.getCommunication().getTaskSchedulerEmailMessage().getRealValue());
            }
            if (sp.getCommunication().isEnableExternalDelivery() != null){
                element("communi.enableExternalDelivery").check(sp.getCommunication().isEnableExternalDelivery());
            }
            if (sp.getCommunication().getExternalFileLocation() != null){
                element("communi.externalFileLocation").input(sp.getCommunication().getExternalFileLocation().getRealValue());
            }
            if (sp.getCommunication().getLetterAttachmentLocation() != null){
                element("communi.letterAttachementLocation").input(sp.getCommunication().getLetterAttachmentLocation().getRealValue());
            }
            if (sp.getCommunication().getTemporaryLetterFolder() != null){
                element("communi.temporaryLetterFolder").input(sp.getCommunication().getTemporaryLetterFolder().getRealValue());
            }
            if (sp.getCommunication().getUploadRootFolder() != null){
                element("communi.uploadRoorFolder").input(sp.getCommunication().getUploadRootFolder().getRealValue());
            }
            if (sp.getCommunication().getDefaultBackupFolder() != null){
                element("communi.defaultBackupFolder").input(sp.getCommunication().getDefaultBackupFolder().getRealValue());
            }
            if (sp.getCommunication().getExternalPdfDisplayFont() != null){
                element("communi.extendPdfDispalyFont").input(sp.getCommunication().getExternalPdfDisplayFont().getRealValue());
            }
        }
    }

    public void setApplicationPreferences(SystemPreference sp) throws Exception{
        if (sp.getApplicationPreferences() != null){
            if (sp.getApplicationPreferences().getTheme() != null){
                element("applipre.theme").selectByVisibleText(sp.getApplicationPreferences().getTheme().getRealValue());
            }
            if (sp.getApplicationPreferences().getLogInPageMessage() != null){
                element("applipre.logInPageMessage").input(sp.getApplicationPreferences().getLogInPageMessage().getRealValue());
            }
            if (sp.getApplicationPreferences().getHomePageMessage() != null){
                element("applipre.homePageMessage").input(sp.getApplicationPreferences().getHomePageMessage().getRealValue());
            }
            if (sp.getApplicationPreferences().getErrorReportingEmailAddress() != null){
                element("applipre.errorReportingEmailAddress").input(sp.getApplicationPreferences().getErrorReportingEmailAddress().getRealValue());
            }
            if (sp.getApplicationPreferences().getErrorReportTelephoneNumber() != null){
                element("applipre.errorReportingTelephoneNumber").input(sp.getApplicationPreferences().getErrorReportTelephoneNumber().getRealValue());
            }
            if (sp.getApplicationPreferences().getErrorReportAdditionalText() != null){
                element("applipre.errorReportAdditionalText").input(sp.getApplicationPreferences().getErrorReportAdditionalText().getRealValue());
            }
            if (sp.getApplicationPreferences().getSessionTimeOut() != null){
                element("applipre.sessionTimeOut").input(sp.getApplicationPreferences().getSessionTimeOut().getRealValue());
            }
            if (sp.getApplicationPreferences().getExposureStatementMaxTableItems() != null){
                element("applipre.exposureStatementMaxTableItems").input(sp.getApplicationPreferences().getExposureStatementMaxTableItems().getRealValue());
            }
            if (sp.getApplicationPreferences().getMaxWaitTimeForGui() != null){
                element("applipre.maxWaitTimeForGui").input(sp.getApplicationPreferences().getMaxWaitTimeForGui().getRealValue());
            }
            if (sp.getApplicationPreferences().getMaxWaitTimeForReport() != null){
                element("applipre.maxWaitTimeForReport").input(sp.getApplicationPreferences().getMaxWaitTimeForReport().getRealValue());
            }
            if (sp.getApplicationPreferences().getMaxWaitTimeForTask() != null){
                element("applipre.maxWaitTimeForTask").input(sp.getApplicationPreferences().getMaxWaitTimeForTask().getRealValue());
            }
            if (sp.getApplicationPreferences().isZipFormatReportsForTask() != null){
                element("applipre.zipFormatReportForTask").check(sp.getApplicationPreferences().isZipFormatReportsForTask());
            }
            if (sp.getApplicationPreferences().getThresholdToZipReports() != null){
                element("applipre.threshholdToZipReport").input(sp.getApplicationPreferences().getThresholdToZipReports().getRealValue());
            }
            if (sp.getApplicationPreferences().isMinimumValidationCheck() != null){
                element("applipre.minimumValidationCheck").check(sp.getApplicationPreferences().isMinimumValidationCheck());
            }
            if (sp.getApplicationPreferences().getTradeFeedLockRetryTimes() != null){
                element("applipre.tradeFeedLockRetryTimes").input(sp.getApplicationPreferences().getTradeFeedLockRetryTimes().getRealValue());
            }
            if (sp.getApplicationPreferences().getTradeFeedLockRetryInterval() != null){
                element("applipre.tradeFeedLockRetryInterval").input(sp.getApplicationPreferences().getTradeFeedLockRetryInterval().getRealValue());
            }
            if (sp.getApplicationPreferences().getTradeFeedLockBatchSize() != null){
                element("applipre.tradeFeedBatchSize").input(sp.getApplicationPreferences().getTradeFeedLockBatchSize().getRealValue());
            }
            if (sp.getApplicationPreferences().getReportDataSaveBatchSize() != null){
                element("applipre.reportDataSaveBatchSize").input(sp.getApplicationPreferences().getReportDataSaveBatchSize().getRealValue());
            }
            if (sp.getApplicationPreferences().getCutOffRetryTimes() != null){
                element("applipre.cutOffRetryTime").input(sp.getApplicationPreferences().getCutOffRetryTimes().getRealValue());
            }
            if (sp.getApplicationPreferences().getCutOffRetryInterval() != null){
                element("applipre.cutOffRetryInterval").input(sp.getApplicationPreferences().getCutOffRetryInterval().getRealValue());
            }
            if (sp.getApplicationPreferences().getCheckCorruptedStatementLockInterval() != null){
                element("applipre.checkCorruptedStatementLockInterval").input(sp.getApplicationPreferences().getCheckCorruptedStatementLockInterval().getRealValue());
            }
            if (sp.getApplicationPreferences().isDeltaTradeFeedExcludeMtmColumns() != null){
                element("applipre.deltaTradeFeedExcludeMtmColumns").check(sp.getApplicationPreferences().isDeltaTradeFeedExcludeMtmColumns());
            }
            if (sp.getApplicationPreferences().isDeltaFeedIgnoreTradeManualOverride() != null){
                element("applipre.deltaFeedIgnoreTradeManualOverride").check(sp.getApplicationPreferences().isDeltaFeedIgnoreTradeManualOverride());
            }
            if (sp.getApplicationPreferences().isEnableTaskRecovery() != null){
                element("applipre.enableTaskRecovery").check(sp.getApplicationPreferences().isEnableTaskRecovery());
            }
            if (sp.getApplicationPreferences().getFxDecimalPlaces() != null){
                element("applipre.fxDecimalRecovery").input(sp.getApplicationPreferences().getFxDecimalPlaces().getRealValue());
            }
            if (sp.getApplicationPreferences().isEnableAmountAbbreviations() != null){
                element("applipre.enableAmountAbbreviation").check(sp.getApplicationPreferences().isEnableAmountAbbreviations());
            }
            if (sp.getApplicationPreferences().isCurrencyUnformatInCsvReport() != null){
                element("applipre.currencyUnformatInCsvReport").check(sp.getApplicationPreferences().isCurrencyUnformatInCsvReport());
            }
            if (sp.getApplicationPreferences().getCsvSeparator() != null){
                element("applipre.csvSeparator",sp.getApplicationPreferences().getCsvSeparator().value()).click();
            }
            if (sp.getApplicationPreferences().getExcelFormat() != null){
                element("applipre.excelFormat").selectByVisibleText(sp.getApplicationPreferences().getExcelFormat().value());
            }
            if (sp.getApplicationPreferences().isEnableDateBasedValuation() != null){
                element("applipre.enableDateBasedValuation").check(sp.getApplicationPreferences().isEnableDateBasedValuation());
            }
            if (sp.getApplicationPreferences().isForcePrincipalAndCounterpartyFiltersToBePopulated() != null){
                element("applipre.forcePrincipal").check(sp.getApplicationPreferences().isForcePrincipalAndCounterpartyFiltersToBePopulated());
            }
            if (sp.getApplicationPreferences().isChangeDateFormatInIsoFromatForTradeOutputsAndAssetSettlementsReports() != null){
                element("applipre.changeDateFromat").check(sp.getApplicationPreferences().isChangeDateFormatInIsoFromatForTradeOutputsAndAssetSettlementsReports());
            }
            if (sp.getApplicationPreferences().getMaxNotifyAgreementEligibilityRules() != null){
                element("applipre.maxMotifyAgreement").input(sp.getApplicationPreferences().getMaxNotifyAgreementEligibilityRules().getRealValue());
            }
            if (sp.getApplicationPreferences().getMaxNumberOfAutoReject() != null){
                element("applipre.maxNumber").input(sp.getApplicationPreferences().getMaxNumberOfAutoReject().getRealValue());
            }
            if (sp.getApplicationPreferences().isEnableAutomaticBookingApproval() != null){
                element("applipre.enableAutoBookingApproval").check(sp.getApplicationPreferences().isEnableAutomaticBookingApproval());
            }
            if (sp.getApplicationPreferences().getTolarance() != null){
                element("applipre.tolerance").input(sp.getApplicationPreferences().getTolarance().getRealValue());
            }
            if (sp.getApplicationPreferences().getTolaranceCurrency() != null){
                element("applipre.toleranceCcy").selectByVisibleText(sp.getApplicationPreferences().getTolaranceCurrency().getRealValue());
            }
        }
    }

    public void editMessaging(SystemPreference sp) throws Exception{
        if (sp.getMessaging() != null){
            if (sp.getMessaging().isSuspendOutgoingMessaging() != null){
                element("messaging.suspendOutgoingMessaging").check(sp.getMessaging().isSuspendOutgoingMessaging());
            }
        }
    }

    public void save() throws Exception{
        element("emailInformation.save").click();
        PageHelper.d31489Workaround(element("hm.return.col"), this);
    }

    public void cancel() throws Exception{
        element("emailInformation.cancel").click();
    }
}
