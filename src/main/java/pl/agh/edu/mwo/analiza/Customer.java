package pl.agh.edu.mwo.analiza;

import static pl.agh.edu.mwo.analiza.FilmSchedule.checkAvailableSeats;

public class Customer extends Person {
    public Customer(String name, String surname, String email, String phone, int age) {
        super(name, surname, email, phone, age);
    }


    public void reserveSeatsForFilm(FilmSchedule filmSchedule, Film film, int numberOfSeatsForAdults, int numberOfSeatsForChildren) {

        boolean b = checkAvailableSeats(filmSchedule, film, numberOfSeatsForAdults + numberOfSeatsForChildren);
        if(b == true){
            processReservation();
        }else{
            return new NoAvailableSeatException("Seats are not available for this film for given time");
        }

    }
}
