
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
public class ASTUnionMatch implements ASTNode {
    private final ASTNode expr;
    private final List<String> labels;
    private final List<String> vars;
    private final List<ASTNode> bodies;

    public ASTUnionMatch(ASTNode expr, List<String> labels, List<String> vars, List<ASTNode> bodies) {
        if (expr == null || labels == null || vars == null || bodies == null) {
            throw new IllegalArgumentException("Arguments to ASTUnionMatch cannot be null");
        }
        if (labels.size() != vars.size() || vars.size() != bodies.size()) {
            throw new IllegalArgumentException("Labels, vars, and bodies lists must have the same size");
        }
        this.expr = expr;
        this.labels = new ArrayList<>(labels);
        this.vars = new ArrayList<>(vars);
        this.bodies = new ArrayList<>(bodies);
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue val = expr.eval(env);
        
        if (val == null) {
            throw new RuntimeException("Union match expression evaluated to null");
        }
        if (!(val instanceof VUnion)) {
            throw new RuntimeException("Union match expression must evaluate to a union, but got: " + val.getClass().getSimpleName());
        }

        VUnion unionVal = (VUnion) val;
        String unionLabel = unionVal.getLabel();
        
        for (int i = 0; i < labels.size(); i++) {
            if (labels.get(i).equals(unionLabel)) {
                Environment<IValue> newEnv = new Environment<>(env);
                newEnv.assoc(vars.get(i), unionVal.getValue());
                return bodies.get(i).eval(newEnv);
            }
        }
        
        throw new RuntimeException("No matching pattern for union label: " + unionLabel);
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        try {
            ASTType exprType = resolveType(expr.typeCheck(env), env);
            if (!(exprType instanceof ASTTUnion)) {
                throw new TypeCheckError("Union match expression must evaluate to a union, but got: " + exprType.toStr());
            }

            ASTTUnion unionType = (ASTTUnion) exprType;
            HashMap<String, ASTType> variants = unionType.getVariants();
            
           
            for (String label : labels) {
                if (!variants.containsKey(label)) {
                    throw new TypeCheckError("Unknown union variant: #" + label);
                }
            }
            
            ASTType resultType = null;
            
           
            for (int i = 0; i < labels.size(); i++) {
                String label = labels.get(i);
                String var = vars.get(i);
                ASTNode body = bodies.get(i);
                
                ASTType variantType = resolveType(variants.get(label), env);
                
                Environment<ASTType> patternEnv = env.beginScope();
                patternEnv.assoc(var, variantType);
                ASTType bodyType = resolveType(body.typeCheck(patternEnv), env);
                
                if (resultType == null) {
                    resultType = bodyType;
                } else if (!resultType.equals(bodyType)) {
                    throw new TypeCheckError("Type mismatch between union match cases: " +
                                           resultType.toStr() + " vs " + bodyType.toStr());
                }
            }
            
            return resultType;
        } catch (InterpreterError e) {
            throw new TypeCheckError("Error while resolving types in union match expression: " + e.getMessage());
        }
    }

    private ASTType resolveType(ASTType t, Environment<ASTType> env) throws TypeCheckError {
        if (t instanceof ASTTId) {
            String alias = ((ASTTId) t).getId();
            try {
                ASTType resolved = env.find(alias);
                return resolveType(resolved, env);
            } catch (InterpreterError e) {
                throw new TypeCheckError("Unknown type alias: " + alias);
            }
        } else if (t instanceof ASTTRef) {
            ASTTRef refType = (ASTTRef) t;
            ASTType resolvedInner = resolveType(refType.getType(), env);
            return new ASTTRef(resolvedInner);
        } else if (t instanceof ASTTArrow) {
            ASTTArrow arr = (ASTTArrow) t;
            return new ASTTArrow(
                resolveType(arr.getParamType(), env),
                resolveType(arr.getReturnType(), env)
            );
        } else if (t instanceof ASTTList) {
            ASTTList listType = (ASTTList) t;
            ASTType resolvedElement = resolveType(listType.getElementType(), env);
            return new ASTTList(resolvedElement);
        } else if (t instanceof ASTTUnion) {
            return t;
        } else {
            return t;
        }
    }

    @Override
    public String toStr() {
        StringBuilder sb = new StringBuilder("match " + expr.toStr() + " { ");
        for (int i = 0; i < labels.size(); i++) {
            if (i > 0) sb.append(" | ");
            sb.append("#").append(labels.get(i)).append("(").append(vars.get(i)).append(") -> ").append(bodies.get(i).toStr());
        }
        sb.append(" }");
        return sb.toString();
    }
}