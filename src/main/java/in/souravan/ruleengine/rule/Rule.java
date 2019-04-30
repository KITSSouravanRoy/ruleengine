package in.souravan.ruleengine.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import in.souravan.ruleengine.expression.Expression;
import in.souravan.ruleengine.expression.action.ActionDispatcher;

public class Rule {
	private List<Expression> expressions;
	private ActionDispatcher dispatcher;

	public static class Builder {
		private List<Expression> expressions = new ArrayList<>();
		private ActionDispatcher dispatcher = null;

		public Builder withExpression(Expression expr) {
			expressions.add(expr);
			return this;
		}

		public Builder withDispatcher(ActionDispatcher dispatcher) {
			this.dispatcher = dispatcher;
			return this;
		}

		public Rule build() {
			return new Rule(expressions, dispatcher);
		}
	}

	private Rule(List<Expression> expressions, ActionDispatcher dispatcher) {
		this.expressions = expressions;
		this.dispatcher = dispatcher;
	}

	public boolean eval(Map<String, ?> bindings) {
		boolean eval = false;
		for (Expression expression : expressions) {
			eval = expression.interpret(bindings);
			if (eval)
				dispatcher.fire();
		}
		return eval;
	}

	public List<Expression> getExpressions() {
		return expressions;
	}

	public ActionDispatcher getDispatcher() {
		return dispatcher;
	}

	@Override
	public String toString() {
		return "Rule [expressions=" + expressions + ", dispatcher=" + dispatcher + "]";
	}

}