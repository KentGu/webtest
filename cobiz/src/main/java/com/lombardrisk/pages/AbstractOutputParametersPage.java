package com.lombardrisk.pages;




import java.util.List;

import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import com.lombardrisk.pojo.OutputFormat;



public abstract class AbstractOutputParametersPage extends PageBase {

    public AbstractOutputParametersPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    public void setOutputParameter(OutputFormat outputFormat) throws Exception{
    	if (outputFormat.getDisplayValue() != null)
            element("rp.display.value", outputFormat.getInternalValue().getRealValue()).input(
            		outputFormat.getDisplayValue().getRealValue());
        if (outputFormat.getDisplayOrder() != null) {
            element("rp.display.order", outputFormat.getInternalValue().getRealValue()).click();
            element("rp.display.order", outputFormat.getInternalValue().getRealValue()).selectByVisibleText(outputFormat.getDisplayOrder().getRealValue());
        }
        if (outputFormat.getGroupLevel() != null) {
            element("rp.group.level", outputFormat.getInternalValue().getRealValue()).click();
            element("rp.group.level", outputFormat.getInternalValue().getRealValue()).selectByVisibleText(outputFormat.getGroupLevel().getRealValue());
        }
    }
    
   public void setOutputParameters(List<OutputFormat> outputFormatList) throws Exception{
	   for (OutputFormat outputFormat : outputFormatList) {
		   setOutputParameter(outputFormat);
	   }
   }

    
    public void applyChanges() throws Exception{
    	element("rp.changes.apply").click();
    }

   
}
