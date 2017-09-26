package co.edu.udea.compumovil.gr04_20172.lab2;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dairo.garcia on 21/09/17.
 */

public class Apartment {
    //int photoId;
    String type;
    String value;
    String area;
    String description;
    String ubication;
    byte[] photo;


    Apartment(byte[] photo, String type, String value, String area, String description, String ubication) {
        this.photo = photo;
        this.type = type;
        this.value = value;
        this.area = area;
        this.description = description;
        this.ubication = ubication;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return value;
    }

    public void setPrice(String price) {
        this.value = price;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getUbitacion() {
        return ubication;
    }

    public void setUbication(String ubication) {
        this.type = ubication;
    }
}

