package uz.uzkassa.uzkassa.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import uz.uzkassa.uzkassa.dto.Currency;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {
    @GetMapping("/list")
    public ResponseEntity<List<Currency>> get(){
        String url="https://nbu.uz/uz/exchange-rates/json/";
        RestTemplate template=new RestTemplate();
        Currency [] currencyList=template.getForObject(url,Currency[].class);
        return ResponseEntity.ok(Arrays.asList(currencyList));
    }
}
