package in.souravan.ruleengine;

import java.util.HashMap;
import java.util.Map;

import in.souravan.ruleengine.expression.Expression;
import in.souravan.ruleengine.expression.OperationRegistor;
import in.souravan.ruleengine.expression.action.ActionDispatcher;
import in.souravan.ruleengine.expression.action.ActionRegistor;
import in.souravan.ruleengine.expression.operations.And;
import in.souravan.ruleengine.expression.operations.Equals;
import in.souravan.ruleengine.expression.operations.GreaterThan;
import in.souravan.ruleengine.expression.operations.LessThan;
import in.souravan.ruleengine.expression.operations.Not;
import in.souravan.ruleengine.expression.template.DispatcherTemplate;
import in.souravan.ruleengine.parser.ExpressionParser;
import in.souravan.ruleengine.rule.Rule;
import in.souravan.ruleengine.rule.RuleSet;

/**
 * Given
 * dynamic data plotgraph points
 * When
 * Expression- ExpressionParser.fromString, 
 * Then
 * Acion - AcionDispatcher
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	 // create a singleton container for operations
        OperationRegistor operations = OperationRegistor.INSTANCE;

        // register new operations with the previously created container
        operations.register(new And());
        operations.register(new Equals());
        operations.register(new Not());
        operations.register(new GreaterThan());
        operations.register(new LessThan());
        
        ActionRegistor actions = ActionRegistor.INSTANCE;
        
        actions.register(new DispatcherTemplate("In Patient") {
			
			@Override
			public void fire() {
				System.out.println("Send patient to IN");				
			}
		});
        actions.register(new DispatcherTemplate("Out Patient") {
			
			@Override
			public void fire() {
				System.out.println("Send patient to OUT");				
			}
		});

        // defines the triggers when a rule should fire
        Expression ex3 = ExpressionParser.fromString("PATIENT_TYPE = 'A' AND ADMISSION_TYPE = 'I' AND NOT ADMISSION_TYPE = 'O'");
        Expression ex1 = ExpressionParser.fromString("PATIENT_TYPE = 'A' AND ADMISSION_TYPE = 'O'");
//        Expression ex2 = ExpressionParser.fromString("PATIENT_TYPE = 'B'");
        
        Expression ex2 = ExpressionParser.fromString("RSI1 < RSI2");

        // define the possible actions for rules that fire
//        DispatcherTemplate inPatient = () -> System.out.println("Send patient to IN");
//        DispatcherTemplate outPatient = () -> System.out.println("Send patient to OUT");

        // create the rules and link them to the accoridng expression and action
        Rule rule1 = new Rule.Builder()
                            .withExpression(ex2)
                            .withDispatcher(ActionRegistor.INSTANCE.getOperation("In Patient"))
                            .build();

        Rule rule2 = new Rule.Builder()
                            .withExpression(ex1)
//                            .withExpression(ex3)
                            .withDispatcher(ActionRegistor.INSTANCE.getOperation("Out Patient"))
                            .build();

        // add all rules to a single container
        RuleSet rules = new RuleSet();
//        rules.add(rule1);
        rules.add(rule2);

        // for test purpose define a variable binding ...
        Map<String, Object> bindings = new HashMap<>();
        bindings.put("PATIENT_TYPE", "'A'");
        bindings.put("ADMISSION_TYPE", "'O'");
        bindings.put("RSI1", 10);
        bindings.put("RSI2", 20);
        // ... and evaluate the defined rules with the specified bindings
        boolean triggered = rules.eval(bindings);
        System.out.println("Action triggered: "+triggered);
    }
}
