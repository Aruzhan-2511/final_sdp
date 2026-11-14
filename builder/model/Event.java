package builder.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Event {
    private final String id;
    private final String title;
    private final EventType type;
    private final LocalDateTime dateTime;
    private final String venue;
    private final List<Seat> seats;

    private Event(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.type = builder.type;
        this.dateTime = builder.dateTime;
        this.venue = builder.venue;
        this.seats = Collections.unmodifiableList(new ArrayList<>(builder.seats));
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public EventType getType() { return type; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getVenue() { return venue; }
    public List<Seat> getSeats() { return seats; }

    public Optional<Seat> findSeat(String seatId) {
        return seats.stream()
                .filter(seat -> seat.getId().equals(seatId))
                .findFirst();
    }

    public static class Builder {
        private String id;
        private String title;
        private EventType type;
        private LocalDateTime dateTime;
        private String venue;
        private final List<Seat> seats = new ArrayList<>();

        public Builder id(String id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder type(EventType type) { this.type = type; return this; }
        public Builder dateTime(LocalDateTime dateTime) { this.dateTime = dateTime; return this; }
        public Builder venue(String venue) { this.venue = venue; return this; }
        public Builder addSeat(Seat seat) { this.seats.add(seat); return this; }

        public Event build() {
            if (id == null || title == null || type == null
                    || dateTime == null || venue == null) {
                throw new IllegalStateException("Event: required fields are missing");
            }
            if (seats.isEmpty()) {
                throw new IllegalStateException("Event must have at least one seat");
            }
            return new Event(this);
        }
    }
}
