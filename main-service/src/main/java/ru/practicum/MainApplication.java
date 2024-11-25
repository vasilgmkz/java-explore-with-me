package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
        // Проверка связи основного приложения и сервиса статистики
//        StatsClient statsClient = new StatsClientImpl("http://localhost:9090");
//        StatFromConsoleDto statFromConsoleDto = new StatFromConsoleDto();
//        statFromConsoleDto.setApp("App");
//        statFromConsoleDto.setIp("Ip");
//        statFromConsoleDto.setUri("Uri");
//        statFromConsoleDto.setTimestamp(LocalDateTime.now());
//        LocalDateTime start = LocalDateTime.now().minusYears(4);
//        LocalDateTime end = LocalDateTime.now().plusYears(6);
//        List<String> uris = List.of("/events/5", "/events/1", "/events/2", "Uri");
//        boolean unique = false;
//        Scanner input = new Scanner(System.in);
//        String text;
//        do {
//            System.out.print("Пожалуйста, post/get/exit: ");
//            text = input.nextLine();
//            if (text.equals("post")) {
//                statsClient.addHit(statFromConsoleDto);
//            }
//            if (text.equals("get")) {
//                List<StatInConsoleDto> get = statsClient.getStats(start, end, uris, unique);
//                System.out.println(get);
//            }
//        } while (!text.equals("exit"));
    }
}