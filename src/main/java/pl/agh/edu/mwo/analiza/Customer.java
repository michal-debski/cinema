package pl.agh.edu.mwo.analiza;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static pl.agh.edu.mwo.analiza.Booking.*;


public class Customer extends Person {

    boolean hasAccount;

    public Customer(String name, String surname, String email, String phone, int age, boolean hasAccount) {
        super(name, surname, email, phone, age);
        this.hasAccount = hasAccount;
    }


    public boolean hasAccount() {
        return hasAccount;
    }

    @Override
    public String toString() {
        return getName() + " " + getSurname() + "  email:" + getEmail();
    }

    public void reserveSeatsForFilm(
            Cinema cinema,
            Film film,
            LocalDateTime localDateTime,
            List<Seat> seatsForAdults,
            List<Seat> seatsForChildren
    ) {

        FilmDetails filmDetailsByFilmTitleInGivenDate = cinema.getFilmSchedule().getFilmDetailsByFilmTitleInGivenDate(film.title(), localDateTime);
        processBooking(cinema, seatsForChildren, seatsForAdults, filmDetailsByFilmTitleInGivenDate, this);
    }

    public void buyTickets(
            Cinema cinema,
            Film film,
            LocalDateTime localDateTime,
            List<Seat> seatsForAdults,
            List<Seat> seatsForChildren
    ) {
        FilmDetails filmDetailsByFilmTitleInGivenDate = cinema.getFilmSchedule().getFilmDetailsByFilmTitleInGivenDate(film.title(), localDateTime);
        payForTickets(cinema, seatsForAdults, seatsForChildren, filmDetailsByFilmTitleInGivenDate);
    }

    public List<Ticket> checkMyTicketsInGivenCinema(Cinema cinema) {
        return cinema.getListOfTicketsForGivenEmail(this.getEmail());
    }

    private void payForTickets(
            Cinema cinema,
            List<Seat> seatsForAdults,
            List<Seat> seatsForChildren,
            FilmDetails filmDetailsByFilmTitleInGivenDate
    ) {
        Booking booking = processBuyingTicket(cinema, seatsForAdults, seatsForChildren, filmDetailsByFilmTitleInGivenDate, this);
        BigDecimal totalPrice = booking.getTotalPrice();
        processPayment(totalPrice, booking);
    }


}
