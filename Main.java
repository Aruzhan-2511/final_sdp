import builder.model.Event;
import builder.model.EventType;
import builder.model.Seat;
import decorator.TicketExtra;
import facade.ReservationFacade;
import facade.model.Reservation;
import observer.subscriber.UserNotifier;
import strategy.SeatSelectionStrategy;
import strategy.impl.BestViewSeatStrategy;
import strategy.impl.CheapestSeatStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ReservationFacade facade = new ReservationFacade();
        seedEvents(facade);
        try (Scanner scanner = new Scanner(System.in)) {
            run(facade, scanner);
        } catch (NoSuchElementException exception) {
            System.out.println("Input closed. Goodbye!");
        }
    }

    private static void seedEvents(ReservationFacade facade) {
        facade.registerEvent(new Event.Builder()
                .id("C01")
                .title("Live Concert")
                .type(EventType.CONCERT)
                .dateTime(LocalDateTime.of(2027, 1, 15, 19, 0))
                .venue("Main Hall")
                .addSeat(new Seat("A1", 6000.0, 9, "Front"))
                .addSeat(new Seat("B5", 3500.0, 5, "Back"))
                .build());
        facade.registerEvent(new Event.Builder()
                .id("M01")
                .title("Movie Night")
                .type(EventType.CINEMA)
                .dateTime(LocalDateTime.of(2027, 1, 16, 18, 30))
                .venue("Hall 2")
                .addSeat(new Seat("A1", 3500.0, 8, "Center"))
                .build());
    }

    private static void run(ReservationFacade facade, Scanner scanner) {
        while (true) {
            printMenu();
            String choice = ask(scanner, "Choose an option: ");
            if ("0".equals(choice)) {
                System.out.println("Goodbye!");
                return;
            }
            try {
                if (!handle(facade, scanner, choice)) {
                    System.out.println("Invalid option. Enter a number from 0 to 5.");
                }
            } catch (IllegalArgumentException | IllegalStateException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private static boolean handle(ReservationFacade facade, Scanner scanner, String choice) {
        switch (choice) {
            case "1" -> showEvents(facade);
            case "2" -> recommendSeat(facade, scanner);
            case "3" -> bookTicket(facade, scanner);
            case "4" -> subscribe(facade, scanner);
            case "5" -> cancelReservation(facade, scanner);
            default -> { return false; }
        }
        return true;
    }

    private static void showEvents(ReservationFacade facade) {
        for (Event event : facade.getEvents()) {
            System.out.println(event.getId() + " - " + event.getTitle());
        }
    }

    private static void recommendSeat(ReservationFacade facade, Scanner scanner) {
        String eventId = ask(scanner, "Enter event ID: ");
        String strategyChoice = ask(scanner, "Choose strategy (1 = cheapest, 2 = best view): ");
        SeatSelectionStrategy strategy = chooseStrategy(strategyChoice);
        facade.recommendSeat(eventId, strategy).ifPresentOrElse(
                seat -> System.out.println("Recommended seat: " + seat.getId()
                        + " - " + seat.getPrice() + " KZT"),
                () -> System.out.println("No available seats."));
    }

    private static SeatSelectionStrategy chooseStrategy(String choice) {
        return switch (choice) {
            case "1" -> new CheapestSeatStrategy();
            case "2" -> new BestViewSeatStrategy();
            default -> throw new IllegalArgumentException("Unknown strategy.");
        };
    }

    private static void bookTicket(ReservationFacade facade, Scanner scanner) {
        String eventId = ask(scanner, "Enter event ID: ");
        String seatId = ask(scanner, "Enter seat ID: ");
        List<TicketExtra> extras = askExtras(scanner);
        Reservation reservation = facade.book(eventId, seatId, extras);
        System.out.println("Booked: " + reservation.getId());
        System.out.println(reservation.getTicket().getDescription());
        System.out.println("Total: " + reservation.getTicket().getPrice() + " KZT");
    }

    private static List<TicketExtra> askExtras(Scanner scanner) {
        List<TicketExtra> extras = new ArrayList<>();
        for (TicketExtra extra : TicketExtra.values()) {
            String answer = ask(scanner,
                    "Add " + extra.getTitle() + " for " + extra.getPrice() + " KZT? (y/N): ");
            if ("y".equalsIgnoreCase(answer)) {
                extras.add(extra);
            }
        }
        return extras;
    }

    private static void subscribe(ReservationFacade facade, Scanner scanner) {
        String eventId = ask(scanner, "Enter event ID: ");
        String userName = ask(scanner, "Enter your name: ");
        if (userName.isEmpty()) {
            throw new IllegalArgumentException("Name is required.");
        }
        facade.subscribe(eventId, new UserNotifier(userName));
        System.out.println("Subscribed to " + eventId + ".");
    }

    private static void cancelReservation(ReservationFacade facade, Scanner scanner) {
        String reservationId = ask(scanner, "Enter reservation ID: ");
        facade.cancel(reservationId);
        System.out.println("Reservation cancelled.");
    }

    private static String ask(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("       TICKET RESERVATION SYSTEM");
        System.out.println("========================================");
        System.out.println("  1. Show events");
        System.out.println("  2. Recommend a seat");
        System.out.println("  3. Book a ticket");
        System.out.println("  4. Subscribe to seat notifications");
        System.out.println("  5. Cancel a reservation");
        System.out.println("  0. Exit");
        System.out.println("----------------------------------------");
    }
}
