package com.lombardrisk.pages.organisations;

import com.lombardrisk.pojo.OrganisationIndustry;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

/**
 * @author Kenny Wang
 */
public final class IndustryPage extends PageBase {

    public IndustryPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }


    /**
     * add industry items in edit industry hierarchy page
     *
     * @param industry
     */
    public void addIndustry(OrganisationIndustry industry) throws Exception {
        if (industry.getParent() == null) {
            element("og.industry.add").click();
        } else {
            element("og.industry.expand", industry.getParent().getOrganisationIndustryName().getRealValue()).click();
            element("og.industry.add.child", industry.getParent().getOrganisationIndustryName().getRealValue()).click();
        }
        if (industry.getOrganisationIndustryName() != null)
            element("og.industry.name").input(industry.getOrganisationIndustryName().getRealValue());
        if (industry.getCode() != null)
            element("og.industry.code").input(industry.getCode().getRealValue());
        if (industry.getStatus() != null)
            element("og.industry.status").selectByVisibleText(industry.getStatus().value());
        element("og.industry.save").click();
    }
    
    public void deleteIndustry(){}

    public void assertOrganisationIndustry(OrganisationIndustry industry) throws Exception{
        if (industry.getParent()==null) {
            assertThat("og.industry.name.a", industry.getOrganisationIndustryName().getRealValue()).displayed().isTrue();
        }else {
            if (industry.getParent() != null && industry.getParent().getOrganisationIndustryName() != null) {
                OrganisationIndustry organisationBook = new OrganisationIndustry();
                organisationBook.setOrganisationIndustryName(industry.getParent().getOrganisationIndustryName());
                organisationBook.setParent(industry.getParent().getParent());
                assertOrganisationIndustry(organisationBook);
                if(element("og.industry.expand", organisationBook.getOrganisationIndustryName().getRealValue()).isDisplayed()){
                    element("og.industry.expand", organisationBook.getOrganisationIndustryName().getRealValue()).click();
                    assertThat("og.industry.name.a", industry.getOrganisationIndustryName().getRealValue()).displayed().isTrue();
                }
            }
        }
    }

}
