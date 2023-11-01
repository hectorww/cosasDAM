import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sn = new Scanner(System.in); // Permite introducir informacion al programa.
        boolean salir = false;
        int opcion;
        while (!salir) {
            System.out.println("-- PRACTICA ACCESO A DATOS: UNIDAD 02 --");
            System.out.println("1. Parte: 1");
            System.out.println("2. Parte: 2");
            System.out.println("3. Parte: 3");
            System.out.println("4. Parte: 4");
            System.out.println("5. Parte: 5");
            System.out.println("6. Parte: 6");
            System.out.println("7. Salir");

            try {
                System.out.print("Introduce un numero: ");
                opcion = sn.nextInt();

                switch (opcion) {
                    case 1:
                        Scanner sn1 = new Scanner(System.in);
                        // O simplemente usar el directorio actual
                        String ruta = "."; // Esto creará "registro.txt" en el directorio donde se ejecuta el programa

                        ArchivoRegistros archivoRegistros = new ArchivoRegistros(ruta);
                        archivoRegistros.crearArchivoPorDefecto();
                        boolean salir1 = false;
                        try {
                            while (!salir1) {
                                System.out.println("-- Menú Principal 1 --");
                                System.out.println("1. Mostrar Registro");
                                System.out.println("2. Agregar Registro");
                                System.out.println("3. Recuperar Registro");
                                System.out.println("4. Modificar Registro");
                                System.out.println("5. Volver al menú principal");
                                System.out.println();
                                System.out.print("Seleccione una opción: ");

                                int opcion1 = sn1.nextInt();
                                sn1.nextLine(); // Consumir la nueva línea

                                switch (opcion1) {
                                    case 1:
                                        System.out.println();
                                        archivoRegistros.mostrarRegistrosInsertados();
                                        break;
                                    case 2:
                                        String codigo = "";
                                        String nombre = "";
                                        String apellido = "";

                                        // Ingresar y validar el código
                                        while (codigo.length() != 4 || archivoRegistros.verificarClaveExistente(codigo)) {
                                            if (codigo.length() != 4) {
                                                System.out.print("Ingrese el código (4 caracteres): ");
                                                codigo = sn1.nextLine();
                                            }
                                            if (archivoRegistros.verificarClaveExistente(codigo)) {
                                                System.out.println("La clave ya está en uso. Por favor, ingrese una clave diferente:");
                                                codigo = sn1.nextLine();
                                            }
                                        }

                                        // Ingresar y validar el nombre
                                        while (nombre.length() > 15 || nombre.isEmpty()) {
                                            System.out.print("Ingrese el nombre (1-15 caracteres): ");
                                            nombre = sn1.nextLine();
                                            if (nombre.length() > 15 || nombre.isEmpty()) {
                                                System.out.println("El nombre no puede estar vacío ni tener más de 15 caracteres.");
                                            }
                                        }

                                        // Ingresar y validar el apellido
                                        while (apellido.length() > 25 || apellido.isEmpty()) {
                                            System.out.print("Ingrese el apellido (1-25 caracteres): ");
                                            apellido = sn1.nextLine();
                                            if (apellido.length() > 25 || apellido.isEmpty()) {
                                                System.out.println("El apellido no puede estar vacío ni tener más de 25 caracteres.");
                                            }
                                        }

                                        // Mostrar todos los campos ingresados
                                        System.out.println("Campos introducidos:");
                                        System.out.println("Código: " + codigo);
                                        System.out.println("Nombre: " + nombre);
                                        System.out.println("Apellido: " + apellido);

                                        Registro nuevoRegistro = new Registro(codigo, nombre, apellido);
                                        archivoRegistros.agregarRegistro(nuevoRegistro);
                                        break;
                                    case 3:
                                        // Mostrar todos los códigos de registro disponibles
                                        archivoRegistros.mostrarCodigosRegistros();

                                        // Solicitar al usuario que ingrese el código a buscar
                                        System.out.print("Ingrese el código a buscar: ");
                                        String codigoBuscar = sn1.nextLine().trim();


                                        Registro registroRecuperado = archivoRegistros.recuperarRegistro(codigoBuscar);

                                        if (registroRecuperado != null) {
                                            // Mostrar toda la información del registro encontrado
                                            System.out.println("Registro encontrado:");
                                            System.out.println("Código: " + registroRecuperado.getCodigo());
                                            System.out.println("Nombre: " + registroRecuperado.getNombre());
                                            System.out.println("Apellidos: " + registroRecuperado.getApellido());
                                        } else {
                                            System.out.println("Registro no encontrado.");
                                        }
                                        break;
                                    case 4:
                                        // Mostrar todos los códigos de registro disponibles
                                        archivoRegistros.mostrarCodigosRegistros();
                                        System.out.print("Ingrese el código del registro que desea modificar: ");
                                        String codigoModificar = sn1.nextLine();
                                        Registro registroExistente = archivoRegistros.recuperarRegistro(codigoModificar);
                                        if (registroExistente != null) {
                                            System.out.println("Registro encontrado:");
                                            System.out.println(registroExistente);
                                            System.out.println("Ingrese los nuevos datos:");
                                            System.out.print("Nuevo código (4 caracteres): ");
                                            codigo = sn1.nextLine();
                                            System.out.print("Nuevo nombre (15 caracteres): ");
                                            nombre = sn1.nextLine();
                                            System.out.print("Nuevo apellido (25 caracteres): ");
                                            apellido = sn1.nextLine();
                                            Registro nuevoRegistroModificado = new Registro(codigo, nombre, apellido);
                                            archivoRegistros.modificarRegistro(codigoModificar, nuevoRegistroModificado);
                                        } else {
                                            System.out.println("Registro no encontrado.");
                                        }
                                        break;
                                    case 5:
                                        System.out.println("Saliendo del menú de parte 1, Gracias.");
                                        salir1 = true;
                                        break;
                                    default:
                                        System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                                        break;
                                }
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Debes introducir un numero dependiendo del menú donde te encuentres.");
                            sn1.next();
                        }
                        break;
                    case 2:
                        System.out.println("Lo sentimos, se encuentra en construccion");
                        System.out.println("Dentro de poco nueva version...");
                        break;
                    case 3:
                        Scanner sn3 = new Scanner(System.in);
                        // O simplemente usar el directorio actual
                        String ruta3 = "."; // Esto creará "registro.txt" en el directorio donde se ejecuta el programa

                        ArchivoRegistros archivoRegistros3 = new ArchivoRegistros(ruta3);
                        archivoRegistros3.crearArchivoPorDefecto();
                        boolean salir3 = false;
                        try {
                            while (!salir3) {
                                System.out.println("-- Menú Principal 3 --");
                                System.out.println("1. Mostrar Registro");
                                System.out.println("2. Agregar Registro");
                                System.out.println("3. Recuperar Registro");
                                System.out.println("4. Modificar Registro");
                                System.out.println("5. Marcar Registro como Borrado");
                                System.out.println("6. Mostrar Registros Marcados como Borrados");
                                System.out.println("7. Volver al menú principal");
                                System.out.println();
                                System.out.print("Seleccione una opción: ");

                                int opcion3 = sn3.nextInt();
                                sn3.nextLine(); // Consumir la nueva línea

                                switch (opcion3) {
                                    case 1:
                                        System.out.println();
                                        archivoRegistros3.mostrarRegistrosInsertados();
                                        break;
                                    case 2:
                                        String codigo = "";
                                        String nombre = "";
                                        String apellido = "";

                                        // Ingresar y validar el código
                                        while (codigo.length() != 4 || archivoRegistros3.verificarClaveExistente(codigo)) {
                                            if (codigo.length() != 4) {
                                                System.out.print("Ingrese el código (4 caracteres): ");
                                                codigo = sn3.nextLine();
                                            }
                                            if (archivoRegistros3.verificarClaveExistente(codigo)) {
                                                System.out.println("La clave ya está en uso. Por favor, ingrese una clave diferente:");
                                                codigo = sn3.nextLine();
                                            }
                                        }

                                        // Ingresar y validar el nombre
                                        while (nombre.length() > 15 || nombre.isEmpty()) {
                                            System.out.print("Ingrese el nombre (1-15 caracteres): ");
                                            nombre = sn3.nextLine();
                                            if (nombre.length() > 15 || nombre.isEmpty()) {
                                                System.out.println("El nombre no puede estar vacío ni tener más de 15 caracteres.");
                                            }
                                        }

                                        // Ingresar y validar el apellido
                                        while (apellido.length() > 25 || apellido.isEmpty()) {
                                            System.out.print("Ingrese el apellido (1-25 caracteres): ");
                                            apellido = sn3.nextLine();
                                            if (apellido.length() > 25 || apellido.isEmpty()) {
                                                System.out.println("El apellido no puede estar vacío ni tener más de 25 caracteres.");
                                            }
                                        }

                                        // Mostrar todos los campos ingresados
                                        System.out.println("Campos introducidos:");
                                        System.out.println("Código: " + codigo);
                                        System.out.println("Nombre: " + nombre);
                                        System.out.println("Apellido: " + apellido);

                                        Registro nuevoRegistro = new Registro(codigo, nombre, apellido);
                                        archivoRegistros3.agregarRegistro(nuevoRegistro);
                                        break;
                                    case 3:
                                        // Mostrar todos los códigos de registro disponibles
                                        archivoRegistros3.mostrarCodigosRegistros();

                                        // Solicitar al usuario que ingrese el código a buscar
                                        System.out.print("Ingrese el código a buscar: ");
                                        String codigoBuscar = sn3.nextLine().trim();

                                        Registro registroRecuperado = archivoRegistros3.recuperarRegistro(codigoBuscar);

                                        if (registroRecuperado != null) {
                                            // Mostrar toda la información del registro encontrado
                                            System.out.println("Registro encontrado:");
                                            System.out.println("Código: " + registroRecuperado.getCodigo());
                                            System.out.println("Nombre: " + registroRecuperado.getNombre());
                                            System.out.println("Apellidos: " + registroRecuperado.getApellido());
                                        } else {
                                            System.out.println("Registro no encontrado.");
                                        }
                                        break;
                                    case 4:
                                        // Mostrar todos los códigos de registro disponibles
                                        archivoRegistros3.mostrarCodigosRegistros();
                                        System.out.print("Ingrese el código del registro que desea modificar: ");
                                        String codigoModificar = sn3.nextLine();
                                        Registro registroExistente = archivoRegistros3.recuperarRegistro(codigoModificar);
                                        if (registroExistente != null) {
                                            System.out.println("Registro encontrado:");
                                            System.out.println(registroExistente);
                                            System.out.println("Ingrese los nuevos datos:");
                                            System.out.print("Nuevo código (4 caracteres): ");
                                            codigo = sn3.nextLine();
                                            System.out.print("Nuevo nombre (15 caracteres): ");
                                            nombre = sn3.nextLine();
                                            System.out.print("Nuevo apellido (25 caracteres): ");
                                            apellido = sn3.nextLine();
                                            Registro nuevoRegistroModificado = new Registro(codigo, nombre, apellido);
                                            archivoRegistros3.modificarRegistro(codigoModificar, nuevoRegistroModificado);
                                        } else {
                                            System.out.println("Registro no encontrado.");
                                        }
                                        break;
                                    case 5:
                                        // Mostrar todos los códigos de registro disponibles
                                        archivoRegistros3.mostrarCodigosRegistros();
                                        System.out.print("Ingrese el código del registro a marcar como borrado: ");
                                        String codigoBorrar = sn3.nextLine();
                                        archivoRegistros3.marcarRegistroComoBorrado(codigoBorrar);
                                        break;
                                    case 6:
                                        archivoRegistros3.mostrarRegistrosMarcadosComoBorrados();
                                        break;
                                    case 7:
                                        System.out.println("Saliendo del menú de parte 3, Gracias.");
                                        salir3 = true;
                                        break;
                                    default:
                                        System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                                        break;
                                }
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Debes introducir un numero dependiendo del menú donde te encuentres.");
                            sn3.next();
                        }
                        break;
                    case 4:
                        Scanner sn4 = new Scanner(System.in);
                        // O simplemente usar el directorio actual
                        String ruta4 = "."; // Esto creará "registro.txt" en el directorio donde se ejecuta el programa

                        ArchivoRegistros archivoRegistros4 = new ArchivoRegistros(ruta4);
                        archivoRegistros4.crearArchivoPorDefecto();
                        boolean salir4 = false;
                        try {
                            while (!salir4) {
                                System.out.println("-- Menú Principal 4 --");
                                System.out.println("1. Mostrar Registro");
                                System.out.println("2. Agregar Registro");
                                System.out.println("3. Recuperar Registro");
                                System.out.println("4. Modificar Registro");
                                System.out.println("5. Marcar Registro como Borrado");
                                System.out.println("6. Mostrar Registros Marcados como Borrados");
                                System.out.println("7. Compactar Archivo");
                                System.out.println("8. Volver al menú principal");
                                System.out.println();
                                System.out.print("Seleccione una opción: ");

                                int opcion4 = sn4.nextInt();
                                sn4.nextLine(); // Consumir la nueva línea

                                switch (opcion4) {
                                    case 1:
                                        System.out.println();
                                        archivoRegistros4.mostrarRegistrosInsertados();
                                        break;
                                    case 2:
                                        String codigo = "";
                                        String nombre = "";
                                        String apellido = "";

                                        // Ingresar y validar el código
                                        while (codigo.length() != 4 || archivoRegistros4.verificarClaveExistente(codigo)) {
                                            if (codigo.length() != 4) {
                                                System.out.print("Ingrese el código (4 caracteres): ");
                                                codigo = sn4.nextLine();
                                            }
                                            if (archivoRegistros4.verificarClaveExistente(codigo)) {
                                                System.out.println("La clave ya está en uso. Por favor, ingrese una clave diferente:");
                                                codigo = sn4.nextLine();
                                            }
                                        }

                                        // Ingresar y validar el nombre
                                        while (nombre.length() > 15 || nombre.isEmpty()) {
                                            System.out.print("Ingrese el nombre (1-15 caracteres): ");
                                            nombre = sn4.nextLine();
                                            if (nombre.length() > 15 || nombre.isEmpty()) {
                                                System.out.println("El nombre no puede estar vacío ni tener más de 15 caracteres.");
                                            }
                                        }

                                        // Ingresar y validar el apellido
                                        while (apellido.length() > 25 || apellido.isEmpty()) {
                                            System.out.print("Ingrese el apellido (1-25 caracteres): ");
                                            apellido = sn4.nextLine();
                                            if (apellido.length() > 25 || apellido.isEmpty()) {
                                                System.out.println("El apellido no puede estar vacío ni tener más de 25 caracteres.");
                                            }
                                        }

                                        // Mostrar todos los campos ingresados
                                        System.out.println("Campos introducidos:");
                                        System.out.println("Código: " + codigo);
                                        System.out.println("Nombre: " + nombre);
                                        System.out.println("Apellido: " + apellido);

                                        Registro nuevoRegistro = new Registro(codigo, nombre, apellido);
                                        archivoRegistros4.agregarRegistro(nuevoRegistro);
                                        break;
                                    case 3:
                                        // Mostrar todos los códigos de registro disponibles
                                        archivoRegistros4.mostrarCodigosRegistros();

                                        // Solicitar al usuario que ingrese el código a buscar
                                        System.out.print("Ingrese el código a buscar: ");
                                        String codigoBuscar = sn4.nextLine().trim();

                                        Registro registroRecuperado = archivoRegistros4.recuperarRegistro(codigoBuscar);

                                        if (registroRecuperado != null) {
                                            // Mostrar toda la información del registro encontrado
                                            System.out.println("Registro encontrado:");
                                            System.out.println("Código: " + registroRecuperado.getCodigo());
                                            System.out.println("Nombre: " + registroRecuperado.getNombre());
                                            System.out.println("Apellidos: " + registroRecuperado.getApellido());
                                        } else {
                                            System.out.println("Registro no encontrado.");
                                        }
                                        break;
                                    case 4:
                                        // Mostrar todos los códigos de registro disponibles
                                        archivoRegistros4.mostrarCodigosRegistros();
                                        System.out.print("Ingrese el código del registro que desea modificar: ");
                                        String codigoModificar = sn4.nextLine();
                                        Registro registroExistente = archivoRegistros4.recuperarRegistro(codigoModificar);
                                        if (registroExistente != null) {
                                            System.out.println("Registro encontrado:");
                                            System.out.println(registroExistente);
                                            System.out.println("Ingrese los nuevos datos:");
                                            System.out.print("Nuevo código (4 caracteres): ");
                                            codigo = sn4.nextLine();
                                            System.out.print("Nuevo nombre (15 caracteres): ");
                                            nombre = sn4.nextLine();
                                            System.out.print("Nuevo apellido (25 caracteres): ");
                                            apellido = sn4.nextLine();
                                            Registro nuevoRegistroModificado = new Registro(codigo, nombre, apellido);
                                            archivoRegistros4.modificarRegistro(codigoModificar, nuevoRegistroModificado);
                                        } else {
                                            System.out.println("Registro no encontrado.");
                                        }
                                        break;
                                    case 5:
                                        // Mostrar todos los códigos de registro disponibles
                                        archivoRegistros4.mostrarCodigosRegistros();
                                        System.out.print("Ingrese el código del registro a marcar como borrado: ");
                                        String codigoBorrar = sn4.nextLine();
                                        archivoRegistros4.marcarRegistroComoBorrado(codigoBorrar);
                                        break;
                                    case 6:
                                        archivoRegistros4.mostrarRegistrosMarcadosComoBorrados();
                                        break;
                                    case 7:
                                        // Llamar al método compactarArchivo
                                        archivoRegistros4.compactarArchivo();
                                        break;
                                    case 8:
                                        System.out.println("Saliendo del menú de parte 4, Gracias.");
                                        salir4 = true;
                                        break;
                                    default:
                                        System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                                        break;
                                }
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Debes introducir un número dependiendo del menú donde te encuentres.");
                            sn4.next();
                        }
                        break;
                    case 5:
                        Scanner sn5 = new Scanner(System.in);
                        // O simplemente usar el directorio actual
                        String ruta5 = "."; // Esto creará "registro.txt" en el directorio donde se ejecuta el programa

                        ArchivoRegistrosFijos archivoRegistrosFijos = new ArchivoRegistrosFijos(ruta5);
                        archivoRegistrosFijos.crearArchivoPorDefecto();
                        boolean salir5 = false;
                        try {
                            while (!salir5) {
                                System.out.println("-- Menú Principal 5 --");
                                System.out.println("1. Mostrar Registro");
                                System.out.println("2. Agregar Registro");
                                System.out.println("3. Recuperar Registro");
                                System.out.println("4. Modificar Registro");
                                System.out.println("5. Marcar Registro como Borrado");
                                System.out.println("6. Mostrar Registros Marcados como Borrados");
                                System.out.println("7. Compactar Archivo");
                                System.out.println("8. Volver al menú principal");
                                System.out.println();
                                System.out.print("Seleccione una opción: ");

                                int opcion5 = sn5.nextInt();
                                sn5.nextLine(); // Consumir la nueva línea

                                switch (opcion5) {
                                    case 1:
                                        System.out.println();
                                        archivoRegistrosFijos.mostrarRegistrosInsertados();
                                        break;
                                    case 2:
                                        String codigo = "";
                                        String nombre = "";
                                        String apellido = "";

                                        // Ingresar y validar el código
                                        while (codigo.length() != 4 || archivoRegistrosFijos.verificarClaveExistente(codigo)) {
                                            if (codigo.length() != 4) {
                                                System.out.print("Ingrese el código (4 caracteres): ");
                                                codigo = sn5.nextLine();
                                            }
                                            if (archivoRegistrosFijos.verificarClaveExistente(codigo)) {
                                                System.out.println("La clave ya está en uso. Por favor, ingrese una clave diferente:");
                                                codigo = sn5.nextLine();
                                            }
                                        }

                                        // Ingresar y validar el nombre
                                        while (nombre.length() > 15 || nombre.isEmpty()) {
                                            System.out.print("Ingrese el nombre (1-15 caracteres): ");
                                            nombre = sn5.nextLine();
                                            if (nombre.length() > 15 || nombre.isEmpty()) {
                                                System.out.println("El nombre no puede estar vacío ni tener más de 15 caracteres.");
                                            }
                                        }

                                        // Ingresar y validar el apellido
                                        while (apellido.length() > 25 || apellido.isEmpty()) {
                                            System.out.print("Ingrese el apellido (1-25 caracteres): ");
                                            apellido = sn5.nextLine();
                                            if (apellido.length() > 25 || apellido.isEmpty()) {
                                                System.out.println("El apellido no puede estar vacío ni tener más de 25 caracteres.");
                                            }
                                        }

                                        // Mostrar todos los campos ingresados
                                        System.out.println("Campos introducidos:");
                                        System.out.println("Código: " + codigo);
                                        System.out.println("Nombre: " + nombre);
                                        System.out.println("Apellido: " + apellido);

                                        Registro nuevoRegistro = new Registro(codigo, nombre, apellido);
                                        archivoRegistrosFijos.agregarRegistro(nuevoRegistro);

                                        // Insertar el registro en orden
                                        archivoRegistrosFijos.insertarRegistroOrdenado(nuevoRegistro);
                                        break;
                                    case 3:
                                        // Mostrar todos los códigos de registro disponibles
                                        archivoRegistrosFijos.mostrarCodigosRegistros();

                                        // Solicitar al usuario que ingrese el código a buscar
                                        System.out.print("Ingrese el código a buscar: ");
                                        String codigoBuscar = sn5.nextLine().trim();

                                        Registro registroRecuperado = archivoRegistrosFijos.recuperarRegistro(codigoBuscar);

                                        if (registroRecuperado != null) {
                                            // Mostrar toda la información del registro encontrado
                                            System.out.println("Registro encontrado:");
                                            System.out.println("Código: " + registroRecuperado.getCodigo());
                                            System.out.println("Nombre: " + registroRecuperado.getNombre());
                                            System.out.println("Apellidos: " + registroRecuperado.getApellido());
                                        } else {
                                            System.out.println("Registro no encontrado.");
                                        }
                                        break;
                                    case 4:
                                        // Mostrar todos los códigos de registro disponibles
                                        archivoRegistrosFijos.mostrarCodigosRegistros();
                                        System.out.print("Ingrese el código del registro que desea modificar: ");
                                        String codigoModificar = sn5.nextLine();
                                        Registro registroExistente = archivoRegistrosFijos.recuperarRegistro(codigoModificar);
                                        if (registroExistente != null) {
                                            System.out.println("Registro encontrado:");
                                            System.out.println(registroExistente);
                                            System.out.println("Ingrese los nuevos datos:");
                                            System.out.print("Nuevo código (4 caracteres): ");
                                            codigo = sn5.nextLine();
                                            System.out.print("Nuevo nombre (15 caracteres): ");
                                            nombre = sn5.nextLine();
                                            System.out.print("Nuevo apellido (25 caracteres): ");
                                            apellido = sn5.nextLine();
                                            Registro nuevoRegistroModificado = new Registro(codigo, nombre, apellido);
                                            archivoRegistrosFijos.modificarRegistro(codigoModificar, nuevoRegistroModificado);
                                        } else {
                                            System.out.println("Registro no encontrado.");
                                        }
                                        break;
                                    case 5:
                                        // Mostrar todos los códigos de registro disponibles
                                        archivoRegistrosFijos.mostrarCodigosRegistros();
                                        System.out.print("Ingrese el código del registro a marcar como borrado: ");
                                        String codigoBorrar = sn5.nextLine();
                                        archivoRegistrosFijos.marcarRegistroComoBorrado(codigoBorrar);
                                        break;
                                    case 6:
                                        archivoRegistrosFijos.mostrarRegistrosMarcadosComoBorrados();
                                        break;
                                    case 7:
                                        // Llamar al método compactarArchivo
                                        archivoRegistrosFijos.compactarArchivo();
                                        break;
                                    case 8:
                                        System.out.println("Saliendo del menú de parte 5, Gracias.");
                                        salir5 = true;
                                        break;
                                    default:
                                        System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                                        break;
                                }
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Debes introducir un número dependiendo del menú donde te encuentres.");
                            sn5.next();
                        }
                        break;
                    case 6:
                        Scanner sn6 = new Scanner(System.in);
                        // O simplemente usar el directorio actual
                        String ruta6 = "."; // Esto creará "registro.txt" en el directorio donde se ejecuta el programa

                        GestorRegistros archivoRegistrosIndexado = new GestorRegistros(ruta6);
                        archivoRegistrosIndexado.crearArchivoPorDefecto();
                        boolean salir6 = false;
                        try {
                            while (!salir6) {
                                System.out.println("-- Menú Principal 6 --");
                                System.out.println("1. Mostrar Registro");
                                System.out.println("2. Agregar Registro");
                                System.out.println("3. Recuperar Registro");
                                System.out.println("4. Modificar Registro");
                                System.out.println("5. Marcar Registro como Borrado");
                                System.out.println("6. Mostrar Registros Marcados como Borrados");
                                System.out.println("7. Compactar Archivo");
                                System.out.println("8. Volver al menú principal");
                                System.out.println();
                                System.out.print("Seleccione una opción: ");

                                int opcion6 = sn6.nextInt();
                                sn6.nextLine(); // Consumir la nueva línea

                                switch (opcion6) {
                                    case 1:
                                        System.out.println();
                                        archivoRegistrosIndexado.mostrarRegistrosInsertados();
                                        break;
                                    case 2:
                                        String codigo = "";
                                        String nombre = "";
                                        String apellido = "";

                                        // Ingresar y validar el código
                                        while (codigo.length() != 4 || archivoRegistrosIndexado.verificarClaveExistente(codigo)) {
                                            if (codigo.length() != 4) {
                                                System.out.print("Ingrese el código (4 caracteres): ");
                                                codigo = sn6.nextLine();
                                            }
                                            if (archivoRegistrosIndexado.verificarClaveExistente(codigo)) {
                                                System.out.println("La clave ya está en uso. Por favor, ingrese una clave diferente:");
                                                codigo = sn6.nextLine();
                                            }
                                        }

                                        // Ingresar y validar el nombre
                                        while (nombre.length() > 15 || nombre.isEmpty()) {
                                            System.out.print("Ingrese el nombre (1-15 caracteres): ");
                                            nombre = sn6.nextLine();
                                            if (nombre.length() > 15 || nombre.isEmpty()) {
                                                System.out.println("El nombre no puede estar vacío ni tener más de 15 caracteres.");
                                            }
                                        }

                                        // Ingresar y validar el apellido
                                        while (apellido.length() > 25 || apellido.isEmpty()) {
                                            System.out.print("Ingrese el apellido (1-25 caracteres): ");
                                            apellido = sn6.nextLine();
                                            if (apellido.length() > 25 || apellido.isEmpty()) {
                                                System.out.println("El apellido no puede estar vacío ni tener más de 25 caracteres.");
                                            }
                                        }

                                        // Mostrar todos los campos ingresados
                                        System.out.println("Campos introducidos:");
                                        System.out.println("Código: " + codigo);
                                        System.out.println("Nombre: " + nombre);
                                        System.out.println("Apellido: " + apellido);

                                        Registro nuevoRegistro = new Registro(codigo, nombre, apellido);
                                        archivoRegistrosIndexado.agregarRegistro(nuevoRegistro);
                                        break;
                                    case 3:
                                        // Mostrar todos los códigos de registro disponibles
                                        archivoRegistrosIndexado.mostrarCodigosRegistros();

                                        // Solicitar al usuario que ingrese el código a buscar
                                        System.out.print("Ingrese el código a buscar: ");
                                        String codigoBuscar = sn6.nextLine().trim();

                                        Registro registroRecuperado = archivoRegistrosIndexado.recuperarRegistro(codigoBuscar);

                                        if (registroRecuperado != null) {
                                            // Mostrar toda la información del registro encontrado
                                            System.out.println("Registro encontrado:");
                                            System.out.println("Código: " + registroRecuperado.getCodigo());
                                            System.out.println("Nombre: " + registroRecuperado.getNombre());
                                            System.out.println("Apellidos: " + registroRecuperado.getApellido());
                                        } else {
                                            System.out.println("Registro no encontrado.");
                                        }
                                        break;
                                    case 4:
                                        // Mostrar todos los códigos de registro disponibles
                                        archivoRegistrosIndexado.mostrarCodigosRegistros();
                                        System.out.print("Ingrese el código del registro que desea modificar: ");
                                        String codigoModificar = sn6.nextLine();
                                        Registro registroExistente = archivoRegistrosIndexado.recuperarRegistro(codigoModificar);
                                        if (registroExistente != null) {
                                            System.out.println("Registro encontrado:");
                                            System.out.println(registroExistente);
                                            System.out.println("Ingrese los nuevos datos:");
                                            System.out.print("Nuevo código (4 caracteres): ");
                                            codigo = sn6.nextLine();
                                            System.out.print("Nuevo nombre (15 caracteres): ");
                                            nombre = sn6.nextLine();
                                            System.out.print("Nuevo apellido (25 caracteres): ");
                                            apellido = sn6.nextLine();
                                            Registro nuevoRegistroModificado = new Registro(codigo, nombre, apellido);
                                            archivoRegistrosIndexado.modificarRegistro(codigoModificar, nuevoRegistroModificado);
                                        } else {
                                            System.out.println("Registro no encontrado.");
                                        }
                                        break;
                                    case 5:
                                        // Mostrar todos los códigos de registro disponibles
                                        archivoRegistrosIndexado.mostrarCodigosRegistros();
                                        System.out.print("Ingrese el código del registro a marcar como borrado: ");
                                        String codigoBorrar = sn6.nextLine();
                                        archivoRegistrosIndexado.marcarRegistroComoBorrado(codigoBorrar);
                                        break;
                                    case 6:
                                        archivoRegistrosIndexado.mostrarRegistrosMarcadosComoBorrados();
                                        break;
                                    case 7:
                                        // Llamar al método compactarArchivo
                                        archivoRegistrosIndexado.compactarArchivo();
                                        break;
                                    case 8:
                                        System.out.println("Saliendo del menú de parte 6, Gracias.");
                                        salir6 = true;
                                        break;
                                    default:
                                        System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                                        break;
                                }
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Debes introducir un número dependiendo del menú donde te encuentres.");
                            sn6.next();
                        }
                        break;
                    case 7:
                        salir = true;
                        break;
                    default:
                        System.out.println("Las opciones son entre 1 y 7 , por favor vualva a introducirlo.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Debes introducir un numero dependiendo del menú donde te encuentres.");
                sn.next();
            }
        }
        System.out.println("Gracias vuelva pronto,FIN!!");
    }
}