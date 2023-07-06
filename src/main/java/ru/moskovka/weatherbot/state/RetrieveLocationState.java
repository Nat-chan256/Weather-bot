package ru.moskovka.weatherbot.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.moskovka.weatherbot.TelegramBot;
import ru.moskovka.weatherbot.service.MessageService;

/**
 * Состояние, получающее геолокацию пользователя. Наследники этого класса должны реализовать логику, обрабтывающую
 * полученную от пользователя геолокацию. После этой обработки бот возвращается в состояние {@link ZeroState}
 *
 * @author Natalya Moskovka
 * @since 24.06.2023
 */
public abstract class RetrieveLocationState implements TelegramBotState {

	@Autowired
	private MessageService messageService;

	@Autowired
	private ApplicationContext context;

	@Override
	public void onUpdateReceived(TelegramBot telegramBot, Update update) throws TelegramApiException {
		if (update.hasMessage()) {
			Message message = update.getMessage();
			Long chatId = message.getChatId();
			if (message.hasLocation()) {
				processLocation(chatId, message.getLocation(), telegramBot);
			} else {
				messageService.sendMessage(chatId, getLocationNotFoundMessage(), telegramBot);
			}
			telegramBot.setState(context.getBean(ZeroState.class));
		}
	}

	/**
	 * Метод, обрабатывающий геолокацию, которую прислал пользователь.
	 * @param chatId	id чата с пользователем.
	 * @param location	геолокация пользователя.
	 */
	protected abstract void processLocation(long chatId, Location location, TelegramBot telegramBot)
		throws TelegramApiException;

	/**
	 * @return сообщение, которое будет отправлено пользователю в случае, если он не прислал геолокацию.
	 */
	protected abstract String getLocationNotFoundMessage();
}
