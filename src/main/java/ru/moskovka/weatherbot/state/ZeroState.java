package ru.moskovka.weatherbot.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.Location;
import ru.moskovka.weatherbot.TelegramBot;
import ru.moskovka.weatherbot.service.MessageService;
import ru.moskovka.weatherbot.service.WeatherService;
import ru.moskovka.weatherbot.util.ZeroStateUserMessageHandler;

/**
 * Исходное состояние телеграм-бота
 *
 * @author Natalya Moskovka
 * @since 10.06.2023
 */
@Component
public class ZeroState implements TelegramBotState {

	@Autowired
	private WeatherService weatherService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private ApplicationContext context;

	@Override
	public void onUpdateReceived(TelegramBot telegramBot, Update update) throws TelegramApiException {
		if (update.hasMessage()) {
			Message message = update.getMessage();
			String messageText = message.getText();
			Long chatId = message.getChatId();
			if (message.hasLocation()) {
				Location userLocation = message.getLocation();
				weatherService.sendWeatherBroadcast(
					chatId,
					userLocation.getLatitude(),
					userLocation.getLongitude(),
					telegramBot
				);
				return;
			}
			ZeroStateUserMessageHandler userMessage = ZeroStateUserMessageHandler.fromMessageText(messageText);
			messageService.sendMessage(chatId, userMessage.handleMessage(chatId, telegramBot, context), telegramBot);
		}
	}
}
