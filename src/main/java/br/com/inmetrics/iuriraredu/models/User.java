package br.com.inmetrics.iuriraredu.models;

import lombok.Getter;
import lombok.Setter;

import java.security.SecureRandom;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.shuffle;

public class User {

    private static final SecureRandom RANDOM = new SecureRandom();
    private final String accountType;
    private final String address;
    private final boolean allowOffersPromotion;
    private final boolean aobUser;
    private final String cityName;
    private final String country;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String stateProvince;
    private final String zipcode;

    @Getter
    @Setter
    private String userId;
    @Getter
    private final String password;
    @Getter
    private final String email;
    @Getter
    private final String loginName;

    public User(String address, String cityName, String firstName, String lastName,
                String phoneNumber, String stateProvince, String zipcode) {
        String userName = removerAcentosEEspacos(firstName + lastName) + getRandomChar("0123456789");
        this.accountType = "USER";
        this.address = address;
        this.allowOffersPromotion = true;
        this.aobUser = true;
        this.cityName = cityName;
        this.country = "BRAZIL_BR";
        this.firstName = firstName;
        this.lastName = lastName;
        this.loginName = userName.toLowerCase();
        this.email = userName.toLowerCase() + "@gmail.com";
        this.password = generateStrongPassword(8);
        this.phoneNumber = phoneNumber.replaceAll("[\\s()-]", "");
        this.stateProvince = stateProvince;
        this.zipcode = zipcode;
    }

    public String getUserInJson() {
        return String.format(
                "{" +
                        "\"accountType\":\"%s\"," +
                        "\"address\":\"%s\"," +
                        "\"allowOffersPromotion\":%b," +
                        "\"aobUser\":%b, " +
                        "\"cityName\":\"%s\"," +
                        "\"country\":\"%s\"," +
                        "\"email\":\"%s\"," +
                        "\"firstName\":\"%s\", " +
                        "\"lastName\":\"%s\"," +
                        "\"loginName\":\"%s\"," +
                        "\"password\":\"%s\"," +
                        "\"phoneNumber\":\"%s\"," +
                        "\"stateProvince\":\"%s\"," +
                        "\"zipcode\":\"%s\"" +
                        "}",
                accountType, address, allowOffersPromotion, aobUser, cityName, country,
                email, firstName, lastName, loginName, password, phoneNumber, stateProvince, zipcode);
    }

    private static String removerAcentosEEspacos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}\\s]", "");
    }

    private static String generateStrongPassword(int length) {
        final String lowercase = "abcdefghijklmnopqrstuvwxyz";
        final String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String numbers = "0123456789";
        final String symbols = "!@#$%^&*()_+-=[]{}|;:,.<>?";
        final String allChars = lowercase + uppercase + numbers + symbols;

        StringBuilder password = new StringBuilder();

        // Garante pelo menos 1 caractere de cada tipo
        password.append(getRandomChar(lowercase));
        password.append(getRandomChar(uppercase));
        password.append(getRandomChar(numbers));
        password.append(getRandomChar(symbols));

        // Preenche o restante da senha com caracteres aleatórios
        while (password.length() < length) {
            password.append(getRandomChar(allChars));
        }

        // Embaralha a senha para não ter padrão fixo
        return shuffleString(password.toString());
    }

    private static char getRandomChar(String characterSet) {
        return characterSet.charAt(RANDOM.nextInt(characterSet.length()));
    }

    private static String shuffleString(String input) {
        List<Character> characters = new ArrayList<>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }
        shuffle(characters, RANDOM);

        StringBuilder shuffled = new StringBuilder();
        for (char c : characters) {
            shuffled.append(c);
        }
        return shuffled.toString();
    }
}

