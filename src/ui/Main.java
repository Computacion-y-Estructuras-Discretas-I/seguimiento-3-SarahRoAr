package ui;

import java.util.Scanner;
import structures.PilaGenerica;
import structures.TablasHash;

public class Main {

    private Scanner sc;

    public Main() {
        sc = new Scanner(System.in);
    }

    public void ejecutar() throws Exception {
        while (true) {
            System.out.println("\nSeleccione la opcion:");
            System.out.println("1. Punto 1, Verificar balanceo de expresión");
            System.out.println("2. Punto 2, Encontrar pares con suma objetivo");
            System.out.println("3. Salir del programa");
            System.out.print("Opcion: ");

            int opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese expresion a verificar:");
                    String expresion = sc.nextLine();
                    boolean resultado = verificarBalanceo(expresion);
                    System.out.println("Resultado: " + (resultado ? "TRUE" : "FALSE"));
                    break;

                case 2:
                    System.out.println("Ingrese numeros separados por espacio: ");
                    String lineaNumeros = sc.nextLine();
                    System.out.println("Ingrese suma objetivo: ");
                    int objetivo = Integer.parseInt(sc.nextLine());

                    String[] partes = lineaNumeros.trim().split("\\s+");
                    int[] numeros = new int[partes.length];
                    for (int i = 0; i < partes.length; i++) {
                        numeros[i] = Integer.parseInt(partes[i]);
                    }

                    encontrarParesConSuma(numeros, objetivo); 
                    break;

                case 3:
                    System.out.println("Chaito");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opcion no permitida");
            }
        }
    }

    /**
     * Verifica si la expresion esta balanceada usando PilaGenerica
     * @param s expresion a verificar
     * @return true si esta balanceada, false si no
     */
    public boolean verificarBalanceo(String s) {
        PilaGenerica<Character> pila = new PilaGenerica<>(s.length());

        if (pila.getSize() == 0){
            return true;
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(' || c == '{' || c == '[') {
                pila.Push(c);
                if (pila.getTop() == 0) {
                    return false;
                }
                
            } else if (c == ')' || c == '}' || c == ']') {
                if (pila.getTop() == 0) {
                    return false;
                }
                char topChar;
                topChar = pila.Pop();
                if ((c == ')' && topChar != '(') ||
                    (c == '}' && topChar != '{') ||
                    (c == ']' && topChar != '[')) {
                    return false;
                }
                
            }
        }
        return pila.getTop() == 0;
    }

    /**
     * Encuentra y muestra todos los pares unicos de numeros que sumen objetivo usando TablasHash.
     * @param numeros arreglo de numeros enteros
     * @param objetivo suma objetivo
     */
    public void encontrarParesConSuma(int[] numeros, int objetivo) {

        try {
            int n = numeros.length;
            TablasHash tabla = new TablasHash(n);

            for (int x : numeros){
                tabla.insert(Math.abs(x), x);
            }


            for (int x : numeros){
                int complemento = objetivo - x;
                if (x != complemento){
                    if (tabla.search(Math.abs(complemento), complemento)) {
                        System.out.println("(" + x + ", " + complemento + ")");
                        tabla.delete(Math.abs(x), x);
                        tabla.delete(Math.abs(complemento), complemento);
                        if (tabla.search(Math.abs(x), x) || tabla.search(Math.abs(complemento), complemento)) {
                            tabla.delete(Math.abs(x), x);
                            tabla.delete(Math.abs(complemento), complemento);
                        }
                    }
                }else{
                    System.out.println("Ningún par encontrado");
                }

            }
        } catch (Exception e) {
            System.out.println("Error con TablasHash: " + e.getMessage());
            e.printStackTrace();
        }
        
    }

    public static void main(String[] args) throws Exception {
        Main app = new Main();
        app.ejecutar();
    }
}
