package pl.agh.edu.mwo.analiza;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FilmDetails {

    private final Film film;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final BigDecimal priceForAdult;
    private final BigDecimal priceForChildren;
    private final String cinemaRoom;
    private final ScreeningType screeningType;

    public FilmDetails(Film film, LocalDateTime startTime, BigDecimal priceForAdult, BigDecimal priceForChildren, String cinemaRoom, ScreeningType screeningType) {
        this.film = film;
        this.startTime = startTime;
        this.priceForAdult = priceForAdult;
        this.priceForChildren = priceForChildren;
        this.cinemaRoom = cinemaRoom;
        this.screeningType = screeningType;

        this.endTime = startTime.plus(film.duration());
    }

    public Film getFilm() {
        return film;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public BigDecimal getPriceForAdult() {
        return priceForAdult;
    }

    public BigDecimal getPriceForChildren() {
        return priceForChildren;
    }


    @Override
    public String toString() {
        return "\nFilm: " +
                "title: " + film +
                ", start time: " + startTime +
                ", approx. end time: " + endTime +
                ", price for adult: " + priceForAdult +
                ", priceForChildren: " + priceForChildren;
    }

    public String getCinemaRoom() {
        return cinemaRoom;
    }

    public ScreeningType getScreeningType() {
        return screeningType;
    }

    public enum ScreeningType{
        SCREENING_3D,
        SCREENING_2D,
        SCREENING_VIP
    }

}
