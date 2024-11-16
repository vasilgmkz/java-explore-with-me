package ru.practicum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "endpointhit")
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "endpointhit_id")
    Integer id; //"Идентификатор записи"
    @Column(name = "endpointhit_app")
    String app; //"Идентификатор сервиса для которого записывается информация"
    @Column(name = "endpointhit_uri")
    String uri; //"URI для которого был осуществлен запрос"
    @Column(name = "endpointhit_ip")
    String ip; // "IP-адрес пользователя, осуществившего запрос"
    @Column(name = "endpointhit_timestamp")
    LocalDateTime timestamp; // "Дата и время, когда был совершен запрос к эндпоинту (в формате \"yyyy-MM-dd HH:mm:ss\")"
}
