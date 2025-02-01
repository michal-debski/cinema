package pl.agh.edu.mwo.analiza;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static pl.agh.edu.mwo.analiza.Cinema.removeBookingIfTicketsAreEmpty;
import static pl.agh.edu.mwo.analiza.Cinema.saveBookingInCinemaStorage;

@Slf4j
public class Booking {
    private String bookingNumber;
    private final List<Ticket> ticketsForBooking;
    private BigDecimal totalPrice;
    private boolean isPaid;

    public Booking() {
        this.ticketsForBooking = new ArrayList<>();
        this.bookingNumber = "";
        this.isPaid = false;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public List<Ticket> getTicketsForBooking() {
        return ticketsForBooking;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public static Booking processBooking(
            List<Seat> seatsForChildren,
            List<Seat> seatsForAdults,
            FilmDetails filmDetails,
            Customer customer
    ) {
        log.info("Starting processing new booking");
        Booking newBooking = new Booking();
        List<Ticket> tickets = creatingTicketForBooking(seatsForChildren, seatsForAdults, filmDetails, customer);
        newBooking.ticketsForBooking.addAll(tickets);
        log.info("Number of tickets for booking: {}", newBooking.ticketsForBooking.size());
        newBooking.bookingNumber = generateOrderNumber();
        log.info("Added booking number: {}", newBooking.bookingNumber);
        newBooking.totalPrice = calculateTotalPrice(seatsForAdults, seatsForChildren, filmDetails);
        log.info("Total price: {} PLN", newBooking.totalPrice);
        if (!tickets.isEmpty()) {
            newBooking.getInfoToPayAtCheckout();
            log.info("Successfully created booking: {} with {} tickets",
                    newBooking.bookingNumber,
                    tickets.size()
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
        Booking newBooking = new Booking();
        List<Ticket> tickets = creatingTicketForBooking(seatsForChildren, seatsForAdults, filmDetails, customer);
        newBooking.ticketsForBooking.addAll(tickets);
        log.info("Number of tickets for booking: {}",
                newBooking.ticketsForBooking.size()
        );
        newBooking.bookingNumber = generateOrderNumber();
        log.info("Booking number for this booking: {}",
                newBooking.bookingNumber
        );
        newBooking.totalPrice = calculateTotalPrice(seatsForAdults, seatsForChildren, filmDetails);
        log.info("Total price: {} PLN", newBooking.totalPrice);
        if (!tickets.isEmpty()) {
            newBooking.getInfoForSuccessfullyBookedAndPaid();
            log.info("Completed buying tickets for booking number: {} with {} tickets",
                    newBooking.bookingNumber,
                    tickets.size()
            );

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
            System.out.println("You succefully paid the booking with total price " + totalPrice);
            booking.setPaid(true);
        } else {
            System.out.println("You cannot pay, because total price is null");
        }

    }


    public static BigDecimal calculateTotalPrice(List<Seat> seatsForAdults, List<Seat> seatsForChildren, FilmDetails filmDetails) {
        return BigDecimal.valueOf(
                        seatsForAdults.size()).multiply(filmDetails.getPriceForAdult())
                .add(BigDecimal.valueOf(
                        seatsForChildren.size()).multiply(filmDetails.getPriceForChildren()));
    }

    private static List<Ticket> creatingTicketForBooking(List<Seat> seatsForChildren, List<Seat> seatsForAdults, FilmDetails filmDetails, Customer customer) {
        List<Ticket> tickets = new ArrayList<>();
        if (customer.hasAccount()) {
            createTicketForAllSeatsForCustomerWithAccount(seatsForChildren, seatsForAdults, filmDetails, customer, tickets);
        } else {
            createTicketForAllSeatsForCustomerWithoutAccount(seatsForChildren, seatsForAdults, filmDetails, tickets);
        }
        return tickets;
    }

    private static void createTickets(List<Seat> seatsList, FilmDetails filmDetails, Customer customer, List<Ticket> tickets) {
        seatsList.forEach(seat -> {
            if (seat.isAvailable()) {
                Ticket ticket = new Ticket(seat.getName(), filmDetails.getFilm().title(), filmDetails.getStartTime(), filmDetails.getStartTime(), customer.getEmail());
                tickets.add(ticket);
                seat.lockSeat();
            } else {
                System.out.println("Seat: " + seat.getName() + " is already locked. Please choose another seat.");
            }
        });
    }

    private static void createTicketForAllSeatsForCustomerWithAccount(List<Seat> seatsForChildren, List<Seat> seatsForAdults, FilmDetails filmDetails, Customer customer, List<Ticket> tickets) {
        createTickets(seatsForAdults, filmDetails, customer, tickets);
        createTickets(seatsForChildren, filmDetails, customer, tickets);
        log.info("Created tickets for new Booking");
    }


    private static void createTicketForAllSeatsForCustomerWithoutAccount(List<Seat> seatsForChildren, List<Seat> seatsForAdults, FilmDetails filmDetails, List<Ticket> tickets) {
        createTicketsForNotSpecifiedCustomer(seatsForAdults, filmDetails, tickets);
        createTicketsForNotSpecifiedCustomer(seatsForChildren, filmDetails, tickets);
    }

    private static void createTicketsForNotSpecifiedCustomer(List<Seat> seatsList, FilmDetails filmDetails, List<Ticket> tickets) {
        seatsList.forEach(seat -> {
            if (seat.isAvailable()) {
                Ticket ticket = new Ticket(seat.getName(), filmDetails.getFilm().title(), filmDetails.getStartTime(), filmDetails.getStartTime());
                tickets.add(ticket);
                seat.lockSeat();
            } else {
                System.out.println("Seat: " + seat.getName() + " is already locked. Please choose another seat.");
            }
        });
    }

    private void getInfoToPayAtCheckout() {
        System.out.println("Successfully booking process for booking number: " + bookingNumber +
                ". You should be paid in checkout before the screening. Total price: " + totalPrice + " PLN");
    }

    private void getInfoForSuccessfullyBookedAndPaid() {
        List<String> seatsForBooking = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder(
                "Successfully booking and payment process for booking number: " + bookingNumber + " for total price: "
                        + totalPrice + " PLN. See you in front of the screen.");
        ticketsForBooking.forEach(ticket -> {
            String seat = ticket.getSeat();
            seatsForBooking.add(seat);
        });
        System.out.println(stringBuilder.append("\nYou booked following seats: ").append(seatsForBooking));
    }

    private void getInfoAboutEmptyTicketList() {
        System.out.println("We don't have any tickets in storage, because you select locked seats");
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

}
