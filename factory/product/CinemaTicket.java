package factory.product;

import builder.model.Event;
import builder.model.Seat;

public class CinemaTicket extends BaseTicket {
    public CinemaTicket(Event event, Seat seat) {
        super(event, seat);
    }

    @Override
    public String getDescription() {
        return "Cinema: " + event.getTitle() + ", " + event.getVenue()
                + ", seat " + seat.getId() + ", " + event.getDateTime();
    }
}
