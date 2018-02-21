import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TCustomHashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TCustomHashSet;
import gnu.trove.set.hash.TLongHashSet;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import trove.SomeModel;
import trove.SomeModelHashingStrategy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

/**
 * Пример работы с небольшой но замечательной библиотекой - trove4j.
 * Решает 2 задачи:
 * - HashSet и HashMap для объектов с использованием не их hashCode/equals, а кастомного.
 * - Коллекции над примитивными типами (снижают потребление памяти )
 */
@Log4j2
public class Trove4jFeatures {

	public static void main(String[] args) {
		featureCustomStrategy();
		log.log(Level.getLevel("SEPAR"), "---");
		featurePrimitiveCollections();
	}

	/**
	 * Пример работы с кастомными хэшами
	 */
	private static void featureCustomStrategy() {
		// Пример работы HashSet с кастомными hashCode/equals
		TCustomHashSet<SomeModel> hashTSet = new TCustomHashSet<>(new SomeModelHashingStrategy());
		// Обычный hashSet для примера
		HashSet<SomeModel> hashSet = new HashSet<>();

		// Just an example, should never have equal business keys for different models
		SomeModel model1 = new SomeModel(1L, "Name", LocalDateTime.now(), null);
		SomeModel model2 = new SomeModel(2L, "Name", LocalDateTime.now(), null);
		SomeModel model3 = new SomeModel(1L, "Name2", LocalDateTime.now(), null);
		SomeModel model4 = new SomeModel(1L, "Name", LocalDateTime.now(), null);

		hashTSet.add(model1);
		hashTSet.add(model2);
		hashTSet.add(model3);
		hashTSet.add(model4);

		// model4 is customEquals model1
		log.debug(hashTSet.size());
		// Можно использовать как обычные коллекции
		log.debug(hashTSet instanceof Collection);

		hashSet.add(model1);
		hashSet.add(model2);
		hashSet.add(model3);
		hashSet.add(model4);

		log.debug(hashSet.size());
	}

	/**
	 * Пример использования примитивных коллекций.
	 */
	private static void featurePrimitiveCollections() {
		TLongSet set = new TLongHashSet();
		set.add(1L);
		set.add(2L);
		set.add(3L);
		log.debug(set);

		TIntIntMap map = new TIntIntHashMap();

		map.put(2, 1);
		map.put(9, 1);
		map.put(12, 2);
		map.put(23, 1);
		map.put(30, 3);

		log.debug(map);

		TIntObjectHashMap<SomeModel> objectMap = new TIntObjectHashMap<>();

		SomeModel model1 = new SomeModel(1L, "Name", LocalDateTime.now(), null);
		SomeModel model2 = new SomeModel(2L, "Name", LocalDateTime.now(), null);
		SomeModel model3 = new SomeModel(1L, "Name2", LocalDateTime.now(), null);
		SomeModel model4 = new SomeModel(1L, "Name", LocalDateTime.now(), null);

		objectMap.put(1, model1);
		objectMap.put(2, model2);
		objectMap.put(3, model3);
		objectMap.put(4, model4);

		log.debug(objectMap);
	}
}
