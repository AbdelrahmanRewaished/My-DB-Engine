package utilities.datatypes;

public class Bound {
    String min, max;

    public Bound(String min, String max) {
        this.min = min;
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }
}
