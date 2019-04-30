package in.souravan.ruleengine.expression.operations;
import java.util.Map;
import java.util.Stack;

import in.souravan.ruleengine.expression.Expression;
import in.souravan.ruleengine.expression.template.OperationTemplate;

public class OR extends OperationTemplate
{    
    public OR()
    {
        super("OR");
    }

    public OR copy()
    {
        return new OR();
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
        return leftOperand.interpret(bindings) || rightOperand.interpret(bindings);
    }
}