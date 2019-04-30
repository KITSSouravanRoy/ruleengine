package in.souravan.ruleengine.expression.operations;

import in.souravan.ruleengine.expression.template.ConditionalOperationTemplate;

public class LessThan extends ConditionalOperationTemplate {
	public LessThan() {
		super("<");
	}

	@Override
	public LessThan copy() {
		return new LessThan();
	}

	public boolean compareValue(Object objl, Object objr) {
		if (objl.getClass().equals(objr.getClass())) {
			if (((Comparable) objl).compareTo(objr) < 0)
				return true;
		}

		return false;
	}

}