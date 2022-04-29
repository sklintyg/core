package se.inera.intyg.cts.infrastructure.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PasswordGeneratorImplTest {

    @InjectMocks
    PasswordGeneratorImpl randomPasswordGenerator;

    @Test
    void testGenerateSecureRandomPasswordChar() {
        String password = randomPasswordGenerator.generateSecurePassword();
        int specialCharCount = 0;
        int numberCharCount = 0;
        int upperCharCount = 0;
        int lowerCharCount = 0;

        for (char c : password.toCharArray()) {
            if (c >= 33 && c <= 47) {
                specialCharCount++;
            } else if (c >= 48 && c <= 57) {
                numberCharCount++;
            } else if (c >= 65 && c <= 90) {
                upperCharCount++;
            } else if (c >= 97 && c <= 122) {
                lowerCharCount++;
            }
        }
        assertTrue(specialCharCount == 2, "Password validation failed wrong number of special characters.");
        assertTrue(numberCharCount == 2, "Password validation failed wrong number of numbers.");
        assertTrue(upperCharCount == 2, "Password validation failed wrong number of upper case characters.");
        assertTrue(lowerCharCount == 4, "Password validation failed wrong number of lower case characters.");
    }

    @Test
    void testPasswordNotEqual() {
        String password1 = randomPasswordGenerator.generateSecurePassword();
        String password2 = randomPasswordGenerator.generateSecurePassword();
        assertNotEquals(password1, password2, "Password validation failed, passwords are identical");
    }
}