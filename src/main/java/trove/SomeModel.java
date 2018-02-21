package trove;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

/**
 * Обычная запись, используемая для отображения, например, моделей в JPA.
 */
@NoArgsConstructor
@AllArgsConstructor
public class SomeModel {

	/**
	 * Идентификатор записи
	 */
	@Getter
	@Setter
	private @NotNull Long id;

	/**
	 * Наименование модели.
	 */
	@Getter
	@Setter
	private @NotNull String name;

	/**
	 * Дата создания модели.
	 */
	@Getter
	@Setter
	private @NotNull LocalDateTime creationDate;

	/**
	 * Пользователь, создавший запись.
	 */
	@Getter
	@Setter
	private @Nullable String createUser;


	/**
	 * Стандартный hashCode, который зависит только от Id, что логично
	 * @return
	 */
	@Override
	public int hashCode() {
		return id.intValue();
	}

	/**
	 * equals, основанный на равенстве id.
	 * @param obj объект для сравнения
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof SomeModel)) {
			return false;
		}

		return id.intValue() == ((SomeModel)obj).getId().intValue();
	}
}
