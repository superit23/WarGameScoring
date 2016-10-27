package bb.rackmesa.wargamescoring;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

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

    public static ResultSet Insert(String query, Object[] params)
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(Configuration.getConnectionString());
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


    public static User CreateUser(String username, String password, String role, String team, int score) throws InvalidKeySpecException, NoSuchAlgorithmException{

        //byte[] salt = bb.rackmesa.wargamescoring.CryptoFunctions.generateSalt(8);
        //String derived = Base64.encodeToString(bb.rackmesa.wargamescoring.CryptoFunctions.pbkdf2(password.toCharArray(), salt, bb.rackmesa.wargamescoring.Configuration.pbkdf2Iterations, bb.rackmesa.wargamescoring.Configuration.pbkdf2NumBytes));


        User newUser = new User(username);
        newUser.setPassword(password);
        newUser.setRole(role);
        newUser.setScore(score);
        newUser.setTeam(team);

        Insert("INSERT INTO users (userName, password, salt, role, team, score) VALUES(?, ?, ?, ?, ?, ?);", new Object[]{newUser.getUserName(), newUser.getPassword(), newUser.getCredentialsSalt().toBase64(), newUser.getRole(), newUser.getTeam(), newUser.getScore()});
        return newUser;

    }

    public static User RetrieveUser(String username) {
        try {
            ResultSet rs = Retrieve("SELECT * FROM users WHERE userName = ?;", new Object[]{username});

            if(rs.next()) {
                User user = new User(username);
                user.setCredentials(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setScore(rs.getInt("score"));
                user.setTeam(rs.getString("team"));
                user.setCredentialsSalt(ByteSource.Util.bytes(Base64.decode(rs.getString("salt"))));
                return user;
            }
            else
            {
                logger.error("RetrieveUser: Resultset is empty!");
                return null;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    public static void UpdateUserScore(User user) {
        Insert("UPDATE users SET score = ? WHERE userName = ?;", new Object[]{user.getScore(), user.getUserName()});
    }

    public static void UpdateUserPassword(User user) {
        Insert("UPDATE users SET password = ?, salt = ? WHERE userName = ?;", new Object[]{user.getPassword(), user.getCredentialsSalt().toBase64(), user.getUserName()});
    }

    public static void UpdateUser(User user)
    {
        Insert("UPDATE users SET password = ?, salt = ?, score = ?, role = ?, team = ? WHERE userName = ?;", new Object[]{user.getPassword(), user.getCredentialsSalt().toBase64(), user.getScore(), user.getRole(), user.getTeam(), user.getUserName()});
    }


    public static void DeleteUser(User user)  {
        Insert("DELETE FROM users WHERE userName = ?;", new Object[]{user.getUserName()});
    }

    public static Coin CreateCoin(String initialUser) {
        Coin newCoin = new Coin();
        newCoin.setInitialUser(initialUser);

        Insert("INSERT INTO coins(uuid, initialUser) VALUES(?,?);", new Object[]{newCoin.getCoin().toString(), newCoin.getInitialUser()});

        return newCoin;
    }

    public static Coin RetrieveCoin(String uuid) {
        try {
            ResultSet rs = Retrieve("SELECT * FROM coins WHERE uuid = ?;", new Object[]{ uuid });

            if(rs.next()) {
                Coin coin = new Coin(uuid);
                coin.setInitialUser(rs.getString("initialUser"));
                return coin;
            }
            else
            {
                logger.error("RetrieveCoin: Resultset is empty!");
                return null;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    public static ArrayList<Coin> RetrieveCoinsForUser(User user) {
        try {
            ResultSet rs = Retrieve("SELECT * FROM coins WHERE initialUser = ?;", new Object[]{ user.getUserName() });
            ArrayList<Coin> coins = new ArrayList<>();

            while(rs.next()) {
                Coin coin = new Coin(rs.getString("uuid"));
                coin.setInitialUser(rs.getString("initialUser"));
                coins.add(coin);
            }

            return coins;

        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    public static void DeleteCoin(UUID id) {
        Insert("DELETE FROM coins WHERE uuid = ?;", new Object[]{id.toString()});

    }

    public static void TransferScore(User sender, User receiver, int score)
    {
        if(score > 0 && sender.getScore() > 0) {
            int modScore = score > sender.getScore() ? sender.getScore() : score;

            sender.setScore(sender.getScore() - modScore);
            receiver.setScore(receiver.getScore() + modScore);

            UpdateUserScore(sender);
            UpdateUserScore(receiver);
        }
    }

    public static boolean DepositCoin(Coin coin)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if(hour == Configuration.depositWindow)
        {
            Main.coinsToCommit.add(coin);
            return true;
        }

        return false;
    }

    public static void CommitCoins()
    {
        logger.info("Starting commit.");
        HashMap<String, ArrayList<Coin>> coinsByUUID = new HashMap<>();
        for (Coin coin:
             Main.coinsToCommit) {

            if(!coinsByUUID.containsKey(coin.getCoin().toString()))
            {
                coinsByUUID.put(coin.getCoin().toString(), new ArrayList<>());
            }

            coinsByUUID.get(coin.getCoin().toString()).add(coin);
        }

        for (String cUUID:
                coinsByUUID.keySet()) {
            ArrayList<Coin> coins = coinsByUUID.get(cUUID);

            List<User> users = coins.stream().map(coin -> DatabaseFunctions.RetrieveUser(coin.getSubmitter())).collect(Collectors.toList());

            logger.debug("Submitting users are: " + users.stream().map(user -> user.getUserName() + "\n").reduce((uname1, uname2) -> uname1 + uname2).get());

            Coin coin = coins.get(0);
            DatabaseFunctions.RetrieveUser(coin.getInitialUser());

            logger.info("Running priority table on user collection.");
            User user = Configuration.priorityTable.prioritize(coin, users);
            user.setScore(user.getScore() + 1);

            logger.info("Updating user " + user.getUserName());
            DatabaseFunctions.UpdateUserScore(user);
        }


    }


}
