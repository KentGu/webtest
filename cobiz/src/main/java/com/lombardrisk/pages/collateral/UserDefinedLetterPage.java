package com.lombardrisk.pages.collateral;


import com.lombardrisk.pojo.LetterTemplate;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

/**
 * @author Kenny Wang
 */
public final class UserDefinedLetterPage extends PageBase {

    public UserDefinedLetterPage(IWebDriverWrapper webDriverWrapper) {
		super(webDriverWrapper);
	}

	public void inputLetterTemplate(LetterTemplate letterTemplate) throws Exception {
        if (letterTemplate.getLetterName() != null)
            element("lc.name").input(letterTemplate.getLetterName().getRealValue());
        if (letterTemplate.getLetterSubject() != null)
            element("lc.subject").input(letterTemplate.getLetterSubject().getRealValue());
        if (letterTemplate.getLetterMessage() != null)
            element("lc.msg").input(letterTemplate.getLetterMessage().getRealValue());
    }
	
	public boolean saveLetterTemplate() throws Exception{
		element("lc.apply").click();
		PageHelper.d31489Workaround(element("hm.return.col"), this);
		return !element("lc.cancel").isDisplayed() || cancelLetterTemplate();
	}
	
	public boolean cancelLetterTemplate() throws Exception{
		element("lc.cancel").click();
		PageHelper.d31489Workaround(element("hm.return.col"), this, element("lc.cancel"));
		return !element("lc.cancel").isDisplayed();
	}
}
