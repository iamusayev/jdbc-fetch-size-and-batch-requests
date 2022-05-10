import util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UseBatchRequestJdbc {

    public static void main(String[] args) throws SQLException {


        batchRequestImplementation(3L);

    }

    public static void batchRequestImplementation(Long id) throws SQLException {

        String accountSql = """
                
                DELETE FROM accounts 
                WHERE user_id =  
                
                """ + id;

        String userSql = """
                
                DELETE FROM users 
                WHERE id = 
                
                """ + id;


        Connection connection = null;
        Statement statement = null;

        try {
            connection = ConnectionManager.get();

            connection.setAutoCommit(false);

            statement = connection.createStatement();
            statement.addBatch(accountSql);

            if(true){
                throw new RuntimeException("HAVING SOME PROBLEMS WITH DATABASE");
            }
            statement.addBatch(userSql);

            statement.executeBatch();
            connection.commit();
        } catch (Exception ex) {
            if (connection != null) {
                connection.rollback();
                throw ex;
            }
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        }

    }





}
