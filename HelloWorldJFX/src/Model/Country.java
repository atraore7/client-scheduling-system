package Model;

import DBAccess.DBContacts;
import DBAccess.DBCountry;
import javafx.collections.ObservableList;

/**
 * Country class that defines a country object and gets the country attributes.
 */
public class Country {
    public String countryId;
    public String countryName;

    public static ObservableList<Country> allCountries = DBCountry.getAllCountries();
    /** Country constructor */
    public Country(String countryId, String countryName){
        this.countryId = countryId;
        this.countryName = countryName;
    }
    /** getter for countryId */
    public String getCountryId() {
        return countryId;
    }
    /** getter for countryName */
    public String getCountryName() {
        return countryName;
    }
    /**overriding the toString method to contain the countryName only */
    @Override
    public String toString(){
        return (countryName);
    }
    /** getAllCountries method to return a list of all the countries from the database */
    public static ObservableList<Country> getAllCountries() {
        return allCountries;
    }
}
