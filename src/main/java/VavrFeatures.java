import com.google.common.base.Strings;
import io.vavr.*;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;

import java.math.BigInteger;

import static io.vavr.API.*;
import static io.vavr.Predicates.is;

/**
 * Истинно функциональный стиль.
 */
@Log4j2
public class VavrFeatures {

	/**
	 * Используются коллекции, называющтеся одинакаво со стандартными.
	 * Не сильно удобно, желательно обговаривать какие из коллекций использовать
	 */
	public static void main(String[] args) {
		arraysJava();
		log.log(Level.getLevel("SEPAR"), "---");
		tuples();
		log.log(Level.getLevel("SEPAR"), "---");
		functions();
		log.log(Level.getLevel("SEPAR"), "---");
		values();
		log.log(Level.getLevel("SEPAR"), "---");
		patternMatching();
	}

	/**
	 * Паттрен матчинг - то, ради чего вообще стоит просто использовать vavr.
	 * Что-то между switch и if.
	 */
	private static void patternMatching() {
		// Простой пример
		//$( ... ) - это стандартный паттерн, можно лямбдой сделать
		int i = 1;
		String s = Match(i).of(
				Case($(is(1)), "one"),
				Case($(is(2)), "two"),
				Case($(), "?")
		);

		log.debug(s);

		// Можно вот так делать
		Function1<Integer, String> fizzBuzz = number -> {
			return Match(number).of(
				Case($(item -> item % 3 == 0 && item % 5 == 0), "FizzBuzz"),
				Case($(item -> item % 3 == 0), "Fizz"),
				Case($(item -> item % 5 == 0), "Buzz"),
				Case($(), number.toString())
			);
		};

		log.debug(fizzBuzz.apply(3));
		log.debug(fizzBuzz.apply(5));
		log.debug(fizzBuzz.apply(15));
		log.debug(fizzBuzz.apply(17));

		// Аналог try {} catch {} в виде Объекта
		log.debug(Try.of(() -> 1 /  0).toEither());
	}

	/**
	 * Прикольные фичи, содержащие значения
	 */
	private static void values() {

		// Lazy - ленивая инициализация
		// Только для интерфейсов
		CharSequence s = Lazy.val(() -> "aaa", CharSequence.class);
		log.debug(s);

		// Вот это никогда не выполнится
		CharSequence never = Lazy.val(() -> {
			log.error("Never happens");
			return "never";
		}, CharSequence.class);

		// Either - содержит одно значение, но одного из двух типов
		Function2<Long, Long, Either<Long, BigInteger>> bigMultiple = (a, b) -> {
			BigInteger bi = BigInteger.valueOf(a);
			BigInteger result = bi.multiply(BigInteger.valueOf(b));
			try {
				return Either.left(result.longValueExact());
			} catch (ArithmeticException e) {
				return Either.right(result);
			}
		};

		Either<Long, BigInteger> number = bigMultiple.apply(1L, 2L);
		// Так делать не надо наверное, но просто для примера
		log.debug(number.right().getOrNull());
		log.debug(number.left().getOrNull());

		Either<Long, BigInteger> bigNumber = bigMultiple.apply(Long.MAX_VALUE, 3L);
		log.debug(bigNumber.right().getOrNull());
		log.debug(bigNumber.left().getOrNull());
	}

	/**
	 * Функциональные интерфейсы у нас есть, но можно расширить на много аргументов
	 */
	private static void functions() {
		Function3<Integer, Integer, String, String> stringModify = (a, b, c) -> Strings.repeat(c, b) + a;
		Function2<Integer, String, String> firstChars = (a, b) -> b.substring(a);

		log.debug(stringModify.apply(1, 3, "la"));

		// Лифтинг - если функция не на всех аргументах работает - она может вернуть Optional
		Function2<Integer, String, Option<String>> f = Function2.lift(firstChars);
		// Тут все ок
		log.debug(f.apply(1, "abcd"));
		// Тут NPE -> None
		log.debug(f.apply(1, null));

		// Частично определенная функции
		Function2<Integer, Integer, Integer> sum = (a, b) -> a + b;
		Function1<Integer, Integer> add2 = sum.apply(2);

		log.debug(add2.apply(3));

		// Вот так после вычисления функции результат кэшируется
		// Если аргументы есть - то с одинаковыми аргументами офк
		// Суперудобно
		Function1<Integer, Integer> add10 = Function1.of((Integer integer) -> {
			log.info("Applying this to " + integer);
			return integer + 10;
		}).memoized();

		log.debug(add10.apply(1));
		log.debug(add10.apply(2));
		// Вот тут log.info не будет
		log.debug(add10.apply(1));


	}

	/**
	 * Tuple - удобные структуры данных для возврата нескольких значений, например
	 */
	private static void tuples() {
		Tuple2<Long, String> tuple = Tuple.of(1L, "One");
		log.debug(tuple);

		// Поля в tuple являются final, поэтому для модификации:
		Tuple2<Long, String> tuple2 = tuple.map(item -> item, item -> "Two");
		log.debug(tuple2);
		// или
		tuple2 = tuple.map((num, string) -> Tuple.of(num, string + "123"));
		log.debug(tuple2);

	}

	/**
	 * Особенные функциональные коллекции
	 */
	private static void arraysJava() {
		// Типикал использование
		log.debug(List.of("a", "b").tail().prepend("c").append("d").asJava());
		// Всякий интересный функционал
		log.debug(List.of("a", "b", "c", "d", "e").combinations(3));
		log.debug(List.of("a", "b", "ca", "lala").partition(item -> item.length() > 1));

		log.debug(List.of(1, 2, 3, 4).groupBy(i -> i % 2));
	}


}
