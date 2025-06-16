import java.util.List;
import java.util.ArrayList;

public class VClosure implements IValue {
    private final Environment<IValue> env;
    private final List<String> params;
    private final ASTNode body;
    private final List<ASTType> paramTypes;
    private final int argsBound;

    public VClosure(Environment<IValue> env, List<String> params, ASTNode body, List<ASTType> paramTypes) {
        this(env, params, body, paramTypes, 0);
    }

    public VClosure(Environment<IValue> env, List<String> params, ASTNode body, List<ASTType> paramTypes, int argsBound) {
        if (params == null || params.isEmpty()) {
            throw new IllegalArgumentException("Function must have at least one parameter");
        }
        if (params.size() != paramTypes.size()) {
            throw new IllegalArgumentException("Number of parameters must match number of types");
        }
        if (argsBound < 0 || argsBound > params.size()) {
            throw new IllegalArgumentException("Invalid number of bound arguments");
        }

        this.env = env;
        this.params = new ArrayList<>(params);      
        this.body = body;
        this.paramTypes = new ArrayList<>(paramTypes); 
        this.argsBound = argsBound;
    }

    public IValue apply(List<IValue> args) throws InterpreterError {
        if (args == null || args.isEmpty()) {
            throw new InterpreterError("Cannot apply with empty arguments");
        }
        if (args.size() + argsBound > params.size()) {
            throw new InterpreterError(String.format(
                "Too many arguments: expected at most %d, got %d",
                params.size() - argsBound, args.size()));
        }

        Environment<IValue> newEnv = env.beginScope();
        
       
        for (int i = 0; i < args.size(); i++) {
            if (args.get(i) == null) {
                throw new InterpreterError("Cannot bind null argument at position " + i);
            }
            newEnv.assoc(params.get(argsBound + i), args.get(i));
        }

        
        if (args.size() + argsBound == params.size()) {
            return body.eval(newEnv);
        }

        return new VClosure(
            newEnv,
            params,
            body,
            paramTypes,
            argsBound + args.size()
        );
    }

    public Environment<IValue> getEnv() { return env; }
    public List<String> getParams() { return new ArrayList<>(params); } // Return copy
    public ASTNode getBody() { return body; }
    public List<ASTType> getParamTypes() { return new ArrayList<>(paramTypes); } // Return copy
    public int getArgsBound() { return argsBound; }

    @Override
    public String toStr() {
        StringBuilder sb = new StringBuilder();
        sb.append("<closure: ");
        if (argsBound == params.size()) {
            sb.append("fully applied");
        } else {
            sb.append(argsBound).append("/").append(params.size())
              .append(" args, waiting for: ");
            for (int i = argsBound; i < params.size(); i++) {
                sb.append(paramTypes.get(i).toStr());
                if (i < params.size() - 1) sb.append(", ");
            }
        }
        sb.append(">");
        return sb.toString();
    }
}
