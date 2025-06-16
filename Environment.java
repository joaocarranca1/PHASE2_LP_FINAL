import java.util.*;

public class Environment<E> {
    private final Environment<E> anc;
    private final Map<String, E> bindings;

    
    Environment() {
        anc = null;
        bindings = new HashMap<>();
    }

    
    Environment(Environment<E> ancestor) {
        this.anc = ancestor;
        this.bindings = new HashMap<>();
    }

    
    public Environment<E> getParent() {
        return anc;
    }

    
    Environment<E> beginScope() {
        return new Environment<>(this);
    }

   
    Environment<E> endScope() {
        return anc;
    }

   
    void preDeclareBind(String id, E bind) {
        bindings.put(id, bind);
    }

    
    void assoc(String id, E bind) throws InterpreterError {
        
       
        bindings.put(id, bind);
    }

    
    void update(String id, E bind) throws InterpreterError {
        if (bindings.containsKey(id)) {
            bindings.put(id, bind);
            return;
        }
        if (anc != null) {
            anc.update(id, bind);
            return;
        }
        throw new InterpreterError("Variable " + id + " not found for update");
    }

    E find(String id) throws InterpreterError {
        if (bindings.containsKey(id)) {
            return bindings.get(id);
        }
        if (anc != null) {
            return anc.find(id);
        }
        throw new InterpreterError("Variable " + id + " not found in any scope");
    }

    Environment<E> extendTypes(Map<String, ASTType> newBindings) {
        Environment<E> extendedEnv = new Environment<>(this);
        for (Map.Entry<String, ASTType> entry : newBindings.entrySet()) {
            extendedEnv.bindings.put(entry.getKey(), (E) entry.getValue());
        }
        return extendedEnv;
    }

    
}
