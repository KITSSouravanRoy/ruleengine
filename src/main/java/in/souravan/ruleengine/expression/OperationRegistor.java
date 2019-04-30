package in.souravan.ruleengine.expression;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import in.souravan.ruleengine.expression.template.OperationTemplate;

public enum OperationRegistor
{
    /** Application of the Singleton pattern using enum **/
    INSTANCE;

    private final Map<String, OperationTemplate> operations = new HashMap<>();

    public void register(OperationTemplate op, String symbol)
    {
        if (!operations.containsKey(symbol))
            operations.put(symbol, op);
    }

    public void register(OperationTemplate op)
    {
        if (!operations.containsKey(op.getSymbol()))
            operations.put(op.getSymbol(), op);
    }

    public OperationTemplate getOperation(String symbol)
    {
        return this.operations.get(symbol);
    }

    public Set<String> getDefinedSymbols()
    {
        return this.operations.keySet();
    }
}