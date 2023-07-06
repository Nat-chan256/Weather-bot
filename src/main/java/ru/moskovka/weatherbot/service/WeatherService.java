package ru.moskovka.weatherbot.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.moskovka.weatherbot.TelegramBot;
import ru.moskovka.weatherbot.util.BotMessage;
import ru.moskovka.weatherbot.util.StringHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * Сервис для получения прогноза погоды по геолокации.
 *
 * @author Natalya Moskovka
 * @since 16.05.2023
 */
@Service
public class WeatherService {

	private static final String WEATHER_API_BASE_URL = "https://api.openweathermap.org/data/2.5/";
	private static final String WEATHER = "weather";
	private static final String LAT_PARAM = "lat";
	private static final String LON_PARAM = "lon";
	private static final String UNITS_PARAM = "units";
	private static final String APPID_PARAM = "appid";
	private static final String LANG_PARAM = "lang";

	private static final String API_KEY = "d468e076cbd225c49914fe2df5a5e385";
	private static final String METRIC_VALUE = "metric";
	private static final String LANG_VALUE = "ru";

	private static final String WEATHER_KEY = "weather";
	private static final String MAIN_KEY = "main";
	private static final String TEMP_KEY = "temp";
	private static final String PRESSURE_KEY = "pressure";
	private static final String WIND_KEY = "wind";
	private static final String SPEED_KEY = "speed";
	private static final String DESCRIPTION_KEY = "description";

	@Autowired
	private MessageService messageService;

	public <T> String getWeatherBroadcast(double latitude, double longitude) throws IOException {
		URL url = new URL(
			WEATHER_API_BASE_URL
				+ WEATHER
				+ "?"
				+ createParamsString(
					LAT_PARAM,
				Double.toString(latitude),
				LON_PARAM,
				Double.toString(longitude),
				APPID_PARAM,
				API_KEY,
				UNITS_PARAM,
				METRIC_VALUE,
				LANG_PARAM,
				LANG_VALUE
			)
		);
		Scanner scanner = new Scanner((InputStream) url.getContent());
		StringBuffer result = new StringBuffer();
		while (scanner.hasNext()) {
			result.append(scanner.next());
		}

		JSONObject jsonObject = new JSONObject(result.toString());
		JSONObject mainJsonObject = jsonObject.getJSONObject(MAIN_KEY);
		StringBuffer broadcastMessage = new StringBuffer(BotMessage.WEATHER);
		broadcastMessage.append(jsonObject.getJSONArray(WEATHER_KEY).getJSONObject(0).getString(DESCRIPTION_KEY))
			.append(StringHelper.LINE_BREAK)
			.append(BotMessage.TEMPERATURE).append(mainJsonObject.getDouble(TEMP_KEY))
			.append(StringHelper.LINE_BREAK)
			.append(BotMessage.ATMOSPHERIC_PRESSURE).append(mainJsonObject.getInt(PRESSURE_KEY))
			.append(StringHelper.LINE_BREAK)
			.append(BotMessage.WIND_SPEED).append(jsonObject.getJSONObject(WIND_KEY).getDouble(SPEED_KEY))
			.append(StringHelper.SPACE).append(BotMessage.METER_PER_SECOND);

		return broadcastMessage.toString();
	}

	private String createParamsString(String... params) {
		if (params.length % 2 != 0) {
			throw new IllegalArgumentException("Длина params должна быть четной");
		}
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < params.length - 1; i += 2) {
			result.append(params[i] + "=" + params[i+1]);
			if (i + 1 < params.length - 1) {
				result.append("&");
			}
		}
		return result.toString();
	}

	public void sendWeatherBroadcast(long chatId, double latitude, double longitude, TelegramBot telegramBot) {
		try {
			messageService.sendMessage(chatId, getWeatherBroadcast(latitude, longitude), telegramBot);
		} catch (IOException exception) {
			messageService.sendErrorMessage(chatId, exception, telegramBot, BotMessage.ERROR_ON_GETTING_BROADCAST);
		} catch(TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
