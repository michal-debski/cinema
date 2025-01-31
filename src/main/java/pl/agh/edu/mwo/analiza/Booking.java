package pl.agh.edu.mwo.analiza;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static pl.agh.edu.mwo.analiza.Cinema.removeBookingIfTicketsAreEmpty;
import static pl.agh.edu.mwo.analiza.Cinema.saveBookingInCinemaStorage;


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

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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
        Booking newBooking = new Booking();
        List<Ticket> tickets = creatingTicketForBooking(seatsForChildren, seatsForAdults, filmDetails, customer);
        newBooking.bookingNumber = generateOrderNumber();
        newBooking.ticketsForBooking.addAll(tickets);
        newBooking.totalPrice = calculateTotalPrice(seatsForAdults, seatsForChildren, filmDetails);
        if (!tickets.isEmpty()) {
            newBooking.getInfoToPayAtCheckout();
        } else {
            newBooking.getInfoAboutEmptyTicketList();
            removeBookingIfTicketsAreEmpty(newBooking);
        }
        saveBookingInCinemaStorage(newBooking);

        return newBooking;
    }

    public static Booking processBuyingTicket(
            List<Seat> seatsForChildren,
            List<Seat> seatsForAdults,
            FilmDetails filmDetails,
            Customer customer
    ) {
        Booking newBooking = new Booking();
        List<Ticket> tickets = creatingTicketForBooking(seatsForChildren, seatsForAdults, filmDetails, customer);
        newBooking.bookingNumber = generateOrderNumber();
        newBooking.ticketsForBooking.addAll(tickets);
        newBooking.totalPrice = calculateTotalPrice(seatsForAdults, seatsForChildren, filmDetails);
        if (!tickets.isEmpty()) {
            newBooking.getInfoForSuccessfullyBookedAndPaid();
        } else {
            newBooking.getInfoAboutEmptyTicketList();
            removeBookingIfTicketsAreEmpty(newBooking);
        }
        saveBookingInCinemaStorage(newBooking);

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


    private static List<Ticket> creatingTicketForBooking(List<Seat> seatsForChildren, List<Seat> seatsForAdults, FilmDetails filmDetails, Customer customer) {
        List<Ticket> tickets = new ArrayList<>();
        if (customer.hasAccount()) {
            createTicketForAllSeatsForCustomerWithAccount(seatsForChildren, seatsForAdults, filmDetails, customer, tickets);
        } else {
            createTicketForAllSeatsForCustomerWithoutAccount(seatsForChildren, seatsForAdults, filmDetails, tickets);
        }
        return tickets;
    }

    private static void createTicketForAllSeatsForCustomerWithAccount(List<Seat> seatsForChildren, List<Seat> seatsForAdults, FilmDetails filmDetails, Customer customer, List<Ticket> tickets) {
        seatsForAdults.forEach(seat -> {
            if (seat.isAvailable()) {
                Ticket ticket = new Ticket(seat.getName(), filmDetails.getFilm().title(), filmDetails.getStartTime(), filmDetails.getStartTime(), customer.getEmail());
                tickets.add(ticket);
                seat.lockSeat();
            } else {
                System.out.println("Seat: " + seat.getName() + " is already locked. Please choose another seat.");
            }
        });
        seatsForChildren.forEach(seat -> {
            if (seat.isAvailable()) {
                Ticket ticket = new Ticket(seat.getName(), filmDetails.getFilm().title(), filmDetails.getStartTime(), filmDetails.getStartTime(), customer.getEmail());
                tickets.add(ticket);
                seat.lockSeat();
            } else {
                System.out.println("Seat: " + seat.getName() + " is already locked. Please choose another seat.");
            }
        });
    }


    private static void createTicketForAllSeatsForCustomerWithoutAccount(List<Seat> seatsForChildren, List<Seat> seatsForAdults, FilmDetails filmDetails, List<Ticket> tickets) {
        seatsForAdults.forEach(seat -> {
            if (seat.isAvailable()) {
                Ticket ticket = new Ticket(seat.getName(), filmDetails.getFilm().title(), filmDetails.getStartTime(), filmDetails.getStartTime());
                tickets.add(ticket);
                seat.lockSeat();
            } else {
                System.out.println("Seat: " + seat.getName() + " is already locked. Please choose another seat.");
            }
        });
        seatsForChildren.forEach(seat -> {
            if (seat.isAvailable()) {
                Ticket ticket = new Ticket(seat.getName(), filmDetails.getFilm().title(), filmDetails.getStartTime(), filmDetails.getStartTime());
                tickets.add(ticket);
                seat.lockSeat();
            } else {
                System.out.println("Seat: " + seat.getName() + " is already locked. Please choose another seat.");
            }
        });
    }

    public static BigDecimal calculateTotalPrice(List<Seat> seatsForAdults, List<Seat> seatsForChildren, FilmDetails filmDetails) {
        return BigDecimal.valueOf(
                        seatsForAdults.size()).multiply(filmDetails.getPriceForAdult())
                .add(BigDecimal.valueOf(
                        seatsForChildren.size()).multiply(filmDetails.getPriceForChildren()));
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
