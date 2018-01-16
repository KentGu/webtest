package com.lombardrisk.pages.collateral;


import com.lombardrisk.pojo.LetterAdmin;
import com.lombardrisk.pojo.MarginLetter;
import com.lombardrisk.pojo.StringType;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class LetterAdminPage extends PageBase {
    public LetterAdminPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    
    /**
     * choose which letter you want to edit
     * @param letterAdmin
     * @throws Exception
     */
    public void editLetter(LetterAdmin letterAdmin) throws Exception{
    	element("la.edit", getLetterRow(letterAdmin)).click();
    }
    
    /**
     * choose which letter you want to remove
     * @param letterAdmin
     * @throws Exception 
     */
    public void removeLetter(LetterAdmin letterAdmin) throws Exception{
    	element("la.remove", getLetterRow(letterAdmin)).click();
    }
    
    
    public void switchToAgreementStatementPage() throws Exception{
    	element("la.agreement.statement").clickByJavaScript();
    }
    
    public void switchToExternalExposurePage() throws Exception{
    	element("la.external.exposures").clickByJavaScript();
    }
    
    public void switchToExposureManagementPage() throws Exception{
    	element("la.exposure.management").clickByJavaScript();
    }
    
    public void switchToAgreementExposureManagementPage() throws Exception{
    	element("la.agreement.exposure.management").clickByJavaScript();
    }
    
    public void switchToInterestManagerPage() throws Exception{
    	element("la.interest.management").clickByJavaScript();
    }
    
    public void switchToAgreementAdminPage() throws Exception{
    	element("la.agreement.admin").clickByJavaScript();
    }

    public void assertMarginLetter(MarginLetter marginLetter) throws Exception{
        assertThat("la.letter.row", getLetterRow(marginLetter)).displayed().isTrue();
    }

    public void assertMarginLetter(LetterAdmin letterAdmin) throws Exception{
        assertThat("la.letter.row", getLetterRow(letterAdmin)).displayed().isTrue();
    }

    private String getLetterRow(LetterAdmin letterAdmin) throws Exception{
        StringBuffer xpath = new StringBuffer();
        Method[] methods = letterAdmin.getClass().getDeclaredMethods();
        for(Method method : methods){
            if(method.invoke(letterAdmin) != null && method.getReturnType() == StringType.class){
                StringType stringType = (StringType) method.invoke(letterAdmin);
                if(method.getName().equals("getCurrentLetters")){
//                    xpath.append("[td[contains(text()[2],'" + stringType.getRealValue() + "')]][string-length(td/text()[2])=string-length('" + stringType.getRealValue() + "')+70]");
                    xpath.append("[td[contains(text()[2],'").append(stringType.getRealValue()).append("')]][string-length(td/text()[2])=string-length('").append(stringType.getRealValue()).append("')+70]");
                }else{
//                    xpath.append("[td[contains(.,'" + stringType.getRealValue() + "')]][td[string-length(.)=string-length('" + stringType.getRealValue() + "')+13]]");
                    xpath.append("[td[contains(.,'").append(stringType.getRealValue()).append("')]][td[string-length(.)=string-length('").append(stringType.getRealValue()).append("')+13]]");
                }
            }
        }
        return xpath.toString();
    }

    private String getLetterRow(MarginLetter marginLetter) {
        StringBuffer xpath = new StringBuffer();
        if(marginLetter.getLetterType() != null) {
//            xpath.append("[td[contains(text()[2],'" + marginLetter.getLetterType().value() + "')]][string-length(td/text()[2])=string-length('" + marginLetter.getLetterType().value() + "')+70]");
            xpath.append("[td[contains(text()[2],'").append(marginLetter.getLetterType().value()).append("')]][string-length(td/text()[2])=string-length('").append(marginLetter.getLetterType().value()).append("')+70]");
        }
        if(marginLetter.getLetterSetupDetails() != null && marginLetter.getLetterSetupDetails().getAdditionalInfo() != null && marginLetter.getLetterSetupDetails().getAdditionalInfo().getLetterName() != null) {
//            xpath.append("[td[contains(.,'" + marginLetter.getLetterSetupDetails().getAdditionalInfo().getLetterName().getRealValue() + "')]][td[string-length(.)=string-length('" + marginLetter.getLetterSetupDetails().getAdditionalInfo().getLetterName().getRealValue() + "')+13]]");
            xpath.append("[td[contains(.,'").append(marginLetter.getLetterSetupDetails().getAdditionalInfo().getLetterName().getRealValue())
                    .append("')]][td[string-length(.)=string-length('")
                    .append(marginLetter.getLetterSetupDetails().getAdditionalInfo().getLetterName().getRealValue()).append("')+13]]");
        }
        return xpath.toString();
    }
}
