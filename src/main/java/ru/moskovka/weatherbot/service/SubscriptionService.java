package ru.moskovka.weatherbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Location;
import ru.moskovka.weatherbot.entity.Subscriber;
import ru.moskovka.weatherbot.exception.EntryNotFoundInDBException;
import ru.moskovka.weatherbot.repository.SubscriberRepository;

import java.util.Optional;

/**
 * Сервис для работы с подпиской пользователя на рассылку прогноза погоды.
 *
 * @author Natalya Moskovka
 * @since 10.06.2023
 */
@Service
public class SubscriptionService {

	@Autowired
	private SubscriberRepository repository;

	/**
	 * Оформляет подписку пользователя на ежедневную рассылку прогноза погоды.
	 * @param chatId 		id чата с пользователем
	 * @param userLocation 	геолокация пользователя
	 */
	public void subscribeToNewsLetter(long chatId, Location userLocation) {
		if (!repository.findByChatId(chatId).isPresent()) {
			repository.save(new Subscriber(chatId, userLocation.getLatitude(), userLocation.getLongitude()));
		}
	}

	/**
	 * Отменяет подписку для чата с id = chatId.
	 */
	public void unsubscribe(long chatId) {
		repository.deleteById(chatId);
	}

	public void changeSubscriberLocation(long chatId, Location location) throws EntryNotFoundInDBException {
		Optional<Subscriber> subscriberOptional = repository.findByChatId(chatId);
		if (subscriberOptional.isPresent()) {
			Subscriber subscriber = subscriberOptional.get();
			subscriber.setLatitude(location.getLatitude());
			subscriber.setLongitude(location.getLongitude());
			repository.save(subscriber);
		} else {
			throw new EntryNotFoundInDBException();
		}
	}
}
