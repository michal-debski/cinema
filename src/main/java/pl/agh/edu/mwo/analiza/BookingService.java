package pl.agh.edu.mwo.analiza;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static pl.agh.edu.mwo.analiza.Cinema.removeBookingIfTicketsAreEmpty;
import static pl.agh.edu.mwo.analiza.Cinema.saveBookingInCinemaStorage;

@Slf4j
public class BookingService {
    private final TicketService ticketService;

    public BookingService(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    public static Booking processBooking(
            List<Seat> seatsForChildren,
            List<Seat> seatsForAdults,
            FilmDetails filmDetails,
            Customer customer
    ) {
        log.info("Starting processing new booking");
        TicketService ticketService = new TicketService();
        List<Ticket> ticketsForBooking = ticketService.creatingTicketForBooking(seatsForChildren, seatsForAdults, filmDetails, customer);
        Booking newBooking = new Booking(ticketsForBooking, generateOrderNumber());
        log.info("Number of tickets for booking: {}", newBooking.getTicketsForBooking().size());
        log.info("Added booking number: {}", newBooking.getBookingNumber());
        newBooking.setTotalPrice(calculateTotalPrice(seatsForAdults, seatsForChildren, filmDetails));
        log.info("Total price: {} PLN", newBooking.getTotalPrice());
        if (!ticketsForBooking.isEmpty()) {
            newBooking.getInfoToPayAtCheckout();
            log.info("Successfully created booking: {} with {} tickets",
                    newBooking.getBookingNumber(),
                    ticketsForBooking.size()
            );
        } else {
            newBooking.getInfoAboutEmptyTicketList();
            log.error("Empty ticket list");
            removeBookingIfTicketsAreEmpty(newBooking);
            log.info("Removed booking from cinema storage");
        }
        saveBookingInCinemaStorage(newBooking);
        log.info("Saved booking in cinema storage");
        return newBooking;
    }

    public static Booking processBuyingTicket(
            List<Seat> seatsForChildren,
            List<Seat> seatsForAdults,
            FilmDetails filmDetails,
            Customer customer
    ) {
        log.info("Starting processing buying tickets");
        TicketService ticketService = new TicketService();
        List<Ticket> ticketsForBooking = ticketService.creatingTicketForBooking(seatsForChildren, seatsForAdults, filmDetails, customer);
        Booking newBooking = new Booking(ticketsForBooking, generateOrderNumber());
        log.info("Number of tickets for booking: {}", newBooking.getTicketsForBooking().size());
        log.info("Booking number for this booking: {}", newBooking.getBookingNumber());
        newBooking.setTotalPrice(calculateTotalPrice(seatsForAdults, seatsForChildren, filmDetails));
        log.info("Total price: {} PLN", newBooking.getTotalPrice());

        if (!ticketsForBooking.isEmpty()) {
            newBooking.getInfoForSuccessfullyBookedAndPaid();
            log.info("Completed buying tickets for booking number: {} with {} tickets", newBooking.getBookingNumber(), ticketsForBooking.size());
        } else {
            newBooking.getInfoAboutEmptyTicketList();
            log.error("Empty ticket list for buying ticket process. Cannot do anything more");
            removeBookingIfTicketsAreEmpty(newBooking);
            log.info("Removed booking from cinema storage");
        }

        saveBookingInCinemaStorage(newBooking);
        log.info("Saved booking in cinema storage");
        return newBooking;
    }

    public static void processPayment(BigDecimal totalPrice, Booking booking) {
        if (totalPrice != null) {
            System.out.println("You successfully paid the booking with total price " + totalPrice + " PLN");
            booking.setPaid(true);
        } else {
            System.out.println("You cannot pay, because total price is null");
        }

    }

    private static BigDecimal calculateTotalPrice(List<Seat> seatsForAdults, List<Seat> seatsForChildren, FilmDetails filmDetails) {
        return BigDecimal.valueOf(
                        seatsForAdults.size()).multiply(filmDetails.getPriceForAdult())
                .add(BigDecimal.valueOf(
                        seatsForChildren.size()).multiply(filmDetails.getPriceForChildren()));
    }
    private static String generateOrderNumber() {
        return "%s%s%s%s%s".formatted(
                LocalDateTime.now().getDayOfMonth(),
                LocalDateTime.now().getMonth().getValue(),
                LocalDateTime.now().getYear(),
                randomInt(10, 100),
                randomInt(10, 100)
        );
    }
    @SuppressWarnings("SameParameterValue")
    private static int randomInt(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    public TicketService getTicketService() {
        return ticketService;
    }
}
