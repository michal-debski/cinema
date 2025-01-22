package pl.agh.edu.mwo.analiza;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FilmDetails {

    private final long id;
    private final Film film;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final BigDecimal priceForAdult;
    private final BigDecimal priceForChildren;
    private final CinemaRoom cinemaRoom;

    public FilmDetails(long id, Film film, LocalDateTime startTime, BigDecimal priceForAdult, BigDecimal priceForChildren, CinemaRoom cinemaRoom) {
        this.id = id;
        this.film = film;
        this.startTime = startTime;
        this.priceForAdult = priceForAdult;
        this.priceForChildren = priceForChildren;
        this.cinemaRoom = cinemaRoom;
        this.endTime = startTime.plus(film.duration());
    }

    public long getId() {
        return id;
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

    public CinemaRoom getCinemaRoom() {
        return cinemaRoom;
    }

    @Override
    public String toString() {
        return "FilmDetails{" +
                "id=" + id +
                ", film=" + film +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", priceForAdult=" + priceForAdult +
                ", priceForChildren=" + priceForChildren +
                ", cinemaRoom=" + cinemaRoom +
                '}';
    }


}
