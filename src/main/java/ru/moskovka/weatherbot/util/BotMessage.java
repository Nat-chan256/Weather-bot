package ru.moskovka.weatherbot.util;

/**
 * Константы, содержащие сообщения бота.
 *
 * @author Natalya Moskovka
 * @since 10.06.2023
 */
public class BotMessage {
	public static final String WEATHER = "Прогноз: ";
	public static final String TEMPERATURE = "Температура: ";
	public static final String ATMOSPHERIC_PRESSURE = "Атмосферное давление: ";
	public static final String WIND_SPEED = "Скорость ветра: ";
	public static final String METER_PER_SECOND = "м/с";
	public static final String START = "Привет! Вам доступны следующие опции:\n" +
		"1. Получить текущий прогноз погоды. Для этого отправьте свою геолокацию.\n" +
		"2. Подписаться на ежедневную рассылку прогноза погоды. Для этого отправьте сообщение /subscribe\n" +
		"3. Отписаться от рассылки. Для этого отправьте сообщение /unsubscribe\n" +
		"4. Изменить геолокацию, если подписаны на рассылку прогноза. Для этого отправьте сообщение /change_location";
	public static final String SUCCESSFULLY_SIGNED = "Вы успешно подписаны на рассылку прогноза погоды";
	public static final String SIGNED_ALREADY = "Вы уже подписаны на рассылку.";
	public static final String FAILED_TO_SIGN = "В попытке подписаться отказано. Попробуйте позже.";
	public static final String SEND_LOCATION_TO_SUBSCRIBE = "Чтобы подписаться на рассылку, отправьте геолокацию, для " +
		"которой будет присылаться прогноз погоды.";
	public static final String YOU_SENT_SOME_SHIT_WHEN_SUBSCRIBE = "Не удалось получить геолокацию. Подписка не была " +
		"оформлена";
	public static final String ERROR = "Возникла ошибка: %s";
	public static final String ERROR_ON_GETTING_BROADCAST = "Возникла ошибка при попытке получить прогноз погоды: %s";
	public static final String SIGN_CANCELLED = "Подписка на прогноз погоды отменена.";
	public static final String FAILED_TO_UNSIGN = "Не удалось отменить подписку. Попробуйте позднее.";
	public static final String LOCATION_CHANGED = "Геолокация успешно изменена.";
	public static final String YOU_SENT_SOME_SHIT_WHEN_CHANGE_LOCATION = "Не удалось получить геолокацию. Геолокация " +
		"не была изменена";
	public static final String SUBSCRIBER_NOT_FOUND = "Вы не подписаны на рассылку прогноза погоды. Чтобы " +
		"подписаться, отправьте команду /subscribe";
	public static final String SEND_LOCATION_TO_CHANGE = "Отправьте новую геолокацию.";
	public static final String YOU_SENT_SOME_SHIT = "Неизвестная команда. Отправьте /start, чтобы получить список " +
		"доступных команд";
}
