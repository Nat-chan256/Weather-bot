package ru.moskovka.weatherbot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

/**
 * Сущность Подписчик. Хранит информацию, нужную для ежедневной рассылки прогноза погоды.
 *
 * @author Natalya Moskovka
 * @since 10.06.2023
 */
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "SUBSCRIBER")
public class Subscriber {
	@NonNull
	@Id
	private long chatId;

	@NonNull
	private double latitude;

	@NonNull
	private double longitude;

	private LocalDate lastNotificationDate;
}
