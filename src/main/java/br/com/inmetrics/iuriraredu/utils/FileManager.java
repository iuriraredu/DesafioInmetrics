package br.com.inmetrics.iuriraredu.utils;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static br.com.inmetrics.iuriraredu.utils.ConfigManager.getReportsPath;
import static org.apache.commons.io.FileUtils.copyFile;
import static org.apache.commons.io.FileUtils.forceMkdir;
import static org.openqa.selenium.OutputType.FILE;

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
            String folderPath = getReportsPath() + "/screenshots/" + LocalDate.now().toString();
            String extension = ".png"; // Define a extensão do arquivo
            filePath = folderPath + "/" + testName + "/" + timestamp + extension; // Atribui o caminho aqui

            File screenshot = ((TakesScreenshot)driver).getScreenshotAs(FILE);
            forceMkdir(new File(folderPath));
            copyFile(screenshot, new File(filePath));
            System.out.println("Screenshot salvo em: " + filePath); // Adiciona um log para confirmar
        } catch (IOException e) {
            System.err.println("Erro ao tirar screenshot: " + e.getMessage());
        } catch (ClassCastException e) { // Caso o driver não seja TakesScreenshot
            System.err.println("O driver não suporta tirar screenshots: " + e.getMessage());
        }
        return filePath; // Retorna o caminho ou null em caso de falha
    }

    /**
     * Obtém um arquivo de imagem a partir do diretório de recursos do projeto.
     *
     * @param fileName Nome do arquivo de imagem a ser recuperado.
     * @return Um objeto {@link File} representando o arquivo de imagem.
     * @throws RuntimeException Se o arquivo não for encontrado no caminho especificado.
     */
    public static File getImageFile(String fileName) {
        // Caminho relativo a partir da raiz do projeto
        String imagePath = Paths.get("src", "test", "resources", "images", fileName).toString();
        File file = new File(imagePath);

        if (!file.exists()) {
            throw new RuntimeException("Arquivo não encontrado: " + file.getAbsolutePath());
        }
        return file;
    }
}
