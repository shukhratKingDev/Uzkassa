package uz.uzkassa.uzkassa.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import uz.uzkassa.uzkassa.dto.Currency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/currencies")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(makeFinal = true)
public class CurrencyController {
    RestTemplate restTemplate;

    @GetMapping()
    public ResponseEntity<List<Currency>> getList() {
        String url = "https://nbu.uz/uz/exchange-rates/json/";
        Currency[] currencyList = restTemplate.getForObject(url, Currency[].class);
        if (currencyList == null) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        return ResponseEntity.ok(Arrays.asList(currencyList));
    }
}
