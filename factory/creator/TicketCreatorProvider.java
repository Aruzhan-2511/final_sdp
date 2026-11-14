package factory.creator;

import builder.model.Event;

public final class TicketCreatorProvider {
    private TicketCreatorProvider() { }

    public static TicketCreator forEvent(Event event) {
        return switch (event.getType()) {
            case CONCERT -> new ConcertTicketCreator();
            case CINEMA -> new CinemaTicketCreator();
        };
    }
}
