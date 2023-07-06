package ru.moskovka.weatherbot.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.moskovka.weatherbot.TelegramBot;
import ru.moskovka.weatherbot.service.WeatherBroadcastMailingListService;

import java.util.concurrent.TimeUnit;

/**
 * Задачи по расписанию, связанные с прогнозом погоды.
 *
 * @author Natalya Moskovka
 * @since 18.06.2023
 */
@Component
public class WeatherScheduledTasks {

	@Autowired
	private TelegramBot telegramBot;

	@Autowired
	private WeatherBroadcastMailingListService weatherBroadcastMailingListService;

	/**
	 * Рассылка прогноза погоды подписанным пользователям.
	 */
	@Scheduled(fixedRate = 24, timeUnit = TimeUnit.HOURS)
	public void sendWeatherBroadcastToSubscribers() {
		weatherBroadcastMailingListService.sendBroadcastToSubscribers(telegramBot);
	}
}
