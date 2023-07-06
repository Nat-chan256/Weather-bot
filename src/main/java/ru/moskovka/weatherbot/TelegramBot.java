package ru.moskovka.weatherbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.moskovka.weatherbot.config.BotConfig;
import ru.moskovka.weatherbot.service.MessageService;
import ru.moskovka.weatherbot.state.TelegramBotState;
import ru.moskovka.weatherbot.state.ZeroState;
import ru.moskovka.weatherbot.util.BotMessage;

import java.io.IOException;
import java.util.Optional;

/**
 * Телеграм-бот, определяющий прогноз погоды для указанной геолокации.
 *
 * @author Natalya Moskovka
 * @since 16.05.2023
 */
@Component
public class TelegramBot extends TelegramLongPollingBot {

	@Autowired
	private BotConfig botConfig;

	@Autowired
	private MessageService messageService;

	@Autowired
	private ApplicationContext context;

	private Optional<TelegramBotState> stateOptional;

	public Optional<TelegramBotState> getStateOptional() {
		if (stateOptional == null) {
			stateOptional = Optional.of(context.getBean(ZeroState.class));
		}
		return stateOptional;
	}

	public void setState(TelegramBotState state) {
		this.stateOptional = Optional.of(state);
	}

	@Override
	public void onUpdateReceived(Update update) {
		getStateOptional().ifPresent(state -> {
			try {
				state.onUpdateReceived(this, update);
			} catch (TelegramApiException e) {
				if (update.hasMessage()) {
					messageService.sendErrorMessage(
						update.getMessage().getChatId(),
						e,
						this,
						BotMessage.ERROR
					);
				}
			}
		});
	}

	@Override
	public String getBotUsername() {
		return botConfig.getBotName();
	}



	@Override
	public String getBotToken() {
		return botConfig.getBotToken();
	}
}
