package pl.agh.edu.mwo.analiza;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class TicketService {
    public List<Ticket> creatingTicketForBooking(List<Seat> seatsForChildren, List<Seat> seatsForAdults, FilmDetails filmDetails, Customer customer) {
        List<Ticket> tickets = new ArrayList<>();
        if (customer.hasAccount()) {
            createTicketForAllSeatsForCustomerWithAccount(seatsForChildren, seatsForAdults, filmDetails, customer, tickets);
        } else {
            createTicketForAllSeatsForCustomerWithoutAccount(seatsForChildren, seatsForAdults, filmDetails, tickets);
        }
        return tickets;
    }

    private void createTicketForAllSeatsForCustomerWithAccount(List<Seat> seatsForChildren, List<Seat> seatsForAdults, FilmDetails filmDetails, Customer customer, List<Ticket> tickets) {
        createTicketsForCustomer(seatsForAdults, filmDetails, customer, tickets);
        createTicketsForCustomer(seatsForChildren, filmDetails, customer, tickets);
        log.info("Created tickets for new Booking when customer has an account");
    }


    private void createTicketForAllSeatsForCustomerWithoutAccount(List<Seat> seatsForChildren, List<Seat> seatsForAdults, FilmDetails filmDetails, List<Ticket> tickets) {
        createTicketsForNotSpecifiedCustomer(seatsForAdults, filmDetails, tickets);
        createTicketsForNotSpecifiedCustomer(seatsForChildren, filmDetails, tickets);
        log.info("Created tickets for new Booking when customer doesn't have account");
    }

    private void createTicketsForCustomer(List<Seat> seatsList, FilmDetails filmDetails, Customer customer, List<Ticket> tickets) {
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

    private void createTicketsForNotSpecifiedCustomer(List<Seat> seatsList, FilmDetails filmDetails, List<Ticket> tickets) {
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
