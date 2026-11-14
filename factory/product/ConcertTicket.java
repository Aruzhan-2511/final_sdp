package factory.product;

import builder.model.Event;
import builder.model.Seat;

public class ConcertTicket extends BaseTicket {
    public ConcertTicket(Event event, Seat seat) {
        super(event, seat);
    }

    @Override
    public String getDescription() {
        return "Concert: " + event.getTitle() + ", " + event.getVenue()
                + ", seat " + seat.getId() + " (" + seat.getSection() + ")";
    }
}
