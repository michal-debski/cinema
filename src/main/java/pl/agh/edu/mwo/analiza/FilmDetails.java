package pl.agh.edu.mwo.analiza;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FilmDetails {

    private final Film film;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final BigDecimal priceForAdult;
    private final BigDecimal priceForChildren;
    private final CinemaRoom cinemaRoom;

    public FilmDetails(Film film, LocalDateTime startTime, BigDecimal priceForAdult, BigDecimal priceForChildren, CinemaRoom cinemaRoom) {
        this.film = film;
        this.startTime = startTime;
        this.priceForAdult = priceForAdult;
        this.priceForChildren = priceForChildren;
        this.cinemaRoom = cinemaRoom;

        this.endTime = startTime.plus(film.duration());
    }

    public Film getFilm() {
        return film;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public BigDecimal getPriceForAdult() {
        return priceForAdult;
    }

    public BigDecimal getPriceForChildren() {
        return priceForChildren;
    }


    @Override
    public String toString() {
        return "Film:" +
                ", title:" + film +
                ", start time: " + startTime +
                ", approx. end time: " + endTime +
                ", price for adult: " + priceForAdult +
                ", priceForChildren: " + priceForChildren;
    }


    public CinemaRoom getCinemaRoom() {
        return cinemaRoom;
    }


}
