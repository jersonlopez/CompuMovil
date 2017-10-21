package co.edu.udea.compumovil.gr04_20172.lab4;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerson.lopez on 21/09/17.
 */

public class Apartment {
    private String area;
    private String largeDescription;
    private String photo;
    private String price;
    private String shortDescription;
    private String type;
    private String ubication;



    Apartment( String area, String largeDescription, String photo, String price, String shortDescription, String type, String ubication) {
        this.photo = photo;
        this.type = type;
        this.price = price;
        this.area = area;
        this.shortDescription= shortDescription;
        this.ubication = ubication;
        this.largeDescription = largeDescription;

    }

    Apartment(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUbitacion() {
        return ubication;
    }

    public void setUbication(String ubication) {
        this.type = ubication;
    }

    public String getLargeDescription() {
        return largeDescription;
    }

    public void setLargeDescription(String largeDescription) {
        this.largeDescription = largeDescription;
    }
}

