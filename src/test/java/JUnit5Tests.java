import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;

import java.time.Duration;

/**
 * 5е JUnit тесты.
 *  <a href="http://junit.org/junit5/docs/current/user-guide/"> Документация </a>
 *  Основные виды тестов приведены ниже.
 */
@Log4j2
public class JUnit5Tests {

	/**
	 * Стартует до всех тестов.
	 */
	@BeforeAll
	public static void veryFirst() {
		log.warn("Very first log4j2");
	}

	/**
	 * Стартует после всех тестов.
	 */
	@AfterAll
	public static void veryLast() {
		System.out.println("Very Last");
	}

	/**
	 * Стартует до каждого теста.
	 */
	@BeforeEach
	public void beforeEach() {
		System.out.println("Starting test");
	}

	/**
	 * Стартует после каждого теста.
	 */
	@AfterEach
	public void afterEach() {
		System.out.println("Ending test");
	}

	/**
	 * Простейший случай
	 */
	@Test
	public void simpleTest() {
		final int expected = 2;
		Assertions.assertEquals(expected,1+1);
	}

	/**
	 * Не будет выполнен.
	 */
	@Test
	@Disabled
	public void disabledTest() {
		final int expected = 3;
		Assertions.assertEquals(expected,1+1);
	}

	/**
	 * Assumptions происходят вместе с тестами.
	 * В случае непрохождения assumptions не будет ошибки при сборке тестов.
	 * Но упавшие assumptions будут видны в репорте.
	 * Таким образом, если все assertions прошли, но не все assumptions, будет считаться, что тесты были пройдены успешно.
	 */
	@Test
	@Disabled
	public void assumeFailTest() {
		Assumptions.assumeTrue(false, "Сообщение для вывода");
	}

	/**
	 * В этом случае ничего не произойдет.
	 */
	@Test
	public void assumeCorrectTest() {
		Assumptions.assumeTrue(true, "Сообщение для вывода");
	}

	/**
	 * Тест, выкидывющий исключения
	 */
	@Test
	public void exceptionTest() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			throw new RuntimeException("Исключение");
		});
	}

	/**
	 * Тест, выкидывющий не то исключение
	 */
	@Test
	@Disabled
	public void exceptionFailTest() {
		Assertions.assertThrows(IllegalStateException.class, () -> {
			int a = 0 / 0;
			System.out.println(a);
		}, "Не та ошибка");
	}

	/**
	 * Тест длительности выполнения кода.
	 * Упадет тогда, когда завершится выполнение метода
	 */
	@Test
	@Disabled
	public void timeoutTest() {
		Assertions.assertTimeout(Duration.ofSeconds(2), () -> Thread.sleep(3000));
	}

	/**
	 * Тест длительности выполнения кода.
	 * Упадет по таймеру, а не когда завершится выполнение.
	 */
	@Test
	@Disabled
	public void timeoutPreemptiveTest() {
		Assertions.assertTimeoutPreemptively(Duration.ofSeconds(2), () -> Thread.sleep(30_000));
	}

	/**
	 * Использование репортера - дополнительная информация при выполнении тестов
	 */
	@Test
	public void simpleReporterTest(TestReporter testReporter) {
		final int expected = 2;
		Assertions.assertEquals(expected,1+1);
		testReporter.publishEntry("string", "value");
	}
}
