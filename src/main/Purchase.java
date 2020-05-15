package main;

public class Purchase implements Comparable {
    private String name;
    private double cost;
    private String type;

    public Purchase() {
    }

    public Purchase(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public Purchase(String name, double cost, String type) {
        this.name = name;
        this.cost = cost;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("%s $%.2f", name, cost);
    }

    public String saveToFileFormat() {
        return String.format("%s !$%.2f !$%s", name, cost, type);
    }

    @Override
    public int compareTo(Object o) {
        Purchase purchase = (Purchase) o;

        if(cost > purchase.getCost()){
            return -1;
        }
        else if(cost < purchase.getCost()){
            return 1;
        }
        else{
            return 0;
        }
    }
}
