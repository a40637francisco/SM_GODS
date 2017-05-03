package model;

/**
 * Created on 03/05/2017.
 */
public class User {


    private final String numId;
    private final String registryDate;

    public User(String num, String date) {
        this.numId = num;
        this.registryDate = date;
    }


    public String getNumId() {
        return numId;
    }

    public String getRegistryDate() {
        return registryDate;
    }
}
