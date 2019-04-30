package in.souravan.ruleengine.expression.template;

import in.souravan.ruleengine.expression.action.ActionDispatcher;

public abstract class DispatcherTemplate implements ActionDispatcher{
	
	private String actionName;

	public DispatcherTemplate(String actionName) {
		super();
		this.actionName = actionName;
	}

	public String getActionName() {
		return actionName;
	}
	

}
