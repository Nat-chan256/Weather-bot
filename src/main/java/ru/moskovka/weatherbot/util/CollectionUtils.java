package ru.moskovka.weatherbot.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Вспомогательный класс для работы с коллекциями.
 *
 * @author Natalya Moskovka
 * @since 10.06.2023
 */
public class CollectionUtils {

	public static <T> Set<T> newHashSet(T... elements) {
		Set<T> set = new HashSet<>();
		Arrays.stream(elements).forEach(set::add);
		return set;
	}
}
