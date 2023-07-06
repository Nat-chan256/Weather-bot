package ru.moskovka.weatherbot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.moskovka.weatherbot.entity.Subscriber;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с подписчиками.
 *
 * @author Natalya Moskovka
 * @since 10.06.2023
 */
public interface SubscriberRepository extends CrudRepository<Subscriber, Long> {

	Optional<Subscriber> findByChatId(long chatId);
}
