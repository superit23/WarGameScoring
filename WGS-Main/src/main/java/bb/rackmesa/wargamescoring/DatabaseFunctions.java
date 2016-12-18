package bb.rackmesa.wargamescoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created by Dan on 9/8/2016.
 */
public class DatabaseFunctions {

    private static Logger logger = LoggerFactory.getLogger(DatabaseFunctions.class);

    public static ResultSet Retrieve(String query, Object[] params, int magic_constant) {
        try {
            Connection conn = DriverManager.getConnection(Configuration.getConnectionString());
            PreparedStatement stmt = null;

            if (magic_constant != -1) {
                stmt = conn.prepareStatement(query, magic_constant);
            } else {
                stmt = conn.prepareStatement(query);
            }


            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }


            ResultSet rs = stmt.executeQuery();

            return rs;
        } catch (SQLException ex) {
            //System.err.println(ex.getMessage());
            logger.error(ex.getMessage());
            return null;
        }
    }


    public static ResultSet Retrieve(String query, Object[] params)
    {
        return Retrieve(query, params, -1);
    }

    public static ResultSet Insert(String query, Object[] params) throws SQLException
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(Configuration.getConnectionString());
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            if(params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }

            int affectedRows = stmt.executeUpdate();
            conn.commit();

            if(affectedRows == 0)
            {
                throw new SQLException("Insert failed: No rows generated.");
            }

            return stmt.getGeneratedKeys();
        }
        catch (SQLException ex)
        {
            //System.err.println(ex.getMessage());
            logger.error(ex.getMessage());
            if(conn != null)
            {
                try
                {
                    conn.rollback();
                }
                catch (SQLException connEx)
                {
                    logger.error(connEx.getMessage());
                }

                throw ex;

            }

            return null;
        }
    }


    public static void CreateUsersTable() throws SQLException
    {
        Insert("CREATE TABLE users (\n" +
                "  userName varchar(20) PRIMARY KEY,\n" +
                "  password varchar(64) DEFAULT NULL,\n" +
                "  salt varchar(12) DEFAULT NULL,\n" +
                "  role varchar(30) DEFAULT NULL,\n" +
                "  team varchar(20) DEFAULT NULL,\n" +
                "  score smallint(5) unsigned DEFAULT NULL);", null);
    }

    public static void CreateCoinsTable() throws SQLException
    {
        Insert(" CREATE TABLE coins (\n" +
                "  uuid varchar(36) NOT NULL,\n" +
                "  initialUser varchar(20) DEFAULT NULL,\n" +
                "  PRIMARY KEY (uuid),\n" +
                "  KEY initialUser (initialUser),\n" +
                "  CONSTRAINT coins_ibfk_1 FOREIGN KEY (initialUser) REFERENCES users (userName) ON DELETE CASCADE ON UPDATE CASCADE);", null);
    }


}
