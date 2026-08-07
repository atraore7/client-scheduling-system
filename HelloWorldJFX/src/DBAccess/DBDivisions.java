package DBAccess;

import Database.JDBC;
import Model.Customer;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Database access class for getting the divisions out of the database.
 */
public class DBDivisions {
    /**
     *  getAllDivisions method to get all Divisions out of the database and into a list
     */
    public static ObservableList<Division> getAllDivisions(){
        //creating allDivisionsList
        ObservableList<Division> allDivisionsList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Division, Division_ID, Country_ID FROM first_level_divisions;";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int divisionid = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                String divisionCountryId = resultSet.getString("Country_ID");



                //creating a division object for each row that passes through the while loop
                Division division = new Division(divisionid, divisionName, divisionCountryId);
                //adding each division to the list created at the beginning of the method
                allDivisionsList.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allDivisionsList;

    }

    /**
     * getUSDivisions method to get US Divisions out of the database and into a list
     * @return
     */
    public static ObservableList<Division> getUSDivisions(){
        //creating allUSDivisionsList
        ObservableList<Division> allUSDivisionsList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Division, Division_ID, Country_ID FROM first_level_divisions " +
                    "WHERE Country_ID=\"1\";\n";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int divisionid = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                String divisionCountryId = resultSet.getString("Country_ID");



                //creating a division object for each row that passes through the while loop
                Division division = new Division(divisionid, divisionName, divisionCountryId);
                //adding each division to the list created at the beginning of the method
                allUSDivisionsList.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUSDivisionsList;

    }

    /**
     *  getUKDivisions method to get UK Divisions out of the database and into a list
     * @return
     */
    public static ObservableList<Division> getUKDivisions(){
        //creating allUkDivisionsList
        ObservableList<Division> allUKDivisionsList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Division, Division_ID, Country_ID FROM first_level_divisions " +
                    "WHERE Country_ID=\"2\";\n";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int divisionid = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                String divisionCountryId = resultSet.getString("Country_ID");



                //creating a division object for each row that passes through the while loop
                Division division = new Division(divisionid, divisionName, divisionCountryId);
                //adding each division to the list created at the beginning of the method
                allUKDivisionsList.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUKDivisionsList;

    }

    /**
     *  getCanadianDivisions method to get Canadian Divisions out of the database and into a list
     * @return
     */
    public static ObservableList<Division> getCanadianDivisions(){
        //creating allCanadianDivisionsList
        ObservableList<Division> allCanadianDivisionsList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Division, Division_ID, Country_ID FROM first_level_divisions" +
                    " WHERE Country_ID=\"3\";\n";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int divisionid = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                String divisionCountryId = resultSet.getString("Country_ID");



                //creating a division object for each row that passes through the while loop
                Division division = new Division(divisionid, divisionName, divisionCountryId);
                //adding each division to the list created at the beginning of the method
                allCanadianDivisionsList.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCanadianDivisionsList;

    }
}
