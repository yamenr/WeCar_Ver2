package com.example.wecar.D_FireBase;


import android.os.Parcel;
import android.os.Parcelable;

public class Car implements Parcelable {
    private String nameCar;
    private  String horse_power;//כוח סוס
    private  String owners; //בעלים
    private String phone;
    private String color; //צבע
    private  String car_num; //מספר הרכב
    private  String manufacturer; //יַצרָן
    private  String year; //שנת היצור
    private  String Car_model; //דגם רכב
    private  String test;
    private  String kilometre;
    private  String Engine_capacity; //קיבולת מנוע
    private  String Gear_shifting_model;  //דגם מעביר הילוכים
    private  String   price; //מחיר
    private String photo;

    public Car() {
    }

    public Car(String nameCar, String horse_power, String owners, String phone, String color,
               String car_num, String manufacturer, String year, String car_model, String test,
               String kilometre, String engine_capacity, String gear_shifting_model, String price, String photo) {
        this.nameCar = nameCar;
        this.horse_power = horse_power;
        this.owners = owners;
        this.phone = phone;
        this.color = color;
        this.car_num = car_num;
        this.manufacturer = manufacturer;
        this.year = year;
        Car_model = car_model;
        this.test = test;
        this.kilometre = kilometre;
        Engine_capacity = engine_capacity;
        Gear_shifting_model = gear_shifting_model;
        this.price = price;
        this.photo = photo;
    }

    protected Car(Parcel in) {
        this.nameCar = in.readString();
        this.horse_power = in.readString();
        this.owners = in.readString();
        this.phone = in.readString();
        this.color = in.readString();
        this.car_num = in.readString();
        this.manufacturer = in.readString();
        this.year = in.readString();
        this.Car_model = in.readString();
        this.test = in.readString();
        this.kilometre = in.readString();
        this.Engine_capacity = in.readString();
        this.Gear_shifting_model = in.readString();
        this.price = in.readString();
        this.photo = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nameCar);
        dest.writeString(this.horse_power);
        dest.writeString(this.owners);
        dest.writeString(this.phone);
        dest.writeString(this.color);
        dest.writeString(this.car_num);
        dest.writeString(this.manufacturer);
        dest.writeString(this.year);
        dest.writeString(this.Car_model);
        dest.writeString(this.test);
        dest.writeString(this.kilometre);
        dest.writeString(this.Engine_capacity);
        dest.writeString(this.Gear_shifting_model);
        dest.writeString(this.price);
        dest.writeString(this.photo);
    }

    public String getNameCar() {
        return nameCar;
    }

    public void setNameCar(String nameCar) {
        this.nameCar = nameCar;
    }

    public String getHorse_power() {
        return horse_power;
    }

    public void setHorse_power(String horse_power) {
        this.horse_power = horse_power;
    }

    public String getOwners() {
        return owners;
    }

    public void setOwners(String owners) {
        this.owners = owners;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCar_num() {
        return car_num;
    }

    public void setCar_num(String car_num) {
        this.car_num = car_num;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCar_model() {
        return Car_model;
    }

    public void setCar_model(String car_model) {
        Car_model = car_model;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getKilometre() {
        return kilometre;
    }

    public void setKilometre(String kilometre) {
        this.kilometre = kilometre;
    }

    public String getEngine_capacity() {
        return Engine_capacity;
    }

    public void setEngine_capacity(String engine_capacity) {
        Engine_capacity = engine_capacity;
    }

    public String getGear_shifting_model() {
        return Gear_shifting_model;
    }

    public void setGear_shifting_model(String gear_shifting_model) {
        Gear_shifting_model = gear_shifting_model;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Car{" +
                "nameCar='" + nameCar + '\'' +
                ", horse_power='" + horse_power + '\'' +
                ", owners='" + owners + '\'' +
                ", phone='" + phone + '\'' +
                ", color='" + color + '\'' +
                ", car_num='" + car_num + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", year='" + year + '\'' +
                ", Car_model='" + Car_model + '\'' +
                ", test='" + test + '\'' +
                ", kilometre='" + kilometre + '\'' +
                ", Engine_capacity='" + Engine_capacity + '\'' +
                ", Gear_shifting_model='" + Gear_shifting_model + '\'' +
                ", price='" + price + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
