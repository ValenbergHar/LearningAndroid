package Model;

public class Car {
    private int id;
    private String name;
    private String price;

    public Car() {
    }

    public Car(int id, String name, String price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Car(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
