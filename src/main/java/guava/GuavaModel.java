package guava;

import lombok.*;
import org.jetbrains.annotations.NotNull;

/**
 * Модель для Guava, в которой hashCode/equals по ссылке
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GuavaModel {

	@Getter
	@Setter
	private @NotNull Long id;

	@Getter
	@Setter
	private @NotNull String name;

	@Getter
	@Setter
	private @NotNull String somethingMore;
}
