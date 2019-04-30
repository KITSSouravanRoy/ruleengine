package in.souravan.ruleengine.parser;
import java.util.Stack;

import in.souravan.ruleengine.expression.Expression;
import in.souravan.ruleengine.expression.OperationRegistor;
import in.souravan.ruleengine.expression.template.OperationTemplate;

public class ExpressionParser
{
    private static final OperationRegistor operations = OperationRegistor.INSTANCE;

    public static Expression fromString(String expr)
    {
        Stack<Expression> stack = new Stack<>();

        String[] tokens = expr.split("\\s");
        for (int i=0; i < tokens.length-1; i++)
        {
            OperationTemplate op = operations.getOperation(tokens[i]);
            if ( op != null )
            {
                // create a new instance
                op = op.copy();
                i = op.parse(tokens, i, stack);
            }
        }

        return stack.pop();
    }
}