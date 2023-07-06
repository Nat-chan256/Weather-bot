package ru.moskovka.weatherbot.state;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.moskovka.weatherbot.TelegramBot;

/**
 * Состояние телеграм-бота. Используется для реализации паттерна "Состояние".
 *
 * @author Natalya Moskovka
 * @since 10.06.2023
 */
@Component
public interface TelegramBotState {

	/**
	 * Логика по обработке полученного обновления из телеграма. В частности, обработка сообщения пользователя.
	 */
	void onUpdateReceived(TelegramBot telegramBot, Update update) throws TelegramApiException;
}
