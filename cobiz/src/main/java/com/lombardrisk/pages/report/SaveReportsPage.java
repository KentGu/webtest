package com.lombardrisk.pages.report;



import com.lombardrisk.pojo.SavedReport;

import com.lombardrisk.pojo.StringType;
import org.yiwan.webcore.test.TestCaseManager;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.util.List;




public final class SaveReportsPage extends PageBase {

    public SaveReportsPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void setSavedReports(SavedReport sr) throws Exception{
    	if (sr.getShowReportsFrom() != null){
    		element("rpsr.from").input(sr.getShowReportsFrom().getRealValue());
    	}
    	if (sr.getShowReportsTo() != null){
    		element("rpsr.to").input(sr.getShowReportsTo().getRealValue());
    	}
    	element("rpsr.filterReports").click();
    	if (sr.getShowLast() != null){
    		element("rpsr.last").input(sr.getShowLast().getRealValue());
    		element("rpsr.filterLast").click();
    	}
    }
    
    public void expandSavedReport(SavedReport sr) throws Exception{
    	if(sr.getReportResult() != null && sr.getReportResult().size() > 0){
    		for (int i = 0; i < sr.getReportResult().size(); i++) {
    			if (sr.getReportResult().get(i).getReportTypeName() != null){
					if(element("rpsr.expand",sr.getReportResult().get(i).getReportTypeName().getRealValue()).isDisplayed())
        				element("rpsr.expand",sr.getReportResult().get(i).getReportTypeName().getRealValue()).clickByJavaScript();
        		}
        		if (sr.getReportResult().get(i).getReportTypeName() != null){
					if(element("rpsr.expand.sub",sr.getReportResult().get(i).getReportTemplateName().getRealValue()).isDisplayed())
        				element("rpsr.expand.sub",sr.getReportResult().get(i).getReportTemplateName().getRealValue()).clickByJavaScript();
        		}
			}
    	}
    }
    
    public void expandSavedReports(List<SavedReport> savedReports) throws Exception{
    	for (SavedReport savedReport : savedReports) {
			expandSavedReport(savedReport);
		}
    }
    
    public void tickDeleteReport(SavedReport sr) throws Exception{
    	if (sr.getReportResult() != null && sr.getReportResult().size() > 0){
			expandSavedReport(sr);
    		for (int i = 0; i < sr.getReportResult().size(); i++) {
    			//String reportId = "Report # "+sr.getReportResult().get(i).getReportId().getRealValue();
    			if (sr.getReportResult().get(i).getReportId() != null){
					if(sr.getReportResult().get(i).getReportId().getRealValue().contains("Report # "))
						sr.getReportResult().get(i).getReportId().setValue(sr.getReportResult().get(i).getReportId().getRealValue().replace("Report # ", ""));
    				element("rpsr.delete",sr.getReportResult().get(i).getReportId().getRealValue()).click();
    			}
			}
    	}
    }
    
    public void tickExportReport(SavedReport sr) throws Exception{
    	if (sr.getReportResult() != null && sr.getReportResult().size() > 0){
			expandSavedReport(sr);
    		for (int i = 0; i < sr.getReportResult().size(); i++) {
    			//String reportId = "Report # "+sr.getReportResult().get(i).getReportId().getRealValue();
    			if (sr.getReportResult().get(i).getReportId() != null){
					if(sr.getReportResult().get(i).getReportId().getRealValue().contains("Report # "))
						sr.getReportResult().get(i).getReportId().setValue(sr.getReportResult().get(i).getReportId().getRealValue().replace("Report # ", ""));
    				element("rpsr.export",sr.getReportResult().get(i).getReportId().getRealValue()).click();
    			}
			}
    	}
    }
    
    public  void getSavedReportId(SavedReport sr) throws Exception{
    	 int i = element("rpsr.save.report.results").getNumberOfMatches();
    	 int reportId = 0;
    	 do {
			 try {
				 int currentId = Integer.parseInt(element("rpsr.save.report.result", String.valueOf(i)).getInnerText().trim().replace("Report # ", ""));
				 if (reportId < currentId) {
					 reportId = currentId;
				 }
			 }catch(NumberFormatException nfx){
				 logger.warn(String.format("The Id of report at row %d is empty.", (int) element("rpsr.save.report.result", String.valueOf(i)).getRow()));
			 }finally {
				 i--;
			 }
		} while (i>0);
    	 String id = String.valueOf(reportId);
    	 if (sr.getReportResult().get(0).getReportId() == null){
    		 sr.getReportResult().get(0).setReportId(new StringType(id));
    	 }else{
    		 sr.getReportResult().get(0).getReportId().setValue(id);
    	 }
    	 
    }

	public void checkAllDelete() throws  Exception{
		element("rpsr.checkAllDelete").click();
	}

	public void deleteMarketReports(SavedReport sr) throws Exception{
		element("rpsr.deleteMarketReports").click();
		if(sr.getAlertHandling() != null){
			if (sr.getAlertHandling().isAccept()){
				alert().accept();
			}else{
				alert().dismiss();
			}
		}
	}

	public void checkAllExport() throws  Exception{
		element("rpsr.checkAllExport").click();
	}

	public void exportMarketReportsToExcel(StringType v) throws  Exception{
		TestCaseManager.getTestCase().startTransaction("");
		TestCaseManager.getTestCase().setPrepareToDownload(true);
		element("rpsr.toExcel").click();
		TestCaseManager.getTestCase().stopTransaction();
		v.setValue(TestCaseManager.getTestCase().getDownloadFile());
	}

	public void exportMarketReportsToXml(StringType v) throws  Exception{
		TestCaseManager.getTestCase().startTransaction("");
		TestCaseManager.getTestCase().setPrepareToDownload(true);
		element("rpsr.toXml").click();
		TestCaseManager.getTestCase().stopTransaction();
		v.setValue(TestCaseManager.getTestCase().getDownloadFile());


	}

	public void exportMarketReportsToPdf(StringType v) throws  Exception{
		TestCaseManager.getTestCase().startTransaction("");
		TestCaseManager.getTestCase().setPrepareToDownload(true);
		element("rpsr.toPdf").click();
		TestCaseManager.getTestCase().stopTransaction();
		v.setValue(TestCaseManager.getTestCase().getDownloadFile());

	}

	public void exportMarketReportsToCsv(StringType v) throws  Exception{
		TestCaseManager.getTestCase().startTransaction("");
		TestCaseManager.getTestCase().setPrepareToDownload(true);
		element("rpsr.toCsv").click();
		TestCaseManager.getTestCase().stopTransaction();
		v.setValue(TestCaseManager.getTestCase().getDownloadFile());
	}

	public void pdfPageSetup() throws  Exception{
		element("rpsr.pdfPageSetup").click();
	}


}
