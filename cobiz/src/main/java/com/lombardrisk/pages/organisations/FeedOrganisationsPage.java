package com.lombardrisk.pages.organisations;

import com.lombardrisk.pages.AbstractFeedPage;
import com.lombardrisk.pojo.FeedResult;
import org.yiwan.webcore.web.IWebDriverWrapper;

/**
 * @author Kenny Wang
 */
public final class FeedOrganisationsPage extends AbstractFeedPage {

    public FeedOrganisationsPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    /**
     * check feed result
     *
     * @param feedResult
     */
    public void assertFeedResult(FeedResult feedResult) throws Exception {
        if (feedResult.getUploadState() != null)
            assertThat("og.uploadComplete").innerText().isEqualTo(feedResult.getUploadState().getRealValue());
        if (feedResult.getSuccessfullyImported() != null)
            assertThat("og.success.count.import").innerText().isEqualTo(feedResult.getSuccessfullyImported().getRealValue());
        if (feedResult.getSuccessfullyModified() != null)
            assertThat("og.success.count.modify").innerText().isEqualTo(feedResult.getSuccessfullyModified().getRealValue());
        if (feedResult.getUnsuccessfulImports() != null) {
            assertThat("og.unsuccess.count.import").innerText().isEqualTo(feedResult.getUnsuccessfulImports().getRealValue());
//            for (StringType unsuccessfulImport : feedResult.getUnsuccessfulImports()) {
//                assertThat("og.unsuccess.count.import").innerText().isEqualTo(unsuccessfulImport.getRealValue());
//            }
        }
        if (feedResult.getUnchangedOrIgnoredImports() != null)
            assertThat("og.count.unchangedOrIgnored").innerText().isEqualTo(
                    feedResult.getUnchangedOrIgnoredImports().getRealValue());
        if (feedResult.getAdditionalInfo() != null)
            assertThat("og.additionalInfo").innerText().isEqualTo(feedResult.getAdditionalInfo().getRealValue());
    }

}
