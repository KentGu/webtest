package com.lombardrisk.pages.systemadmin;

import com.lombardrisk.pojo.*;
import com.lombardrisk.util.Biz;
import org.yiwan.webcore.test.ITestBase;
import org.yiwan.webcore.util.PropHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class TaskSchedulerPage extends PageBase {
    public TaskSchedulerPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void enterWorkflowTab() throws Exception {
        element("sa.task.workflow.tab").click();
        //element("sa.task.edit.reset.total.settlement.amt").click();
    }

    public void switchToTaskScheduleTab(TaskSchedulerDetail taskSchedulerDetail) throws Exception {
        if (taskSchedulerDetail.getType() != null){
            element("sa.task.tab",taskSchedulerDetail.getType().value()).click();
        }
    }
    
    public void editTaskSchedule(TaskSchedulerDetail taskSchedulerDetail) throws Exception {
        if (taskSchedulerDetail.getTaskName() != null){
            element("sa.task.edit",taskSchedulerDetail.getTaskName().getRealValue()).scrollIntoView().clickByJavaScript();
        }
    }

    public void inputTaskSchedule(TaskSchedulerDetail taskSchedulerDetail) throws Exception {
        if (taskSchedulerDetail.getCategory() != null){
            element("sa.input.category").selectByVisibleText(taskSchedulerDetail.getCategory().getRealValue());
        }
        if (taskSchedulerDetail.getFrequency() != null){
            element("sa.input.frequency").selectByVisibleText(taskSchedulerDetail.getFrequency().getRealValue());
        }
        if (taskSchedulerDetail.getStartTime() != null){
            if (taskSchedulerDetail.getStartTime().getStartTimeHour() != null){
                element("sa.input.startTimeHour").selectByVisibleText(taskSchedulerDetail.getStartTime().getStartTimeHour().getRealValue());
            }
            if (taskSchedulerDetail.getStartTime().getStartTimeMinute() != null){
                element("sa.input.startTimeMinute").selectByVisibleText(taskSchedulerDetail.getStartTime().getStartTimeMinute().getRealValue());
            }
            if (taskSchedulerDetail.getStartTime().getTimeZone() != null){
                element("sa.input.timeZone").selectByVisibleText(taskSchedulerDetail.getStartTime().getTimeZone().getRealValue());
            }
        }
        if (taskSchedulerDetail.getEndTime() != null){
            element("sa.input.endTime").selectByVisibleText(taskSchedulerDetail.getEndTime().getRealValue());
        }
        if (taskSchedulerDetail.getRepeatInterval() != null){
            element("sa.input.repeatInterval").selectByVisibleText(taskSchedulerDetail.getRepeatInterval().getRealValue());
        }
        if (taskSchedulerDetail.getPriority() != null){
            element("sa.input.priority").selectByVisibleText(taskSchedulerDetail.getPriority().getRealValue());
        }
        if (taskSchedulerDetail.getAdditionalInfo() != null){
            element("sa.input.additionalInfo").input(taskSchedulerDetail.getAdditionalInfo().getRealValue());
        }
        if (taskSchedulerDetail.isActive() != null){
            element("sa.input.active").check(taskSchedulerDetail.isActive());
        }
        if (taskSchedulerDetail.isRunAsSystemUser() != null){
            element("sa.input.runAsSystemUser").check(taskSchedulerDetail.isRunAsSystemUser());
        }
        if (taskSchedulerDetail.getRegion() != null && taskSchedulerDetail.getRegion().size() > 0){
            element("sa.region").deselectAll();
            List<StringType> regions = taskSchedulerDetail.getRegion();
            for (StringType region : taskSchedulerDetail.getRegion()){
                element("sa.region").selectByVisibleText(region.toString());
            }
        }
        if (taskSchedulerDetail.getGroup() != null && taskSchedulerDetail.getGroup().size() > 0){
            element("sa.group").deselectAll();
            List<StringType> groups = taskSchedulerDetail.getGroup();
            for (StringType group : taskSchedulerDetail.getGroup()){
                element("sa.group").selectByVisibleText(group.toString());
            }
        }

        if (taskSchedulerDetail.getBusinessLine() != null && taskSchedulerDetail.getBusinessLine().size() > 0){
            element("sa.businessLine").deselectAll();
            List<StringType> businessLines = taskSchedulerDetail.getBusinessLine();
            for (StringType businessLine : taskSchedulerDetail.getBusinessLine()){
                element("sa.businessLine").selectByVisibleText(businessLine.toString());
            }
        }

        if (taskSchedulerDetail.getSystemUserPassword() != null){
            element("sa.input.systemUserPassword").input(taskSchedulerDetail.getSystemUserPassword().getRealValue());
        }
        if (taskSchedulerDetail.getConfirmedPassword() != null){
            element("sa.input.confirmedPassword").input(taskSchedulerDetail.getConfirmedPassword().getRealValue());
        }
        if (taskSchedulerDetail.getPreTaskScriptFile() != null){
            element("sa.input.preTaskScriptFile").input(taskSchedulerDetail.getPreTaskScriptFile().getRealValue());
        }
        if (taskSchedulerDetail.getPostTaskScriptFile() != null){
            element("sa.input.postTaskScriptFile").input(taskSchedulerDetail.getPostTaskScriptFile().getRealValue());
        }
        if (taskSchedulerDetail.getMisc1() != null){
            element("sa.input.miscInfo1").input(taskSchedulerDetail.getMisc1().getRealValue());
        }
        if (taskSchedulerDetail.getMisc2() != null){
            element("sa.input.miscInfo2").input(taskSchedulerDetail.getMisc2().getRealValue());
        }
    }

    public void save() throws Exception {
        element("sa.input.save").click();
    }

    public void cancel() throws Exception {
        element("sa.input.cancel").click();
    }
    
    public void duplicatedTaskSchedule(TaskSchedulerDetail taskSchedulerDetail) throws Exception {
        if (taskSchedulerDetail.getTaskName() != null){
            element("sa.task.duplicate",taskSchedulerDetail.getTaskName().getRealValue()).click();
        }
    }
    
    public void removeTaskSchedule(TaskSchedulerDetail taskSchedulerDetail) throws Exception {
        if (taskSchedulerDetail.getTaskName() != null){
            element("sa.task.remove", taskSchedulerDetail.getTaskName().getRealValue()).click();
        }
    }
    
    public void runNow(TaskSchedulerDetail taskSchedulerDetail) throws Exception {
        if (taskSchedulerDetail.getTaskName() != null){
            element("sa.task.runNow", taskSchedulerDetail.getTaskName().getRealValue()).click();
        }
    }
    
    public void saveAllWorkflowTasks() throws Exception{
        element("sa.task.saveAll").click();
    }

    public void saveAllMyWorkflowTasks() throws Exception{
        element("sa.task.saveMyTasks").click();
    }

    public <T> void copyFeedXlsFile(ITestBase testCase, Feed feed, List<T> list, StringType serverFolderPath, StringType fileName) throws Exception {
        Date date = getServerTime();
        for(T obj : list){
            Biz.realize(obj, date);
        }
        if(feed.getFileName() != null) {
            feed.getFileName().setValue(Biz.getXlsFeedFile(list));
        }else{
            feed.setFileName(new StringType(Biz.getXlsFeedFile(list)));
        }
//        fileName.setValue(feed.getFileName().getRealValue());
        fileName.setValue((new File(feed.getFileName().getRealValue()).getName()));
        URL url = new URL(getCurrentUrl());
        String host = url.getHost();
        Biz.resetFolderBySSH(testCase, serverFolderPath.getRealValue(), host);
        Biz.copyFileToServerBySSH(testCase, fileName.getRealValue(), serverFolderPath.getRealValue(), host);
//        String serverPath = "\\\\" + url.getHost() + "\\Feed\\" + list.get(0).getClass().getSimpleName();
//        Biz.removeFilesInFolder(serverPath);
//        Biz.copyFileToServer(feed.getFileName().getRealValue(), serverPath);
    }

    public <T> void getTaskScheduelResult(ITestBase testCase, StringType serverFolderPath, StringType fileName) throws Exception {
        URL url = new URL(getCurrentUrl());
        String host = url.getHost();
        Biz.getFileFromServerBySSH(testCase, fileName.getRealValue(), serverFolderPath.getRealValue(), host);
    }

    public void createResultFolderOnServer(ITestBase testCase, StringType serverFolderPath) throws Exception{
        URL url = new URL(getCurrentUrl());
        String host = url.getHost();
        Biz.resetFolderBySSH(testCase, serverFolderPath.getRealValue(), host);
    }

    public <T> void copyFeedXlsxFile(ITestBase testCase, Feed feed, List<T> list, StringType serverFolderPath, StringType fileName) throws Exception {
        Date date = getServerTime();
        for(T obj : list){
            Biz.realize(obj, date);
        }
        if(feed.getFileName() != null) {
            feed.getFileName().setValue(Biz.getXlsxFeedFile(list));
        }else{
            feed.setFileName(new StringType(Biz.getXlsxFeedFile(list)));
        }
//        fileName.setValue(feed.getFileName().getRealValue());
        fileName.setValue((new File(feed.getFileName().getRealValue()).getName()));
        URL url = new URL(getCurrentUrl());
        String host = url.getHost();
        Biz.resetFolderBySSH(testCase, serverFolderPath.getRealValue(), host);
        Biz.copyFileToServerBySSH(testCase, fileName.getRealValue(), serverFolderPath.getRealValue(), host);
//        String serverPath = "\\\\" + url.getHost() + "\\Feed\\" + list.get(0).getClass().getSimpleName();
//        Biz.removeFilesInFolder(serverPath);
//        Biz.copyFileToServer(feed.getFileName().getRealValue(), serverPath);
    }

    public <T> void copyFeedXmlFile(ITestBase testCase, Feed feed, List<T> list, StringType serverFolderPath, StringType fileName) throws Exception {
        Date date = getServerTime();
        for(T obj : list){
            Biz.realize(obj, date);
        }
        if(feed.getFileName() != null) {
            feed.getFileName().setValue(Biz.getXmlFeedFile(list));
        }else{
            feed.setFileName(new StringType(Biz.getXmlFeedFile(list)));
        }
//        fileName.setValue(feed.getFileName().getRealValue());
        fileName.setValue((new File(feed.getFileName().getRealValue()).getName()));
        URL url = new URL(getCurrentUrl());
        String host = url.getHost();
        Biz.resetFolderBySSH(testCase, serverFolderPath.getRealValue(), host);
        Biz.copyFileToServerBySSH(testCase, fileName.getRealValue(), serverFolderPath.getRealValue(), host);
//        String serverPath = "\\\\" + url.getHost() + "\\Feed\\" + list.get(0).getClass().getSimpleName();
//        Biz.removeFilesInFolder(serverPath);
//        Biz.copyFileToServer(feed.getFileName().getRealValue(), serverPath);
    }

    public <T> void copyFeedCsvFile(ITestBase testCase, Feed feed, List<T> list, StringType serverFolderPath, StringType fileName) throws Exception {
        Date date = getServerTime();
        for(T obj : list){
            Biz.realize(obj, date);
        }
        if(feed.getFileName() != null) {
            feed.getFileName().setValue(Biz.getCsvFeedFile(list));
        }else{
            feed.setFileName(new StringType(Biz.getCsvFeedFile(list)));
        }
//        fileName.setValue(feed.getFileName().getRealValue());
        fileName.setValue((new File(feed.getFileName().getRealValue()).getName()));
        URL url = new URL(getCurrentUrl());
        String host = url.getHost();
        Biz.resetFolderBySSH(testCase, serverFolderPath.getRealValue(), host);
        Biz.copyFileToServerBySSH(testCase, fileName.getRealValue(), serverFolderPath.getRealValue(), host);
//        String serverPath = "\\\\" + url.getHost() + "\\Feed\\" + list.get(0).getClass().getSimpleName();
//        Biz.removeFilesInFolder(serverPath);
//        Biz.copyFileToServer(feed.getFileName().getRealValue(), serverPath);
    }

    public Date getServerTime() throws Exception{
        Date date;
        String time;
        if(element("hm.server.time", "scrollingMessage").isDisplayed()){
            time = element("hm.server.time", "scrollingMessage").getInnerText();
        }else if(element("hm.server.time", "noNewScrollingMessage").isDisplayed()){
            time = element("hm.server.time", "noNewScrollingMessage").getInnerText();
        }else{
            time = element("hm.server.time", "nonScrollingMessage").getInnerText();
        }
        if(time == null || time.equals("")) {
            date = new Date();
        }else {
            try {
                date = new Date(time);
            }catch (IllegalArgumentException iae){
                SimpleDateFormat s = new SimpleDateFormat("EEEE, d MMMM yyyy");
                date = s.parse(time);
            }
        }
        return date;
    }

    public void refreshSchedulerMessage() throws Exception{
        element("sa.refresh.log").clickByJavaScript();
    }

    public void assertSchedulerMessages(TaskSchedulerMessage taskSchedulerMessage) throws Exception{
        long rowCount = element("sa.log.tab").getRowCount();
        int exit = 0;
        while(element("sa.log.tab").getRowCount() <= rowCount){
            refreshSchedulerMessage();
            waitThat().timeout(PropHelper.TIMEOUT_INTERVAL);
            exit++;
            if(exit == 4)
                break;
        }
        if(taskSchedulerMessage.getMessage() != null)
            assertThat("sa.log.message").innerText().matches(taskSchedulerMessage.getMessage().getRealValue());
    }
}
