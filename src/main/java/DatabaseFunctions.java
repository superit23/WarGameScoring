import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.SimpleAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created by Dan on 9/8/2016.
 */
public class DatabaseFunctions {

    private static Logger logger = LoggerFactory.getLogger(DatabaseFunctions.class);

    public static ResultSet Retrieve(String query, Object[] params, int magic_constant)
    {
        try {
            Connection conn = DriverManager.getConnection(Configuration.connectionString);
            PreparedStatement stmt = null;

            if(magic_constant != -1)
            {
                stmt = conn.prepareStatement(query, magic_constant);
            }
            else
            {
                stmt = conn.prepareStatement(query);
            }


            for(int i = 0; i < params.length; i++)
            {
                stmt.setObject(i + 1, params[i]);
            }


            ResultSet rs = stmt.executeQuery();

            return rs;
        }
        catch (SQLException ex)
        {
            //System.err.println(ex.getMessage());
            logger.error(ex.getMessage());
            return null;
        }
    }

    public static ResultSet Retrieve(String query, Object[] params)
    {
        return Retrieve(query, params, -1);
    }

    public static ResultSet Insert(String query, Object[] params)
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(Configuration.connectionString);
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            for(int i = 0; i < params.length; i++)
            {
                stmt.setObject(i + 1, params[i]);
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

            }

            return null;
        }
    }

    public static void Execute(String query, Object[] params)
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(Configuration.connectionString);
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(query);

            for(int i = 0; i < params.length; i++)
            {
                stmt.setObject(i + 1, params[i]);
            }


            //stmt.execute();

            int affectedRows = stmt.executeUpdate();
            conn.commit();

            if(affectedRows == 0)
            {
                throw new SQLException("Execute failed: No rows affected.");
            }
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

            }

        }
    }

    public static SimpleAccount RetrieveUser(String username)
    {
        return null;
    }
}
