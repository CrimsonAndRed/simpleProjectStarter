package trove;

import gnu.trove.strategy.HashingStrategy;

import java.util.Objects;

public class SomeModelHashingStrategy implements HashingStrategy<SomeModel> {

	@Override
	public int computeHashCode(SomeModel object) {
		return object.getId().hashCode() ^ object.getName().hashCode();
	}

	@Override
	public boolean equals(SomeModel o1, SomeModel o2) {
		return Objects.equals(o1.getId(), o2.getId()) && Objects.equals(o1.getName(), o2.getName());
	}
}
