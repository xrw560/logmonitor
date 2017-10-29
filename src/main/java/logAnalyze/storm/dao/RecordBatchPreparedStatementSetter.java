package logAnalyze.storm.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * Describe: 请补充类描述
 */
public class RecordBatchPreparedStatementSetter implements BatchPreparedStatementSetter {

    private Map<String, Map<String, Object>> appData;

    @Override
    public void setValues(PreparedStatement ps, int i) throws SQLException {

    }

    @Override
    public int getBatchSize() {
        return appData.get("pv").size();
    }
}
