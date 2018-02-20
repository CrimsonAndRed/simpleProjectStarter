import com.google.common.base.Throwables;
import com.google.common.collect.*;
import com.google.common.primitives.Longs;
import guava.GuavaModel;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Guava - одна из фундаментальных библиотек, упрощающая работу существующего функционала и вводящее ряд полезных фишек.
 * Состоит из:
 * - Мультиколлекции
 * - Preconditions (не нужен, так как теперь (с java 7) есть {@link java.util.Objects} вместо {@link com.google.common.base.Preconditions})
 * - Ordering - улучшенный компаратов, нужен только до Java 8, так как в 8д есть стримы и т.д.
 * - Throwables - сложный класс утилитных методов для работы с исключениями
 * - Прочие утилиты для коллекций
 * - Графы - не нужны в бытовухе.
 * - Кэши - нужны в бытовухе
 */
@Log4j2
public class GuavaFeatures {

	public static void main(String[] args) {
		featureMultimap();
		log.log(Level.getLevel("SEPAR"), "---");
		featureThrowables();
		log.log(Level.getLevel("SEPAR"), "---");
		featureCollections();
	}

	/**
	 * Мультимапы - очень хороший способ не попасться на null в коллекциях типа {@code Map<Long, List<Model>>}
	 */
	public static void featureMultimap() {
		Multimap<Long, GuavaModel> multimap = HashMultimap.create();

		GuavaModel model1 = new GuavaModel(1L, "Name", "Something");
		GuavaModel model2 = new GuavaModel(2L, "Name", "Something");
		GuavaModel model3 = new GuavaModel(1L, "ObjectName", "Something");
		GuavaModel model4 = new GuavaModel(1L, "BlahBlah", "Something");

		multimap.put(model1.getId(), model1);
		multimap.put(model2.getId(), model2);
		multimap.put(model3.getId(), model3);
		multimap.put(model4.getId(), model4);

		log.debug(multimap);

		Multiset<GuavaModel> multiset = TreeMultiset.create((item1, item2) -> item1.getName().compareTo(item2.getName()));

		multiset.add(model1);
		multiset.add(model2);
		multiset.add(model3);
		multiset.add(model4);

		log.debug(multiset);
	}

	/**
	 * Пример использования наиболее удобной фичи класса - получение стек-трейса ошибки.
	 * Как ни странно, штатными средствами просто это не сделать (только printStackTrace, который не сработает с логгером).
	 * Существует аналогичный метод в Apache Commons
	 */
	public static void featureThrowables() {
		try {
			int a = 0/0;
		} catch (ArithmeticException e) {
			log.error(Throwables.getStackTraceAsString(e));
		}
	}

	/**
	 * Некоторые другие коллекции, не включая Мультиколлекции, которые приведены ранее.
	 */
	public static void featureCollections() {
		//-- Immutables
		// Immutable коллекции - у них все поинтереснее с потреблением памяти, достаточно годно для константного пула, например

		// Здесь Arrays.asList не подойдет, так как он возврщает неизменяемый совсем список.
		List<String> list = Lists.newArrayList("one", "two", "three");
		List<String> ul =  Collections.unmodifiableList(list);
		ImmutableList<String> il = ImmutableList.copyOf(list);

		// Добавление элемента в оригинальный список
		list.add("four");
		// ImmutableList не изменился, а UnmodifiableList - изменился
		log.debug(list);
		log.debug(il);
		log.debug(ul);

		//-- BiMaps
		// BiMap позволяет делать Map<A, B> и одновременно Map<B, A>

		BiMap<Long, Long> biMap = HashBiMap.create();

		biMap.put(3L, 12L);
		biMap.put(4L, 22L);
		biMap.put(6L, 13L);

		log.debug(biMap.get(3L));
		log.debug(biMap.inverse().get(13L));

		//--ClassToInstanceMap
		// Пул для синглтонов
		ClassToInstanceMap<Object> map = MutableClassToInstanceMap.create();

		map.putInstance(Long.class, 0L);
		map.putInstance(String.class, "");
		map.putInstance(Integer.class, 0);

		log.debug(map.getInstance(Integer.class));

		//-- RangeSet
		// Для удобной обработки допустимых значений, например
		RangeSet<Long> rangeSet = TreeRangeSet.create();
		rangeSet.add(Range.closed(0L, 10L));
		rangeSet.add(Range.atMost(-50L));
		rangeSet.add(Range.open(20L, 30L));
		rangeSet.remove(Range.open(-100L, -70L));
		log.debug(rangeSet);

		//--Статические методы над коллекциями
		List<Long> listLongs1 = Longs.asList(0L, 1L, 22L, 323L);
		log.debug(Lists.partition(listLongs1, 2));

		Set<String> strings1 = Sets.newHashSet("one", "two", "three");
		Set<String> strings2 = Sets.newHashSet("three", "four", "five");

		log.debug(Sets.union(strings1, strings2));
		log.debug(Sets.intersection(strings1, strings2));
		log.debug(Sets.difference(strings1, strings2));

	}
}
