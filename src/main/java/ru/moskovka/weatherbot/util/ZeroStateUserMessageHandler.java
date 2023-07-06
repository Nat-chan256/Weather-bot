package ru.moskovka.weatherbot.util;

import org.springframework.context.ApplicationContext;
import ru.moskovka.weatherbot.TelegramBot;
import ru.moskovka.weatherbot.service.SubscriptionService;
import ru.moskovka.weatherbot.state.ChangeLocationState;
import ru.moskovka.weatherbot.state.SubscribeState;

import java.util.Set;

/**
 * Обработчик сообщений от пользователя.
 *
 * @author Natalya Moskovka
 * @since 10.06.2023
 */
public enum ZeroStateUserMessageHandler {

	start {
		@Override
		public String handleMessage(long chatId, TelegramBot telegramBot, ApplicationContext context) {
			return BotMessage.START;
		}
	}, subscribe {
		@Override
		public String handleMessage(long chatId, TelegramBot telegramBot, ApplicationContext context) {
			telegramBot.setState(context.getBean(SubscribeState.class));
			return BotMessage.SEND_LOCATION_TO_SUBSCRIBE;
		}
	}, unsubscribe {
		@Override
		public String handleMessage(long chatId, TelegramBot telegramBot, ApplicationContext context) {
			SubscriptionService subscriptionService = context.getBean(SubscriptionService.class);
			if (subscriptionService != null) {
				subscriptionService.unsubscribe(chatId);
				return BotMessage.SIGN_CANCELLED;
			}
			return BotMessage.FAILED_TO_UNSIGN;
		}
	}, change_location {
		@Override
		public String handleMessage(long chatId, TelegramBot telegramBot, ApplicationContext context) {
			telegramBot.setState(context.getBean(ChangeLocationState.class));
			return BotMessage.SEND_LOCATION_TO_CHANGE;
		}
	}, wtf {
		@Override
		public String handleMessage(long chatId, TelegramBot telegramBot, ApplicationContext context) {
			return BotMessage.YOU_SENT_SOME_SHIT;
		}
	};

	private static Set<String> USER_MESSAGES_SET = CollectionUtils.newHashSet(
		"/start",
		"/subscribe",
		"/unsubscribe",
		"/change_location"
	);

	public static ZeroStateUserMessageHandler fromMessageText(String userMessageText) {
		if (!USER_MESSAGES_SET.contains(userMessageText)) {
			return wtf;
		}
		return valueOf(userMessageText.replace(StringHelper.SLASH, StringHelper.EMPTY_LINE));
	}

	/**
	 * Обработать сообщение.
	 * @param chatId	id чата, в который будет отправляться ответ.
	 * @return 			ответ пользователю
	 */
	public String handleMessage(long chatId, TelegramBot telegramBot, ApplicationContext context) {
		return StringHelper.EMPTY_LINE;
	}
}
