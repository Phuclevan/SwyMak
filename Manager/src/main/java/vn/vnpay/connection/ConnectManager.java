package vn.vnpay.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;
import vn.vnpay.config.ConfigCommon;
import vn.vnpay.config.DbConfig;

import java.sql.Connection;
import java.sql.SQLException;

@Log4j2
public class ConnectManager {
    private static HikariDataSource hikariDataSource;
    private DbConfig dbConfig = ConfigCommon.getDbConfig();
    private static final class SingletonHolder {

        private static final ConnectManager INSTANCE = new ConnectManager();
    }
    public static ConnectManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private ConnectManager() {
    }

    public void initDB() {
        try {
            log.debug("Begin init database online");
            final HikariConfig config = new HikariConfig();
            config.setConnectionTimeout(dbConfig.getIDLE_TIMEOUT());
            config.setMaximumPoolSize(dbConfig.getMAXIMUM_POOL_SIZE());
            config.setMinimumIdle(dbConfig.getMINIMUM_IDLE());
            config.setDriverClassName(dbConfig.getDriverClassName());
            config.setJdbcUrl(dbConfig.getDB_URL());
            config.setUsername(dbConfig.getDB_USERNAME());
            config.setPassword(dbConfig.getDB_PASSWORD());
//            config.addDataSourceProperty("cachePrepStmts", DbConfig.CACHE_PREP_STMTS);
//            config.addDataSourceProperty("prepStmtCacheSize", DbConfig.PREP_STMT_CACHE_SIZE);
//            config.addDataSourceProperty("prepStmtCacheSqlLimit", DbConfig.PREP_STMT_CACHE_SIZE);
            hikariDataSource = new HikariDataSource(config);
            log.debug("Init database Online success: {}", dbConfig.getDB_URL());
        } catch (Exception ex) {
            log.warn("Init DB Online fail: ", ex);
        }
    }
    public Connection getConnectionOnline() {
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException ex) {
            log.error("Get connection DB Online failed by ex: ", ex);
            return null;
        }
    }
    public void closeDBOnline(Connection conn) {
        if (conn != null) {
            try {
                log.info("Valid Connection Pool DB Online ...successful.");
                conn.close();
            } catch (SQLException e) {
                log.warn("Close connection DB Online fail: ", e);
            }
        } else {
            log.error("Valid Connection Pool DB Online ...fail.");
        }
    }
    public void destroyDataSourceOn() {
        try {
            hikariDataSource.unwrap(HikariDataSource.class).close();
        } catch (SQLException ex) {
            log.error("Destroy connection to database Online fail: ", ex);
        }
    }


}
