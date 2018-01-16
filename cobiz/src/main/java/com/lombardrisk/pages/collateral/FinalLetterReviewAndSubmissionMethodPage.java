package com.lombardrisk.pages.collateral;



import com.lombardrisk.pojo.StringType;
import org.yiwan.webcore.test.TestCaseManager;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class FinalLetterReviewAndSubmissionMethodPage extends PageBase {
    public FinalLetterReviewAndSubmissionMethodPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    
    public void switchToLetterSummary() throws Exception{
//        closeAlertIfPresent();
    	element("flrsb.letterSummary").click();
    }
    
    public void email() throws Exception{
//        closeAlertIfPresent();
    	element("flrsb.email").click();
    }

    public void download(StringType path) throws Exception{
//        closeAlertIfPresent();
        TestCaseManager.getTestCase().startTransaction("");
        TestCaseManager.getTestCase().setPrepareToDownload(true);
        element("flrsb.download").click();
        TestCaseManager.getTestCase().stopTransaction();
        path.setValue(TestCaseManager.getTestCase().getDownloadFile());
    }

    private void closeAlertIfPresent(){
        while (alert().isPresent())
            waitThat().alert().toBePresent();
            alert().accept();
    }
   
}
