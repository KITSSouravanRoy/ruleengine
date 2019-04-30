package in.souravan.ruleengine.expression.template;

import java.util.Stack;

import in.souravan.ruleengine.expression.Expression;
import in.souravan.ruleengine.expression.OperationRegistor;

public abstract class OperationTemplate implements Expression {
	protected String symbol;

	protected Expression leftOperand = null;
	protected Expression rightOperand = null;

	public OperationTemplate(String symbol) {
		this.symbol = symbol;
	}

	public abstract OperationTemplate copy();

	public String getSymbol() {
		return this.symbol;
	}

	public abstract int parse(final String[] tokens, final int pos, final Stack<Expression> stack);

	protected Integer findNextExpression(String[] tokens, int pos, Stack<Expression> stack) {
		OperationRegistor operations = OperationRegistor.INSTANCE;

		for (int i = pos; i < tokens.length; i++) {
			OperationTemplate op = operations.getOperation(tokens[i]);
			if (op != null) {
				op = op.copy();
				// we found an operation
				i = op.parse(tokens, i, stack);

				return i;
			}
		}
		return null;
	}

	public Expression getLeftOperand() {
		return leftOperand;
	}

	public Expression getRightOperand() {
		return rightOperand;
	}

	@Override
	public String toString() {
		return "OperationTemplate [symbol=" + symbol + ", leftOperand=" + leftOperand + ", rightOperand=" + rightOperand
				+ "]";
	}

}