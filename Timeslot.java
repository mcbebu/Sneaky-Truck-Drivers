package tutorial;

public class Timeslot {
    private int capacity;
    private String timingAndPricing;
    public Timeslot(int capacity, String timingAndPricing) {
        this.capacity = capacity;
        this.timingAndPricing = timingAndPricing;
    }

    public void decreaseCapacity() {
        this.capacity--;
    }

    public boolean isAvailable() {
        return capacity > 0;
    }

    @Override
    public String toString() {
        return timingAndPricing;
    }

    public void changeCapacity(int cap) {
        this.capacity = cap;
    }
}
