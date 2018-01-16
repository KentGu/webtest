package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.LetterTemplate;
import com.lombardrisk.util.Biz;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.util.PropHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.io.File;

/**
 * @author Kenny Wang
 */
public final class LetterTemplateEditorPage extends PageBase {

    public LetterTemplateEditorPage(IWebDriverWrapper webDriverWrapper) {
		super(webDriverWrapper);
	}

	public void inputLetterTemplate(LetterTemplate letterTemplate) throws Exception {
        logger.debug("input Letter Template information");
        if (letterTemplate.getTemplateType() != null)
            element("lc.type").selectByVisibleText(letterTemplate.getTemplateType().getRealValue());
        if (letterTemplate.getDescription() != null)
            element("lc.desc").input(letterTemplate.getDescription().getRealValue());
        if (letterTemplate.getTemplateFile() != null){
        	String filePath = Biz.getWorkspace()+ PropHelper.getProperty("letter.template.path")+letterTemplate.getTemplateFile().getRealValue();
        	element("lc.upload.file").type(filePath.replace("/", File.separator));
        }
    }
	
	public boolean saveLetterTemplate() throws Exception{
		element("lc.save").click();
		PageHelper.d31489Workaround(element("hm.return.col"), this);
		return !element("lc.cancel").isDisplayed() || cancelLetterTemplate();
	}
	
	public boolean cancelLetterTemplate() throws Exception{
		element("lc.cancel").click();
		PageHelper.d31489Workaround(element("hm.return.col"), this, element("lc.cancel"));
		return !element("lc.cancel").isDisplayed();
	}
}
