package in.souravan.ruleengine.expression.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import in.souravan.ruleengine.expression.template.DispatcherTemplate;

public enum ActionRegistor {
	/** Application of the Singleton pattern using enum **/
	INSTANCE;

	private final Map<String, DispatcherTemplate> actions = new HashMap<>();

	public void register(DispatcherTemplate op) {
		if (!actions.containsKey(op.getActionName()))
			actions.put(op.getActionName(), op);
	}

	public DispatcherTemplate getOperation(String symbol) {
		return this.actions.get(symbol);
	}

	public Set<String> getDefinedSymbols() {
		return this.actions.keySet();
	}
}