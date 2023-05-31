package com.murex.dntp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {

    @Test
    public void aRegisteredClientCanViewProfileAndViewBalanceOfTheirAccount() {
        Bank bank = new Bank();

        // User information
        String email = "myEamil@gmail.com";
        String firstName = "my";
        String surname = "Email";
        String dateOfBirth = "1/1/1991";
        String nationality = "Lebanese";
        String countryOfResidence = "Lebanon";
        String currency = "USD";
        String amount = "600";

        bank.register(email);

        String profile = bank.viewProfile(email);

        assertEquals("Client[email=myEamil@gmail.com, firstName=my, middleName=, surname=Email, dateOfBirth=1/1/1991, nationality=Lebanese, secondNationality=, countryOfResidence=Lebanon, currency=USD, amount=600]"
                , profile);

        String iban = bank.openAccount(email, 600);

        String balance = bank.viewBalance(iban);
        assertEquals("$600", balance);
    }
}
