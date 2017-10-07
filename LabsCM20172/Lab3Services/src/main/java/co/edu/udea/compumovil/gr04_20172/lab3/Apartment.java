package co.edu.udea.compumovil.gr04_20172.lab3;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerson.lopez on 21/09/17.
 */

public class Apartment {
    //int photoId;

    String type;
    String price;
    String area;
    String description;
    String ubication;
    Bitmap photo;
    int id1;

    public Apartment(Bitmap photo, String type, String price, String area, String description, String ubication, int id) {
        this.photo = photo;
        this.type = type;
        this.price = price;
        this.area = area;
        this.description = description;
        this.ubication = ubication;
        this.id1 = id;

    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getUbitacion() {
        return ubication;
    }

    public void setUbication(String ubication) {
        this.ubication = ubication;
    }

    public int getId1() {
        return id1;
    }

    public void setId1(int id) {
        this.id1 = id;
    }


    //Atributos
    String Id;
    String photoapartment;
    String typeapartment;
    String priceapartment;
    String areaapartment;
    String roomsapartment;
    String shortdescriptionapartment;
    String largedescriptionapartment;

    public Apartment(String Id, String photoapartment, String typeapartment, String priceapartment, String areaapartment, String roomsapartment, String shortdescriptionapartment, String largedescriptionapartment) {
        this.Id = Id;
        this.photoapartment = photoapartment;
        this.typeapartment = typeapartment;
        this.priceapartment = priceapartment;
        this.areaapartment = areaapartment;
        this.roomsapartment = roomsapartment;
        this.shortdescriptionapartment = shortdescriptionapartment;
        this.largedescriptionapartment = largedescriptionapartment;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPhotoapartment() {
        return photoapartment;
    }

    public void setPhotoapartment(String photoapartment) {
        this.photoapartment = photoapartment;
    }

    public String getTypeapartment() {
        return typeapartment;
    }

    public void setTypeapartment(String typeapartment) {
        this.typeapartment = typeapartment;
    }

    public String getPriceapartment() {
        return priceapartment;
    }

    public void setPriceapartment(String priceapartment) {
        this.priceapartment = priceapartment;
    }

    public String getAreaapartment() {
        return areaapartment;
    }

    public void setAreaapartment(String areaapartment) {
        this.areaapartment = areaapartment;
    }

    public String getRoomsapartment() {
        return roomsapartment;
    }

    public void setRoomsapartment(String roomsapartment) {
        this.roomsapartment = roomsapartment;
    }

    public String getShortdescriptionapartment() {
        return shortdescriptionapartment;
    }

    public void setShortdescriptionapartment(String shortdescriptionapartment) {
        this.shortdescriptionapartment = shortdescriptionapartment;
    }

    public String getLargedescriptionapartment() {
        return largedescriptionapartment;
    }

    public void setLargedescriptionapartment(String largedescriptionapartment) {
        this.largedescriptionapartment = largedescriptionapartment;
    }
}