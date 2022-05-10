import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UseTransactionJdbc {

    public static void main(String[] args) throws SQLException {
        deleteUserWithHisAccounts(2L);
    }


    public static void deleteUserWithHisAccounts(Long id) throws SQLException {

        String userSql = """
                                
                DELETE FROM users WHERE id = ?
                                
                """;

        String accountSql = """
                                
                DELETE FROM accounts WHERE user_id = ?
                                
                """;

        Connection connection = null;
        PreparedStatement userPS = null;
        PreparedStatement accountPS = null;
        try {
            connection = ConnectionManager.get();
            connection.setAutoCommit(false);

            accountPS = connection.prepareStatement(accountSql);
            userPS = connection.prepareStatement(userSql);

            accountPS.setLong(1, id);
            userPS.setLong(1, id);

            accountPS.executeUpdate();

            if (true) {
                throw new RuntimeException();
            }
            userPS.executeUpdate();

            connection.commit();
        } catch (Exception ex) {
            if (connection != null) {
                connection.rollback();
                throw ex;
            }
            if (connection != null) {
                connection.close();
            }
            if (accountPS != null) {
                accountPS.close();
            }
            if (userPS != null) {
                userPS.close();
            }
        }

    }

}
