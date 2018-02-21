import lombok.extern.log4j.Log4j2;
import log4j.model.Entity;
import log4j.model.NoDebugObject;
import org.apache.logging.log4j.Level;
import log4j.some.Outer;
import log4j.some.pack.Inner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Используется log4j2.
 * Смысла в использовании slf4j не вижу, так как смена логгера - дело кажущееся мне достаточно редким.
 * По крайней мере в бытовых условиях.
 * Кроме того функционал slf4j несколько уже функционала log4j2
 */
@Log4j2
public class LoggerFeatures {

	public static void main(String[] args) {
		// Тест кирилицы - должно правильно выводиться.
		log.error("Тест кирилицы");
		// Разноцветные логи
		log.warn("Warning");
		log.fatal("Very bad");
		log.info("Some information");
		log.debug("Debug");
		// Только UTF-8
		log.warn(System.getProperties().get("file.encoding"));

		// Так логируем данные, который должны быть сконкатенированы
		log.debug("Formatted {}", Long.reverse(25));
		// Или так
		log.debug("Bla blo: {}", () -> Stream.of("blah", "bla", "bleh").collect(Collectors.joining(", ")));

		// Вот тут заебись будет
		log.trace("one ", () -> {
			try {
				Thread.sleep(20000);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			return "two";
		});

		// Проверка инспекции IDEA - второй аругмент должен быть подсвечен, так как @NotNull
		Entity e = new Entity(null, null);
		// Автосгенерированный toString() ломбоком - очень вкусный
		log.debug(e);

		// Логирование для класса NoDebugObject выставлено на уровне warn
		NoDebugObject.logMe();
		// Кастомный левел, определенный в log4j.xml
		log.log(Level.getLevel("DATAB"), "DATABASE ACCESS");

		// Разное логирование в иерархии пакетов
		Outer.logMe();
		Inner.logMe();
	}
}
