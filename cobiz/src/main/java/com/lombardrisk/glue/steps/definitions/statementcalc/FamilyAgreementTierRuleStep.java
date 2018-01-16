package com.lombardrisk.glue.steps.definitions.statementcalc;

import com.lombardrisk.pages.PageManager;
import com.lombardrisk.pojo.AgreementSearchResult;
import com.lombardrisk.pojo.FeedAgreement;
import com.lombardrisk.pojo.StringType;
import com.lombardrisk.pojo.TierType;
import com.lombardrisk.test.TestDataManager;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yiwan.webcore.test.ITestBase;
import org.yiwan.webcore.test.TestCaseManager;

import java.util.List;


public class FamilyAgreementTierRuleStep {
    private final static Logger logger = LoggerFactory.getLogger(FamilyAgreementTierRuleStep.class);
    private ITestBase testCase = TestCaseManager.getTestCase();
    private TestDataManager testDataManager = (TestDataManager) testCase.getTestDataManager();
    private PageManager pageManager = (PageManager) testCase.getPageManager();

    /**
     * Description: Set linked agreement id on a tier of a family agreement<br/>
     * Start page: agreement search page<br/>
     * End page: agreement search page<br/>
     *
     * @param searchResultId  The id to create a:<br /><li>{@link AgreementSearchResult}</li>
     * @param feedAgreementId The id to create a:<br /><li>{@link FeedAgreement}</li>
     * @throws Exception
     */
    @When("^set a linked agreement on tier (\\S+) (\\S+)$")
    public void setALinkedAgreementOnThirdTier(String searchResultId, String feedAgreementId) throws Exception {
        AgreementSearchResult searchResult = testDataManager.getAgreementSearchResult(searchResultId);
        String etdAgrId = pageManager.agreementSearchPage.getAgreementId(searchResult);
        testCase.embedTestData(searchResult);
        FeedAgreement.FamilyTiers familyTiers = testDataManager.getFeedAgreement(feedAgreementId).getFamilyTiers();
        TierType firstLevel = familyTiers.getTier();

        List<TierType> secondLevel = firstLevel.getTier();
        for (TierType tierLevel : secondLevel) {
            if (tierLevel.getTierName().contentEquals("Tier3")){
                tierLevel.setLinkedAgreement(new StringType(etdAgrId));
            }
        }

    }
}
