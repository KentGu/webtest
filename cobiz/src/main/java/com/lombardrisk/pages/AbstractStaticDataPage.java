package com.lombardrisk.pages;

import com.lombardrisk.pojo.AblityStatusType;
import com.lombardrisk.pojo.StaticData;
import com.lombardrisk.pojo.StringType;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;


public abstract class AbstractStaticDataPage extends PageBase  {

	public AbstractStaticDataPage(IWebDriverWrapper webDriverWrapper) {
		super(webDriverWrapper);
	}

	public boolean staticDataExist(StaticData data) throws Exception{
		if (data.getType() != null)
			element("cd.type", data.getType().getRealValue()).clickByJavaScript();
		return element("cd.record.edit", getXpath(data)).isDisplayed();
	}

	public void enableAllStaticDataInCategory(StaticData data) throws Exception {
		if (data.getType() != null) {
			element("cd.type", data.getType().getRealValue()).click();
			for (IWebDriverWrapper.IWebElementWrapper e : element("cd.column.status.all.disabled").getAllMatchedElements())
				e.selectByVisibleText("enabled");
		}
	}

	public void enableStaticData(StaticData data, String action) throws Exception {
		element("cd.column.status", data.getDataName().getRealValue()).selectByVisibleText(action);
	}

	/**
	 * add staticData on Collateral -> Static Data -> Static Data page
	 *
	 * @param data
	 */
	public void addStaticData(StaticData data) throws Exception {
		if (data.getType() != null){
			element("cd.type", data.getType().getRealValue()).click();
		}
		element("cd.member.add").click();
		if (data.getDataName() != null){
			element("cd.member.name").input(data.getDataName().getRealValue());
		}
		if (data.getDescription() != null){
			element("cd.member.desc").input(data.getDescription().getRealValue());
		}
		if (data.getProductCategory() != null){
			element("cd.member.category").selectByVisibleText(data.getProductCategory().value());
		}
		if (data.getStatus() != null){
			element("cd.member.status").selectByVisibleText(data.getStatus().value());
		}
		element("cd.member.submit").click();
	}

	public void editStaticData(StaticData data) throws Exception{
		if (data.getDataName() != null)
			element("cd.member.edit.name").input(data.getDataName().getRealValue());
		if (data.getDescription() != null)
			element("cd.member.edit.desc").input(data.getDescription().getRealValue());
		if (data.getStatus() != null)
			element("cd.member.edit.status").selectByVisibleText(data.getStatus().value());
		element("cd.member.edit.save").click();
	}

	public void openStaticDataType(StaticData data) throws Exception{
		if (data.getType() != null)
			element("cd.type", data.getType().getRealValue()).click();
	}

	public void openStaticDataRecord(StaticData data) throws Exception{
		String xpath = getXpath(data);
		element("cd.record.edit", xpath).click();
	}

	protected String getXpath(StaticData data) throws Exception {
		StringBuffer xpath = new StringBuffer();
		Method[] methods = data.getClass().getDeclaredMethods();

		for(Method method : methods){
			if((method.getName().equals("getDataName")
					|| method.getName().equals("getDescription")
					|| method.getName().equals("getStatus"))
					&& method.invoke(data) != null){
				if(method.getName().equals("getStatus")){
					AblityStatusType ablityStatusType = (AblityStatusType) method.invoke(data);
//					xpath.append("[//option[contains(text(),'" + ablityStatusType.value() + "') and @selected='true']]");
					xpath.append("[//option[contains(text(),'").append(ablityStatusType.value()).append("') and @selected='true']]");
				}else {
					StringType v = (StringType) method.invoke(data);
//					xpath.append("[td='" + v.getRealValue() + "']");
					xpath.append("[td='").append(v.getRealValue()).append("']");
				}
			}
		}
		return xpath.toString();
	}

	public void assertStaticData(StaticData data) throws Exception{
		if (data.getDataName()!=null){
			assertThat("cd.column.name",data.getDataName().getRealValue()).innerText().isEqualTo(data.getDataName().getRealValue());
			if (data.getDescription()!=null)
				assertThat("cd.column.description",data.getDataName().getRealValue()).innerText().isEqualTo(data.getDescription().getRealValue());
			if (data.getStatus()!=null)
				assertThat("cd.column.status",data.getDataName().getRealValue()).allSelectedTexts().containsOnly(data.getStatus().value());
		}
	}
		
}
