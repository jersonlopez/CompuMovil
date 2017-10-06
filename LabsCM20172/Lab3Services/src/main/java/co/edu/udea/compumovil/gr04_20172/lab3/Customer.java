package co.edu.udea.compumovil.gr04_20172.lab3;

/**
 * Created by dairo.garcia on 5/10/17.
 */

public class Customer {
    String Id;
    String username;
    String userlastname;
    String birthdate;
    String address;
    String password;
    String numberphone;
    String gender;
    String city;
    String photo;

    public Customer(String Id, String username, String userlastname, String birthdate,
                    String address, String password, String numberphone, String gender,
                    String city, String photo){

        this.Id = Id;
        this.username = username;
        this.userlastname = userlastname;
        this.birthdate = birthdate;
        this.address = address;
        this.password = password;
        this.numberphone = numberphone;
        this.gender = gender;
        this.city = city;
        this.photo = photo;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserlastname() {
        return userlastname;
    }

    public void setUserlastname(String userlastname) {
        this.userlastname = userlastname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumberphone() {
        return numberphone;
    }

    public void setNumberphone(String numberphone) {
        this.numberphone = numberphone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}