package builder.model;

public class Seat {
    private final String id;
    private final double price;
    private final int viewRating;
    private final String section;
    private boolean available = true;

    public Seat(String id, double price, int viewRating, String section) {
        this.id = id;
        this.price = price;
        this.viewRating = viewRating;
        this.section = section;
    }

    public String getId() { return id; }
    public double getPrice() { return price; }
    public int getViewRating() { return viewRating; }
    public String getSection() { return section; }
    public boolean isAvailable() { return available; }

    public void book() {
        if (!available) {
            throw new IllegalStateException("Seat is already booked: " + id);
        }
        available = false;
    }

    public void release() {
        available = true;
    }
}
