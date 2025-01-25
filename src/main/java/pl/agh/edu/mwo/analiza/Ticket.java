package pl.agh.edu.mwo.analiza;



public class Ticket {

    private final Seat seat;
    private final FilmDetails filmDetails;
    private final Customer customer;


    public Ticket(Seat seat, FilmDetails filmDetails) {
        this.seat = seat;
        this.filmDetails = filmDetails;
        this.customer = null;
    }

    public Ticket(Seat seat, FilmDetails filmDetails, Customer customer) {
        this.seat = seat;
        this.filmDetails = filmDetails;
        this.customer = customer;
    }

    public FilmDetails getFilmDetails() {
        return filmDetails;
    }

    public Seat getSeat() {
        return seat;
    }



    @Override
    public String toString() {
        return "Ticket {" +
                "\n  seat: " + seat +
                ",\n  film title: " + filmDetails.getFilm().title() +
                ",\n  customer, who bought tickets: " + customer +
                "\n}";
    }

    public Customer getCustomer() {
        return customer;
    }

}
