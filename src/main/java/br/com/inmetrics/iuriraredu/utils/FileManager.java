package br.com.inmetrics.iuriraredu.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe utilitária para operações de arquivos relacionadas a testes automatizados.
 * Fornece métodos para captura e armazenamento de screenshots durante a execução dos testes.
 */
public class FileManager {

    /**
     * Captura um screenshot da tela atual do navegador e salva em um diretório específico.
     *
     * @param driver   Instância do {@link WebDriver} utilizada para capturar o screenshot.
     * @param testName Nome do teste ou cenário, utilizado no nome do arquivo.
     * @return Caminho do arquivo de screenshot salvo, ou {@code null} em caso de falha.
     */
    public static String takeScreenShot(WebDriver driver, String testName) {
        String filePath = null; // Inicializa com null
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String folderPath = "target/screenshots/" + LocalDate.now().toString();
            filePath = folderPath + "/" + testName + "_" + timestamp + ".png"; // Atribui o caminho aqui

            File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.forceMkdir(new File(folderPath));
            FileUtils.copyFile(screenshot, new File(filePath));
            System.out.println("Screenshot salvo em: " + filePath); // Adiciona um log para confirmar
        } catch (IOException e) {
            System.err.println("Erro ao tirar screenshot: " + e.getMessage());
            // Ou usar uma biblioteca de log como Log4j/SLF4j
        } catch (ClassCastException e) { // Caso o driver não seja TakesScreenshot
            System.err.println("O driver não suporta tirar screenshots: " + e.getMessage());
        }
        return filePath; // Retorna o caminho ou null em caso de falha
    }
}
