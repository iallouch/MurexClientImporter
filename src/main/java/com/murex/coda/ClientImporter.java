package com.murex.coda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClientImporter {

    private static final Logger logger = LogManager.getLogger(ClientImporter.class);

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
                    logger.error("Invalid data: " + line);
                    continue;
                }

                // IBAN,First Name,Middle Name,Last Name,Email,Date of Birth,1st Nationality,2nd Nationality,Country of Residence,Currency,Amount
                String IBAN = parts[0];
                String firstName = parts[1];
                String middleName = parts[2]; // Middle Name is not used in registration
                String lastName = parts[3];
                String email = parts[4];
                String dateOfBirth = parts[5];
                String firstNationality = parts[6];
                String secondNationality = parts[7]; // Second Nationality is not used in registration
                String countryOfResidence = parts[8];
                String currency = parts[9];
                double amount = Double.parseDouble(parts[10]);

                try {
                    // Ensure the data meets the requirements
                    if (!currency.equals("USD") && !currency.equals("EUR")) {
                        logger.error("Invalid currency for email: " + email);
                        continue;
                    }
                    if (calculateAge(dateOfBirth) < 18) {
                        logger.error("Client younger than 18: " + email);
                        continue;
                    }
                    if (amount < 500) {
                        logger.error("Amount less than minimum required for email: " + email);
                        continue;
                    }

                    // Register client
                    bank.register(email, firstName, lastName, dateOfBirth, firstNationality, countryOfResidence, currency, amount);
                    logger.info("Client registered successfully: " + email);
                } catch (IllegalArgumentException e) {
                    logger.error("Failed to register client: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            logger.error("Error reading file: " + filename, e);
        }
    }

    private int calculateAge(String dateOfBirth) {
        // Simplified age calculation. In a real scenario, use a proper date library like java.time
        String[] dobParts = dateOfBirth.split("/");
        int birthYear = Integer.parseInt(dobParts[2]);
        int currentYear = 2024; // Assuming current year is 2024 for this example
        return currentYear - birthYear;
    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        ClientImporter importer = new ClientImporter(bank);
        importer.importClients("clients.csv");
    }
}
