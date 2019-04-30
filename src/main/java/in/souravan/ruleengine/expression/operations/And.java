package in.souravan.ruleengine.expression.operations;
import java.util.Map;
import java.util.Stack;

import in.souravan.ruleengine.expression.Expression;
import in.souravan.ruleengine.expression.template.OperationTemplate;

public class And extends OperationTemplate
{    
    public And()
    {
        super("AND");
    }

    public And copy()
    {
        return new And();
    }

    @Override
    public int parse(String[] tokens, int pos, Stack<Expression> stack)
    {
        Expression left = stack.pop();
        int i = findNextExpression(tokens, pos+1, stack);
        Expression right = stack.pop();

        this.leftOperand = left;
        this.rightOperand = right;

        stack.push(this);

        return i;
    }

    @Override
    public boolean interpret(Map<String, ?> bindings)
    {
        return leftOperand.interpret(bindings) && rightOperand.interpret(bindings);
    }

}