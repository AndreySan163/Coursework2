import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NetworkScanner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите IP-адрес для сканирования: ");
        String ip = scanner.nextLine();

        List<Integer> openPorts = scanPorts(ip);

        System.out.println("Сканирование сети завершено. Найдены открытые порты: " + openPorts);

        System.out.print("Хотите ли вы сохранить результаты в файл? (да/нет): ");
        String saveToFile = scanner.nextLine();

        if ("да".equalsIgnoreCase(saveToFile)) {
            savePortsToFile(openPorts);
            System.out.println("Результаты успешно сохранены в файл.");
        }

        scanner.close();
    }

    public static List<Integer> scanPorts(String ip) {
        List<Integer> openPorts = new ArrayList<>();

        for (int port = 1; port <= 65535; port++) {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 1000);
                socket.close();
                openPorts.add(port);
            } catch (IOException ignored) {
            }
        }

        return openPorts;
    }

    public static void savePortsToFile(List<Integer> openPorts) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя файла: ");
        String fileName = scanner.nextLine();

        try (FileWriter writer = new FileWriter(fileName)) {
            for (Integer port : openPorts) {
                writer.write(Integer.toString(port));
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении файла: " + e.getMessage());
        }

        scanner.close();
    }
}