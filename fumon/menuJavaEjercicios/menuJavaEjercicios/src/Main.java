import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner sn = new Scanner(System.in); //permite introducir informacion al programa.
        boolean salir = false;
        int opcion;
        while (!salir) {
            System.out.println("--EJERCICIOS ACCESO A DATOS: UNIDAD 02--");
            System.out.println("1. Ejercicio: 1");
            System.out.println("2. Ejercicio: 2");
            System.out.println("3. Ejercicio: 3");
            System.out.println("4. Ejercicio: 4");
            System.out.println("5. Ejercicio: 5");
            System.out.println("6. Ejercicio: 6");
            System.out.println("7. Ejercicio: 7");
            System.out.println("8. Ejercicio: 8");
            System.out.println("9. Ejercicio: 9");
            System.out.println("10. Salir");

            try {
                System.out.print("Introduce un numero: ");
                opcion = sn.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.println("--EJERCICIO 1--");
                        int opcion2;
                        boolean salir2 = false;

                        // El usuario selecciona operaciones de la calculadora hasta que decida volver al menú principal.
                        do {
                            System.out.println("--Bienvenido al menú de la calculadora--");
                            System.out.println("1. Suma");
                            System.out.println("2. Resta");
                            System.out.println("3. Multiplicación");
                            System.out.println("4. División");
                            System.out.println("5. Volver al menú principal");
                            try {
                                System.out.print("Introduce un número: ");
                                opcion2 = sn.nextInt();

                                switch (opcion2) {
                                    case 1:
                                        // Realizar una suma.
                                        Calculadora calSum = new Calculadora();
                                        System.out.print("Introduce dos valores para sumar: ");
                                        int valor1Sum = sn.nextInt();
                                        int valor2Sum = sn.nextInt();
                                        int resultadoSum = calSum.suma(valor1Sum, valor2Sum);
                                        System.out.println("El resultado de la suma es = " + resultadoSum);
                                        System.out.println("Marca otra operación si lo desea!");
                                        break;
                                    case 2:
                                        // Realizar una resta.
                                        Calculadora calRes = new Calculadora();
                                        System.out.print("Introduce dos valores para restar: ");
                                        int valor1Res = sn.nextInt();
                                        int valor2Res = sn.nextInt();
                                        int resultadoRes = calRes.resta(valor1Res, valor2Res);
                                        System.out.println("El resultado de la resta es = " + resultadoRes);
                                        System.out.println("Marca otra operación si lo desea!");
                                        break;
                                    case 3:
                                        // Realizar una multiplicación.
                                        Calculadora calMul = new Calculadora();
                                        System.out.print("Introduce dos valores para multiplicar: ");
                                        int valor1Mul = sn.nextInt();
                                        int valor2Mul = sn.nextInt();
                                        int resultadoMul = calMul.mult(valor1Mul, valor2Mul);
                                        System.out.println("El resultado de la multiplicación es = " + resultadoMul);
                                        System.out.println("Marca otra operación si lo desea!");
                                        break;
                                    case 4:
                                        // Realizar una división.
                                        Calculadora calDiv = new Calculadora();
                                        System.out.print("Introduce dos valores para dividir: ");
                                        int valor1Div = sn.nextInt();
                                        int valor2Div = sn.nextInt();
                                        int resultadoDiv = calDiv.divi(valor1Div, valor2Div);
                                        System.out.println("El resultado de la división es = " + resultadoDiv);
                                        System.out.println("Marca otra operación si lo desea!");
                                        break;
                                    case 5:
                                        // Volver al menú principal.
                                        salir2 = true;
                                        break;
                                    default:
                                        System.out.println("Las opciones son entre 1 y 5, por favor vuelva a introducirlo.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Debes introducir un número entre 1 y 5");
                                sn.next();
                            }
                        } while (!salir2);
                        break;
                    case 2:
                        System.out.println("--EJERCICIO 2--");
                        int opcion3;
                        boolean salir3 = false;

                        // El usuario selecciona opciones del concesionario hasta que decida volver al menú principal.
                        do {
                            System.out.println("--Bienvenido al menú del concesionario--");
                            System.out.println("1. Toyota Yaris");
                            System.out.println("2. Harley Davidson FDBX");
                            System.out.println("3. Volver al menú principal");
                            try {
                                System.out.print("Introduce un número: ");
                                opcion3 = sn.nextInt();

                                switch (opcion3) {
                                    case 1:
                                        // Mostrar características del Toyota Yaris.
                                        System.out.println("--Características del Toyota Yaris--");
                                        Vehiculo ToyotaYaris = new Vehiculo();
                                        ToyotaYaris.marca = "Toyota";
                                        System.out.println("La marca es " + ToyotaYaris.marca);
                                        ToyotaYaris.color = "Violeta";
                                        System.out.println("El color es " + ToyotaYaris.color);
                                        ToyotaYaris.cilindrada = "1.5 litros";
                                        System.out.println("La cilindrada es " + ToyotaYaris.cilindrada);
                                        ToyotaYaris.modelo = "Yaris Hatchback";
                                        System.out.println("El modelo es " + ToyotaYaris.modelo);
                                        ToyotaYaris.puertas = "5";
                                        System.out.println("Las puertas son " + ToyotaYaris.puertas);
                                        ToyotaYaris.combustible = "Gasolina";
                                        System.out.println("El combustible es " + ToyotaYaris.combustible);
                                        ToyotaYaris.enciende();
                                        ToyotaYaris.acelera();
                                        ToyotaYaris.frena();
                                        ToyotaYaris.autodestruccion();
                                        break;
                                    case 2:
                                        // Mostrar características de la Harley Davidson FDBX.
                                        System.out.println("--Características de la Harley Davidson FDBX--");
                                        Vehiculo HarleyDavidsonFDBX = new Vehiculo();
                                        HarleyDavidsonFDBX.marca = "Harley";
                                        System.out.println("La marca es " + HarleyDavidsonFDBX.marca);
                                        HarleyDavidsonFDBX.color = "Negro";
                                        System.out.println("La color es " + HarleyDavidsonFDBX.color);
                                        HarleyDavidsonFDBX.cilindrada = "1442 cc";
                                        System.out.println("La cilindrada es " + HarleyDavidsonFDBX.cilindrada);
                                        HarleyDavidsonFDBX.modelo = "Harley Davidson FDBX";
                                        System.out.println("El modelo es " + HarleyDavidsonFDBX.modelo);
                                        HarleyDavidsonFDBX.puertas = "sin puertas";
                                        System.out.println("Las puertas son, " + HarleyDavidsonFDBX.puertas);
                                        HarleyDavidsonFDBX.combustible = "Gasolina";
                                        System.out.println("El combustible es " + HarleyDavidsonFDBX.combustible);
                                        break;
                                    case 3:
                                        // Volver al menú principal.
                                        salir3 = true;
                                        break;
                                    default:
                                        System.out.println("Las opciones son entre 1 y 3, por favor vuelva a introducirlo.");
                                }

                            } catch (InputMismatchException e) {
                                System.out.println("Debes introducir un número entre 1 y 3");
                                sn.next();
                            }
                        } while (!salir3);
                        break;
                    case 3:
                        System.out.println("--EJERCICIO 3--");
                        int opcion4;
                        boolean salir4 = false;

                        // El usuario selecciona opciones relacionadas con ArrayList hasta que decida volver al menú principal.
                        do {
                            System.out.println("--Bienvenido al menú de los Arrays--");
                            System.out.println("1. Ejercicio de ArrayList con comentarios.");
                            System.out.println("2. Volver al menú principal");
                            try {
                                System.out.print("Introduce un número: ");
                                opcion4 = sn.nextInt();

                                switch (opcion4) {
                                    case 1:
                                        // Declaración del ArrayList con nombre "lista".
                                        ArrayList<String> lista = new ArrayList<String>();

                                        // Añadimos 10 elementos a la ArrayList.
                                        for (int i = 1; i <= 10; i++) {
                                            lista.add("Elemento " + i);
                                        }

                                        // Sustituimos el segundo elemento por "Elemento 3".
                                        lista.set(1, "Elemento 3");

                                        // Declaramos un Iterador y recorremos el ArrayList para mostrar los elementos.
                                        System.out.println("Elementos del ArrayList:");
                                        Iterator<String> iter = lista.iterator();
                                        while (iter.hasNext()) {
                                            String elemento = iter.next();
                                            System.out.println(elemento);
                                        }

                                        // Mostramos el número de elementos del ArrayList.
                                        System.out.println("El ArrayList tiene " + lista.size() + " elementos.");

                                        // Eliminamos el primer elemento.
                                        lista.remove(0);
                                        System.out.println("Eliminamos el primer elemento.");

                                        // Mostramos los elementos actuales.
                                        System.out.println("Elementos del ArrayList después de eliminar el primer elemento:");
                                        for (String elemento : lista) {
                                            System.out.println(elemento);
                                        }

                                        // Eliminamos los elementos iguales a "Elemento 3".
                                        lista.removeIf(elemento -> elemento.equals("Elemento 3"));
                                        System.out.println("Elementos del ArrayList después de eliminar 'Elemento 3':");
                                        for (String elemento : lista) {
                                            System.out.println(elemento);
                                        }
                                        break;
                                    case 2:
                                        // Volver al menú principal.
                                        salir4 = true;
                                        break;
                                    default:
                                        System.out.println("Las opciones son entre 1 y 2, por favor vuelva a introducirlo.");
                                }

                            } catch (InputMismatchException e) {
                                System.out.println("Debes introducir un número entre 1 y 2.");
                                sn.next();
                            }
                        } while (!salir4);
                        break;
                    case 4:
                        System.out.println("--EJERCICIO 4--");
                        ArrayList<Integer> numeros = new ArrayList<>();
                        int opcion5;

                        // El usuario selecciona opciones relacionadas con cálculos de suma y media hasta que decida volver al menú principal.
                        do {
                            System.out.println("--Caso 4: Calculadora de Suma y Media--");
                            System.out.println("1. Introducir números");
                            System.out.println("2. Calcular suma y media");
                            System.out.println("3. Mostrar valores mayores que la media");
                            System.out.println("4. Volver al menú principal");
                            System.out.print("Selecciona una opción: ");

                            opcion5 = sn.nextInt();

                            switch (opcion5) {
                                case 1:
                                    // El usuario introduce números enteros (termina con -99) y los agrega a la ArrayList "numeros".
                                    System.out.println("Introduce números enteros (termina con -99):");
                                    int numero;
                                    while (true) {
                                        numero = sn.nextInt();
                                        if (numero == -99) {
                                            break;
                                        }
                                        numeros.add(numero);
                                    }
                                    break;
                                case 2:
                                    if (numeros.isEmpty()) {
                                        // Si la lista está vacía, muestra un mensaje de que aún no se han introducido números.
                                        System.out.println("Aún no has introducido números.");
                                    } else {
                                        // Calcula la suma y la media de los números en la lista.
                                        int suma = 0;
                                        for (int n : numeros) {
                                            suma += n;
                                        }
                                        double media = (double) suma / numeros.size();
                                        System.out.println("Número de valores leídos: " + numeros.size());
                                        System.out.println("Suma de los valores: " + suma);
                                        System.out.println("Media de los valores: " + media);
                                    }
                                    break;
                                case 3:
                                    if (numeros.isEmpty()) {
                                        // Si la lista está vacía, muestra un mensaje de que aún no se han introducido números.
                                        System.out.println("Aún no has introducido números.");
                                    } else {
                                        // Calcula la suma y la media de los números en la lista.
                                        int suma = 0;
                                        for (int n : numeros) {
                                            suma += n;
                                        }
                                        double media = (double) suma / numeros.size();

                                        // Muestra los valores mayores que la media junto con su cantidad.
                                        System.out.println("Valores mayores que la media:");
                                        int contadorMayores = 0;
                                        for (int n : numeros) {
                                            if (n > media) {
                                                System.out.println(n);
                                                contadorMayores++;
                                            }
                                        }
                                        System.out.println("Total de valores mayores que la media: " + contadorMayores);
                                    }
                                    break;
                                case 4:
                                    // Volver al menú principal.
                                    System.out.println("Saliendo del programa.");
                                    break;
                                default:
                                    System.out.println("Opción no válida. Por favor, selecciona una opción válida.");
                            }
                        } while (opcion5 != 4);
                        break;
                    case 5:
                        int opcion0;
                        boolean salir5 = false;

                        // El usuario selecciona opciones relacionadas con archivos hasta que decida volver al menú principal.
                        do {
                            System.out.println("--Bienvenido al menú de los Ficheros--");
                            System.out.println("1. Ejercicio introduce un texto en el fichero.");
                            System.out.println("2. Mostrar registros.");
                            System.out.println("3. Volver al menú principal");
                            try {
                                System.out.print("Introduce un número: ");
                                opcion0 = sn.nextInt();

                                switch (opcion0) {
                                    case 1:
                                        System.out.println("--EJERCICIO 5--");
                                        Scanner sn1 = new Scanner(System.in);
                                        FileWriter fileWriter = null;

                                        try {
                                            // a. Crear un archivo llamado "ejercicio2_5Secu.txt" de tipo secuencial.
                                            File archivo = new File("ejercicio2_5Secu.txt");

                                            // b. Crear un flujo de datos de escritura con FileWriter para el archivo definido anteriormente.
                                            fileWriter = new FileWriter(archivo, true); // El segundo argumento "true" permite escribir al final del archivo.

                                            // Pedir por teclado un texto e introducirlo en el archivo.
                                            System.out.print("Introduce un texto para escribir en el archivo: ");
                                            String texto = sn1.nextLine();
                                            fileWriter.write(texto + "\n"); // Escribimos el texto en el archivo seguido de un salto de línea.

                                            System.out.println("Texto escrito en el archivo correctamente.");
                                        } catch (IOException e) {
                                            System.err.println("Error al operar con el archivo: " + e.getMessage());
                                        } finally {
                                            try {
                                                if (fileWriter != null) {
                                                    // c. Cerrar el archivo.
                                                    fileWriter.close();
                                                }
                                            } catch (IOException e) {
                                                System.err.println("Error al cerrar el archivo: " + e.getMessage());
                                            }
                                        }
                                        break;
                                    case 2:
                                        try {
                                            // Leer y mostrar los datos dentro del archivo "ejercicio2_5Secu.txt".
                                            File archivo = new File("ejercicio2_5Secu.txt");
                                            Scanner scanner = new Scanner(archivo);

                                            System.out.println("Contenido del archivo 'ejercicio2_5Secu.txt':");
                                            while (scanner.hasNextLine()) {
                                                String linea = scanner.nextLine();
                                                System.out.println(linea);
                                            }

                                            scanner.close();
                                        } catch (IOException e) {
                                            System.err.println("Error al leer el archivo: " + e.getMessage());
                                        }
                                        break;
                                    case 3:
                                        // Volver al menú principal.
                                        salir5 = true;
                                        break;
                                    default:
                                        System.out.println("Las opciones son entre 1 y 3, por favor vuelva a introducirlo.");
                                }

                            } catch (InputMismatchException e) {
                                System.out.println("Debes introducir un número entre 1 y 3.");
                                sn.next();
                            }
                        } while (!salir5);
                        break;
                    case 6:
                        System.out.println("--EJERCICIO 6--");
                        String archivo = "ejercicio2_6Bin.bin";

                        try (FileOutputStream fos = new FileOutputStream(archivo);
                             DataOutputStream dos = new DataOutputStream(fos)) {

                            // Crear un ArrayList de enteros y llenarlo con 100 números aleatorios.
                            ArrayList<Integer> numeros1 = new ArrayList<>();
                            Random rand = new Random();

                            for (int i = 0; i < 100; i++) {
                                int numeroAleatorio = rand.nextInt(1000); // Genera números aleatorios entre 0 y 999.
                                numeros1.add(numeroAleatorio);
                                dos.writeInt(numeroAleatorio); // Escribir el número entero en el archivo.
                            }

                            System.out.println("Se han escrito 100 números enteros en el archivo '" + archivo + "'.");

                            // Mostrar los números escritos
                            System.out.println("Números escritos:");
                            for (Integer numero : numeros1) {
                                System.out.print(numero + " ");
                            }
                            System.out.println(); // Salto de línea después de la lista de números.
                        } catch (IOException e) {
                            System.err.println("Error al escribir en el archivo: " + e.getMessage());
                        }
                        break;
                    case 7:
                        System.out.println("--EJERCICIO 7--");
                        String archivo7 = "ejercicio2_6Bin.bin";

                        try (FileInputStream fis = new FileInputStream(archivo7);
                             DataInputStream dis = new DataInputStream(fis)) {

                            System.out.println("Contenido del archivo '" + archivo7 + "':");

                            while (dis.available() > 0) {
                                int numero = dis.readInt();        // Lee un número entero del archivo
                                char caracter = (char) numero;     // Convierte el número en un caracter
                                System.out.print(caracter);        // Imprime el caracter en la consola
                            }

                            System.out.println(); // Salto de línea al final.
                        } catch (IOException e) {
                            System.err.println("Error al leer el archivo: " + e.getMessage());
                        }
                        break;
                    case 8:
                        System.out.println("--EJERCICIO 8--");
                        String archivo8 = "ejercicio2_8AleatorioEmple.dat";

                        try (RandomAccessFile raf = new RandomAccessFile(archivo8, "rw")) {
                            // Insertar 7 registros de empleados.
                            String[] apellidos = {"FERNANDEZ", "GIL", "LOPEZ", "RAMOS", "SEVILLA", "CASILLA", "REY"};
                            int[] codigosDepartamento = {10, 20, 10, 10, 30, 30, 20};
                            double[] salarios = {1000.45, 2400.60, 3000.0, 1500.56, 2200.0, 1435.87, 2000.0};

                            for (int i = 0; i < 7; i++) {
                                // Identificador (índice + 1).
                                int identificador = i + 1;

                                // Apellido del empleado (10 caracteres).
                                String apellido = apellidos[i];
                                StringBuffer apellidoBuffer = new StringBuffer(apellido);
                                apellidoBuffer.setLength(10); // Asegurarse de que el apellido ocupe 10 caracteres

                                // Código de departamento.
                                int codigoDepartamento = codigosDepartamento[i];

                                // Salario.
                                double salario = salarios[i];

                                // Escribir los datos en el archivo
                                raf.writeInt(identificador);
                                raf.writeChars(apellidoBuffer.toString());
                                raf.writeInt(codigoDepartamento);
                                raf.writeDouble(salario);
                            }

                            System.out.println("Se han insertado 7 registros de empleados en el archivo '" + archivo8 + "'.");

                            // Mostrar los registros de empleados desde el archivo.
                            raf.seek(0); // Posicionar al inicio del archivo.

                            System.out.println("Registros de empleados en el archivo:");

                            while (raf.getFilePointer() < raf.length()) {
                                int identificador = raf.readInt();
                                char[] apellidoChars = new char[10];
                                for (int i = 0; i < 10; i++) {
                                    apellidoChars[i] = raf.readChar();
                                }
                                String apellido = new String(apellidoChars);
                                int codigoDepartamento = raf.readInt();
                                double salario = raf.readDouble();

                                System.out.println("Identificador: " + identificador +
                                        ", Apellido: " + apellido.trim() +
                                        ", Código de Departamento: " + codigoDepartamento +
                                        ", Salario: " + salario);
                            }
                        } catch (IOException e) {
                            System.err.println("Error al operar con el archivo: " + e.getMessage());
                        }
                        break;
                    case 9:
                        System.out.println("--EJERCICIO 9--");
                        String archivo9 = "ejercicio2_8AleatorioEmple.dat";

                        try (RandomAccessFile raf = new RandomAccessFile(archivo9, "r")) {
                            System.out.println("Datos de empleados en el archivo '" + archivo9 + "':");

                            while (raf.getFilePointer() < raf.length()) {
                                // Lee y muestra cada registro de empleado en el archivo..
                                int identificador = raf.readInt(); // Lee el identificador.
                                char[] apellidoChars = new char[10];
                                for (int i = 0; i < 10; i++) {
                                    apellidoChars[i] = raf.readChar(); // Lee los caracteres del apellido.
                                }
                                String apellido = new String(apellidoChars).trim(); // Convierte los caracteres del apellido en una cadena y elimina espacios en blanco adicionales.
                                int codigoDepartamento = raf.readInt(); // Lee el código de departamento.
                                double salario = raf.readDouble(); // Lee el salario.

                                // Muestra los datos del empleado.
                                System.out.println("ID: " + identificador +
                                        ", Apellido: " + apellido +
                                        ", Departamento: " + codigoDepartamento +
                                        ", Salario: " + salario);
                            }
                        } catch (IOException e) {
                            System.err.println("Error al leer el archivo: " + e.getMessage());
                        }
                        break;
                    case 10:
                        salir = true; // Logica para salir del programa.
                        break;
                    default:
                        System.out.println("Las opciones son entre 1 y 10 , por favor vualva a introducirlo");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes introducir un numero entre 1 y 10");
                sn.next();
            }
        }
        System.out.println("Gracias vuelva pronto,FIN!!");
    }
}