package pl.agh.edu.mwo.analiza;

import java.time.Duration;

public record Film (

        String title,
        String filmType,
        String description,
        Duration duration
) {

}
