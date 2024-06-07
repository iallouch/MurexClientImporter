package com.murex.coda;

import java.util.HashMap;
import java.util.Map;

public class Bank {

    private Map<String, Client> clients = new HashMap<>();
    private Map<String, Double> accounts = new HashMap<>();

    public void register(String email, String firstName, String surname, String dateOfBirth, String nationality, String countryOfResidence, String currency, double amount) {
        Client client = new Client();
        client.setEmail(email);
        client.setFirstName(firstName);
        client.setSurname(surname);
        client.setDateOfBirth(dateOfBirth);
        client.setNationality(nationality);
        client.setCountryOfResidence(countryOfResidence);
        client.setCurrency(currency);
        client.setAmount(amount);
        clients.put(email, client);
    }

    public String openAccount(String email, double amount) {
        Client client = clients.get(email);
        if (client == null) {
            throw new IllegalArgumentException("Client not registered: " + email);
        }
        String iban = generateIBAN();
        accounts.put(iban, amount);
        return iban;
    }

    public String viewBalance(String iban) {
        return "$" + accounts.getOrDefault(iban, 0.0);
    }

    public String viewProfile(String email) {
        Client client = clients.get(email);
        if (client == null) {
            throw new IllegalArgumentException("Client not registered: " + email);
        }
        return client.toString();
    }

    private String generateIBAN() {
        // Generate a unique IBAN
        return "IBAN-" + accounts.size();
    }

    private static class Client {
        private String email;
        private String firstName;
        private String surname;
        private String dateOfBirth;
        private String nationality;
        private String countryOfResidence;
        private String currency;
        private double amount;

        public void setEmail(String email) {
            this.email = email;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

        public void setCountryOfResidence(String countryOfResidence) {
            this.countryOfResidence = countryOfResidence;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "Client[email=" + email + ", firstName=" + firstName + ", surname=" + surname + ", dateOfBirth=" + dateOfBirth + ", nationality=" + nationality + ", countryOfResidence=" + countryOfResidence + ", currency=" + currency + ", amount=" + amount + "]";
        }
    }
}
