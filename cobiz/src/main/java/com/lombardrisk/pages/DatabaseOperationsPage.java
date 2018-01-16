package com.lombardrisk.pages;

import com.lombardrisk.pojo.DatabaseQuery;
import com.lombardrisk.pojo.DatabaseQueryResult;
import com.lombardrisk.pojo.DatabaseQueryResultRow;
import com.lombardrisk.pojo.ParameterType;
import org.yiwan.webcore.database.DatabaseWrapper;
import org.yiwan.webcore.web.PageBase;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Kent Gu on 7/6/2017.
 */
public class DatabaseOperationsPage extends PageBase {
    private boolean isConnected = false;

    public DatabaseOperationsPage(DatabaseWrapper databaseWrapper) {
        super(databaseWrapper);
    }

    public void connectToDB() throws Exception{
        databaseWrapper().connect();
        isConnected = true;
    }

    public void executeQuery(DatabaseQuery databaseQuery) throws Exception {
        if (!isConnected)
            connectToDB();
        if (databaseQuery.getSqlQuery() != null) {
            DatabaseQuery.SqlQuery sqlQuery = databaseQuery.getSqlQuery();
            if (sqlQuery.getContent() != null){
                String sql = sqlQuery.getContent().getRealValue();
                if (sqlQuery.getParameter() != null && !sqlQuery.getParameter().isEmpty()){
                    for (ParameterType parameterType : sqlQuery.getParameter()){
                        sql = sql.replace(parameterType.getParameterName().getRealValue(), parameterType.getParameterValue().getRealValue());
                    }
                }
                if (databaseQuery.getResultId() != null)
                    databaseWrapper().addResultSetToMap(databaseQuery.getResultId().getRealValue(), databaseWrapper().query(sql));
                else
                    databaseWrapper().query(sql);
            }
        }
    }

    public List<HashMap<String,String>> getResult(String key) throws Exception {
        return databaseWrapper().getResult(key);
    }

    public void validateDatabaseQueryResult(DatabaseQueryResult databaseQueryResult) throws Exception {
        List<HashMap<String,String>> list = getResult(databaseQueryResult.getResultId().getRealValue());
        if (databaseQueryResult.getDatabaseQueryResultRow() != null && !databaseQueryResult.getDatabaseQueryResultRow().isEmpty()) {
//            for (DatabaseQueryResultRow row : databaseQueryResult.getDatabaseQueryResultRow()){
//                databaseValidateThat(row.convertDatabaseQueryResultRowToMap()).rowExisted(list).isTrue();
//            }
            databaseQueryResult.getDatabaseQueryResultRow().forEach((row) -> {
                if (row.isApply() != null && !row.isApply()){
                    databaseValidateThat(row.convertDatabaseQueryResultRowToMap()).rowExisted(list).isFalse();
                } else {
                    databaseValidateThat(row.convertDatabaseQueryResultRowToMap()).rowExisted(list).isTrue();
                }
            });
        }
    }
}
