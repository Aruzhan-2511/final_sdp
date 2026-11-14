package observer.subscriber;

import builder.model.Event;
import builder.model.Seat;

public interface SeatObserver {
    void onSeatAvailable(Event event, Seat seat);
}
