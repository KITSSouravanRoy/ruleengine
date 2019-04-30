package in.souravan.ruleengine.expression.operations;

import in.souravan.ruleengine.expression.template.ConditionalOperationTemplate;

public class GreaterThan extends ConditionalOperationTemplate {
	public GreaterThan() {
		super(">");
	}

	@Override
	public GreaterThan copy() {
		return new GreaterThan();
	}

	public boolean compareValue(Object objl, Object objr) {
		if (objl.getClass().equals(objr.getClass())) {
			if (((Comparable) objl).compareTo(objr) > 0)
				return true;
		}

		return false;
	}

}