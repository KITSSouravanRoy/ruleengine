package in.souravan.ruleengine.expression;

import java.util.Map;

@FunctionalInterface
public interface Expression {
	public boolean interpret(final Map<String, ?> bindings);
}