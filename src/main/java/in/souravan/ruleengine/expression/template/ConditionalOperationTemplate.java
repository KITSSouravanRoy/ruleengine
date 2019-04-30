package in.souravan.ruleengine.expression.template;
import java.util.Map;
import java.util.Stack;

import in.souravan.ruleengine.expression.Expression;
import in.souravan.ruleengine.expression.Value;
import in.souravan.ruleengine.expression.Variable;

public abstract class ConditionalOperationTemplate extends OperationTemplate
{      
    public ConditionalOperationTemplate(String symbol)
    {
        super(symbol);
    }

    @Override
    public int parse(final String[] tokens, int pos, Stack<Expression> stack)
    {
        if (pos-1 >= 0 && tokens.length >= pos+1)
        {
            String var = tokens[pos-1];

            this.leftOperand = new Variable(var);
            this.rightOperand = Value.getBaseType(tokens[pos+1]);
            stack.push(this);

            return pos+1;
        }
        throw new IllegalArgumentException("Cannot assign value to variable");
    }

    @Override
    public boolean interpret(Map<String, ?> bindings)
    {
        Variable vl = (Variable)this.leftOperand;
        Object objl = getValueFromBinding(bindings, vl);

        if(this.rightOperand instanceof Variable) {
        	Variable vr = (Variable)this.rightOperand;
            Object objr = getValueFromBinding(bindings, vr);
            return compareValue(objl, objr);
        }else {
        	Value<?> type = (Value<?>)this.rightOperand;
        	return compareValue(objl, type.getValue());
        }
        
    }
    
    protected abstract boolean compareValue(Object objl, Object objr);

	protected Object getValueFromBinding(Map<String, ?> bindings, Variable vl) {
		Object objl = bindings.get(vl.getName());
        if (objl == null)
            return false;
		return objl;
	}
}