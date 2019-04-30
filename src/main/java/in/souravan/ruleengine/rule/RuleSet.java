package in.souravan.ruleengine.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import in.souravan.ruleengine.expression.Expression;
import in.souravan.ruleengine.expression.Variable;
import in.souravan.ruleengine.expression.template.ConditionalOperationTemplate;
import in.souravan.ruleengine.expression.template.OperationTemplate;

public class RuleSet {

	private List<Rule> rules = new ArrayList<Rule>();
	private List<Variable> allUniqueVariables = new ArrayList<Variable>();

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	public void add(Rule rule) {
		this.rules.add(rule);
	}

	public boolean eval(Map<String, ?> bindings) {
		return rules.stream().map(x -> x.eval(bindings)).reduce(Boolean.FALSE, (a, b) -> a || b);
	}

	public List<Variable> getAllUniqueVariables() {

//		List<Variable> allVars = this.rules.stream().flatMap(x -> x.getExpressions().stream())
//				.filter(x -> x instanceof ConditionalOperationTemplate).map(x -> (ConditionalOperationTemplate) x)
//				.reduce(new ArrayList<Variable>(), this::addVariable, (x, y) -> {
//					x.addAll(y);
//					return x;
//				});
//
//		return allVars.stream().distinct().collect(Collectors.toList());

		List<Expression> allExpressions = this.rules.stream().flatMap(x -> x.getExpressions().stream())
				.collect(Collectors.toList());
		
		addVariable(allExpressions);
		
		return allUniqueVariables;

	}

	private void addVariable(final List<Expression> allExpressions) {

		for (Expression ops : allExpressions) {

			// If only instance of operational template but not conditional operation
			// that means ops is a Joining expression, like , AND/OR/NOT
			if (ops instanceof OperationTemplate) {

				if (ops instanceof ConditionalOperationTemplate) {
					if (((ConditionalOperationTemplate)ops).getLeftOperand() instanceof Variable) {
						allUniqueVariables.add((Variable) ((ConditionalOperationTemplate)ops).getLeftOperand());
					}
					if (((ConditionalOperationTemplate)ops).getRightOperand() instanceof Variable) {
						allUniqueVariables.add((Variable) ((ConditionalOperationTemplate)ops).getRightOperand());
					}
				}else {
					List<Expression> tempExpressions = new ArrayList<Expression>();
					tempExpressions.add(((OperationTemplate)ops).getLeftOperand());
					tempExpressions.add(((OperationTemplate)ops).getRightOperand());
					addVariable(tempExpressions);
				}
			}
		}
	}

	@Override
	public String toString() {
		return "RuleSet [rules=" + rules + "]";
	}
}
