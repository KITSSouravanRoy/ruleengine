package in.souravan.ruleengine.expression;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.math.NumberUtils;

public class Value<T> implements Expression {
	public T value;
	public Class<T> type;

	public Value(T value, Class<T> type) {
		this.value = value;
		this.type = type;
	}

	public T getValue() {
		return this.value;
	}

	public Class<T> getType() {
		return this.type;
	}

	@Override
	public boolean interpret(Map<String, ?> bindings) {
		return true;
	}

	public static Expression getBaseType(final String string) {
		if (string == null)
			throw new IllegalArgumentException("The provided string must not be null");

		Optional<Value<?>> value = evaluateValue(string);

		return value.isPresent() ? value.get() : new Variable(string);
	}

	private static Optional<Value<?>> evaluateValue(final String string) {

		Value<?> value = null;
		if ("true".equals(string) || "false".equals(string))
			value = new Value<>(Boolean.getBoolean(string), Boolean.class);
		else if (string.startsWith("'"))
			value = new Value<>(string, String.class);
		else if (NumberUtils.isCreatable(string) && string.contains("."))
			value = new Value<>(NumberUtils.toDouble(string), Double.class);
		else if(NumberUtils.isCreatable(string))
			value = new Value<>(NumberUtils.toInt(string), Integer.class);

		return Optional.ofNullable(value);
	}

	@Override
	public String toString() {
		return "Value [value=" + value + ", type=" + type + "]";
	}
}