package ru.moskovka.weatherbot.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.moskovka.weatherbot.TelegramBot;
import ru.moskovka.weatherbot.exception.EntryNotFoundInDBException;
import ru.moskovka.weatherbot.service.MessageService;
import ru.moskovka.weatherbot.service.SubscriptionService;
import ru.moskovka.weatherbot.util.BotMessage;

/**
 * Состояние "Изменить геолокацию". Активируется после того, как пользователь ввел "/change_location"
 *
 * @author Natalya Moskovka
 * @since 24.06.2023
 */
@Component
public class ChangeLocationState extends RetrieveLocationState {

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private MessageService messageService;

	@Override
	protected void processLocation(long chatId, Location location, TelegramBot telegramBot)
		throws TelegramApiException {
		try {
			subscriptionService.changeSubscriberLocation(chatId, location);
			messageService.sendMessage(chatId, BotMessage.LOCATION_CHANGED, telegramBot);
		} catch (EntryNotFoundInDBException exception) {
			messageService.sendMessage(chatId, BotMessage.SUBSCRIBER_NOT_FOUND, telegramBot);
		}
	}

	@Override
	protected String getLocationNotFoundMessage() {
		return BotMessage.YOU_SENT_SOME_SHIT_WHEN_CHANGE_LOCATION;
	}
}
