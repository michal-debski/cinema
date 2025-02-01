package pl.agh.edu.mwo.analiza;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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

    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "\nFilm: " +
                "title: " + film +
                ", start time: " + startTime.format(formatter) +
                ", approx. end time: " + endTime.format(formatter) +
                ", price for adult: " + priceForAdult  + " PLN"+
                ", priceForChildren: " + priceForChildren + " PLN";
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
