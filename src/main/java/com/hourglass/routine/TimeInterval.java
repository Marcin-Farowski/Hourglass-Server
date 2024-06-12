package com.hourglass.routine;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TimeInterval {

    private int seconds;
    private int minutes;
    private int hours;
    private int days;
    private int months;
    private int years;
}