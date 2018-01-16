package com.lombardrisk.util.collateral;

import com.lombardrisk.pojo.StringType;
import org.apache.commons.lang.StringUtils;

public final class AgreementUtils {
    private AgreementUtils() {}

    /**
     * Gets the category name as it would be displayed in the HTML pages in colline
     * i.e. CAT_EUR_90213713 (EUR)
     * @param categoryName
     * @param currency
     * @return
     */
    public static String getDisplayedCategoryName(String categoryName, String currency) {
        if (StringUtils.isNotEmpty(currency)) {
            return categoryName + " (" + currency + ")";
        }
        return categoryName;
    }

    public static String getDisplayedCategoryName(StringType categoryName, StringType currency) {
        String categoryNameDerefenced = categoryName == null ? null : categoryName.getRealValue();
        String currencyDereferenced = currency == null ? null : currency.getRealValue();
        return getDisplayedCategoryName(categoryNameDerefenced, currencyDereferenced);
    }
}
