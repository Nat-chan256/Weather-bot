package ru.moskovka.weatherbot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.moskovka.weatherbot.TelegramBot;
import ru.moskovka.weatherbot.util.BotMessage;

import java.io.IOException;

/**
 * Сервис, отправляющий сообщения в телеграм.
 *
 * @author Natalya Moskovka
 * @since 10.06.2023
 */
@Service
public class MessageService {

	/**
	 * Отправить сообщение пользователю
	 * @param message текст сообщения
	 */
	public void sendMessage(long chatId, String message, TelegramBot telegramBot) throws TelegramApiException {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(chatId);
		sendMessage.setText(message);
		telegramBot.execute(sendMessage);
	}

	public void sendErrorMessage(long chatId, Exception exception, TelegramBot telegramBot, String message) {
		try {
			sendMessage(chatId, String.format(message, exception.getMessage()), telegramBot);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
