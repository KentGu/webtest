package com.lombardrisk.util.feed;

import com.lombardrisk.pojo.*;
import com.lombardrisk.util.feed.agreement.AgreementFeed;
import com.lombardrisk.util.feed.agreement.ia.ExternalIAFeed;
import com.lombardrisk.util.feed.agreement.nav.NAVFeed;
import com.lombardrisk.util.feed.agreement.rating.AgreementRatingFeed;
import com.lombardrisk.util.feed.agreement.si.SettlementInstructionFeed;
import com.lombardrisk.util.feed.agreement.tsa.PortfolioTSAFeed;
import com.lombardrisk.util.feed.agreement.udf.AgreementUDFFeed;
import com.lombardrisk.util.feed.asset.pricing.AssetPricingFeed;
import com.lombardrisk.util.feed.asset.rating.AssetRatingFeed;
import com.lombardrisk.util.feed.booking.AssetBookingFeed;
import com.lombardrisk.util.feed.booking.InventoryManagerFeed;
import com.lombardrisk.util.feed.fxrate.FxRateFeed;
import com.lombardrisk.util.feed.interest.InterestAmountFeed;
import com.lombardrisk.util.feed.interest.rate.InterestRateFeed;
import com.lombardrisk.util.feed.mtm.MtMFeed;
import com.lombardrisk.util.feed.org.OrganisationFeed;
import com.lombardrisk.util.feed.org.contact.OrganisationContactFeed;
import com.lombardrisk.util.feed.other.HolidayCentreFeed;
import com.lombardrisk.util.feed.other.StaticDataFeed;
import com.lombardrisk.util.feed.other.WeekendFeed;
import com.lombardrisk.util.feed.security.SecurityTemplateFeed;
import com.lombardrisk.util.feed.template.EligibilityRulesTemplateFeed;
import com.lombardrisk.util.feed.trade.TradeFeed;
import com.lombardrisk.util.feed.trade.etf.ETFTradeFeed;
import com.lombardrisk.util.feed.trade.repo.RepoSettlementFeed;
import com.lombardrisk.util.feed.trade.repo.RepoTradeFeed;
import com.lombardrisk.util.feed.workflow.CounterpartyAmountFeed;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory to provide feed logic.
 * Created by Lawrence Xu on 5/22/2017.
 */
public final class FeedFactory {

    private static final Map<Class, IFeedLogic> FEED_MAP;

    static {
        FEED_MAP = new HashMap<>();
        FEED_MAP.put(FeedAgreement.class, new AgreementFeed());
        FEED_MAP.put(FeedAgreementRating.class, new AgreementRatingFeed());
        FEED_MAP.put(FeedAgreementUDF.class, new AgreementUDFFeed());
        FEED_MAP.put(FeedAssetPricing.class, new AssetPricingFeed());
        FEED_MAP.put(FeedAssetRating.class, new AssetRatingFeed());
        FEED_MAP.put(FeedFxRate.class, new FxRateFeed());
        FEED_MAP.put(FeedTrade.class, new TradeFeed());
        FEED_MAP.put(FeedETFTrade.class, new ETFTradeFeed());
        FEED_MAP.put(FeedRepoTrade.class, new RepoTradeFeed());
        FEED_MAP.put(FeedMtM.class, new MtMFeed());
        FEED_MAP.put(FeedOrganisation.class, new OrganisationFeed());
        FEED_MAP.put(FeedOrganisationContact.class, new OrganisationContactFeed());
        FEED_MAP.put(FeedAssetBooking.class, new AssetBookingFeed());
        FEED_MAP.put(FeedInventoryManager.class, new InventoryManagerFeed());
        FEED_MAP.put(FeedCounterpartyAmount.class, new CounterpartyAmountFeed());
        FEED_MAP.put(FeedRepoSettlement.class, new RepoSettlementFeed());
        FEED_MAP.put(FeedPortfolioTSA.class, new PortfolioTSAFeed());
        FEED_MAP.put(FeedExternalIA.class, new ExternalIAFeed());
        FEED_MAP.put(FeedInterestRate.class, new InterestRateFeed());
        FEED_MAP.put(FeedSettlementInstruction.class, new SettlementInstructionFeed());
        FEED_MAP.put(FeedInterestAmount.class, new InterestAmountFeed());
        FEED_MAP.put(FeedHolidayCentre.class, new HolidayCentreFeed());
        FEED_MAP.put(FeedWeekend.class, new WeekendFeed());
        FEED_MAP.put(FeedStaticData.class, new StaticDataFeed());
        FEED_MAP.put(FeedNAV.class, new NAVFeed());
        FEED_MAP.put(FeedSecurityTemplate.class, new SecurityTemplateFeed());
        FEED_MAP.put(FeedEligibilityRulesTemplate.class, new EligibilityRulesTemplateFeed());
    }

    private FeedFactory() {
        throw new AssertionError();
    }

    /**
     * Get corresponding feed logic.
     * @param clazz class of object
     * @return feed logic
     */
    public static IFeedLogic<?> getFeedLogic(Class clazz) {
        return FEED_MAP.get(clazz);
    }
}
