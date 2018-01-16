package com.lombardrisk.util;

import com.jcraft.jsch.*;
import com.lombardrisk.pojo.*;
import com.lombardrisk.util.feed.FeedFactory;
import com.lombardrisk.util.feed.IFeedLogic;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.examples.ToCSV;
import org.assertj.core.api.SoftAssertions;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yiwan.webcore.test.ITestBase;
import org.yiwan.webcore.test.TestCaseManager;
import org.yiwan.webcore.util.Helper;
import org.yiwan.webcore.util.PropHelper;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Kenny Wang
 */
public class Biz extends Helper {
    public static final String PREFIX = "qtp";
    public static final String FIELD_SEPARATOR = "|";
    public static final String SEPARATOR = ">";
    public static final String TRIGGER_EVENT_CLICK = "click";
    public static final String TRIGGER_EVENT_MOUSEOVER = "mouseover";
    public static final String TRIGGER_EVENT_MOUSEOUT = "mouseout";
    public static final String FIRE_EVENT_ONCLICK = "onclick";
    public static final String FIRE_EVENT_ONCHANGE = "onchange";
    public static final String MAIN_ORG = "org.main";
    public static final String J_WINNAME = "j_winname";
    private static final Logger logger = LoggerFactory.getLogger(Biz.class);


    /**
     * generate csv feed files
     *
     * @param list
     * @return {@link String }
     */
    public static <T> String getCsvFeedFile(List<T> list)
            throws IOException, IllegalAccessException, ParseException, InvalidFormatException {
        if (list != null && !list.isEmpty()) {
            String path = getXlsxFeedFile(list);
            ToCSV toCSV = new ToCSV();
            File file = new File(path);
            toCSV.convertExcelToCSV(path, file.getParent());
            return path.replace("xlsx", "csv");
        }
        return null;
    }

    /**
     * generate excel feed files
     *
     * @param list
     * @return {@link String }
     */
    public static <T> String getXlsFeedFile(List<T> list) throws IOException, IllegalAccessException, ParseException {
        if (list != null && !list.isEmpty()) {
            String fileName = randomize() + ".xls";
            String filePath = getWorkspace() + File.separator + PropHelper.TARGET_SCENARIO_DATA_FOLDER + "feed" + File.separator + fileName;
            File file = new File(filePath);
            File folder = new File(file.getParent());
            if (!folder.exists())
                folder.mkdir();
            if (!file.exists())
                file.createNewFile();
            PoiHelper.marshalXls(list).write(new FileOutputStream(file));
            return file.getAbsolutePath();
        }
        return null;
    }

    public static <T> String getXlsxFeedFile(List<T> list) throws IOException, IllegalAccessException, ParseException {
        if (list != null && !list.isEmpty()) {
            String fileName = randomize() + ".xlsx";
            String filePath = getWorkspace() + File.separator + PropHelper.TARGET_SCENARIO_DATA_FOLDER + "feed" + File.separator + fileName;
            File file = new File(filePath);
            File folder = new File(file.getParent());
            if (!folder.exists())
                folder.mkdir();
            if (!file.exists())
                file.createNewFile();
            PoiHelper.marshalXlsx(list).write(new FileOutputStream(file));
            return file.getAbsolutePath();
        }
        return null;
    }

    /**
     * generate xml feed files
     *
     * @param list {@link List}
     * @return {@link String }
     */
    @SuppressWarnings({"unchecked"})
    public static <T> String getXmlFeedFile(List<T> list) throws IOException, JAXBException {
        if (list != null && !list.isEmpty()) {
            String fileName = randomize() + ".xml";
            String filePath = getWorkspace() + File.separator + PropHelper.TARGET_SCENARIO_DATA_FOLDER + "feed" + File.separator + fileName;
            String xmlString = null;
            IFeedLogic feedLogic = FeedFactory.getFeedLogic(list.get(0).getClass());
            if (feedLogic != null) {
                xmlString = feedLogic.convertListToXML(list);
            }

            File file = new File(filePath);
            File folder = new File(file.getParent());
            if (!folder.exists())
                folder.mkdir();
            if (!file.exists())
                file.createNewFile();
            PrintWriter writer = new PrintWriter(file);
            if (xmlString != null)
                writer.write(xmlString);
            writer.close();
            return file.getAbsolutePath();
        }
        return null;
    }

    public static void compareXmlFile(SoftAssertions softAssertions, String sourceFilePath, String actualFilePath) throws Exception {
        File sourceFile = new File(sourceFilePath);
        File actualFile = new File(actualFilePath);
        String sourceStr = FileUtils.readFileToString(sourceFile, Charset.defaultCharset());
        String actualStr = FileUtils.readFileToString(actualFile, Charset.defaultCharset());
        assertXMLEquals(softAssertions, sourceStr, actualStr);
    }

    private static void assertXMLEquals(SoftAssertions softAssertions, String expectedXML, String actualXML) throws Exception {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
        XMLUnit.setIgnoreComments(true);
        DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(expectedXML, actualXML));
        List<?> allDifferencesList = diff.getAllDifferences();
        softAssertions.assertThat(allDifferencesList.size()).as(StringUtils.join(allDifferencesList.toArray(), "\n")).isEqualTo(0);
    }

    public static void validationReport(SoftAssertions softAssertions, String format, StringType reportFile, ReportValidationRules reportRules) throws Exception {
        switch (format.toLowerCase()) {
            case "excel worksheet":
                validateExcelReport(softAssertions, reportFile, reportRules);
            default:
                break;
        }
    }

    private static void validateExcelReport(SoftAssertions softAssertions, StringType reportFile, ReportValidationRules reportRules) throws Exception {
        List<Map<String, String>> resultList =PoiHelper.exchangeExcelToList(reportFile);
        validateReport(softAssertions, reportFile, reportRules, resultList);
    }

    private static void validateReport(SoftAssertions softAssertions, StringType reportFile, ReportValidationRules reportRules, List<Map<String, String>> resultList) {
        if (reportRules.getReportValidationRule() != null && reportRules.getReportValidationRule().size() > 0) {
            for (ReportValidationRule reportRule : reportRules.getReportValidationRule()) {
                int meetRulesCount = 0;
                String rule = "";
                if (reportRule.getField() != null && reportRule.getField().size() > 0) {
                    for (Map<String, String> eachRow : resultList) {
                        boolean flag = false;
                        for (Field field : reportRule.getField()) {
                            if (field.getFieldName() != null && field.getValue() != null) {
                                flag = eachRow.get(field.getFieldName().getRealValue()).replace("\n", "\\n").equals(field.getValue().getRealValue());
                                if (!flag) break;
                            }
                        }
                        if (flag)
                            meetRulesCount += 1;
                    }
                    for (int i = 0; i < reportRule.getField().size(); i++) {
                        if (reportRule.getField().get(i).getFieldName() != null && reportRule.getField().get(i).getValue() != null) {
                            rule += reportRule.getField().get(i).getFieldName().getRealValue() + " = " + reportRule.getField().get(i).getValue().getRealValue() + "\n";
//                            if (i<reportRule.getField().size()-1){
//                                rule+="\n";
//                            }
                        }
                    }
                } else {
                    meetRulesCount = resultList.size();
                    rule = "all rows ";
                }

                rule = "validate report " + reportFile.getRealValue() + " row counts with rules : \n" + rule + reportRule.getOperator();
                switch (reportRule.getOperator()) {
                    case "=":
                        softAssertions.assertThat(meetRulesCount).as(rule).isEqualTo(reportRule.getCount());
                        break;
                    case ">":
                        softAssertions.assertThat(meetRulesCount).as(rule).isGreaterThan(reportRule.getCount());
                        break;
                    case ">=":
                        softAssertions.assertThat(meetRulesCount).as(rule).isGreaterThanOrEqualTo(reportRule.getCount());
                        break;
                    case "<":
                        softAssertions.assertThat(meetRulesCount).as(rule).isLessThan(reportRule.getCount());
                        break;
                    case "<=":
                        softAssertions.assertThat(meetRulesCount).as(rule).isLessThanOrEqualTo(reportRule.getCount());
                        break;
                }
            }
        }
    }

    public static Double getRepoInterestAccrued(TradeDetail trade, Agreement agr) {
        double repoInterestAccrued = 0;
        // Open Cash * [(Repo Rate/100)/Year count basis] * Cash Accrual Day
        // Count
//        if (trade.getOpenCash() != null && trade.getRepoRate() != null) {
//            ArrayList<Integer> args = getCashCouponDayCount(trade, agr);
//            double f1 = Double.parseDouble(trade.getOpenCash().getRealValue())
//                    * (Double.parseDouble(trade.getRepoRate().getRealValue()) / 100 / args.get(0)) * args.get(1);
//            BigDecimal b = new BigDecimal(f1);
//            repoInterestAccrued = b.setScale(7, BigDecimal.ROUND_HALF_UP).doubleValue();
//        }
        // TODO report or log the invalid input data
        return repoInterestAccrued;
    }

    /**
     * get coupon day count according to the Accrual Basis
     *
     * @param trade
     * @param agr
     * @return {@link ArrayList }
     */
    public static ArrayList<Integer> getCashCouponDayCount(TradeDetail trade, Agreement agr) {
        long date1 = 0;
        long date2 = 0;
        Integer couponDayCount = 0;
        Integer yearCountBasics = 0;
//        if (trade.getCashAccrualBasics() != null) {
//            ArrayList<String> time = getYearMonthDay();
//            // String [] aDay = trade.getCashAccrualBasics().split("/");
//            // String sDaysPerMonth = aDay[0];
//            // String sDaysPerYear = aDay[1];
//            switch (trade.getCashAccrualBasics().getRealValue()) {
//                case "30/360":
//                    yearCountBasics = 360;
//                    if (trade.getStartDate() != null) {
//                        ArrayList<String> startDate = getYearMonthDay(trade.getStartDate().getRealValue());
//                        couponDayCount = (Integer.parseInt(time.get(0)) - Integer.parseInt(startDate.get(0))) * 360
//                                + (Integer.parseInt(time.get(1)) - Integer.parseInt(startDate.get(1))) * 30
//                                + (Integer.parseInt(time.get(2)) - Integer.parseInt(startDate.get(2))) + countDay(agr);
//                    }
//                    break;
//                case "30E/365":
//                    yearCountBasics = 365;
//                    if (trade.getStartDate() != null) {
//                        ArrayList<String> startDate = getYearMonthDay(trade.getStartDate().getRealValue());
//                        couponDayCount = (Integer.parseInt(time.get(0)) - Integer.parseInt(startDate.get(0))) * 365
//                                + (Integer.parseInt(time.get(1)) - Integer.parseInt(startDate.get(1))) * 30
//                                + (Integer.parseInt(time.get(2)) - Integer.parseInt(startDate.get(2))) + countDay(agr);
//                    }
//                    break;
//                case "30E/Act":
//                    date1 = getDateTime(showDate());
//                    date2 = getDateTime(trade.getStartDate().getRealValue());
//                    couponDayCount = dateInterval(date1, date2);
//                    break;
//                case "Act/360":
//                    date1 = getDateTime(showDate());
//                    date2 = getDateTime(trade.getStartDate().getRealValue());
//                    couponDayCount = dateInterval(date1, date2);
//                    break;
//                case "Act/365":
//                    date1 = getDateTime(showDate());
//                    date2 = getDateTime(trade.getStartDate().getRealValue());
//                    couponDayCount = dateInterval(date1, date2);
//                    break;
//                case "Act/Act":
//                    date1 = getDateTime(showDate());
//                    date2 = getDateTime(trade.getStartDate().getRealValue());
//                    couponDayCount = dateInterval(date1, date2);
//                    break;
//                default:
//                    break;
//            }
//
//        }
        ArrayList<Integer> list = new ArrayList<>();
        list.add(yearCountBasics);
        list.add(couponDayCount);
        return list;
    }

    /**
     * count ExposureProfile's value By holiday Centre [include weekend]
     *
     * @param agr
     * @return int
     */
    public static int countDay(Agreement agr) throws ParseException {
        int f1;
        if (agr.getExposureProfile() != null && !agr.getExposureProfile().getRealValue().equals("T")) {
            f1 = Integer.parseInt(agr.getExposureProfile().getRealValue().substring(2));
            for (int i = 0; i <= f1; i++) {
                int day = dayForWeek() + i;
                if (day == 6 || day == 7) {
                    f1 = f1 + 1;
                }
            }
        } else {
            f1 = 0;
        }
        return f1;
    }

    /**
     * Calculate the number of days between two dates
     *
     * @param date1
     * @param date2
     * @return int
     */
    public static int dateInterval(long date1, long date2) {
        if (date2 > date1) {
            date2 = date2 + date1;
            date1 = date2 - date1;
            date2 = date2 - date1;
        }

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        // check whether the same year or not
        int y1 = calendar1.get(Calendar.YEAR);
        int y2 = calendar2.get(Calendar.YEAR);

        int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int day;
        if (y1 - y2 > 0) {
            day = numerical(d1, d2, y1, y2, calendar2);
        } else {
            day = d1 - d2;
        }
        return day;
    }

    /**
     * Date interval computation eg:20121201- 20121212
     *
     * @param d1
     * @param d2
     * @param y1
     * @param y2
     * @param calendar
     * @return int
     */
    public static int numerical(int d1, int d2, int y1, int y2, Calendar calendar) {
        int day = d1 - d2;
        int betweenYears = y1 - y2;
        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
        List<Integer> d366 = new ArrayList<>();

        if (calendar.getActualMaximum(Calendar.DAY_OF_YEAR) == 366) {
            System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
            day += 1;
        }

        for (int i = 0; i < betweenYears; i++) {
            calendar.set(Calendar.YEAR, (calendar.get(Calendar.YEAR)) + 1);

            if (maxDays != 366) {
                day += maxDays;
            } else {
                d366.add(maxDays);
            }
            if (i == betweenYears - 1 && betweenYears > 1 && maxDays == 366) {
                day -= 1;
            }
        }
        if (!d366.isEmpty()) {
            for (Integer aD366 : d366) {
                day += aD366;
            }
        }
        return day;
    }

    /**
     * judge if this year is a leap year
     *
     * @param year
     * @return boolean
     */
    public static boolean judgeYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    /**
     * get current year 、 month 、day
     */
    public static ArrayList<String> getYearMonthDay() {
        DateFormat formatYear = new SimpleDateFormat("yyyy");
        DateFormat formatMonth = new SimpleDateFormat("MM");
        DateFormat formatDay = new SimpleDateFormat("dd");
        Date now = new Date(System.currentTimeMillis());
        String[] compareArray = {"01", "02", "03", "04", "05", "06", "07", "08", "09"};
        String year = formatYear.format(now);
        String month = formatMonth.format(now);
        String day = formatDay.format(now);
        for (String aCompareArray : compareArray) {
            if (month.equals(aCompareArray)) {
                month = month.substring(1);
            }
            if (day.equals(aCompareArray)) {
                day = day.substring(1);
            }
        }
        int intMonth = Integer.parseInt(month);
        month = intMonth + "";
        ArrayList<String> list = new ArrayList<>();
        list.add(year);
        list.add(month);
        list.add(day);
        return list;
    }

    /**
     * get the year 、 month 、day of the arg
     *
     * @param date
     * @return {@link ArrayList }
     */
    public static ArrayList<String> getYearMonthDay(String date) {
        String[] arg = date.split("-");
        String[] compareArray = {"01", "02", "03", "04", "05", "06", "07", "08", "09"};
        for (String aCompareArray : compareArray) {
            if (arg[1].equals(aCompareArray)) {
                arg[1] = arg[1].substring(1);
            }
            if (arg[2].equals(aCompareArray)) {
                arg[2] = arg[2].substring(1);
            }
        }
        int intMonth = Integer.parseInt(arg[1]);
        arg[1] = intMonth + "";
        ArrayList<String> list = new ArrayList<>();
        list.add(arg[0]);
        list.add(arg[1]);
        list.add(arg[2]);
        System.out.println(list);
        return list;
    }

    /**
     * judge current the week of date
     *
     * @return int
     */
    public static int dayForWeek() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(showDate()));
        int dayForWeek;
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }

        return dayForWeek;
    }

    /**
     * get current system time
     *
     * @return {@link String }
     */
    public static String showDate() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date(System.currentTimeMillis());
        return format.format(now);

    }

    /**
     * convert date string to date
     *
     * @param strDate eg:yyyyMMdd
     * @return long
     */
    public static long getDateTime(String strDate) throws ParseException {
        return getDateByFormat(strDate, "yyyyMMdd").getTime();
    }

    public static String convertAmount(String amount) {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("#,#00.00");
        if (amount.matches("^-?(\\d+|[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*)[kmbKMB]$")) {
            String lastLowerCase = String.valueOf(amount.charAt(amount.length() - 1)).toLowerCase();
            String amountPart = amount.substring(0, amount.length() - 1);
            double i = Double.parseDouble(amountPart);
            switch (lastLowerCase) {
                case "m":
                    i = i * 1000000;
                    break;
                case "k":
                    i = i * 1000;
                    break;
                case "b":
                    i = i * 1000000000;
                    break;
            }
            return format.format(i);
        } else if (amount.matches("^-?\\d+$")) {
            int i = Integer.parseInt(amount);
            return format.format(i);
        } else
            return amount;
    }

    public static String convertDate(Date serverTime, String date, String timeFormat) {
        int i = 0;
        if (date.trim().matches("^[Tt]([+]\\d+)$")) {
            i = Integer.valueOf(date.split("[+]")[date.split("[+]").length - 1]);
            return Biz.getCollineTime(serverTime, i, timeFormat);
        } else if (date.trim().matches("^[Tt](-\\d+)$")) {
            i = 0 - Integer.valueOf(date.split("-")[date.split("-").length - 1]);
            return Biz.getCollineTime(serverTime, i, timeFormat);
        } else if (date.trim().matches("^[Tt]$")) {
            return Biz.getCollineTime(serverTime, i, timeFormat);
        } else
            return date;

    }

    public static String getCollineTime(Date date, int offset, String timeFormat) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, offset);
        DateFormat format = new SimpleDateFormat(timeFormat);
        Date ret = calendar.getTime();
        return format.format(ret);
    }

    /**
     * @param strDate
     * @param format
     * @return {@link Date }
     */
    public static Date getDateByFormat(String strDate, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(strDate);
    }

    public static <T> T realize(T object) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = object.getClass().getDeclaredMethods();
        if (object.getClass().getPackage().getName().equals("com.lombardrisk.pojo")) {
            for (Method method : methods) {
                if (method.getName().startsWith("get")) {
                    Object ret;
                    ret = method.invoke(object);
                    if (ret != null) {
                        if (ret.getClass().equals(StringType.class)) {
                            ((StringType) ret).getRealValue();
                        } else {
                            if (ret instanceof List) {
                                @SuppressWarnings("unchecked")
                                List<T> list = (List<T>) ret;
                                for (T obj : list) {
                                    realize(obj);
                                }
                            } else {
                                realize(ret);
                            }
                        }
                    }
                }
            }
        }
        return object;
    }

    public static <T> T realize(T object, Date date) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = object.getClass().getDeclaredMethods();
        if (object.getClass().getPackage().getName().equals("com.lombardrisk.pojo")) {
            for (Method method : methods) {
                if (method.getName().startsWith("get")) {
                    Object ret;
                    ret = method.invoke(object);
                    if (ret != null) {
                        if (ret.getClass().equals(StringType.class)
                                || (ret instanceof JAXBElement && ((JAXBElement) ret).getDeclaredType().equals(StringType.class))) {
                            StringType v;
                            if (ret instanceof JAXBElement) {
                                v = (StringType) ((JAXBElement) ret).getValue();
                            } else {
                                v = (StringType) ret;
                            }
                            if ((v.isRandom() != null && v.isRandom())
                                    && (v.getVariation() != null) && v.getVariation().matches("^[Tt]([+|-]\\d+)?$")) {
                                int i = 0;
                                if (v.getVariation().matches("^[Tt]([+]\\d+)$")) {
                                    i = Integer.valueOf(v.getVariation().split("[+]")[v.getVariation().split("[+]").length - 1]);
                                } else if (v.getVariation().matches("^[Tt](-\\d+)$")) {
                                    i = 0 - Integer.valueOf(v.getVariation().split("-")[v.getVariation().split("-").length - 1]);
                                }
                                v.setValue(getCollineTime(date, i, "yyyy-MM-dd"));
                                v.setRandom(false);
                            } else {
                                v.getRealValue();
                            }
                        } else {
                            if (ret instanceof List) {
                                @SuppressWarnings("unchecked")
                                List<T> list = (List<T>) ret;
                                for (T obj : list) {
                                    realize(obj, date);
                                }
                            } else {
                                realize(ret, date);
                            }
                        }
                    }
                }
            }
        }
        return object;
    }

    public static StringType realize(StringType object) {
        object.getRealValue();
        return object;
    }

    /**
     * @param obj
     * @param list
     * @return {@link Object }
     */
    public static Object matchedObjectWithName(Object obj, List list) throws Exception {
        if (list != null && list.size() > 0 && obj.getClass().getName().equals(list.get(0).getClass().getName())) {
            String name = null;
            Method method = obj.getClass().getDeclaredMethod("getName");
            if (method.invoke(obj) != null)
                name = (String) method.invoke(obj);
            for (Object o : list) {
                if (method.invoke(o) != null) {
                    String v = (String) method.invoke(o);
                    if (v.equals(name))
                        return o;
                }
            }
        }
        return null;
    }

    public static Object matchedObjectWithNameAndIsApplied(Object obj, List list) throws Exception {
        List<Object> retList = new ArrayList<>();
        if (obj.getClass().getName().equals(list.get(0).getClass().getName())) {
            String name = null;
            Method method = obj.getClass().getDeclaredMethod("getName");
            if (method.invoke(obj) != null)
                name = (String) method.invoke(obj);
            for (Object o : list) {
                if (method.invoke(o) != null) {
                    String v = (String) method.invoke(o);
                    if (v.equals(name))
                        retList.add(o);
                }
            }
        }
        for (Object o : retList) {
            Method method = obj.getClass().getDeclaredMethod("isApply");
            if (method.invoke(o) != null && (boolean) method.invoke(o))
                return o;
        }
        return null;
    }

    /**
     * @param obj
     * @param list
     * @return {@link Object }
     */
    public static Object matchedLetterConfigurationWithLetterType(Object obj, List list) throws Exception {
        MarginLetterConfigurationType marginLetterConfigurationType = null;
        Method[] objMethods = obj.getClass().getDeclaredMethods();
        for (Method m : objMethods) {
            if (m.getName().equals("getLetterType")
                    && m.invoke(obj) != null) {
                marginLetterConfigurationType = (MarginLetterConfigurationType) m.invoke(obj);
            }
        }

        for (Object o : list) {
            objMethods = o.getClass().getDeclaredMethods();
            for (Method m : objMethods) {
                if (m.getName().equals("getLetterType")
                        && m.invoke(o) != null) {
                    MarginLetterConfigurationType v = (MarginLetterConfigurationType) m.invoke(o);
                    if (v == marginLetterConfigurationType)
                        return o;
                }
            }
        }
        return null;
    }

    public static String getColumnName(String targetName, String regEx) {
        String[] columnName = targetName.split(regEx);
        StringBuffer columnBuffer = new StringBuffer();
        for (String name : columnName)
            columnBuffer.append(name).append(" ");
        return columnBuffer.toString().trim();
    }

    public static String getWorkspace() {
        return System.getProperty("user.dir");
    }

    @SuppressWarnings("Annotator")
    public static void copyFileToServer(String filePath, String destPath) throws Exception {
        String filename = File.separator.equals("\\") ? filePath.split("\\" + File.separator)[filePath.split("\\" + File.separator).length - 1] : filePath.split(File.separator)[filePath.split(File.separator).length - 1];
//        if (File.separator.equals("\\")) {
//            filename = filePath.split("\\" + File.separator)[filePath.split("\\" + File.separator).length - 1];
//        } else {
//            filename = filePath.split(File.separator)[filePath.split(File.separator).length - 1];
//        }

        Files.copy(new File(filePath).toPath()
                , new File(destPath + File.separator + filename).toPath()
                , StandardCopyOption.REPLACE_EXISTING);
    }

    public static void removeFilesInFolder(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists()) {
            if (folder.listFiles() != null) {
                for (File file : folder.listFiles()) {
                    file.delete();
                }
            }
        } else {
            folder.mkdir();
        }
    }

    public static void createFolderBySSH(ITestBase testCase, String folderPath, String host) throws Exception {
//        File file = new File(folderPath);
        String command = "if [ ! -d '" + folderPath + "' ]; then mkdir '" + folderPath
                + "'; chmod 777 " + folderPath
                + "; fi\n";
        sshCommand(testCase, command, host);
    }

    public static void resetFolderBySSH(ITestBase testCase, String folderPath, String host) throws Exception {
        File file = new File(folderPath);
        String parentFolder = file.getParent();
        if (testCase.getTestEnvironment().getApplicationServers().get(0).getHardwareInformation() != null
                && testCase.getTestEnvironment().getApplicationServers().get(0).getHardwareInformation().getOs() != null) {
            if (!testCase.getTestEnvironment().getApplicationServers().get(0).getHardwareInformation().getOs().equalsIgnoreCase("windows"))
                parentFolder = parentFolder.replace(File.separator, "/");
        }
        String command = "rm -r " + parentFolder + "\n"
                + "mkdir " + parentFolder + "\n"
                + "chmod 777 -R " + parentFolder + "\n"
                + "cd " + parentFolder + "\n"
                + "mkdir " + folderPath + "\n"
                + "chmod 777 -R " + folderPath + "\n"
                + "ls -l\n";
        sshCommand(testCase, command, host);
    }

    public static void sshCommand(ITestBase testCase, String command, String host) throws Exception {
        boolean flag = true;
        String keyFile = testCase.getTestEnvironment().getApplicationServers().get(0).getKey();
        String user = testCase.getTestEnvironment().getApplicationServers().get(0).getUsername();
        Channel channel = null;
        Session session = null;

        try {
            JSch jSch = new JSch();
            jSch.addIdentity(keyFile);
            session = jSch.getSession(user, host, 22);

            // username and passphrase will be given via UserInfo interface.
            UserInfo ui = new MyUserInfo();
            session.setUserInfo(ui);
            session.connect();
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            InputStream in = channel.getInputStream();
            channel.connect();
            logger.debug("Executing ssh command:\n" + command);

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    logger.debug(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    if (in.available() > 0) continue;
                    logger.debug("exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                    logger.error(ee.getMessage(), ee);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            flag = false;
        } finally {
            if (channel != null)
                channel.disconnect();
            if (session != null)
                session.disconnect();
            if (!flag)
                throw new Exception();
            else
                logger.debug("Executed ssh command:\n" + command);
        }

    }

    public static void copyFileToServerBySSH(ITestBase testCase, String localFile, String destinationFolder, String host) throws Exception {

        boolean flag = true;
        String keyFile = testCase.getTestEnvironment().getApplicationServers().get(0).getKey();
        String user = testCase.getTestEnvironment().getApplicationServers().get(0).getUsername();
        Channel channel = null;
        Session session = null;
        FileInputStream fis = null;
//        boolean ptimestamp = true;
//        String command = "scp -i " + keyFile + (ptimestamp ? " -p" : "") + " -t " + destinationFolder;
        String command = "scp -i " + keyFile + " -p -t " + destinationFolder;

        try {
            JSch jSch = new JSch();
            jSch.addIdentity(keyFile);
            session = jSch.getSession(user, host, 22);
            UserInfo ui = new MyUserInfo();
            session.setUserInfo(ui);
            session.connect();
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            InputStream in = channel.getInputStream();
            OutputStream out = channel.getOutputStream();
            channel.connect();
            logger.debug("Executing ssh command:\n" + command);

            if (checkAck(in) != 0) {
                logger.error("checkAct failed");
                throw new Exception();
            }
            File _lfile = new File(PropHelper.getProperty("path.target.scenario.data.folder") + "feed/" + localFile);
//            if (ptimestamp) {
//                command = "T " + (_lfile.lastModified() / 1000) + " 0";
//                command += (" " + (_lfile.lastModified() / 1000) + " 0\n");
//                out.write(command.getBytes());
//                out.flush();
//                if (checkAck(in) != 0) {
//                    logger.error("checkAct failed");
//                    throw new Exception();
//                }
//            }
            command = "T " + (_lfile.lastModified() / 1000) + " 0";
            command += (" " + (_lfile.lastModified() / 1000) + " 0\n");
            out.write(command.getBytes());
            out.flush();
            if (checkAck(in) != 0) {
                logger.error("checkAct failed");
                throw new Exception();
            }
            long fileSize = _lfile.length();
            command = "C0644 " + fileSize + " ";
            if (localFile.lastIndexOf(File.separator) > 0) {
                command += localFile.substring(localFile.lastIndexOf(File.separator) + 1);
            } else {
                command += localFile;
            }
            command += "\n";
            out.write(command.getBytes());
            out.flush();
            if (checkAck(in) != 0) {
                logger.error("checkAct failed");
                throw new Exception();
            }
            fis = new FileInputStream(PropHelper.getProperty("path.target.scenario.data.folder") + "feed/" + localFile);
            byte[] buf = new byte[1024];
            while (true) {
                int len = fis.read(buf, 0, buf.length);
                if (len <= 0) break;
                out.write(buf, 0, len); //out.flush();
            }
            fis.close();
            fis = null;
            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            if (checkAck(in) != 0) {
                logger.error("checkAct failed");
                throw new Exception();
            }
            out.close();
            if (channel.isClosed()) {
                logger.debug("exit-status: " + channel.getExitStatus());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            flag = false;
            try {
                if (fis != null)
                    fis.close();
            } catch (Exception ee) {
                logger.error(ee.getMessage(), ee);
            }
        } finally {
            if (channel != null)
                channel.disconnect();
            if (session != null)
                session.disconnect();
            if (!flag)
                throw new Exception();
            else {
                logger.debug("Executed ssh command:\n" + command);
                logger.debug("Check if file has been successfully copied");
                String checkCommon = "chmod 777 -R " + destinationFolder + "\n"
                        + "cd " + destinationFolder + "\n"
                        + "pwd\n" + "du -a -k -h\n" + "ls -l\n";
                sshCommand(testCase, checkCommon, host);
            }
        }
    }

    public static void getFileFromServerBySSH(ITestBase testCase, String localFile, String serverFolder, String host) throws Exception {

        String keyFile = testCase.getTestEnvironment().getApplicationServers().get(0).getKey();
        String user = testCase.getTestEnvironment().getApplicationServers().get(0).getUsername();
        Channel channel = null;
        Session session = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        String commandRename = "cd " + serverFolder + "\n"
                + "mv *.* " + localFile + ".xml" + "\n"
                + "ls -l" + "\n";
        sshCommand(testCase, commandRename, host);
        String command = "scp -f " + serverFolder + "/" + localFile + ".xml "; //-i " + keyFile + "

        try {
            JSch jSch = new JSch();
            jSch.addIdentity(keyFile);
            session = jSch.getSession(user, host, 22);
            UserInfo ui = new MyUserInfo();
            session.setUserInfo(ui);
            session.connect();
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            InputStream in = channel.getInputStream();
            OutputStream out = channel.getOutputStream();
            channel.connect();
            byte[] buf = new byte[1024];
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            while (true) {
                int c = checkAck(in);
                if (c != 'C') {
                    break;
                }
                in.read(buf, 0, 5);
                long filesize = 0L;
                while (true) {
                    if (in.read(buf, 0, 1) < 0) {
                        // error
                        break;
                    }
                    if (buf[0] == ' ') break;
                    filesize = filesize * 10L + (long) (buf[0] - '0');
                }
                String file;
                for (int i = 0; ; i++) {
                    in.read(buf, i, 1);
                    if (buf[i] == (byte) 0x0a) {
                        file = new String(buf, 0, i);
                        break;
                    }
                }
                buf[0] = 0;
                out.write(buf, 0, 1);
                out.flush();
                fos = new FileOutputStream(new File(PropHelper.getProperty("path.target.scenario.data.folder") + file));
                int foo;
                while (true) {
                    if (buf.length < filesize)
                        foo = buf.length;
                    else foo = (int) filesize;
                    foo = in.read(buf, 0, foo);
                    if (foo < 0) {
                        // error
                        break;
                    }
                    fos.write(buf, 0, foo);
                    filesize -= foo;
                    if (filesize == 0L) break;
                }
                fos.close();
                fos = null;

                if (checkAck(in) != 0) {
                    throw new Exception();
                }

                // send '\0'
                buf[0] = 0;
                out.write(buf, 0, 1);
                out.flush();
            }
            session.disconnect();
            throw new Exception();

        } catch (Exception e) {
            logger.error(e.getMessage());
            try {
                if (fos != null)
                    fos.close();
            } catch (Exception ee) {
                logger.error(ee.getMessage(), ee);
            }
        } finally {
            if (channel != null)
                channel.disconnect();
            if (session != null)
            session.disconnect();
        }
        logger.debug("Executed ssh command:\n" + command);
//        logger.debug("Check if file has been successfully copied");
    }


    public static int checkAck(InputStream in) throws IOException {
        int b = in.read();
        // b may be 0 for success,
        //          1 for error,
        //          2 for fatal error,
        //          -1
        if (b == 0) return b;
        if (b == -1) return b;

        if (b == 1 || b == 2) {
            StringBuffer sb = new StringBuffer();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            }
            while (c != '\n');
            if (b == 1) { // error
                logger.error(sb.toString());
            }
            if (b == 2) { // fatal error
                logger.error(sb.toString());
            }
        }
        return b;
    }

    public static class MyUserInfo implements UserInfo, UIKeyboardInteractive {
        @Override
        public String[] promptKeyboardInteractive(String destination, String name, String instruction, String[] prompt, boolean[] echo) {
            return prompt;
        }

        String passphrase;

        @Override
        public String getPassphrase() {
            return passphrase;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public boolean promptPassword(String message) {
            logger.debug(message);
            return true;
        }

        @Override
        public boolean promptPassphrase(String message) {
            logger.debug(message);
            return true;
        }

        @Override
        public boolean promptYesNo(String message) {
            logger.debug(message);
            return true;
        }

        @Override
        public void showMessage(String message) {
            logger.debug(message);
        }
    }

}