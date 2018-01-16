package com.lombardrisk.pages.collateral;


import com.lombardrisk.pages.AbstractCollinePage;
import com.lombardrisk.pojo.Agreement;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.StringType;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;


/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AgreementPartyCounterpartySetupPage extends AbstractCollinePage {
    public AgreementPartyCounterpartySetupPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setupAgreementPartyCounterparty(Agreement agmt) throws Exception {
        //set duplicate times
        if (agmt.getDuplicateCount() != null) {
            element("ap.duplicate.count").input(agmt.getDuplicateCount().getRealValue());
        }
        //set linkage set
        if (agmt.getLinkageSet() != null)
            for (StringType linkage : agmt.getLinkageSet())
                element("ap.linkage.set").selectByVisibleText(linkage.getRealValue());

        // set principal
        if (agmt.getPrincipal() != null) {
            element("ap.search.prin").click();
            element("ap.frame.prin").switchTo();
            searchOrganisation(agmt.getPrincipal());
            switchTo().defaultContent();
        }
        // set counterparty

        if (agmt.getCounterparty() != null) {
            element("ap.search.cpty").click();
            element("ap.frame.cpty").switchTo();
            searchOrganisation(agmt.getCounterparty());
            switchTo().defaultContent();
        }
        // set custodian
        if (agmt.getCustodian() != null) {
            element("ap.search.cust").click();
            element("ap.frame.cust").switchTo();
            searchOrganisation(agmt.getCustodian());
            switchTo().defaultContent();
        }
        //set busuiness line
        if (agmt.getBusinessLine() != null) {
            element("ap.bussiness.line").selectByVisibleText(agmt.getBusinessLine().value());
            //set principal role
            if (agmt.getBusinessLine().value().equals("ETD") && agmt.getPrincipalRole() != null)
                element("ap.principal.role").selectByVisibleText(agmt.getPrincipalRole().value());
        }

        if (agmt.isFamilyAgreement() != null) {
            element("ap.family.agreement").check(agmt.isFamilyAgreement());
        }

        if (agmt.isConfigurableRule() != null) {
            element("ap.configurable.rule").check(agmt.isConfigurableRule());
        }

        //set ccp
        if (agmt.getCcp() != null) {
            if (agmt.getBusinessLine().value().equals("ETD")) {
                element("ap.etd.ccp").selectByVisibleText(agmt.getCcp().getRealValue());
            } else {
                element("ap.ccp").selectByVisibleText(agmt.getCcp().getRealValue());
            }
        }
        //tick regulatory agreement
        if (agmt.isRegulatoryAgreement() != null) {
            element("ap.regulatory").check(agmt.isRegulatoryAgreement());
        }
        //set agreed jurisdiction
        if (agmt.getAgreedJurisdiction() != null) {
            if (agmt.getAgreedJurisdiction().getFilter()!=null) {
//                element("ap.agreed.jurisdiction").setValue(agmt.getAgreedJurisdiction().getFilter().getRealValue());
                element("ap.agreed.jurisdiction").input(agmt.getAgreedJurisdiction().getFilter().getRealValue());
                element("ap.agreed.jurisdiction.list").click();
//                element("ap.agreed.jurisdiction").input(agmt.getAgreedJurisdiction().getFilter().getRealValue());
            }
        }
        //set other jurisdiction
        if (agmt.getOtherJurisdiction() != null && agmt.getOtherJurisdiction().size()>0) {
            for (int i = 0; i < agmt.getOtherJurisdiction().size(); i++) {
                if (agmt.getOtherJurisdiction().get(i).getFilter()!=null)
                    element("ap.other.jurisdiction").setAttribute("value", agmt.getOtherJurisdiction().get(i).getFilter().getRealValue());
            }
        }
        //tick multi model
        if (agmt.isMultiModel() != null) {
            element("ap.multi.mode").check(agmt.isMultiModel());
        }
      
        //tick umbrella agreement
        if (agmt.isUmbrellaAgreement() != null){
            element("ap.umbrella").check(agmt.isUmbrellaAgreement());
        }
        //select umbrella rule
        if (agmt.getUmbrellaRule() != null)
            element("ap.umbrella.rule").selectByVisibleText(agmt.getUmbrellaRule().getRealValue());

        if (agmt.getIndividualOrders() != null) {
            for (int i = 0; i < agmt.getIndividualOrders().size(); i++) {
                element("ap.individual.orders").input(agmt.getIndividualOrders().get(i).getRealValue());
                PageHelper.handleAlters(agmt.getIndividualOrderMessage());
            }
        }
        //set agreement description
        if (agmt.getAgreementDescription() != null)
            element("ap.desc").input(agmt.getAgreementDescription().getRealValue());
        
        //set agreement status
        if (agmt.getStatus() != null) {
            element("ap.status").selectByVisibleText(agmt.getStatus().value());
        }
        if (agmt.getComment() != null) {
            element("ap.comment").input(agmt.getComment().getRealValue());
        }
        if (agmt.getGroup() != null) {
            element("ap.group").selectByVisibleText(agmt.getGroup().getRealValue());
        }
        if (agmt.getCounterpartyOrgRegion() != null) {
            element("ap.cpt.org.region").input(agmt.getCounterpartyOrgRegion().getRealValue());
        }
        if (agmt.getRegion() != null) {
            element("ap.region").selectByVisibleText(agmt.getRegion().getRealValue());
        }
        if (agmt.getCategory() != null) {
            element("ap.category").selectByVisibleText(agmt.getCategory().getRealValue());
        }
        if (agmt.getCollateralManager() != null) {
            element("ap.col.manager").selectByVisibleText(agmt.getCollateralManager().getRealValue());
        }
        if (agmt.getNotes() != null) {
            element("ap.notes").input(agmt.getNotes().getRealValue());
        }
        if (agmt.getAgreementName() != null)
            element("ap.name").input(agmt.getAgreementName().getRealValue());
      
        if (agmt.getExternalId() != null)
            element("ap.exid").input(agmt.getExternalId().getRealValue());
        if (agmt.getRank() != null) {
            element("ap.rank").input(agmt.getRank().getRealValue());
        }
        //after setup all parameters enternext page
        enterNextStage();
        if(agmt.getAlertHandling() != null && !agmt.getAlertHandling().isEmpty())
            PageHelper.handleAlters(agmt.getAlertHandling());
    }

    public void assertPartyCounterparty(Agreement agmt) throws Exception {
        if (agmt.isFamilyAgreement() != null) {
            validateThat("ap.family.agreement").selected().isEqualTo(agmt.isFamilyAgreement());
        }

        if (agmt.isConfigurableRule() != null) {
            validateThat("ap.configurable.rule").selected().isEqualTo(agmt.isConfigurableRule());
        }

        if (agmt.isRegulatoryAgreement() != null) {
            validateThat("ap.regulatory").selected().isEqualTo(agmt.isRegulatoryAgreement());
        }

        if (agmt.getAgreedJurisdiction() != null) {
            validateThat("ap.agreed.jurisdiction").attributeValueOf("value")
                    .isEqualToIgnoringWhitespace(agmt.getAgreedJurisdiction().getFilter().getRealValue());
        }

        if (agmt.getAgreementDescription() != null) {
            validateThat("ap.desc").attributeValueOf("value")
                    .isEqualToIgnoringWhitespace(agmt.getAgreementDescription().getRealValue());
        }
    }

    public void searchOrganisation(OrganisationSearch search) throws Exception {
        if (search.getCriteria() != null)
            element("ap.frame.criteria").selectByVisibleText(search.getCriteria().value());
        if (search.getType() != null)
            element("ap.frame.type").selectByVisibleText(search.getType().value());
        if (search.getFilter() != null)
            element("ap.frame.filter").input(search.getFilter().getRealValue());
        element("ap.frame.search").clickByJavaScript();

        if (search.getLinkText() != null)
            element("ap.frame.link", search.getLinkText().getRealValue()).click();
    }
    
    /**
     * enter next stage while setup agreement
     */
    public void enterNextStage() throws Exception {
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        element("ap.next").clickByJavaScript();
        PageHelper.d33640Workaround();
    }
    
    /**
     * click save & exit
     */
    public void saveAndExit() throws Exception {
        element("ap.save.and.exit").click();
    }

    public void copyAndSave(String times) throws Exception {
        element("ap.duplicate.count").input(times);
        saveAndExit();
    }
}
