package ru.moskovka.weatherbot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Конфигурация погодного телеграм-бота.
 *
 * @author Natalya Moskovka
 * @since 14.05.2023
 */
@Configuration
@Data
@PropertySource("application.properties")
public class BotConfig {
	@Value("${bot.name}")
	String botName;
	@Value("${bot.token}")
	String botToken;
}
