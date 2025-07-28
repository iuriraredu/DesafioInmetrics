package br.com.inmetrics.iuriraredu.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.util.Properties;

/**
 * Classe utilitária para gerenciamento das propriedades de configuração da aplicação.
 * Permite ler e escrever valores no arquivo de propriedades padrão localizado em
 * {@code src/test/resources/config/application.properties}.
 *
 * <p>Os métodos são estáticos e garantem que o arquivo de propriedades seja carregado apenas uma vez.</p>
 */
public class ConfigManager {

    static Properties properties = new Properties();
    private static boolean propertiesLoaded = false;
    private static final String PROPERTIES_PATH = "src/test/resources/config/application.properties";

    /**
     * Obtém o valor associado a uma chave no arquivo de propriedades.
     * Carrega o arquivo apenas uma vez durante a execução.
     *
     * @param key Chave da propriedade a ser buscada.
     * @return Valor da propriedade, ou {@code null} se não encontrada ou em caso de erro ao carregar o arquivo.
     * @throws NumberFormatException Se o valor não puder ser convertido para o tipo esperado em métodos que o utilizam.
     */
    public static String getPropertiesValue(String key) {
        if (!propertiesLoaded) {
            try (InputStream input = new FileInputStream(PROPERTIES_PATH)) {
                properties.load(input);
                propertiesLoaded = true;
            } catch (IOException e) {
                System.err.println("Erro ao carregar as propriedades: " + e.getMessage());
            }
        }
        return properties.getProperty(key);
    }

    /**
     * Define ou atualiza o valor de uma chave no arquivo de propriedades.
     * O valor é persistido imediatamente no arquivo.
     *
     * @param key   Chave da propriedade a ser definida ou atualizada.
     * @param value Valor a ser atribuído à chave.
     * @throws IOException Se ocorrer erro ao salvar o arquivo de propriedades.
     */
    public static void setPropertiesValue(String key, String value) {
        properties.setProperty(key, value);
        try (OutputStream output = new FileOutputStream(PROPERTIES_PATH)) {
            properties.store(output, null);
        } catch (IOException e) {
            System.err.println("Erro ao salvar a propriedade '" + key + "': " + e.getMessage());
        }
    }

    /**
     * Retorna o valor da propriedade "wait" como uma instância de {@link Duration} em segundos.
     * Caso o valor seja inválido, ausente ou menor que 1, retorna {@link Duration} de 5 segundos.
     *
     * @return {@link Duration} correspondente ao valor da propriedade "wait" em segundos, ou 5 segundos se inválido.
     * @throws NumberFormatException Se o valor da propriedade "wait" não puder ser convertido para {@code long} (exceto se capturado internamente).
     */
    public static long getPropertiesValueInLong(String propertyName) {
        try {
            return Long.parseLong(getPropertiesValue(propertyName));
        } catch (Exception e) {
            System.out.println("Valor inválido para '" + propertyName + "', usando valor padrão de 5 segundos.");
        }
        return 5L;
    }

    public static Duration getGlobalTimeout() {
        return Duration.ofSeconds(getPropertiesValueInLong("GLOBAL_TIMEOUT"));
    }

    public static Duration getPollingInterval() {
        return Duration.ofSeconds(getPropertiesValueInLong("POLLING_INTERVAL"));
    }

    public static String getReportsPath() {
        return getPropertiesValue("reportPath");
    }
}