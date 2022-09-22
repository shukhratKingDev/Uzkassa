package uz.uzkassa.uzkassa.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.uzkassa.uzkassa.dto.ErrorMessage;
import uz.uzkassa.uzkassa.exceptions.EntityAlreadyExistException;
import uz.uzkassa.uzkassa.exceptions.ExpiredException;
import uz.uzkassa.uzkassa.exceptions.NotAllowedException;
import uz.uzkassa.uzkassa.exceptions.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandlerService {
    @ExceptionHandler(NotAllowedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorMessage handleNotAllowedException(NotAllowedException ex){
      ErrorMessage errorMessage=new ErrorMessage();
      errorMessage.setUserMessage("No data found");
      errorMessage.setDeveloperMessage("No data found");
      return errorMessage;
    }


    @ExceptionHandler(EntityAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorMessage handleEntityAlreadyExistException(EntityAlreadyExistException ex){
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setDeveloperMessage("This entity already exist");
        errorMessage.setUserMessage("User with this credentials is already exist");
        return errorMessage;

    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessage handleNotFoundException(NotFoundException ex){
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setDeveloperMessage("Credentials not found");
        errorMessage.setUserMessage("Invalid information");
        return errorMessage;
    }

    @ExceptionHandler(ExpiredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorMessage handleExpiredException(ExpiredException ex){
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setDeveloperMessage("Expired error");
        errorMessage.setUserMessage("The code has already expired");
        return errorMessage;
    }

    @ExceptionHandler(MailAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorMessage handleMailAuthenticationException(MailAuthenticationException ex){
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setUserMessage("We could not sent message because of your security settings");
        errorMessage.setDeveloperMessage("Mail could not sent");
        return errorMessage;
    }


    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage handleMailBadCredentialsException(BadCredentialsException ex){
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setUserMessage("Invalid data");
        errorMessage.setDeveloperMessage("User credentials are not correct");
        return errorMessage;
    }




}
