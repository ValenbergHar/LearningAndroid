package Model;

public class Car {
    public int id;
    public String name;
    public String price;

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

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
