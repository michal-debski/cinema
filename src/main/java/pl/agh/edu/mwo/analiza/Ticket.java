package pl.agh.edu.mwo.analiza;


import java.time.LocalDateTime;

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
                "\n";
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
}
