package pl.agh.edu.mwo.analiza;

public class FilmAlreadyExistException extends RuntimeException {

    public FilmAlreadyExistException(String message) {
        super(message);
    }
}
