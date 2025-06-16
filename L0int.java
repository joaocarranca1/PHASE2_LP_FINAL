public class L0int {

    public static void main(String args[]) {
        Parser parser = new Parser(System.in);
        ASTNode exp;

        System.out.println("L0 interpreter PL MEIC 2024/25 (v0.0)\n");

        Environment<ASTType> typeEnv = new Environment<>();
        Environment<IValue> valueEnv = new Environment<>();

        while (true) {
            try {
                System.out.print("# ");
                exp = parser.Start();
                if (exp == null) System.exit(0);

              
                
                // Use the SAME environments across all iterations
                exp.typeCheck(typeEnv);     
                
               
                
                IValue v = exp.eval(valueEnv);  // Accumulates value bindings
                System.out.println(v.toStr());
            } catch (TypeCheckError e) {
                System.out.println("Type Error: " + e.getMessage());
                parser.ReInit(System.in);
            } catch (ParseException e) {
                System.out.println("Syntax Error.");
                parser.ReInit(System.in);
            } catch (Exception e) {
                e.printStackTrace();
                parser.ReInit(System.in);
            }
        }
    }
}
