import entity.UserEntity;
import util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UseFetchSizeJdbc {

    public static void main(String[] args) {

        System.out.println(getAllUsers());
    }

    public static List<UserEntity> getAllUsers() {

        String sql = """
                                
                SELECT 
                id,
                name,
                lastname,
                patronymic,
                age
                FROM users
                                
                """;


        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setFetchSize(10);
            preparedStatement.setMaxRows(5);
            preparedStatement.setQueryTimeout(20);
            System.out.println(preparedStatement);
            var resultSet = preparedStatement.executeQuery();
            List<UserEntity> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(new UserEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getString("patronymic"),
                        resultSet.getInt("age")
                ));
            }
            return users;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }


}
