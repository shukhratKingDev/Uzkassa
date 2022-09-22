package uz.uzkassa.uzkassa.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotFoundException extends RuntimeException {
    public NotFoundException(String s) {
        super(s);
    }
}
