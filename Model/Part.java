package Model;

import javafx.collections.ObservableList;

public abstract class Part {
    protected int id;
    protected String name;
    protected double price;
    protected int stock;
    protected int min;
    protected int max;

    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public abstract void setId(int id);
    public abstract void setName(String name);
    public abstract void setPrice(double price);
    public abstract void setStock(int stock);
    public abstract void setMin(int min);
    public abstract void setMax(int max);

    public abstract int getId();
    public abstract String getName();
    public abstract double getPrice();
    public abstract int getStock();
    public abstract int getMin();
    public abstract int getMax();
}
