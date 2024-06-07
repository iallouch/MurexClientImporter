package com.murex.coda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClientImporter {

    private Bank bank;

    public ClientImporter(Bank bank) {
        this.bank = bank;
    }

    public void importClients(String filename) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                // Skip the header line
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length != 11) {
                    System.err.println("Invalid data: " + line);
                    continue;
                }

                // Extract and print the information
                String IBAN = parts[0];
                String firstName = parts[1];
                String middleName = parts[2];
                String lastName = parts[3];
                String email = parts[4];
                String dateOfBirth = parts[5];
                String firstNationality = parts[6];
                String secondNationality = parts[7];
                String countryOfResidence = parts[8];
                String currency = parts[9];
                double amount = Double.parseDouble(parts[10]);

                try {
                    bank.register(email, firstName, lastName, dateOfBirth, firstNationality, countryOfResidence, currency, amount);
                    System.out.println("Client registered successfully: " + email);
                } catch (IllegalArgumentException e) {
                    System.err.println("Failed to register client: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        ClientImporter importer = new ClientImporter(bank);
        importer.importClients("clients.csv");
    }
}
