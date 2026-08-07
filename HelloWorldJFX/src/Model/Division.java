package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *Division class that defines division objects and gets their attributes.
 */
public class Division {
    public int divisionId;
    public String divisionName;
    public String divisionCountryId;

    /** Division constructor */
    public Division(int divisionId, String divisionName, String divisionCountryId){
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.divisionCountryId = divisionCountryId;
    }
    /** getter for divisionId */
    public int getDivisionId(){
        return divisionId;
    }
    /** getter for divisionName */
    public String getDivisionName(){
        return divisionName;
    }
    /** getter for divisionCountryId */
    public String getDivisionCountryId(){
        return divisionCountryId;
    }
    /**override toString to contain the division name */
    @Override
    public String toString() {
        return (divisionName);
    }
}

