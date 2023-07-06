package ru.moskovka.weatherbot.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.moskovka.weatherbot.TelegramBot;
import ru.moskovka.weatherbot.service.MessageService;
import ru.moskovka.weatherbot.service.SubscriptionService;
import ru.moskovka.weatherbot.util.BotMessage;

/**
 * Состояние "Подписка". Активируется после того, как пользователь ввел "/subscribe"
 *
 * @author Natalya Moskovka
 * @since 10.06.2023
 */
@Component
public class SubscribeState extends RetrieveLocationState {
	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private MessageService messageService;

	@Override
	protected void processLocation(long chatId, Location location, TelegramBot telegramBot)
		throws TelegramApiException {
		subscriptionService.subscribeToNewsLetter(chatId, location);
		messageService.sendMessage(chatId, BotMessage.SUCCESSFULLY_SIGNED, telegramBot);
	}

	@Override
	protected String getLocationNotFoundMessage() {
		return BotMessage.YOU_SENT_SOME_SHIT_WHEN_SUBSCRIBE;
	}
}
