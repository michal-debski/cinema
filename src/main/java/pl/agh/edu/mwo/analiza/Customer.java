package pl.agh.edu.mwo.analiza;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static pl.agh.edu.mwo.analiza.Booking.processBooking;


public class Customer extends Person {

    boolean hasAccount;
    public Customer(String name, String surname, String email, String phone, int age, boolean hasAccount) {
        super(name, surname, email, phone, age);
        this.hasAccount = hasAccount;
    }


    public boolean isHasAccount() {
        return hasAccount;
    }

    @Override
    public String toString() {
        return getName() + " " + getSurname() + "  email:" + getEmail();
    }


    public static FilmDetails getFilmDetailsForGivenFilmDetailsList(Film film, List<FilmDetails> filmDetailsList) {
        return filmDetailsList.stream()
                .filter(fd -> fd.getFilm().equals(film))
                .findFirst().orElseThrow(()-> new IllegalArgumentException("Film not found"));
    }

    public void reserveSeatsForFilm(FilmSchedule filmSchedule, Film film, LocalDateTime localDateTime, List<Seat> seatsForAdults, List<Seat> seatsForChildren, Booking.PaymentType paymentType) {
        FilmDetails filmDetailsByFilmTitleInGivenDate = filmSchedule.getFilmDetailsByFilmTitleInGivenDate(film.title(), localDateTime);
        processBooking(seatsForChildren,seatsForAdults,filmDetailsByFilmTitleInGivenDate, this, paymentType);
   }


    public List<Ticket> getTicketsForCustomer(String email) {
        return Cinema.getTicketsForSelectedCustomer(email);
    }


}
