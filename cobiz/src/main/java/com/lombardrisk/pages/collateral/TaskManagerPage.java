package com.lombardrisk.pages.collateral;

import com.lombardrisk.pages.AbstractFeedPage;
import com.lombardrisk.pojo.*;
import org.yiwan.webcore.web.IWebDriverWrapper;

import java.lang.reflect.Method;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class TaskManagerPage extends AbstractFeedPage {
    public TaskManagerPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void assertFeedResult(FeedResult feedResult) throws Exception {
        if (feedResult.getWrongFileSpecified() != null) {
            assertThat("tm.wrong.file.specified").innerText().matches(feedResult.getWrongFileSpecified().getRealValue());
        } else {
            if (feedResult.getFileUploaded() == null
                    || (feedResult.getFileUploaded().getRealValue().isEmpty()
                    || (feedResult.getFileUploaded().isApply() != null && feedResult.getFileUploaded().isApply()))) {
                String file = element("tm.result.fileuploaded").getInnerText().trim();
                if (feedResult.getFileUploaded() == null) {
                    StringType value = new StringType(file);
                    feedResult.setFileUploaded(value);
                } else {
                    feedResult.getFileUploaded().setValue(file);
                }
            } else {
                String file = element("tm.result.fileuploaded").getInnerText().trim();
                    feedResult.getFileUploaded().setValue(file);
                if (element("tm.result.fileuploaded").isDisplayed())
                    assertThat("tm.result.fileuploaded").innerText().isEqualToIgnoringWhitespace(feedResult.getFileUploaded().getRealValue());
            }

            if (feedResult.getUploadState() != null)
                assertThat("tm.result.feedstate").innerText().isEqualToIgnoringWhitespace(feedResult.getUploadState().getRealValue());

            if (feedResult.getSuccessfullyImported() != null) {
                assertThat("tm.success.count.import").innerText().isEqualToIgnoringWhitespace(feedResult.getSuccessfullyImported().getRealValue());
            }
            if (feedResult.getUnsuccessfulImports() != null) {
                assertThat("tm.unsuccessful.count").innerText().isEqualToIgnoringWhitespace(feedResult.getUnsuccessfulImports().getRealValue());
//          for(StringType stringType : feedResult.getUnsuccessfulImports()) {
//              assertThat("tm.unsuccessful.count").innerText().isEqualToIgnoringWhitespace(stringType.getRealValue());
//          }
            }
            if (feedResult.getParsingFailures() != null) {
                assertThat("tm.parsing.fail.count").innerText().isEqualToIgnoringCase(feedResult.getParsingFailures().getRealValue());
            }
            if (feedResult.getAdditionalInfo() != null) {
                assertThat("tm.additional.info").innerText().matches(feedResult.getAdditionalInfo().getRealValue());
            }
        }
    }

    public void assertFeedStatus(FeedStatus feedStatus) throws Exception {
        String xpath = getFeedStatusRow(feedStatus);
        assertThat("tm.result.feedrecord", xpath).displayed().isTrue();
        FeedLog feedLog;
        if (feedStatus.getSuccessfulFeed() != null && feedStatus.getSuccessfulFeed().isApply() != null && feedStatus.getSuccessfulFeed().isApply()) {
            element("fl.successful.feed", xpath).clickByJavaScript();
            feedLog = feedStatus.getSuccessfulFeed();
            if (feedLog.getImportType() != null)
                assertThat("fl.log.importtype").innerText().isEqualToIgnoringWhitespace(feedLog.getImportType().getRealValue());
            if (feedLog.getImportDateAndTime() != null)
                assertThat("fl.log.dateandtime").innerText().isEqualToIgnoringWhitespace(feedLog.getImportDateAndTime().getRealValue());
            if (feedLog.getValuationDateAndTime() != null)
                assertThat("fl.log.valuationdataandtime").innerText().isEqualToIgnoringWhitespace(feedLog.getValuationDateAndTime().getRealValue());
            if (feedLog.getValuationFile() != null)
                assertThat("fl.log.valuationfile").innerText().isEqualToIgnoringWhitespace(feedLog.getValuationFile().getRealValue());
            if (feedLog.getTotal() != null)
                assertThat("fl.log.total").innerText().isEqualToIgnoringWhitespace(feedLog.getTotal().getRealValue());
            if (feedLog.getEntry() != null && !feedLog.getEntry().isEmpty()) {
                for (Entry entry : feedLog.getEntry()) {
                    if (entry.getContent() != null) {
                        String contentVal;
                        contentVal = entry.getContent().getRealValue();
                        if (entry.getParameter() != null && !entry.getParameter().isEmpty()) {
                            for (ParameterType feedLogParameterType : entry.getParameter()) {
                                if (feedLogParameterType.getParameterName() != null && feedLogParameterType.getParameterValue() != null)
                                    contentVal = contentVal.replace(feedLogParameterType.getParameterName().getRealValue(), feedLogParameterType.getParameterValue().getRealValue());
                            }
                        }
                        assertThat("fl.log.entry", contentVal).displayed().isTrue();
                    }
                }
            }
            navigate().backward();
        }else if(feedStatus.getSuccessfulFeed() != null && (feedStatus.getSuccessfulFeed().isApply() == null || !feedStatus.getSuccessfulFeed().isApply())){
//            assertThat("fl.successful.feed", xpath).displayed().isFalse();
            assertThat("fl.successful.feed.notlink", xpath).displayed().isTrue();
        }
        if (feedStatus.getFailedFeed() != null && feedStatus.getFailedFeed().isApply() != null && feedStatus.getFailedFeed().isApply()) {
            element("fl.failed.feed", xpath).clickByJavaScript();
            feedLog = feedStatus.getFailedFeed();
            if (feedLog.getImportType() != null)
                assertThat("fl.log.importtype").innerText().isEqualToIgnoringWhitespace(feedLog.getImportType().getRealValue());
            if (feedLog.getImportDateAndTime() != null)
                assertThat("fl.log.dateandtime").innerText().isEqualToIgnoringWhitespace(feedLog.getImportDateAndTime().getRealValue());
            if (feedLog.getValuationDateAndTime() != null)
                assertThat("fl.log.valuationdataandtime").innerText().isEqualToIgnoringWhitespace(feedLog.getValuationDateAndTime().getRealValue());
            if (feedLog.getValuationFile() != null)
                assertThat("fl.log.valuationfile").innerText().isEqualToIgnoringWhitespace(feedLog.getValuationFile().getRealValue());
            if (feedLog.getTotal() != null)
                assertThat("fl.log.total").innerText().isEqualToIgnoringWhitespace(feedLog.getTotal().getRealValue());
            if (feedLog.getEntry() != null && !feedLog.getEntry().isEmpty()) {
                for (Entry entry : feedLog.getEntry()) {
                    if (entry.getContent() != null) {
                        String contentVal;
                        contentVal = entry.getContent().getRealValue();
                        if (entry.getParameter() != null && !entry.getParameter().isEmpty()) {
                            for (ParameterType feedLogParameterType : entry.getParameter()) {
                                if (feedLogParameterType.getParameterName() != null && feedLogParameterType.getParameterValue() != null)
                                    contentVal = contentVal.replace(feedLogParameterType.getParameterName().getRealValue(), feedLogParameterType.getParameterValue().getRealValue());
                            }
                        }
                        assertThat("fl.log.entry", contentVal).displayed().isTrue();
                    }
                }
            }
            navigate().backward();
        }else if(feedStatus.getFailedFeed() != null && (feedStatus.getFailedFeed().isApply() == null || !feedStatus.getFailedFeed().isApply())) {
//            assertThat("fl.failed.feed", xpath).displayed().isFalse();
            assertThat("fl.failed.feed.notlink", xpath).displayed().isTrue();
        }
    }

    private String getFeedStatusRow(FeedStatus feedStatus) throws Exception {
        StringBuffer xpath = new StringBuffer();
        Method[] methods = feedStatus.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get")
                    && (method.getReturnType() == StringType.class || method.getReturnType() == FeedLog.class)
                    && method.invoke(feedStatus) != null) {
                if (method.getReturnType() == StringType.class) {
                    String value = ((StringType) method.invoke(feedStatus)).getRealValue();
                    switch (method.getName()) {
                        case "getSystemName":
//                        xpath.append("[td[2][contains(.,'" + value + "')] and string-length(td[2])=string-length('" + value + "')+2]");
                            xpath.append("[td[2][contains(.,'").append(value).append("')] and string-length(td[2])=string-length('").append(value).append("')+2]");
                            break;
                        case "getDescription":
//                        xpath.append("[td[8][contains(.,'" + value + "')] and string-length(td[8])=string-length('" + value + "')+2]");
                            xpath.append("[td[8][contains(.,'").append(value).append("')] and string-length(td[8])=string-length('").append(value).append("')+2]");
                            break;
                        default:
//                        xpath.append("[td[normalize-space(.)='" + value + "']]");
                            xpath.append("[td[normalize-space(.)='").append(value).append("']]");
                            break;
                    }
                } else {
                    FeedLog feedLog = (FeedLog) method.invoke(feedStatus);
                    if (feedLog.getTotal() != null) {
                        String total = feedLog.getTotal().getRealValue();
                        if (method.getName().equals("getSuccessfulFeed")) {
//                            xpath.append("[td[9]/a[normalize-space(.)='" + total + "'] or td[9][normalize-space(.)='" + total + "']]");
                            xpath.append("[td[9]/a[normalize-space(.)='").append(total).append("'] or td[9][normalize-space(.)='").append(total).append("']]");
                        } else {
//                            xpath.append("[td[10]/a[normalize-space(.)='" + total + "']]");
                            xpath.append("[td[10]/a[normalize-space(.)='").append(total).append("'] or td[10][normalize-space(.)='").append(total).append("']]");
                        }
                    }
                }
            }
        }
        return xpath.toString();
    }
}
