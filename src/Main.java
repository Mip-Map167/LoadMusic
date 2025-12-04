import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
public class Main {
    public static void main(String[] args) {
        String fileURL = "https://dl3s5.muzika.fun/aHR0cDovL2YubXAzcG9pc2submV0L21wMy8wMDkvNDkzLzkzMi85NDkzOTMyLm1wMw=="+".mp3";
        String saveDir = "/Users/oleg/Documents/программирование/системное программирование/10 лаба (запуск и скачивание)";
        try {
            createDirectoryIfNotExists(saveDir);
            downloadFileNIO(fileURL, saveDir);
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке файла: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void downloadFileNIO(String fileURL,String saveDir) throws IOException {
        URL url = new URL(fileURL);
        String fileName = getFileNameFromURL(url);
        String  result = fileName.substring(0,fileName.length()-4);
        String filePath = saveDir + File.separator + fileName;
        System.out.println("Загрузка файла: " + fileName);
        System.out.println("Сохранение в: " + filePath);
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        }
        System.out.println("Файл успешно загружен!");
    }
    public static void downloadFileIO(String fileURL,String photo, String saveDir) throws IOException {
        URL url = new URL(fileURL);
        String fileName = getFileNameFromURL(url);
        String filePath = saveDir + File.separator + fileName;
        System.out.println("Загрузка файла (IO метод): " + fileName);
        try (InputStream in = url.openStream();
             FileOutputStream out = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        System.out.println("Файл успешно загружен!");
    }
    private static String getFileNameFromURL(URL url) {
        String path = url.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }
    private static void createDirectoryIfNotExists(String dirPath) {
        File directory = new File(dirPath);
        if (!directory.exists()) {
            directory.mkdirs();
            System.out.println("Создана директория: " + dirPath);
        }
    }
}