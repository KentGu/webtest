package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.*;
import com.lombardrisk.util.Biz;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class PortfolioDetailsPage extends PageBase {
    public PortfolioDetailsPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    /**
     * create letter
     *
     * @param marginLetterType
     */
    public void letterProcessing(MarginLetterType marginLetterType) throws Exception {
        if (marginLetterType != null)
            element("ls.letter", marginLetterType.value()).click();
    }

    public void goToAgreementStatement() throws Exception {
        element("ls.agr.statement").click();
    }

    public void goToNoteOrDisputeHistory() throws Exception {
        element("ls.notedispute.history").click();
    }

    public void goToDiaryEvents() throws Exception {
        element("ls.diary.event").click();
    }

    public void goToLetterHistory() throws Exception {
        element("ls.letter.history").click();
    }

    public void setupNotesOrDisputeHistory(NotesOrDisputeHistory notesOrDisputeHistory) throws  Exception {
        if(notesOrDisputeHistory != null) {
            goToNoteOrDisputeHistory();
            if (notesOrDisputeHistory.getDisputeHistory() != null && !notesOrDisputeHistory.getDisputeHistory().isEmpty()) {
                for (DisputeHistory disputeHistory : notesOrDisputeHistory.getDisputeHistory()) {
                    if (disputeHistory.isRemove() != null && disputeHistory.isRemove()) {
                        element("ls.dispute.history.remove", getNotesOrDisputeHistoryRow(disputeHistory)).clickByJavaScript();
                    } else {
                        if (disputeHistory.getCurrentDisputeHistory() != null)
                            element("ls.dispute.history.txtarea").type(disputeHistory.getCurrentDisputeHistory().getRealValue());
                        element("ls.dispute.history.save").click();
                    }
                }
            }
            if (notesOrDisputeHistory.getNotes() != null && !notesOrDisputeHistory.getNotes().isEmpty()) {
                for (Note note : notesOrDisputeHistory.getNotes()) {
                    if (note.isRemove() != null && note.isRemove()) {
                        element("ls.note.remove", getNotesOrDisputeHistoryRow(note)).clickByJavaScript();
                    } else {
                        if (note.getCurrentNote() != null)
                            element("ls.note.txtarea").type(note.getCurrentNote().getRealValue());
                        if (note.isReminder() != null)
                            element("ls.note.reminder").check(note.isReminder());
                        element("ls.note.save").click();
                    }
                }
            }
            if (notesOrDisputeHistory.getDocumentation() != null && !notesOrDisputeHistory.getDocumentation().isEmpty()) {
                for (FileAttachmentType fileAttachmentType : notesOrDisputeHistory.getDocumentation()) {
                    if (fileAttachmentType.isRemove() != null && fileAttachmentType.isRemove()) {
                        element("ls.doc.remove", getNotesOrDisputeHistoryRow(fileAttachmentType)).clickByJavaScript();
                    } else {
                        element("ls.doc.add").click();
                        if (fileAttachmentType.getAttachmentName() != null)
                            element("ls.doc.attach.name").type(fileAttachmentType.getAttachmentName().getRealValue());
                        if (fileAttachmentType.getDescription() != null)
                            element("ls.doc.description").type(fileAttachmentType.getDescription().getRealValue()).fireEvent("onkeyup");
                        element("ls.doc.save").click();
                    }
                }
            }
        }
    }

    public void downloadNotesOrDisputeHistoryDocument(FileAttachmentType fileAttachmentType) throws Exception{
        element("ls.doc.download", getNotesOrDisputeHistoryRow(fileAttachmentType)).click();
    }

    private String getNotesOrDisputeHistoryRow(Object obj) throws Exception{
        StringBuffer xpath = new StringBuffer();
        Method[] methods = obj.getClass().getDeclaredMethods();
        for(Method method : methods){
            if(method.getParameterTypes().length == 0 && method.invoke(obj) != null){
                if(method.getReturnType() == StringType.class){
                    StringType stringType = (StringType) method.invoke(obj);
                    xpath.append("[td='").append(stringType.getRealValue()).append("']");
                }else if(method.getName().equals("isReminder")){
                    boolean flag = (Boolean) method.invoke(obj);
                    if(flag)
                        xpath.append("[td/img[contains(@src,'tickb.gif')]]");
                }
            }
        }
        return xpath.toString();
    }

    public void setupDiaryEvents(List<DiaryEvents> list) throws Exception{
        if(list != null && !list.isEmpty()) {
            goToDiaryEvents();
            for (DiaryEvents diaryEvents : list) {
                if (diaryEvents.isRemove() != null && diaryEvents.isRemove()) {
                    element("ls.diary.event.del", getDiaryEventsRow(diaryEvents)).clickByJavaScript();
                } else if(diaryEvents.getName() != null && (diaryEvents.isApply() == null || !diaryEvents.isApply())){
                    element("ls.diary.event.edit", getDiaryEventsRow(diaryEvents)).click();
                    DiaryEvents editDiaryEvents = (DiaryEvents) Biz.matchedObjectWithNameAndIsApplied(diaryEvents, list);
                    setupDiaryEvents(editDiaryEvents);
                } else {
                    setupDiaryEvents(diaryEvents);
                }
            }
        }
    }

    private String getDiaryEventsRow(DiaryEvents diaryEvents) {
        StringBuffer xpath = new StringBuffer();
        if(diaryEvents.getDiaryEventDetails() != null)
            xpath.append("[td[contains(text()[2],'").append(diaryEvents.getDiaryEventDetails().getRealValue()).append("')][string-length(text()[2])=string-length('").append(diaryEvents.getDiaryEventDetails().getRealValue()).append("')+60]]");
        if(diaryEvents.getEventDate() != null)
            xpath.append("[td='").append(diaryEvents.getEventDate().getRealValue()).append("']");
        if(diaryEvents.getRecurringFrequency() != null)
            xpath.append("[td='").append(diaryEvents.getRecurringFrequency().value()).append("']");
        if(diaryEvents.getMonthlyCalculationRule() != null)
            xpath.append("[td='").append(diaryEvents.getMonthlyCalculationRule().value()).append("']");
        else if(diaryEvents.getFrequencyMonthDays() != null && !diaryEvents.getFrequencyMonthDays().isEmpty()){
            StringBuffer freqMonDay = new StringBuffer();
            for(StringType stringType : diaryEvents.getFrequencyMonthDays()){
                freqMonDay.append(stringType.getRealValue()).append(",");
            }
            xpath.append("[td='").append(freqMonDay.deleteCharAt(freqMonDay.length() - 1).toString()).append("']");
        }else if(diaryEvents.getFrequencyWeekDays() != null && !diaryEvents.getFrequencyWeekDays().isEmpty()){
            StringBuffer freqWkDay = new StringBuffer();
            for(WeekType weekType : diaryEvents.getFrequencyWeekDays()){
                freqWkDay.append(weekType.value()).append(",");
            }
            xpath.append("[td='").append(freqWkDay.deleteCharAt(freqWkDay.length() - 1).toString()).append("']");
        }
        if(diaryEvents.getTrigger() != null)
            xpath.append("[td='").append(diaryEvents.getTrigger().getRealValue()).append("']");
        return xpath.toString();
    }

    public void setupDiaryEvents(DiaryEvents diaryEvents) throws Exception {
        if (diaryEvents != null) {
            if (diaryEvents.getDiaryEventsType() != null && diaryEvents.getDiaryEventsType() == DiaryEventsType.SINGLE_EVENT) {
                element("ls.diary.single.event").click();
                if (diaryEvents.getEventDate() != null)
                    element("ls.diary.single.event.date").input(diaryEvents.getEventDate().getRealValue());
            } else if (diaryEvents.getDiaryEventsType() != null && diaryEvents.getDiaryEventsType() == DiaryEventsType.RECURRING_EVENT) {
                element("ls.diary.recurring.event").click();
                if (diaryEvents.getRecurringFrequency() != null) {
                    element("ls.diary.recurring.freq").selectByVisibleText(diaryEvents.getRecurringFrequency().value());
                    if (diaryEvents.getRecurringFrequency() == FrequencyType.WEEKLY) {
                        element("ls.diary.recurring.freqweek").deselectAll();
                        if (diaryEvents.getFrequencyWeekDays() != null) {
                            for (WeekType weekType : diaryEvents.getFrequencyWeekDays()) {
                                element("ls.diary.recurring.freqweek").selectByVisibleText(weekType.value());
                            }
                        }
                    } else if (diaryEvents.getRecurringFrequency() == FrequencyType.MONTHLY) {
                        if (diaryEvents.getFrequencyMonthDays() != null) {
                            element("ls.diary.recurring.freqmonth").deselectAll();
                            for (StringType stringType : diaryEvents.getFrequencyMonthDays()) {
                                element("ls.diary.recurring.freqmonth").selectByVisibleText(stringType.getRealValue());
                            }
                        }
                        if (diaryEvents.getMonthlyCalculationRule() != null)
                            element("ls.diary.recurring.freqmonth.calrule").selectByVisibleText(diaryEvents.getMonthlyCalculationRule().value());
                    }

                }
                if (diaryEvents.isRollForward() != null)
                    element("ls.diary.recurring.rollfor").check(diaryEvents.isRollForward());
                if (diaryEvents.isRollBackward() != null)
                    element("ls.diary.recurring.rollbak").check(diaryEvents.isRollBackward());
            }
            if (diaryEvents.getEventLabel() != null)
                element("ls.diary.event.label").selectByVisibleText(diaryEvents.getEventLabel().value());
            if (diaryEvents.getDiaryEventDetails() != null)
                element("ls.diary.event.details").input(diaryEvents.getDiaryEventDetails().getRealValue());
            element("ls.diary.event.save").click();
        }
    }

    public void assertPortfolioDetailsPage(PortfolioDetails portfolioDetails) throws Exception{
        assertLetterProcessing(portfolioDetails.getLetterProcessing());
        assertNotesOrDisputeHistory(portfolioDetails.getNotesOrDisputeHistory());
        assertDiaryEvents(portfolioDetails.getDiaryEvents());
    }

    private void assertLetterProcessing(List<LetterProcessing> list) throws Exception {
        if (list != null && !list.isEmpty()) {
            for (LetterProcessing letterProcessing : list) {
                if (letterProcessing.isApply() != null && !letterProcessing.isApply()) {
                    assertThat("ls.letterProcessingType", letterProcessing.getLetterProcessingType().value()).displayed().isFalse();
                } else {
                    assertThat("ls.letterProcessingType", letterProcessing.getLetterProcessingType().value()).displayed().isTrue();
                }
            }
        }
    }


        private void assertDiaryEvents(List<DiaryEvents> list) throws Exception{
        if(list != null && !list.isEmpty()){
            goToDiaryEvents();
            for(DiaryEvents diaryEvents : list){
                assertThat("ls.diary.event.edit", getDiaryEventsRow(diaryEvents)).displayed().isTrue();
            }
        }
    }

    private void assertNotesOrDisputeHistory(NotesOrDisputeHistory notesOrDisputeHistory) throws Exception{
        if(notesOrDisputeHistory != null){
            goToNoteOrDisputeHistory();
            if(notesOrDisputeHistory.getDisputeHistory() != null && !notesOrDisputeHistory.getDisputeHistory().isEmpty()){
                for(DisputeHistory disputeHistory : notesOrDisputeHistory.getDisputeHistory()){
                    assertThat("ls.dispute.history.remove", getNotesOrDisputeHistoryRow(disputeHistory)).displayed().isTrue();
                }
            }
            if(notesOrDisputeHistory.getNotes() != null && !notesOrDisputeHistory.getNotes().isEmpty()){
                for(Note note : notesOrDisputeHistory.getNotes()){
                    assertThat("ls.note.remove", getNotesOrDisputeHistoryRow(note)).displayed().isTrue();
                }
            }
            if(notesOrDisputeHistory.getDocumentation() != null && !notesOrDisputeHistory.getDocumentation().isEmpty()){
                for(FileAttachmentType fileAttachmentType : notesOrDisputeHistory.getDocumentation()){
                    assertThat("ls.doc.remove", getNotesOrDisputeHistoryRow(fileAttachmentType)).displayed().isTrue();
                }
            }
        }
    }
}
