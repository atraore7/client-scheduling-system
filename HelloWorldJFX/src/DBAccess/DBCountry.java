package DBAccess;

import Database.JDBC;
import Model.Country;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Database access class for getting all the countries out of the database.
 */
public class DBCountry {
    /**
     * getAllCountries method to get all Countries out of the database and into a list
     */
    public static ObservableList<Country> getAllCountries(){
        //creating allCountriesList
        ObservableList<Country> allCountriesList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Country_ID, Country FROM countries;";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                String countryId = resultSet.getString("Country_ID");
                String countryName = resultSet.getString("Country");

                //creating a Country object for each row that passes through the while loop
                Country country = new Country(countryId, countryName);
                //adding each Country to the list created at the beginning of the method
                allCountriesList.add(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCountriesList;

    }
}
