package com.lombardrisk.util;

import com.lombardrisk.pojo.FeedAgreement;
import com.lombardrisk.pojo.FeedAssetRating;
import com.lombardrisk.pojo.FeedOrganisation;
import com.lombardrisk.pojo.StringType;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yiwan.webcore.util.Helper;
import org.yiwan.webcore.util.PropHelper;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElement;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Jova Qiu
 */
public class PoiHelper {
    private final static Logger logger = LoggerFactory.getLogger(PoiHelper.class);

    public static <T> XSSFWorkbook marshalXlsx(List<T> list) throws IOException, ParseException, IllegalAccessException {
        String sheetName = "Sheet1";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);
        sheet.createRow(0);
        if (list != null && !list.isEmpty()) {
            logger.info(PropHelper.MAPS_FOLDER + Helper.lowerCamel(list.get(0).getClass().getSimpleName()) + ".map");
            URL url = ClassLoader.getSystemResource(PropHelper.MAPS_FOLDER + Helper.lowerCamel(list.get(0).getClass().getSimpleName()) + ".map");
            logger.info("Map file actual location is - " + url.toString());
            Map<String, Map<String, String>> map = Helper.getFeedMapping(ClassLoader.getSystemResourceAsStream(PropHelper.MAPS_FOLDER + Helper.lowerCamel(list.get(0).getClass().getSimpleName()) + ".map"));
                for (T t : list) {
                XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
                if (t instanceof FeedOrganisation)
                    writeFeedOrganisation((FeedOrganisation) t, map, sheet, row, workbook);
                else if (t instanceof FeedAssetRating)
                    writeFeedAssetRating((FeedAssetRating) t, map, sheet, row, workbook);
                else
                    writeNonSubFeedObject(t, map, sheet, row, workbook);
            }
        }
        return workbook;
    }

    public static <T> HSSFWorkbook marshalXls(List<T> list) throws IOException, ParseException, IllegalAccessException {
        String sheetName = "Sheet1";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);
        sheet.createRow(0);
        if (list != null && !list.isEmpty()) {
            logger.info(PropHelper.MAPS_FOLDER + Helper.lowerCamel(list.get(0).getClass().getSimpleName()) + ".map");
            URL url = ClassLoader.getSystemResource(PropHelper.MAPS_FOLDER + Helper.lowerCamel(list.get(0).getClass().getSimpleName()) + ".map");
            logger.info("Map file actual location is - " + url.toString());
            Map<String, Map<String, String>> map = Helper.getFeedMapping(ClassLoader.getSystemResourceAsStream(PropHelper.MAPS_FOLDER + Helper.lowerCamel(list.get(0).getClass().getSimpleName()) + ".map"));
            for (T t : list) {
                HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
                if (t instanceof FeedOrganisation)
                    writeFeedOrganisation((FeedOrganisation) t, map, sheet, row, workbook);
//                else if (t instanceof FeedAgreement)
//                    writeFeedAgreement((FeedAgreement) t, map, sheet, row, workbook);
                else if (t instanceof FeedAssetRating)
                    writeFeedAssetRating((FeedAssetRating) t, map, sheet, row, workbook);
                else
                    writeNonSubFeedObject(t, map, sheet, row, workbook);
            }
        }
        return workbook;
    }

    private static String getXmlElementName(Field field) {
        String xmlElementName;
        try {
            xmlElementName = field.getAnnotation(XmlElement.class).name();
            if (xmlElementName.equals("##default"))
                xmlElementName = field.getName();
        } catch (NullPointerException e) {
            xmlElementName = field.getName();
        }
        return xmlElementName;
    }

    private static String getInputCellValue(Object o) {
        StringBuffer value = new StringBuffer();
        if (o.getClass().equals(StringType.class)) {
            value.append(((StringType) o).getRealValue());
        } else if (o.getClass().equals(JAXBElement.class)) {
            if (((JAXBElement) o).getValue() instanceof StringType)
                value.append(((StringType) ((JAXBElement) o).getValue()).getRealValue());
        } else {
            value.append(o.toString());
        }
        if(value.toString().isEmpty())
            return "";
        else
            return value.toString();
    }

    private static void generateExcelBody(String value, String header, String dataType, Row row, Row firstRow, Workbook workbook) throws ParseException {
        int colNum;
        colNum = getCellNumber(header, firstRow);
        if (colNum == -1) {
            if (firstRow.getLastCellNum() == -1)
                firstRow.createCell(firstRow.getLastCellNum() + 1).setCellValue(header);
            else
                firstRow.createCell(firstRow.getLastCellNum()).setCellValue(header);
            colNum = getCellNumber(header, firstRow);
        }
        switch (dataType) {
            case "date":
                Cell cell = row.createCell(colNum);
                cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").parse(value));
                CellStyle cellDateStyle = workbook.createCellStyle();
                DataFormat format = workbook.createDataFormat();
                cellDateStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));
                cell.setCellStyle(cellDateStyle);
                break;
            case "long":
                row.createCell(colNum).setCellValue(Long.parseLong(value));
                break;
            case "decimal":
                row.createCell(colNum).setCellValue(Float.parseFloat(value));
                break;
            case "int":
                row.createCell(colNum).setCellValue(Integer.parseInt(value));
                break;
            default:
                row.createCell(colNum).setCellValue(value);
                break;
        }
    }

    private static void generateExcelBody(String value, Set<Entry<String, String>> entrySet, Row row, Row firstRow, Workbook workbook) throws ParseException {
        int colNum;
        for (Entry<String, String> entry : entrySet) {
            colNum = getCellNumber(entry.getKey(), firstRow);
            if (colNum == -1) {
                if (firstRow.getLastCellNum() == -1)
                    firstRow.createCell(firstRow.getLastCellNum() + 1).setCellValue(entry.getKey());
                else
                    firstRow.createCell(firstRow.getLastCellNum()).setCellValue(entry.getKey());
                colNum = getCellNumber(entry.getKey(), firstRow);
            }
            switch (entry.getValue()) {
                case "date":
                    Cell cell = row.createCell(colNum);
                    cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").parse(value));
                    CellStyle cellDateStyle = workbook.createCellStyle();
                    DataFormat format = workbook.createDataFormat();
                    cellDateStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));
                    cell.setCellStyle(cellDateStyle);
                    break;
                case "long":
                    row.createCell(colNum).setCellValue(Long.parseLong(value));
                    break;
                case "decimal":
                    row.createCell(colNum).setCellValue(Float.parseFloat(value));
                    break;
                case "int":
                    row.createCell(colNum).setCellValue(Integer.parseInt(value));
                    break;
                default:
                    row.createCell(colNum).setCellValue(value);
                    break;
            }
        }
    }

    public static int getCellNumber(String value, Row row) {
        int rowNum = -1;
        for (int i = 0; i < row.getLastCellNum(); i++) {
            if (row.getCell(i) == null) {
                return rowNum;
            } else if (row.getCell(i).getStringCellValue().equals(value)) {
                rowNum = i;
                break;
            }
        }
        return rowNum;
    }

    private static void writeFeedAssetRating(FeedAssetRating feedAssetRating, Map<String, Map<String, String>> map, Sheet sheet, Row row, Workbook workbook) throws IllegalAccessException, ParseException {
        Field[] fields = feedAssetRating.getClass().getDeclaredFields();
        Row firstRow = sheet.getRow(0);
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(feedAssetRating) != null) {
                String xmlElementName = getXmlElementName(field);
                if (map.containsKey(xmlElementName)) {
                    Class fieldClass = field.getType();
                    String value = "";
                    if (fieldClass == List.class) {
                        if (field.getName().equals("rating")) {
                            List<FeedAssetRating.Rating> ratingList = (List<FeedAssetRating.Rating>) field.get(feedAssetRating);
                            if (!ratingList.isEmpty()) {
                                for (FeedAssetRating.Rating rating : ratingList) {
                                    String headerName = null;
                                    if (rating.getRatingName() != null) {
                                        headerName = rating.getRatingName().getRealValue();
                                        String ratingValue = null;
                                        if (rating.getRatingValue() != null)
                                            ratingValue = rating.getRatingValue().getRealValue();
                                        generateExcelBody(ratingValue, headerName, "string", row, firstRow, workbook);
                                    }
                                }
                            }
                        }
                    }else if (fieldClass == StringType.class
                            || fieldClass == boolean.class
                            || fieldClass == Boolean.class
                            || field.getType().equals(JAXBElement.class)) {
                        generateExcelBody(getInputCellValue(field.get(feedAssetRating)), map.get(xmlElementName).entrySet(), row, firstRow, workbook);
                    }
                }
            }
        }
    }

    private static void writeFeedAgreement(FeedAgreement feedAgreement, Map<String, Map<String, String>> map, Sheet sheet, Row row, Workbook workbook) throws IllegalAccessException, ParseException {
        Field[] fields = feedAgreement.getClass().getDeclaredFields();
        Row firstRow = sheet.getRow(0);
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(feedAgreement) != null) {
                String xmlElementName = getXmlElementName(field);
                if (map.containsKey(xmlElementName)) {
                    Class fieldClass = field.getType();
                    String value = "";
                    if (fieldClass.getPackage().getName().contains("com.lombardrisk.pojo")
                            && fieldClass != StringType.class) {
                        if (fieldClass == FeedAgreement.SendConfirmationLetter.class){

                        }else if (fieldClass == FeedAgreement.ApplicableAgencies.class) {

                        }else if (fieldClass == FeedAgreement.PrincipalBranches.class) {

                        }else if (fieldClass == FeedAgreement.CptyBranches.class) {

                        }else if (fieldClass == FeedAgreement.AssetClasses.class) {

                        }else if (fieldClass == FeedAgreement.SettlementInstructions.class) {

                        }else if (fieldClass == FeedAgreement.ModelEligRules.class) {

                        }else if (fieldClass == FeedAgreement.LettersAttachments.class) {

                        }else if (fieldClass == FeedAgreement.PrincipalInternalOnly.class) {

                        }else if (fieldClass == FeedAgreement.CptyInternalOnly.class) {

                        }else if (fieldClass == FeedAgreement.TradeCalcRule.class) {

                        }
                    }else if (fieldClass == List.class) {
                        switch (field.getName()) {
                            case "tradeMappingRule":

                                break;
                            case "product":

                                break;
                            case "asset":

                                break;
                            case "autoBookingRules":

                                break;
                            case "cptyRatingBasedData":

                                break;
                            case "principalRatingBasedData":

                                break;
                            case "principalIMRatingBasedData":

                                break;
                            case "cptyIMRatingBasedData":

                                break;
                            case "reportFreq":

                                break;
                        }
                    }else if (fieldClass == StringType.class
                            || fieldClass == boolean.class
                            || fieldClass == Boolean.class
                            || field.getType().equals(JAXBElement.class)) {
                        generateExcelBody(getInputCellValue(field.get(feedAgreement)), map.get(xmlElementName).entrySet(), row, firstRow, workbook);
                    }
                }
            }
        }
    }

    private static void writeFeedOrganisation(FeedOrganisation feedOrganisation, Map<String, Map<String, String>> map, Sheet sheet, Row row, Workbook workbook) throws IllegalAccessException, ParseException {
        Field[] fields = feedOrganisation.getClass().getDeclaredFields();
        Row firstRow = sheet.getRow(0);
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(feedOrganisation) != null) {
                String xmlElementName = getXmlElementName(field);
                if (map.containsKey(xmlElementName)) {
                    field.setAccessible(true);
                    Class fieldClass = field.getType();
                    String value = "";
                    field.setAccessible(true);
                    if (fieldClass.getPackage().getName().contains("com.lombardrisk.pojo")
                            && fieldClass != StringType.class) {
                        if (fieldClass == FeedOrganisation.OrgHeader.class) {
                            FeedOrganisation.OrgHeader orgHeader = (FeedOrganisation.OrgHeader) field.get(feedOrganisation);
                            if (orgHeader.getOrgCode() != null)
                                generateExcelBody(orgHeader.getOrgCode().getRealValue(), map.get("orgCode").entrySet(), row, firstRow, workbook);
                            if (orgHeader.getLongName() != null)
                                generateExcelBody(orgHeader.getLongName().getRealValue(), map.get("longName").entrySet(), row, firstRow, workbook);
                            if (orgHeader.getShortName() != null)
                                generateExcelBody(orgHeader.getShortName().getRealValue(), map.get("shortName").entrySet(), row, firstRow, workbook);
                            if (orgHeader.getLEI() != null)
                                generateExcelBody(orgHeader.getLEI().getRealValue(), map.get("LEI").entrySet(), row, firstRow, workbook);
                        } else if (fieldClass == FeedOrganisation.Relationships.class) {
                            FeedOrganisation.Relationships relationships = (FeedOrganisation.Relationships) field.get(feedOrganisation);
                            if (relationships.getRelationship() != null && !relationships.getRelationship().isEmpty()) {
                                for (FeedOrganisation.Relationships.Relationship relationship : relationships.getRelationship()) {
                                    if (relationship.getSubOrg() != null)
                                        generateExcelBody(relationship.getSubOrg().getRealValue(), map.get("subOrg").entrySet(), row, firstRow, workbook);
                                    if (relationship.getRelationshipType() != null)
                                        generateExcelBody(relationship.getRelationshipType().getRealValue(), map.get("relationshipType").entrySet(), row, firstRow, workbook);
                                    if (relationship.getRelationshipOperation() != null)
                                        generateExcelBody(relationship.getRelationshipOperation().getRealValue(), map.get("relationshipOperation").entrySet(), row, firstRow, workbook);
                                }
                            }
                        } else if (fieldClass == FeedOrganisation.UltimateParents.class) {
                            FeedOrganisation.UltimateParents ultimateParents = (FeedOrganisation.UltimateParents) field.get(feedOrganisation);
                            if (ultimateParents.getUltimateParent() != null && !ultimateParents.getUltimateParent().isEmpty()) {
                                StringBuffer ultimateParentValue = new StringBuffer();
                                for (StringType ultimateParent : ultimateParents.getUltimateParent()) {
//                                    ultimateParentValue.append(ultimateParent.getRealValue() + "/");
                                    ultimateParentValue.append(ultimateParent.getRealValue()).append("/");
                                }
                                ultimateParentValue.deleteCharAt(ultimateParentValue.length() - 1);
                                generateExcelBody(ultimateParentValue.toString(), map.get("ultimateParent").entrySet(), row, firstRow, workbook);
                            }
                        } else if (fieldClass == FeedOrganisation.CloseLinks.class) {
                            FeedOrganisation.CloseLinks closeLinks = (FeedOrganisation.CloseLinks) field.get(feedOrganisation);
                            if (closeLinks.getCloseLink() != null && !closeLinks.getCloseLink().isEmpty()) {
                                StringBuffer closeLinkValue = new StringBuffer();
                                for (StringType closeLink : closeLinks.getCloseLink()) {
//                                    closeLinkValue.append(closeLink.getRealValue() + "/");
                                    closeLinkValue.append(closeLink.getRealValue()).append("/");
                                }
                                closeLinkValue.deleteCharAt(closeLinkValue.length() - 1);
                                generateExcelBody(closeLinkValue.toString(), map.get("closeLink").entrySet(), row, firstRow, workbook);
                            }
                        } else if (fieldClass == FeedOrganisation.ExternalCodes.class) {
                            FeedOrganisation.ExternalCodes externalCodes = (FeedOrganisation.ExternalCodes) field.get(feedOrganisation);
                            if (externalCodes.getExternalCode() != null && !externalCodes.getExternalCode().isEmpty()) {

                            }
                        }
                    } else if (fieldClass == List.class) {
                        switch (field.getName()) {
                            case "orgRole":
                                @SuppressWarnings("unchecked")
                                List<StringType> orgRoleList = (ArrayList<StringType>) field.get(feedOrganisation);
                                StringBuffer orgRoleValue = new StringBuffer();
                                if (!orgRoleList.isEmpty()) {
                                    for (StringType orgRole : orgRoleList) {
//                                    orgRoleValue.append(orgRole.getRealValue() + "/");
                                        orgRoleValue.append(orgRole.getRealValue()).append("/");
                                    }
                                }
                                orgRoleValue.deleteCharAt(orgRoleValue.length() - 1);
                                generateExcelBody(orgRoleValue.toString(), map.get(xmlElementName).entrySet(), row, firstRow, workbook);
                                break;
                            case "orgCreditRating":
                                @SuppressWarnings("unchecked")
                                List<FeedOrganisation.OrgCreditRating> orgCreditRatingList = (ArrayList<FeedOrganisation.OrgCreditRating>) field.get(feedOrganisation);
                                if (!orgCreditRatingList.isEmpty()) {
                                    StringBuffer fitchRating = new StringBuffer();
                                    StringBuffer sandpRating = new StringBuffer();
                                    StringBuffer moodysRating = new StringBuffer();
                                    for (FeedOrganisation.OrgCreditRating orgCreditRating : orgCreditRatingList) {
                                        if (orgCreditRating.getRatingAgency() != null) {
                                            if (orgCreditRating.getRatingAgency().getRealValue().equals("Fitch")) {
                                                if (orgCreditRating.getRating() != null)
                                                    fitchRating.append(orgCreditRating.getRating().getRealValue());
                                                if (orgCreditRating.getDebtClassification() != null) {
//                                                fitchRating.append("/" + orgCreditRating.getDebtClassification().getRealValue());
                                                    fitchRating.append("/").append(orgCreditRating.getDebtClassification().getRealValue());
                                                }
                                                fitchRating.append(" ");
                                            } else if (orgCreditRating.getRatingAgency().getRealValue().equals("SandP")) {
                                                if (orgCreditRating.getRating() != null)
                                                    sandpRating.append(orgCreditRating.getRating().getRealValue());
                                                if (orgCreditRating.getDebtClassification() != null) {
//                                                sandpRating.append("/" + orgCreditRating.getDebtClassification().getRealValue());
                                                    sandpRating.append("/").append(orgCreditRating.getDebtClassification().getRealValue());
                                                }
                                                sandpRating.append(" ");
                                            } else if (orgCreditRating.getRatingAgency().getRealValue().equals("MOODYS")) {
                                                if (orgCreditRating.getRating() != null)
                                                    moodysRating.append(orgCreditRating.getRating().getRealValue());
                                                if (orgCreditRating.getDebtClassification() != null) {
//                                                moodysRating.append("/" + orgCreditRating.getDebtClassification().getRealValue());
                                                    moodysRating.append("/").append(orgCreditRating.getDebtClassification().getRealValue());
                                                }
                                                moodysRating.append(" ");
                                            }
                                        }
                                    }
                                    if (!fitchRating.toString().equals("")) {
                                        fitchRating.deleteCharAt(fitchRating.length() - 1);
                                        generateExcelBody(fitchRating.toString(), "Fitch_PARENT", "string", row, firstRow, workbook);
                                    }
                                    if (!sandpRating.toString().equals("")) {
                                        sandpRating.deleteCharAt(sandpRating.length() - 1);
                                        generateExcelBody(sandpRating.toString(), "SandP_PARENT", "string", row, firstRow, workbook);
                                    }
                                    if (!moodysRating.toString().equals("")) {
                                        moodysRating.deleteCharAt(moodysRating.length() - 1);
                                        generateExcelBody(moodysRating.toString(), "MOODYS_PARENT", "string", row, firstRow, workbook);
                                    }
                                }
                                break;
                            case "udf":
                                @SuppressWarnings("unchecked")
                                List<FeedOrganisation.Udf> udfList = (ArrayList<FeedOrganisation.Udf>) field.get(feedOrganisation);
                                if (!udfList.isEmpty()) {
                                    for (FeedOrganisation.Udf udf : udfList) {
                                        String headerName;
                                        if (udf.getUdfName() != null) {
                                            String udfValue = null;
                                            headerName = udf.getUdfName().getRealValue();
                                            if (udf.getUdfValue() != null)
                                                udfValue = udf.getUdfValue().getRealValue();
                                            generateExcelBody(udfValue, headerName, "string", row, firstRow, workbook);
                                        }

                                    }
                                }
                                break;
                        }
                    } else if (fieldClass == StringType.class
                            || fieldClass == boolean.class
                            || fieldClass == Boolean.class
                            || field.getType().equals(JAXBElement.class)) {
                        generateExcelBody(getInputCellValue(field.get(feedOrganisation)), map.get(xmlElementName).entrySet(), row, firstRow, workbook);
                    }
                }
            }
        }
    }

    private static void writeNonSubFeedObject(Object t, Map<String, Map<String, String>> map, Sheet sheet, Row row, Workbook workbook) throws IllegalAccessException, ParseException {
        Field[] fields = t.getClass().getDeclaredFields();
        Row firstRow = sheet.getRow(0);
        for (Field field : fields) {
            field.setAccessible(true);
             if (field.get(t) != null) {
                 String xmlElementName;
                 if (getXmlElementName(field).equals("rowNumber")){
                     xmlElementName="Row number";
                     generateExcelBody(getInputCellValue(field.get(t)), "Row number", "string", row, firstRow, workbook);
                 }
                 else if (getXmlElementName(field).equals("failureReason")){
                     xmlElementName = "Failure reason";
                     generateExcelBody(getInputCellValue(field.get(t)), "Failure reason", "string", row, firstRow, workbook);
                 }
                 else xmlElementName = getXmlElementName(field);
                        if (map.containsKey(xmlElementName)) {
                            field.setAccessible(true);
                            Class fieldClass = field.getType();
                            String value = "";
                            field.setAccessible(true);
                            if (fieldClass == StringType.class
                                    || fieldClass == boolean.class
                                    || fieldClass == Boolean.class
                                    || field.getType().equals(JAXBElement.class)) {
                                generateExcelBody(getInputCellValue(field.get(t)), map.get(xmlElementName).entrySet(), row, firstRow, workbook);
                    }
                }
            }
        }
    }

    /**
     * @param reportFile
     */
    public static List<Map<String, String>> exchangeExcelToList(StringType reportFile) throws Exception {
        List<Map<String, String>> resultList = new ArrayList<>();
        InputStream inputStream = new FileInputStream(new File(reportFile.getRealValue()));
        Workbook workbook = null;
        if (reportFile.getRealValue().endsWith(".xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (reportFile.getRealValue().endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        }
        if (workbook != null) {
            Sheet sheet = workbook.getSheetAt(0);
            int totalRowNum = sheet.getLastRowNum();
            if (totalRowNum > 0) {
                int totalColumnNum = sheet.getRow(0).getPhysicalNumberOfCells();
                if (totalColumnNum > 0) {
                    for (int rowIndex = 1; rowIndex <= totalRowNum; rowIndex++) {
                        Map<String, String> rowMap = new HashMap<>();
                        for (int columnIndex = 0; columnIndex < totalColumnNum; columnIndex++) {
                            switch (sheet.getRow(rowIndex).getCell(columnIndex).getCellType()) {
                                case Cell.CELL_TYPE_STRING:
                                    rowMap.put(sheet.getRow(0).getCell(columnIndex).getStringCellValue().trim(), sheet.getRow(rowIndex).getCell(columnIndex).getStringCellValue().trim());
                                    break;
                                case Cell.CELL_TYPE_BLANK:
                                    rowMap.put(sheet.getRow(0).getCell(columnIndex).getStringCellValue().trim(), sheet.getRow(rowIndex).getCell(columnIndex).getStringCellValue().trim());
                                    break;
                                case Cell.CELL_TYPE_BOOLEAN:
                                    if (sheet.getRow(rowIndex).getCell(columnIndex).getBooleanCellValue())
                                        rowMap.put(sheet.getRow(0).getCell(columnIndex).getStringCellValue().trim(), "True");
                                    else
                                        rowMap.put(sheet.getRow(0).getCell(columnIndex).getStringCellValue().trim(), "False");
                                    break;
                                case Cell.CELL_TYPE_NUMERIC:
                                    DataFormatter dataFormatter = new DataFormatter();
                                    rowMap.put(sheet.getRow(0).getCell(columnIndex).getStringCellValue().trim(), dataFormatter.formatCellValue(sheet.getRow(rowIndex).getCell(columnIndex)));
                                    break;
                                default:
                                    break;
                            }
                        }
                        resultList.add(rowMap);
                    }
                }

            }
        }
        return resultList;
    }
}