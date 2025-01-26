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
    private final PaymentType paymentType;
    private static BigDecimal totalPrice;

    public Booking(PaymentType paymentType) {
        this.paymentType =paymentType;
        this.ticketsForBooking = new ArrayList<>();
        this.bookingNumber = "";
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

    public static Booking processBooking(
            List<Seat> seatsForChildren,
            List<Seat> seatsForAdults,
            FilmDetails filmDetails,
            Customer customer,
            PaymentType paymentType
    ) {
        Booking newBooking = new Booking(paymentType);
        List<Ticket> tickets = creatingTicketForBooking(seatsForChildren, seatsForAdults, filmDetails, customer);
        newBooking.bookingNumber = generateOrderNumber();
        newBooking.ticketsForBooking.addAll(tickets);
        if (!tickets.isEmpty() && newBooking.paymentType == PaymentType.ALREADY_PAID_VIA_NET) {
            newBooking.getInfoForSuccessfullyBooking();
        } else if (!tickets.isEmpty() && newBooking.paymentType == PaymentType.IN_CHECKOUT) {
            newBooking.getInfoToPayAtCheckout();
        } else if (tickets.isEmpty()) {
            newBooking.getInfoAboutEmptyTicketList();
            removeBookingIfTicketsAreEmpty(newBooking);
        }
        totalPrice = calculateTotalPrice(seatsForAdults, seatsForChildren, filmDetails);
        saveBookingInCinemaStorage(newBooking);

        return newBooking;
    }


    private static List<Ticket> creatingTicketForBooking(List<Seat> seatsForChildren, List<Seat> seatsForAdults, FilmDetails filmDetails, Customer customer) {
        List<Ticket> tickets = new ArrayList<>();
        if (customer.isHasAccount()) {
            createTicketForAllSeatsForCustomerWithAccount(seatsForChildren, seatsForAdults, filmDetails, customer, tickets);
        } else {
            createTicketForAllSeatsForCustomerWithoutAccount(seatsForChildren, seatsForAdults, filmDetails, tickets);
        }
        return tickets;
    }

    private static void createTicketForAllSeatsForCustomerWithAccount(List<Seat> seatsForChildren, List<Seat> seatsForAdults, FilmDetails filmDetails, Customer customer, List<Ticket> tickets) {
        seatsForAdults.forEach(seat -> {
            if (seat.isAvailable()) {
                Ticket ticket = new Ticket(seat.getName(), filmDetails.getFilm().title(),filmDetails.getStartTime(), filmDetails.getStartTime(), customer.getEmail());
                tickets.add(ticket);
                seat.lockSeat();
            } else {
                System.out.println("Seat: " + seat.getName() + " is already locked. Please choose another seat.");
            }
        });
        seatsForChildren.forEach(seat -> {
            if (seat.isAvailable()) {
                Ticket ticket = new Ticket(seat.getName(), filmDetails.getFilm().title(),filmDetails.getStartTime(), filmDetails.getStartTime(), customer.getEmail());
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
                Ticket ticket = new Ticket(seat.getName(), filmDetails.getFilm().title(),filmDetails.getStartTime(), filmDetails.getStartTime());
                tickets.add(ticket);
                seat.lockSeat();
            } else {
                System.out.println("Seat: " + seat.getName() + " is already locked. Please choose another seat.");
            }
        });
        seatsForChildren.forEach(seat -> {
            if (seat.isAvailable()) {
                Ticket ticket = new Ticket(seat.getName(), filmDetails.getFilm().title(),filmDetails.getStartTime(), filmDetails.getStartTime());
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
        System.out.println("Successfully booking process fo booking number: " + bookingNumber +
                ". You should be paid in checkout before the screening. ");
    }

    private void getInfoForSuccessfullyBooking() {
        List<String> seatsForBooking = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("Successfully booking process for booking number: " + bookingNumber + ". See you in front of the screen.");
        ticketsForBooking.forEach(ticket -> {
            String seat = ticket.getSeat();
            seatsForBooking.add(seat);
        });
        System.out.println(stringBuilder.append("\n You booked following seats: ").append(seatsForBooking));
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

    public enum PaymentType {
        IN_CHECKOUT,
        ALREADY_PAID_VIA_NET
    }
}
