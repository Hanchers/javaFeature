package java8.grammar;

import java.time.*;
import java.util.Date;

public class TestTime {
    public static void main(String[] args) {

        //协调世界时-UTC
        Instant instant = Instant.now();
        System.out.println(instant);
        System.out.println(instant.getEpochSecond());
        System.out.println(Instant.EPOCH);
        System.out.println(Instant.MAX);
        System.out.println(Instant.MIN);

        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);

        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);


        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);


        Date date = Date.from(instant);
        Instant instant1 = date.toInstant();
    }
}
