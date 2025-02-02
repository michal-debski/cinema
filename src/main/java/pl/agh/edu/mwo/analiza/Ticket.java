package pl.agh.edu.mwo.analiza;


import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Ticket {

    private final String seat;
    private final String filmTitle;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private String email;


    public Ticket(String seat, String filmTitle, LocalDateTime startTime, LocalDateTime endTime) {
        this.seat = seat;
        this.filmTitle = filmTitle;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Ticket(String seat, String filmTitle, LocalDateTime startTime, LocalDateTime endTime, String email) {
        this.seat = seat;
        this.filmTitle = filmTitle;
        this.startTime = startTime;
        this.endTime = endTime;
        this.email = email;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public String getSeat() {
        return seat;
    }

    @Override
    public String toString() {
        return "Ticket :" +
                "\n  seat: " + seat +
                ",\n  film title: " + filmTitle+
                ",\n  customer, who bought tickets: " + email +
                "";
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
    protected static List<Ticket> creatingTicketForBooking(List<Seat> seatsForChildren, List<Seat> seatsForAdults, FilmDetails filmDetails, Customer customer) {
        List<Ticket> tickets = new ArrayList<>();
        if (customer.hasAccount()) {
            createTicketForAllSeatsForCustomerWithAccount(seatsForChildren, seatsForAdults, filmDetails, customer, tickets);
        } else {
            createTicketForAllSeatsForCustomerWithoutAccount(seatsForChildren, seatsForAdults, filmDetails, tickets);
        }
        return tickets;
    }

    private static void createTicketForAllSeatsForCustomerWithAccount(List<Seat> seatsForChildren, List<Seat> seatsForAdults, FilmDetails filmDetails, Customer customer, List<Ticket> tickets) {
        createTicketsForCustomer(seatsForAdults, filmDetails, customer, tickets);
        createTicketsForCustomer(seatsForChildren, filmDetails, customer, tickets);
        log.info("Created tickets for new Booking when customer has an account");
    }


    private static void createTicketForAllSeatsForCustomerWithoutAccount(List<Seat> seatsForChildren, List<Seat> seatsForAdults, FilmDetails filmDetails, List<Ticket> tickets) {
        createTicketsForNotSpecifiedCustomer(seatsForAdults, filmDetails, tickets);
        createTicketsForNotSpecifiedCustomer(seatsForChildren, filmDetails, tickets);
        log.info("Created tickets for new Booking when customer doesn't have account");
    }

    private static void createTicketsForCustomer(List<Seat> seatsList, FilmDetails filmDetails, Customer customer, List<Ticket> tickets) {
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
}
