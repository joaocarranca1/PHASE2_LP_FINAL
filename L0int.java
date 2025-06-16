import java.io.FileInputStream;

public class L0int {

    public static void main(String args[]) {
        Parser parser;
        ASTNode exp;
        Environment<ASTType> typeEnv = new Environment<>();
        Environment<IValue> valueEnv = new Environment<>();

        try {
            if (args.length == 0) {
                // Interactive mode
                parser = new Parser(System.in);
                System.out.println("X++ interpreter PL MEIC 2024/25 (v1.0)\n");
                
                while (true) {
                    try {
                        System.out.print("# ");
                        exp = parser.Start();
                        if (exp == null) System.exit(0);
                        
                        exp.typeCheck(typeEnv);
                        IValue v = exp.eval(valueEnv);
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
            } else {
                // File execution mode
                String filename = args[0];
                FileInputStream fileInput = new FileInputStream(filename);
                parser = new Parser(fileInput);
                
                try {
                    while (true) {
                        exp = parser.Start();
                        if (exp == null) break;
                        
                        exp.typeCheck(typeEnv);
                        IValue v = exp.eval(valueEnv);
                        System.out.println(v.toStr());
                    }
                } catch (TypeCheckError e) {
                    System.out.println("Type Error: " + e.getMessage());
                } catch (ParseException e) {
                    System.out.println("Syntax Error in file " + filename);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    fileInput.close();
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
