package com.murex.coda;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {

    @Test
    public void aRegisteredClientCanViewProfileAndViewBalanceOfTheirAccount() {
        Bank bank = new Bank();

        // User information
        String email = "myEmail@gmail.com";
        String firstName = "my";
        String surname = "Email";
        String dateOfBirth = "1/1/1991";
        String nationality = "Lebanese";
        String countryOfResidence = "Lebanon";
        String currency = "USD";
        double amount = 600.0;

        bank.register(email, firstName, surname, dateOfBirth, nationality, countryOfResidence, currency, amount);

        String profile = bank.viewProfile(email);

        assertEquals("Client[email=myEmail@gmail.com, firstName=my, surname=Email, dateOfBirth=1/1/1991, nationality=Lebanese, countryOfResidence=Lebanon, currency=USD, amount=600.0]"
                , profile);

        String iban = bank.openAccount(email, 600);

        String balance = bank.viewBalance(iban);
        assertEquals("$600.0", balance);
    }
}


