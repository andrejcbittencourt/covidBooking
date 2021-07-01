package kea.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Booking {

    @Id
    private Long BookingID;
    private String Title;
    private Long UserID;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate Date;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime Time;

}
