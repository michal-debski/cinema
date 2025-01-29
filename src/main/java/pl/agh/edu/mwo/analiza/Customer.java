package pl.agh.edu.mwo.analiza;

import java.time.LocalDateTime;
import java.util.List;

import static pl.agh.edu.mwo.analiza.Booking.processBooking;


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
                .findFirst().orElseThrow(()-> new IllegalArgumentException("Film not found"));
    }

    public void reserveSeatsForFilm(FilmSchedule filmSchedule, Film film, LocalDateTime localDateTime, List<Seat> seatsForAdults, List<Seat> seatsForChildren, Booking.PaymentType paymentType) {
        FilmDetails filmDetailsByFilmTitleInGivenDate = filmSchedule.getFilmDetailsByFilmTitleInGivenDate(film.title(), localDateTime);
        processBooking(seatsForChildren,seatsForAdults,filmDetailsByFilmTitleInGivenDate, this, paymentType);
   }


    public static List<Ticket> getTicketsForCustomer(Customer customer) {
        return Cinema.getListOfTicketsForGivenEmail(customer.getEmail());
    }


}
