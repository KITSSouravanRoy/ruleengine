package in.souravan.ruleengine.expression.operations;

import in.souravan.ruleengine.expression.template.ConditionalOperationTemplate;

public class Equals extends ConditionalOperationTemplate {
	public Equals() {
		super("=");
	}

	@Override
	public Equals copy() {
		return new Equals();
	}

	public boolean compareValue(Object objl, Object objr) {
		if (objl.getClass().equals(objr.getClass())) {
			if (((Comparable) objl).compareTo(objr) == 0)
				return true;
		}

		return false;
	}

}