package in.souravan.ruleengine.expression;
import java.util.Map;

public class Variable implements Expression
{
    private String name;

    public Variable(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    @Override
    public boolean interpret(Map<String, ?> bindings)
    {
        return true;
    }

	@Override
	public String toString() {
		return "Variable [name=" + name + "]";
	}
}