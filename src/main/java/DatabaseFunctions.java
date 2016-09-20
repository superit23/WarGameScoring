import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.UUID;

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

    public static void CreateUser(User newUser){
        Execute("INSERT INTO users( userName, password, role, score) VALUES(?, ?, ?, ?);", new Object[]{newUser.getUserName(), newUser.getPassword(), newUser.getRole(), newUser.getScore()});

    }

    public static User RetrieveUser(User user) {
        try {
            ResultSet rs = Retrieve("SELECT * FROM users WHERE userName='?'", new Object[]{user.getUserName()});
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            user.setScore(rs.getInt("score"));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void UpdateUserScore(User user){
        Execute("UPDATE users SET score = ? WHERE userName = '?';", new Object[]{user.getUserName(), user.getScore()});
    }

    public static void UpdateUserPassword(User user){
        Execute("UPDATE users SET password = '?' WHERE userName = '?';", new Object[]{user.getPassword(), user.getUserName()});
    }

    public static void DeleteUser(User user){
        Execute("DELETE FROM users WHERE userName='?'", new Object[]{user.getUserName()});
    }

    public static void CreateCoin(Coin newCoin){
        Execute("INSERT INTO coins(uuid, initialUser) VALUES(?,?);", new Object[]{newCoin.getCoin(), newCoin.getInitialUser()});

    }

    public static Coin RetrieveCoin(Coin coin){
        ResultSet rs = Retrieve("SELECT * FROM coins WHERE uuid='?'", new Object[]{coin.getCoin()});
        try {
            coin.setInitialUser(rs.getString("initialUser"));
            return coin;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void DeleteCoin(UUID id){
         Execute("DELETE FROM coins WHERE uuid='?';", new Object[]{id.toString()});

    }


}
