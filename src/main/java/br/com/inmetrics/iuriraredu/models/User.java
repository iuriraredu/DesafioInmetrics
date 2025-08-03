package br.com.inmetrics.iuriraredu.models;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.shuffle;

/**
 * Representa um usuário do sistema, contendo informações pessoais e métodos utilitários
 * para geração de credenciais e serialização em JSON.
 *
 * <p>Utilizada para cadastro, autenticação e manipulação de dados de usuários em testes automatizados.</p>
 *
 * <p>Os campos incluem endereço, nome, telefone, credenciais de acesso e dados de localização.</p>
 */
public class User {
    /** Gerador seguro de números aleatórios para criação de senhas e nomes de usuário. */
    private static final SecureRandom RANDOM = new SecureRandom();
    /** Tipo de conta do usuário (fixo como "USER"). */
    private final String accountType;
    /** Endereço do usuário. */
    private final String address;
    /** Indica se o usuário aceita ofertas e promoções. */
    private final boolean allowOffersPromotion;
    /** Indica se o usuário é do tipo AOB. */
    private final boolean aobUser;
    /** Nome da cidade do usuário. */
    private final String cityName;
    /** País do usuário (fixo como "BRAZIL_BR"). */
    private final String country;
    /** Primeiro nome do usuário. */
    private final String firstName;
    /** Sobrenome do usuário. */
    private final String lastName;
    /** Número de telefone do usuário. */
    private final String phoneNumber;
    /** Estado ou província do usuário. */
    private final String stateProvince;
    /** Código postal do usuário. */
    private final String zipcode;
    /**
     * Identificador único do usuário.
     */
    @Getter
    @Setter
    private String userId;
    /**
     * Senha do usuário, gerada automaticamente.
     */
    @Getter
    private String password;
    /**
     * E-mail do usuário, gerado automaticamente.
     */
    @Getter
    private final String email;
    /**
     * Nome de login do usuário, gerado automaticamente.
     */
    @Getter
    private final String loginName;

    /**
     * Construtor da classe User.
     *
     * @param address        Endereço do usuário.
     * @param cityName       Cidade do usuário.
     * @param firstName      Primeiro nome do usuário.
     * @param lastName       Sobrenome do usuário.
     * @param phoneNumber    Número de telefone do usuário.
     * @param stateProvince  Estado ou província do usuário.
     * @param zipcode        Código postal do usuário.
     */
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

    /**
     * Retorna uma representação JSON dos dados do usuário.
     *
     * @return String contendo os dados do usuário em formato JSON.
     */
    public String toJson() {
        return new Gson().toJson(this);
//        return String.format( "{" + "\"accountType\":\"%s\"," + "\"address\":\"%s\"," + "\"allowOffersPromotion\":%b," + "\"aobUser\":%b, " + "\"cityName\":\"%s\"," + "\"country\":\"%s\"," + "\"email\":\"%s\"," + "\"firstName\":\"%s\", " + "\"lastName\":\"%s\"," + "\"loginName\":\"%s\"," + "\"password\":\"%s\"," + "\"phoneNumber\":\"%s\"," + "\"stateProvince\":\"%s\"," + "\"zipcode\":\"%s\"" + "}", accountType, address, allowOffersPromotion, aobUser, cityName, country, email, firstName, lastName, loginName, password, phoneNumber, stateProvince, zipcode);
    }

    /**
     * Remove acentos e espaços de uma string.
     *
     * @param texto Texto a ser normalizado.
     * @return String sem acentos e espaços.
     */
    private static String removerAcentosEEspacos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}\\s]", "");
    }

    /**
     * Gera uma senha forte com pelo menos um caractere de cada tipo (minúsculo, maiúsculo, número, símbolo).
     *
     * @param length Tamanho desejado para a senha.
     * @return Senha gerada aleatoriamente.
     */
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

    /**
     * Retorna um caractere aleatório do conjunto informado.
     *
     * @param characterSet Conjunto de caracteres possíveis.
     * @return Caractere selecionado aleatoriamente.
     */
    private static char getRandomChar(@NotNull String characterSet) {
        return characterSet.charAt(RANDOM.nextInt(characterSet.length()));
    }

    /**
     * Embaralha os caracteres de uma string.
     *
     * @param input String a ser embaralhada.
     * @return String com os caracteres embaralhados.
     */
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

