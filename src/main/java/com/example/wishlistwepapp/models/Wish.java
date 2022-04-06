package com.example.wishlistwepapp.models;

public class Wish {

    private int id;
    private String title;
    private String description;
    private String urlAddress;
    private int price;

    public Wish(String title, String description, int price, String urlAddress) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.urlAddress = urlAddress;
    }

    public Wish(int id, String title, String description, String urlAddress, int price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.urlAddress = urlAddress;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "- Wish {" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", urlAddress='" + urlAddress + '\'' +
                ", price=" + price +
                '}';
    }
}
