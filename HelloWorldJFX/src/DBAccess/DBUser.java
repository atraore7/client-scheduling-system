package DBAccess;

import Database.JDBC;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
/**
 * Database access class called DBUser for getting all users out of the database.
 */
public class DBUser {
    /**
     *  getAllUsers method to get all users out of the database and into a list
     * @return
     */
    public static ObservableList<User> getAllUsers() {
        //creating allUsersList
        ObservableList<User> allUsersList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT User_ID, User_Name, Password FROM users;";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                int user_id = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                String password = resultSet.getString("Password");
                Locale userLocale = Locale.getDefault();
                ZoneId userTimeZone = ZoneId.systemDefault();


                //creating a User object for each row that passes through the while loop
                User user = new User(user_id, userName, password, userLocale, userTimeZone);
                //adding each user to the list created at the beginning of the method
                allUsersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsersList;
    }
}


