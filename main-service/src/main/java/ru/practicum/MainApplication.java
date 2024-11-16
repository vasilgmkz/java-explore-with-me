package ru.practicum;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        // Проверка связи основного приложения и сервиса статистики
//        ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class);
//        StatsClient statsClient = context.getBean(StatsClient.class);
//        StatFromConsoleDto statFromConsoleDto = new StatFromConsoleDto();
//        statFromConsoleDto.setApp("App");
//        statFromConsoleDto.setIp("Ip");
//        statFromConsoleDto.setUri("Uri");
//        statFromConsoleDto.setTimestamp("2022-09-06 11:00:23");
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