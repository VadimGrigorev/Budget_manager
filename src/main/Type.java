package main;

public class Type implements Comparable {
    private String type;
    private double sum;

    public Type() {
        this.sum = 0;
    }

    public Type(String type) {
        this.type = type;
        this.sum = 0;
    }

    public Type(String type, double sum) {
        this.type = type;
        this.sum = sum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return String.format("%s - $%.2f", type, sum);
    }

    @Override
    public int compareTo(Object o) {
        Type type = (Type) o;
        if(sum > type.getSum()){
            return 1;
        }
        else if(sum < type.getSum()){
            return -1;
        }
        else{
            return 0;
        }
    }
}
