package uz.uzkassa.uzkassa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Currency {
    String title;

    String code;

    BigDecimal cb_price;

    BigDecimal nbu_buy_price;

    BigDecimal nbu_cell_price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate date;

}
