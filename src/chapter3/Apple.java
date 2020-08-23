package chapter3;

public class Apple {
    private int weight;
    private String color;

    public Apple(int weight, String color) {
        this.color = color;
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public String getColor() {
        return this.color;
    }

    @Override
    public String toString() {
        return this.getWeight() + " " + this.getColor();
    }
}
