package com.lombardrisk.pages.organisations;


import com.lombardrisk.pages.AbstractCollinePage;
import com.lombardrisk.pojo.*;
import com.lombardrisk.util.Biz;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.util.PropHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author Kenny Wang
 */
public final class ViewOrganisationsPage extends AbstractCollinePage {

    public ViewOrganisationsPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    /**
     * view our organisation in hirerarchy
     *
     * @param layer
     */
    public void navigate(StringType... layer) throws Exception {
        int length = layer.length;
        for (int i = 0; i < length - 1; i++)
            element("og.expand", layer[i].getRealValue()).click();
        element("og.search.result", layer[length - 1].getRealValue()).click();
    }

    public void editOrganisation(Organisation org) throws Exception {
        if ((org.getRole() != null && org.getRole().size() > 0)
                || (org.getAddress() != null && org.getAddress().size() > 0))
            editOrganisationDetails(org);

        if (org.getClRuleTemplate() != null || org.getClCurrency() != null || org.isClIncludeSubsidiaries() != null || org.isFinra4210Exempt() != null || org.getDebtClassification() != null)
            editOrganisationCreditDetails(org);

        if (org.getAdditionalFields() != null && org.getAdditionalFields().size() > 0) {
            editOrganisationAdditionalField(org);
        }

//        editOrganisationCreditDetails(org);

        if ((org.getUltimateParent() != null && !org.getUltimateParent().isEmpty()) || (org.getCloseLink() != null && !org.getCloseLink().isEmpty())) {
            switchToRelationships();
            element("og.ult.edit").click();
            if (org.getUltimateParent() != null && !org.getUltimateParent().isEmpty()) {
                element("og.relationships.ultimate.parent").setValue("");
                for (OrganisationSearch ult : org.getUltimateParent()) {
                    editUltimate(ult);
                }
            }
            if (org.getCloseLink() != null && !org.getCloseLink().isEmpty()) {
                element("og.relationships.close.link").setValue("");
                for (OrganisationSearch closeLink : org.getCloseLink())
                    editCloseLink(closeLink);
            }
            element("og.ult.save").click();
            PageHelper.handleAlters(org.getAlertHandling());
        }
        if (org.getRelationship() != null && org.getRelationship().size() > 0) {
            switchToOrganisationDetails();
            if (element("og.approve").isEnabled())
                approveOrganisation();
            editOrganisationRelationship(org);
        }

        if (org.getExternalCode() != null && org.getExternalCode().size() > 0)
            editOrganisationExternalCodes(org.getExternalCode());

        if (org.getStatus() != null && org.getStatus().equals(SimpleStatusType.APPROVED)
                && (org.getRelationship() == null || org.getRelationship().size() < 1))
            approveOrganisation();


    }

    public void editOrganisationAdditionalField(Organisation org) throws Exception {
        if (org.getAdditionalFields() != null && org.getAdditionalFields().size() > 0) {
            switchToAdditionalFiled();
            element("og.edit.additional.field").clickByJavaScript();
            for (AdditionalField additionalField : org.getAdditionalFields()) {
                if (additionalField.getUdfDescription() != null) {
                    if (element("og.additional.field.input", additionalField.getUdfDescription().getRealValue()).isDisplayed()) {
                        if (additionalField.getUdfValue() != null) {
                            element("og.additional.field.input", additionalField.getUdfDescription().getRealValue()).input(additionalField.getUdfValue().getRealValue());
                        }
                    } else if (additionalField.getUdfValue() != null) {
                        element("og.additional.field.select", additionalField.getUdfDescription().getRealValue()).selectByVisibleText(additionalField.getUdfValue().getRealValue());
                    }

                }

            }
        }
        element("og.additional.field.save").click();
    }

    public void editOrganisationDetails(Organisation org) throws Exception {
        switchToOrganisationDetails();
        editOrganisationMainDetails(org);
        if (org.getRole() != null && !org.getRole().isEmpty()) {
            switchToOrganisationDetails();
            editOrganisationRoles(org.getRole());
        }
        if (org.getAddress() != null && !org.getAddress().isEmpty()) {
            switchToOrganisationDetails();
            editOrganisationAddresses(org.getAddress());
        }
    }

    /**
     * switch to organization details tab
     */
    public void switchToOrganisationDetails() throws Exception {
        int i = 0;
        while (!element("og.approve").isDisplayed() && i++ < 10) {
            element("og.details.tab").click();
            waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
        }
    }

    /**
     * there are some condition need added
     */
    public void searchOrganisation() throws Exception {
        element("og.search.og").click();
    }

    /**
     * there are some condition need added
     *
     * @param search
     */
    public void searchOrganisationAndView(OrganisationSearch search) throws Exception {
        searchOrganisation();
        element("og.search.frame").switchTo();
        waitThat("hm.frame.body").innerText().notToBe("false");
        searchOrganisation(search);
        switchTo().defaultContent();
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
    }

    /**
     * search an organisation
     *
     * @param search
     */
    public void searchOrganisation(OrganisationSearch search) throws Exception {
        if (search.getRole() != null)
            element("og.search.role").selectByVisibleText(search.getRole().getRealValue());
        if (search.getStatus() != null)
            element("og.search.status").selectByVisibleText(search.getStatus().value());
        if (search.getFilter() != null)
            element("og.search.filter").input(search.getFilter().getRealValue());
        element("og.search.submit").click();
        waitThat("og.search.table").toBeVisible();
        if (search.getLinkText() != null) {
            element("og.search.result", search.getLinkText().getRealValue()).click();
        }
    }

    public boolean isOrganisationExist(OrganisationSearch search) throws Exception {
        searchOrganisation();
        element("og.search.frame").switchTo();
        waitThat("og.search.frame.body").innerText().notToBe("false");
        if (search.getRole() != null)
            element("og.search.role").selectByVisibleText(search.getRole().getRealValue());
        if (search.getStatus() != null)
            element("og.search.status").selectByVisibleText(search.getStatus().value());
        if (search.getFilter() != null)
            element("og.search.filter").input(search.getFilter().getRealValue());
        element("og.search.submit").click();
//        waitThat("og.search.table").toBeVisible();
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        boolean flag = element("og.search.result", search.getLinkText().getRealValue()).isDisplayed();
        switchTo().defaultContent();
        return flag;
    }

    public void editOrganisationMainDetails(Organisation org) throws Exception {
        element("og.main.detail.edit").click();
        if (org.getShortName() != null)
            element("og.sname").input(org.getShortName().getRealValue());
        if (org.getLongName() != null)
            element("og.lname").input(org.getLongName().getRealValue());
        if (org.getCode() != null)
            element("og.code").input(org.getCode().getRealValue());
        if (org.getLei() != null)
            element("og.lei").input(org.getLei().getRealValue());
        if (org.getRegion() != null && org.getRegion().size() > 0) {
            element("og.region").deselectAll();
            for (StringType region : org.getRegion())
                element("og.region").selectByVisibleText(region.getRealValue());
        }
        if (org.getLinkage() != null)
            element("og.linkage").selectByVisibleText(org.getLinkage().getRealValue());
        if (org.getNewLinkage() != null)
            element("og.newlinkage").input(org.getNewLinkage().getRealValue());
        element("og.role.save").clickByJavaScript();
        PageHelper.d31489Workaround(element("hm.return.col"), this);
    }

    /**
     * change organisation roles
     *
     * @param roles
     */
    public void editOrganisationRoles(List<StringType> roles) throws Exception {
        element("og.role.edit").click();
        for (StringType role : roles) {
            element("og.role", role.getRealValue()).check(true);
        }
        element("og.role.save").click();
    }

    /**
     * set a group of organisation addresses
     *
     * @param addresses
     */
    public void editOrganisationAddresses(List<Address> addresses) throws Exception {
        for (Address address : addresses) {
            editOrganisationAddress(address);
        }
    }

    /**
     * set an organisation addresses
     *
     * @param address
     */
    public void editOrganisationAddress(Address address) throws Exception {
        element("og.address.add").click();
        if (address.getContact() != null)
            element("og.contactName").input(address.getContact().getRealValue());
        if (address.getAddress1() != null)
            element("og.address1").input(address.getAddress1().getRealValue());
        if (address.getAddress2() != null)
            element("og.address2").input(address.getAddress2().getRealValue());
        if (address.getAddress3() != null)
            element("og.address3").input(address.getAddress3().getRealValue());
        if (address.getAddress4() != null)
            element("og.address4").input(address.getAddress4().getRealValue());
        if (address.getAddress5() != null)
            element("og.address5").input(address.getAddress5().getRealValue());
        if (address.getTitle() != null)
            element("og.title").input(address.getTitle().getRealValue());
        if (address.getLabel() != null)
            element("og.label").input(address.getLabel().getRealValue());
        if (address.getTel() != null)
            element("og.telno").input(address.getTel().getRealValue());
        if (address.getFax() != null)
            element("og.faxno").input(address.getFax().getRealValue());
        if (address.getEmail() != null)
            element("og.email").input(address.getEmail().getRealValue());
        if (address.getFtp() != null)
            element("og.ftp").input(address.getFtp().getRealValue());
        if (address.getNotes() != null)
            element("og.notes").input(address.getNotes().getRealValue());
        if (address.isEnable() != null)
            element("og.enabled").check(address.isEnable());
        element("og.address.save").click();
    }

    public void editOrganisationExternalCodes(List<ExternalCode> externalCode) throws Exception {
        switchToOrganisationDetails();
        for (ExternalCode code : externalCode) {
            editOrganisationexternalCode(code);
        }
    }

    public void editOrganisationexternalCode(ExternalCode ec) throws Exception {
        element("og.edit.externalCode").clickByJavaScript();
        if (ec.getSystemName() != null && ec.getExternalCode() != null) {
            element("og.externalCode", ec.getSystemName().getRealValue()).input(ec.getExternalCode().getRealValue());
        }
        element("og.externalCode.submit").clickByJavaScript();
    }

    public void editUltimate(OrganisationSearch org) throws Exception {
        if (org.getType() != null)
            element("og.relationships.ultimate.parent.type").selectByVisibleText(org.getType().value());
        if (org.getFilter() != null)
            element("og.relationships.ultimate.parent").type(org.getFilter().getRealValue());
        if (org.getLinkText() != null)
            element("og.linktext", org.getLinkText().getRealValue()).click();
    }

    public void editCloseLink(OrganisationSearch org) throws Exception {
        if (org.getType() != null)
            element("og.relationships.close.link.type").selectByVisibleText(org.getType().value());
        if (org.getFilter() != null)
            element("og.relationships.close.link").type(org.getFilter().getRealValue());
        if (org.getLinkText() != null)
            element("og.linktext", org.getLinkText().getRealValue()).click();
    }

    /**
     * set organization relationship including its parent
     *
     * @param org
     */
    public void editOrganisationRelationship(Organisation org) throws Exception {
        for (Relationship relationship : org.getRelationship()) {
            if (relationship.isParent() != null && relationship.isParent()) {
                element("og.view.og").click();
                searchOrganisationAndView(relationship.getOrganisationSearch());
                Organisation parent = new Organisation();
                //List<Relationship> pRelationships = new ArrayList<Relationship>();
                Relationship pRelationship = new Relationship();
                pRelationship.setParent(false);
                pRelationship.setType(relationship.getType());
                OrganisationSearch pSearchOrganisation = new OrganisationSearch();
                pSearchOrganisation.setFilter(org.getLongName());
                pSearchOrganisation.setLinkText(org.getLongName());
                pRelationship.setOrganisationSearch(pSearchOrganisation);
                //pRelationships.add(pRelationship);
                parent.getRelationship().add(pRelationship);
                //parent.setRelationship(pRelationships);
                parent.setStatus(org.getStatus());
                parent.setVariation("isParent");
                editOrganisationRelationship(parent);
                if (org.getStatus() != null && org.getStatus() == SimpleStatusType.APPROVED) {
                    approveOrganisation();
                    switchToRelationships();
                }
                if (org.getLongName() != null) {
                    element("og.search.result", org.getLongName().getRealValue()).click();
                    if (org.getStatus() != null && org.getStatus() == SimpleStatusType.APPROVED) {
                        approveOrganisation();
                        switchToRelationships();
                    }
                }
            } else {
                switchToRelationships();
                if (relationship.isRemove() != null && relationship.isRemove())
                    removeRelationship(relationship);
                else {
                    for (int i = 0; i < 10 && !element("og.relationshps.search").isDisplayed(); i++) {
                        element("og.relationshps.add", relationship.getType().getRealValue().toUpperCase()).click();
                        waitThat().jQuery().toBeInactive();
                        waitThat().document().toBeReady();
                        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
                    }
                    searchRelationship(relationship.getOrganisationSearch());
                    waitThat().document().toBeReady();
                    waitThat().jQuery().toBeInactive();
                    waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
                    if (org.getStatus() != null && org.getStatus() == SimpleStatusType.APPROVED) {
                        if (org.getVariation() == null || !org.getVariation().equals("isParent")) {
                            if (relationship.getOrganisationSearch().getLinkText() != null) {
                                for (int i = 0; i < 3 && element("og.relation.link", relationship.getOrganisationSearch().getLinkText().getRealValue()).isDisplayed(); i ++) {
                                    element("og.relation.link", relationship.getOrganisationSearch().getLinkText().getRealValue()).click();
                                    waitThat().jQuery().toBeInactive();
                                    waitThat().document().toBeReady();
                                    waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
                                }
                            }
                            approveOrganisation();
                            switchToRelationships();
                            waitThat().jQuery().toBeInactive();
                            waitThat().document().toBeReady();
                            waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
                            if (org.getLongName() != null) {
                                for (int i = 0; i < 3 && element("og.relation.top.link", org.getLongName().getRealValue()).isDisplayed(); i++) {
                                    element("og.relation.top.link", org.getLongName().getRealValue()).click();
                                    waitThat().jQuery().toBeInactive();
                                    waitThat().document().toBeReady();
                                    waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
                                }
                                approveOrganisation();
                                switchToRelationships();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * switch to relationships tab
     */
    public void switchToRelationships() throws Exception {
        int i = 0;
        while (!element("og.relationshps.branch.add").isDisplayed() && i++ < 10) {
            element("og.relationshps.tab").click();
            waitThat().jQuery().toBeInactive();
            waitThat().document().toBeReady();
            waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
        }
    }

    public void switchToAdditionalFiled() throws Exception {
        element("og.addtional.field.tab").click();
        waitThat().jQuery().toBeInactive();
        waitThat().document().toBeReady();
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
    }

    public void searchRelationship(OrganisationSearch os) throws Exception {
        if (os.getFilter() != null)
            element("og.relationshps.searchFilter").input(os.getFilter().getRealValue());
        element("og.relationshps.search").click();
        if (os.getLinkText() != null) {
            element("og.search.result", os.getLinkText().getRealValue()).clickByJavaScript();
        }
    }

    public void removeRelationship(Relationship re) throws Exception {
        if (re.getOrganisationSearch() != null && re.getOrganisationSearch().getLinkText() != null) {
            if (re.getAlertHandling() != null && re.getAlertHandling().size() > 0 && re.getAlertHandling().get(0).isAccept() != null) {
                alert().disable(re.getAlertHandling().get(0).isAccept());
            } else {
                alert().disable();
            }
            element("og.relationship.remove", re.getOrganisationSearch().getLinkText().getRealValue()).clickByJavaScript();
            alert().enable();
        }

    }

    /**
     * change credit details you should goto the organization page you need to
     * set first
     *
     * @param org
     */
    public void editOrganisationCreditDetails(Organisation org) throws Exception {
        if (org.getRiskCountry() != null
                || org.getIndustry() != null
                || org.getClassification() != null
                || org.getFlagCountry() != null
                || org.getClRuleTemplate() != null
                || org.getClCurrency() != null
                || org.isClIncludeSubsidiaries() != null
                || org.getImThreshold() != null
                || org.getImThresholdCurrency() != null
                || org.isImIncludeSubsidiaries() != null
                || org.isFinra4210Exempt() != null) {
            switchToCreditDetails();
            element("og.creditDetails.edit").click();
            if (org.getRiskCountry() != null)
                element("og.countryOfRisk").selectByVisibleText(org.getRiskCountry().getRealValue());
            if (org.getIndustry() != null)
                element("og.industry").selectByVisibleText(org.getIndustry().getRealValue());
            if (org.getClassification() != null) {
                element("og.classification").selectByVisibleText(org.getClassification().getRealValue());
            }
            if (org.getFlagCountry() != null) {
                element("og.flagCountry").selectByVisibleText(org.getFlagCountry().getRealValue());
            }

            if (org.getClRuleTemplate() != null)
                element("og.clRuleTemplate").selectByVisibleText(org.getClRuleTemplate().getRealValue());

            if (org.getClCurrency() != null)
                element("og.clCcy").selectByVisibleText(org.getClCurrency().getRealValue());
            if (org.isClIncludeSubsidiaries() != null)
                element("og.clIncludeSub").check(org.isClIncludeSubsidiaries());
            if (org.getImThreshold() != null) {
                element("og.imThreshold").input(org.getImThreshold().getRealValue());
            }
            if (org.getImThresholdCurrency() != null) {
                element("og.imThreshold.ccy").selectByVisibleText(org.getImThresholdCurrency().getRealValue());
            }
            if (org.isImIncludeSubsidiaries() != null) {
                element("og.imIncludeSub").check(org.isImIncludeSubsidiaries());
            }
            if (org.isFinra4210Exempt() != null) {
                element("og.finra4210Exempt").check(org.isFinra4210Exempt());
            }
            // TODO you can add more edit here that you need
            if (org.getDebtClassification() != null && org.getDebtClassification().size() > 0) {
                for (DebtClassification debtClassification : org.getDebtClassification()) {
                    if (debtClassification.getDebtName() != null) {
                        if (debtClassification.getCreditRating() != null && debtClassification.getCreditRating().size() > 0) {
                            for (OrganisationRating organisationRating : debtClassification.getCreditRating()) {
                                if (organisationRating.getAgency() != null) {
                                    if (organisationRating.getRating() != null) {
                                        //tr[td/b[contains(text(),'SEN')]]/following-sibling::tr//td[normalize-space(text())='MOODYS']/following-sibling::td[1]/select
                                        element("og.debt.classification.rating", debtClassification.getDebtName().getRealValue(), organisationRating.getAgency().getRealValue()).selectByVisibleText(organisationRating.getRating().getRealValue());
                                    }
                                }
                            }
                        }
                    }
                }
            }

            element("og.creditDetails.save").click();
            switchToOrganisationDetails();
        }
    }

    /**
     * switch to credit details tab
     */
    public void switchToCreditDetails() throws Exception {
        int i = 0;
        while (!element("og.creditRating.form").isDisplayed() && i++ < 10) {
            element("og.creditDetails.tab").click();
            waitThat().jQuery().toBeInactive();
            waitThat().document().toBeReady();
            waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
        }
    }

    public void switchToAddtionalFields() throws Exception {
        element("og.additionalFields.tab").click();
        waitThat().jQuery().toBeInactive();
        waitThat().document().toBeReady();
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
    }

    public void approveOrganisation() throws Exception {
        switchToOrganisationDetails();
        for (int i = 0; i < 3; i++) {
            element("og.approve").fireEvent(Biz.FIRE_EVENT_ONCLICK);
            waitThat().jQuery().toBeInactive();
            waitThat().document().toBeReady();
            waitThat().timeout(PropHelper.TIMEOUT_INTERVAL);
            if (!element("og.approve").isEnabled())
                break;
        }
        assertThat("og.status.assert").innerText().isEqualToIgnoringCase(SimpleStatusType.APPROVED.value());

    }

    /**
     * enter edit credit details page
     */
    public void enterOrganisationCreditDetailsEditor() throws Exception {
        element("og.creditDetails.edit").click();
    }

    /**
     * click save credit details button on credit details editor tab
     */
    public void saveCreditDetails() throws Exception {
        element("og.creditDetails.save").click();
    }

    public void enterViewOurOrganisationPage() throws Exception {
        element("og.viewOurOrganisation").click();
    }

    public void assertOrganisation(Organisation organisation) throws Exception {
        if (organisation.getShortName() != null || organisation.getLongName() != null || organisation.getCode() != null ||
                organisation.getRegion() != null || organisation.getLinkage() != null || organisation.getRole() != null ||
                organisation.getAddress() != null || organisation.getExternalCode() != null) {
            switchToOrganisationDetails();
            if (organisation.getShortName() != null) {
                assertThat("og.org.details.shortName.assert").innerText().isEqualTo(organisation.getShortName().getRealValue());
            }
            if (organisation.getLongName() != null) {
                assertThat("og.org.details.longName.assert").innerText().isEqualTo(organisation.getLongName().getRealValue());
            }
            if (organisation.getCode() != null) {
                assertThat("og.org.details.code.assert").innerText().isEqualTo(organisation.getCode().getRealValue());
            }
            if (organisation.getLei() != null) {
                assertThat("og.org.details.lei.assert").innerText().isEqualTo(organisation.getLei().getRealValue());
            }
            if (organisation.getRegion() != null && organisation.getRegion().size() > 0) {
                for (int i = 0; i < organisation.getRegion().size(); i++) {
                    assertThat("og.org.details.orgRegion.assert").allInnerTexts().contains(organisation.getRegion().get(i).getRealValue());
                }
            }
            if (organisation.getLinkage() != null) {
                assertThat("og.org.details.linkage.assert").innerText().isEqualTo(organisation.getLinkage().getRealValue());
            }
            if (organisation.getRole() != null && organisation.getRole().size() > 0) {
                for (int i = 0; i < organisation.getRole().size(); i++) {
                    assertThat("og.org.details.roles.assert").allInnerTexts().contains(organisation.getRole().get(i).getRealValue());
                }
            }
            if (organisation.getAddress() != null && organisation.getAddress().size() > 0) {
                for (Address address : organisation.getAddress()) {
                    if (address.getContact() != null) {
                        assertThat("og.org.details.contactName.assert").allInnerTexts().contains(address.getContact().getRealValue());
                    }
                    if (address.getAddress1() != null
                            || address.getAddress2() != null
                            || address.getAddress3() != null
                            || address.getAddress4() != null
                            || address.getAddress5() != null) {
                        StringBuffer addText = new StringBuffer();
                        if (address.getAddress1() != null)
                            addText.append(address.getAddress1().getRealValue());
                        if (address.getAddress2() != null) {
//                            addText.append("\n" + address.getAddress2().getRealValue());
                            addText.append("\n").append(address.getAddress2().getRealValue());
                        }
                        if (address.getAddress3() != null) {
//                            addText.append("\n" + address.getAddress3().getRealValue());
                            addText.append("\n").append(address.getAddress3().getRealValue());
                        }
                        if (address.getAddress4() != null) {
//                            addText.append("\n" + address.getAddress4().getRealValue());
                            addText.append("\n").append(address.getAddress4().getRealValue());
                        }
                        if (address.getAddress5() != null) {
//                            addText.append("\n" + address.getAddress5().getRealValue());
                            addText.append("\n").append(address.getAddress5().getRealValue());
                        }
                        assertThat("og.org.details.address.assert").allInnerTexts().contains(addText.toString());
                    }
//                    String text = element("og.org.details.address.assert").getInnerText();
//                    String[] textArray = text.split("\n");
//                    int textaArrayLenth = textArray.length;
//                    if (address.getAddress1() != null){
//                        if (textaArrayLenth > 1){
//                            assertThat("og.org.details.address.assert").innerText().contains(address.getAddress1().getRealValue()+"\n");
//                        }else {
//                            assertThat("og.org.details.address.assert").innerText().contains(address.getAddress1().getRealValue());
//                        }
//                    }else if (address.getAddress2() != null){
//                        if (textaArrayLenth > 2){
//                            assertThat("og.org.details.address.assert").innerText().contains(address.getAddress2().getRealValue()+"\n");
//                        }else {
//                            assertThat("og.org.details.address.assert").innerText().contains(address.getAddress2().getRealValue());
//                        }
//                    }else if (address.getAddress3() != null){
//                        if (textaArrayLenth > 3){
//                            assertThat("og.org.details.address.assert").innerText().contains(address.getAddress3().getRealValue()+"\n");
//                        }else {
//                            assertThat("og.org.details.address.assert").innerText().contains(address.getAddress3().getRealValue());
//                        }
//                    }else if (address.getAddress4() != null){
//                        if (textaArrayLenth > 4){
//                            assertThat("og.org.details.address.assert").innerText().contains(address.getAddress4().getRealValue()+"\n");
//                        }else {
//                            assertThat("og.org.details.address.assert").innerText().contains(address.getAddress4().getRealValue());
//                        }
//                    }else if (address.getAddress5() != null){
//                        assertThat("og.org.details.address.assert").innerText().contains(address.getAddress5().getRealValue());
//                    }

                    if (address.getTitle() != null) {
                        assertThat("og.org.details.title.assert").allInnerTexts().contains(address.getTitle().getRealValue());
                    }
                    if (address.getLabel() != null) {
                        assertThat("og.org.details.label.assert").allInnerTexts().contains(address.getLabel().getRealValue());
                    }
                    if (address.getTel() != null) {
                        assertThat("og.org.details.telNo.assert").allInnerTexts().contains(address.getTel().getRealValue());
                    }
                    if (address.getFax() != null) {
                        assertThat("og.org.details.faxNo.assert").allInnerTexts().contains(address.getFax().getRealValue());
                    }
                    if (address.getEmail() != null) {
                        assertThat("og.org.details.email.assert").allInnerTexts().contains(address.getEmail().getRealValue());
                    }
                    if (address.getFtp() != null) {
                        assertThat("og.org.details.ftp.assert").allInnerTexts().contains(address.getFtp().getRealValue());
                    }
                    if (address.getNotes() != null) {
                        assertThat("og.org.details.notes.assert").allInnerTexts().contains(address.getNotes().getRealValue());
                    }
                }
            }
            if (organisation.getExternalCode() != null && organisation.getExternalCode().size() > 0) {
                element("og.org.details.showExternalCode").click();
                for (ExternalCode externalCode : organisation.getExternalCode()) {
                    if (externalCode.getSystemName() != null || externalCode.getExternalCode() != null) {
                        StringBuffer systemName = new StringBuffer();
                        StringBuffer exCode = new StringBuffer();
                        if (externalCode.getSystemName() != null) {
//                            systemName.append("[td[1]='" + externalCode.getSystemName().getRealValue() + "']");
                            systemName.append("[td[1]='").append(externalCode.getSystemName().getRealValue()).append("']");
                        }
                        if (externalCode.getExternalCode() != null) {
//                            exCode.append("[td[2][contains(text(),'" + externalCode.getExternalCode().getRealValue() + "')]]");
                            exCode.append("[td[2][contains(text(),'").append(externalCode.getExternalCode().getRealValue()).append("')]]");
                        }
                        assertThat("og.org.details.externalCodes.record", systemName.toString() + exCode.toString()).displayed().isTrue();
                    }
//                    if (externalCode.getSystemName() != null){
//                        assertThat("og.org.details.externalCodes.systemName").allInnerTexts().contains(externalCode.getSystemName().getRealValue());
//                    }
//                    if (externalCode.getExternalCode() != null){
//                        assertThat("og.org.details.externalCodes.externalCode").allInnerTexts().contains(externalCode.getExternalCode().getRealValue());
//                    }
                }
            }
        }

        if ((organisation.getRelationship() != null && organisation.getRelationship().size() > 0)
                || (organisation.getUltimateParent() != null && !organisation.getUltimateParent().isEmpty())
                || (organisation.getCloseLink() != null && !organisation.getCloseLink().isEmpty())) {
            switchToRelationships();
            if (organisation.getUltimateParent() != null && !organisation.getUltimateParent().isEmpty()) {
                for (OrganisationSearch organisationSearch : organisation.getUltimateParent()) {
                    assertThat("og.ultimate.parent.assert").innerText().contains(organisationSearch.getLinkText().getRealValue());
                }
            }
            if (organisation.getCloseLink() != null && !organisation.getCloseLink().isEmpty()) {
                for (OrganisationSearch organisationSearch : organisation.getCloseLink()) {
                    assertThat("og.close.link.assert").innerText().contains(organisationSearch.getLinkText().getRealValue());
                }
            }
            if (organisation.getRelationship() != null && organisation.getRelationship().size() > 0) {
                for (Relationship relationship : organisation.getRelationship()) {
                    if (relationship.isParent() == null || !relationship.isParent()) {
                        if (relationship.getType() != null && relationship.getType().getRealValue().equals("Subsidiary")) {
                            if (relationship.getOrganisationSearch() != null) {
                                if (relationship.getOrganisationSearch().getLinkText() != null) {
                                    assertThat("og.subsidiary.assert").allInnerTexts().contains(relationship.getOrganisationSearch().getLinkText().getRealValue());
                                }
                            }
                        } else if (relationship.getType() != null && relationship.getType().getRealValue().equals("Department")) {
                            if (relationship.getOrganisationSearch() != null) {
                                if (relationship.getOrganisationSearch().getLinkText() != null) {
                                    assertThat("og.department.assert").allInnerTexts().contains(relationship.getOrganisationSearch().getLinkText().getRealValue());
                                }
                            }
                        } else if (relationship.getType() != null && relationship.getType().getRealValue().equals("Branch")) {
                            if (relationship.getOrganisationSearch() != null) {
                                if (relationship.getOrganisationSearch().getLinkText() != null) {
                                    assertThat("og.branch.assert").allInnerTexts().contains(relationship.getOrganisationSearch().getLinkText().getRealValue());
                                }
                            }
                        }
                    } else {
                        if (relationship.getOrganisationSearch() != null) {
                            if (relationship.getOrganisationSearch().getLinkText() != null) {
                                assertThat("og.parent.assert").innerText().isEqualTo(relationship.getOrganisationSearch().getLinkText().getRealValue());
                            }
                        }
                    }
                }
            }
        }
        if (organisation.getRiskCountry() != null || organisation.getIndustry() != null || organisation.getClassification() != null ||
                organisation.getFlagCountry() != null || organisation.getClRuleTemplate() != null || organisation.getClCurrency() != null ||
                organisation.isClIncludeSubsidiaries() != null || organisation.getImThreshold() != null || organisation.getImThresholdCurrency() != null ||
                organisation.isImIncludeSubsidiaries() != null || organisation.getDebtClassification() != null) {
            switchToCreditDetails();
            if (organisation.getRiskCountry() != null) {
                assertThat("og.credit.details.countryOfRisk.assert").innerText().isEqualTo(organisation.getRiskCountry().getRealValue());
            }
            if (organisation.getIndustry() != null) {
                assertThat("og.credit.details.industry.assert").innerText().isEqualTo(organisation.getIndustry().getRealValue());
            }
            if (organisation.getClassification() != null) {
                assertThat("og.credit.details.classification.assert").innerText().isEqualTo(organisation.getClassification().getRealValue());
            }
            if (organisation.getFlagCountry() != null) {
                assertThat("og.credit.details.flagCountry.assert").innerText().isEqualTo(organisation.getFlagCountry().getRealValue());
            }
            if (organisation.getClRuleTemplate() != null) {
                assertThat("og.credit.details.clRuleTemplate.assert").innerText().isEqualTo(organisation.getClRuleTemplate().getRealValue());
            }
            if (organisation.getClCurrency() != null) {
                assertThat("og.credit.details.clCurrency.assert").innerText().isEqualTo(organisation.getClCurrency().getRealValue());
            }
            if (organisation.isClIncludeSubsidiaries() != null) {
                if (organisation.isClIncludeSubsidiaries()) {
                    assertThat("og.credit.details.clIncludeSubsidiaries.assert").innerText().isEqualTo("YES");
                } else {
                    assertThat("og.credit.details.clIncludeSubsidiaries.assert").innerText().isEqualTo("NO");
                }
            }
            if (organisation.getImThreshold() != null) {
                DecimalFormat format = new DecimalFormat();
                format.applyPattern("###,###,###,##0.00");
                assertThat("og.credit.details.imThreshold.assert").innerText().isEqualTo(
                        format.format(Float.parseFloat(organisation.getImThreshold().getRealValue()))
                );
            }
            if (organisation.getImThresholdCurrency() != null) {
                assertThat("og.credit.details.imThresholdCurrency.assert").innerText().isEqualTo(organisation.getImThresholdCurrency().getRealValue());
            }
            if (organisation.isImIncludeSubsidiaries() != null) {
                if (organisation.isImIncludeSubsidiaries()) {
                    assertThat("og.credit.details.imIncludeSubsidiaries.assert").innerText().isEqualTo("YES");
                } else {
                    assertThat("og.credit.details.imIncludeSubsidiaries.assert").innerText().isEqualTo("NO");
                }
            }
            if (organisation.getDebtClassification() != null && organisation.getDebtClassification().size() > 0) {
                for (DebtClassification debtClassification : organisation.getDebtClassification()) {
                    if (debtClassification.getDebtName() != null) {
                        if (debtClassification.getCreditRating() != null && debtClassification.getCreditRating().size() > 0) {
                            for (OrganisationRating organisationRating : debtClassification.getCreditRating()) {
                                if (organisationRating.getAgency() != null) {
                                    if (organisationRating.getRating() != null) {
                                        assertThat("og.credit.details.rating.assert", debtClassification.getDebtName().getRealValue(), organisationRating.getAgency().getRealValue()).innerText().isEqualToIgnoringWhitespace(organisationRating.getRating().getRealValue());
                                    }

                                }
                            }
                        }
                    }

                }
            }
        }
        if (organisation.getAdditionalFields() != null && organisation.getAdditionalFields().size() > 0) {
            switchToAddtionalFields();
            for (AdditionalField additionalField : organisation.getAdditionalFields()) {
                if (additionalField.getUdfDescription() != null) {
                    assertThat("og.additional.fields.udf.assert", additionalField.getUdfDescription().getRealValue()).innerText().isEqualTo(additionalField.getUdfValue().getRealValue());
                }
            }
        }

        if (organisation.getStatus() != null) {
            switchToOrganisationDetails();
            assertThat("og.status.assert").innerText().isEqualToIgnoringCase(organisation.getStatus().value());
        }
    }

    public void assertOrganisationSearchResult(OrganisationSearch organisationSearch) throws Exception {
        if (organisationSearch.getLinkText() != null) {
            element("og.search.frame").switchTo();
            waitThat("og.search.frame.body").innerText().notToBe("false");
            assertThat("og.search.result.assert").allInnerTexts().contains(organisationSearch.getLinkText().getRealValue());
            switchTo().defaultContent();
        }
    }
}
