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


    public static FilmDetails getFilmDetailsForGivenFilmDetailsList(Film film, List<FilmDetails> filmDetailsList) {
        return filmDetailsList.stream()
                .filter(fd -> fd.getFilm().equals(film))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Film not found"));
    }

    public void reserveSeatsForFilm(
            FilmSchedule filmSchedule,
            Film film,
            LocalDateTime localDateTime,
            List<Seat> seatsForAdults,
            List<Seat> seatsForChildren
    ) {
        FilmDetails filmDetailsByFilmTitleInGivenDate = filmSchedule.getFilmDetailsByFilmTitleInGivenDate(film.title(), localDateTime);
        processBooking(seatsForChildren, seatsForAdults, filmDetailsByFilmTitleInGivenDate, this);
    }

    public void buyTickets(
            FilmSchedule filmSchedule,
            Film film,
            LocalDateTime localDateTime,
            List<Seat> seatsForAdults,
            List<Seat> seatsForChildren
    ) {
        FilmDetails filmDetailsByFilmTitleInGivenDate = filmSchedule.getFilmDetailsByFilmTitleInGivenDate(film.title(), localDateTime);
        payForTickets(seatsForAdults, seatsForChildren, filmDetailsByFilmTitleInGivenDate);
    }

    private void payForTickets(
            List<Seat> seatsForAdults,
            List<Seat> seatsForChildren,
            FilmDetails filmDetailsByFilmTitleInGivenDate
    ) {
        Booking booking = processBuyingTicket(seatsForAdults, seatsForChildren, filmDetailsByFilmTitleInGivenDate, this);
        BigDecimal totalPrice = booking.getTotalPrice();
        processPayment(totalPrice, booking);
    }

    public List<Ticket> getMyTickets() {
        return Cinema.getListOfTicketsForGivenEmail(this.getEmail());
    }


}
