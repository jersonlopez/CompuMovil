package co.edu.udea.compumovil.gr04_20172.lab2;

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
    int photoId;


    Apartment(int in1, String type, String value, String area, String description) {
        this.photoId = in1;
        this.type = type;
        this.value= value;
        this.area=area;
        this.description=description;

    }

    /*public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }*/

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
}

