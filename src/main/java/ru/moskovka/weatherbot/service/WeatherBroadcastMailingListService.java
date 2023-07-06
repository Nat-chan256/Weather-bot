package ru.moskovka.weatherbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.moskovka.weatherbot.TelegramBot;
import ru.moskovka.weatherbot.repository.SubscriberRepository;

import java.time.LocalDate;
import java.util.Date;

/**
 * Сервис для работы с рассылкой.
 *
 * @author Natalya Moskovka
 * @since 18.06.2023
 */
@Service
public class WeatherBroadcastMailingListService {

	@Autowired
	private SubscriberRepository subscriberRepository;

	@Autowired
	private WeatherService weatherService;

	public void sendBroadcastToSubscribers(TelegramBot telegramBot) {
		LocalDate today = LocalDate.now();
		subscriberRepository.findAll()
			.forEach(subscriber -> {
				if (subscriber.getLastNotificationDate() == null
					|| subscriber.getLastNotificationDate().isBefore(today)) {
					weatherService.sendWeatherBroadcast(
						subscriber.getChatId(),
						subscriber.getLatitude(),
						subscriber.getLongitude(),
						telegramBot
					);
					subscriber.setLastNotificationDate(today);
					subscriberRepository.save(subscriber);
				}
			});
	}
}
