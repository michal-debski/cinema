package pl.agh.edu.mwo.analiza;

import java.util.List;



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

    public List<FilmDetails> getAvailableFilmScheduleForGivenTime(FilmSchedule filmSchedule, int day) {
        return filmSchedule.getFilmDetailsForGivenDay(day);
    }
    public static FilmDetails getFilmDetailsForGivenFilmDetailsList(Film film, List<FilmDetails> filmDetailsList) {
        return filmDetailsList.stream()
                .filter(fd -> fd.getFilm().equals(film))
                .findFirst().orElseThrow(()-> new IllegalArgumentException("Film not found"));
    }

//    public void reserveSeatsForFilm(FilmDetails filmDetails, Film film, int day, List<Seat> seatsForAdults, List<Seat> seatsForChildren) {
//        List<Seat> listOfSeatsToBook = Stream.of(seatsForAdults, seatsForChildren).flatMap(List::stream).toList();
//        boolean b = checkAvailableSeats(filmSchedule, day, film, listOfSeatsToBook);
//        if (b == true) {
//            processReservation();
//        } else {
//            return new NoAvailableSeatException("Seats are not available for this film for given time");
//        }
//   }


//    private List<Ticket> getTicketsForCustomer(String email) {
//        return getTicketsForSelectedCustomer(email);
//    }


}
