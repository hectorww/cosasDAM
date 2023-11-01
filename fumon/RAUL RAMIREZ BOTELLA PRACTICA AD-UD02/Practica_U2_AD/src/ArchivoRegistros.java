import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// La clase ArchivoRegistros gestiona un archivo de registros que contiene información sobre registros de datos.
public class ArchivoRegistros {
    private File archivo; // Representa el archivo de registros.

    // Constructor de la clase que recibe la ruta del archivo.
    public ArchivoRegistros(String ruta) {
        archivo = new File(ruta, "registro.txt");
        try {
            if (!archivo.exists()) {
                System.out.println("El archivo no existe. Creando archivo con datos por defecto...");
                crearArchivoPorDefecto(); // Si el archivo no existe, se crea con registros de ejemplo.
            }
        } catch (Exception e) {
            System.err.println("Error: No se pudo encontrar el archivo en la ubicación especificada.");
            e.printStackTrace();
        }
    }

    // Método para crear un archivo por defecto con registros de ejemplo.
    public void crearArchivoPorDefecto() {
        try (FileWriter fw = new FileWriter(archivo);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write("0001,Raul,Botella");
            bw.newLine();
            bw.write("0002,Alvarito,Galvez");
            bw.newLine();
            bw.write("0003,Javier,Roca");
            System.out.println("Se ha creado un archivo por defecto con registros de ejemplo.");
        } catch (IOException e) {
            System.err.println("Error al crear el archivo por defecto con registros de ejemplo.");
            e.printStackTrace();
        }
    }

    // Método para agregar un nuevo registro al archivo.
    public void agregarRegistro(Registro registro) {
        Scanner scanner = new Scanner(System.in);
        boolean claveValida = false;

        do {
            String claveNueva = registro.getCodigo();
            boolean claveExiste = verificarClaveExistente(claveNueva);

            if (claveExiste) {
                System.out.println("La clave ya está en uso. Por favor, ingrese una clave diferente:");
                claveNueva = scanner.nextLine();
                registro.setCodigo(claveNueva); // Actualiza la clave en el registro.
            } else {
                claveValida = true;
            }
        } while (!claveValida);
        try (FileWriter fw = new FileWriter(archivo, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            if (archivo.length() > 0) {
                bw.newLine(); // Agregar un salto de línea si el archivo no está vacío.
            }
            bw.write(registro.getCodigo() + "," + registro.getNombre() + "," + registro.getApellido());
        } catch (IOException e) {
            System.err.println("Error al agregar un nuevo registro al archivo. Por favor, inténtelo de nuevo más tarde.");
            e.printStackTrace();
        }
    }
    // Método para verificar si una clave (código) ya existe en el archivo de registros.
    public boolean verificarClaveExistente(String clave) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Dividir la línea en campos utilizando ',' como delimitador.
                String[] campos = linea.split(",");

                // Verificar que la línea tenga al menos 3 campos y que el primer campo coincida con la clave proporcionada.
                if (campos.length >= 3 && campos[0].trim().equals(clave.trim())) {
                    return true; // La clave ya existe en el archivo.
                }
            }
        } catch (IOException e) {
            System.err.println("Error al verificar la clave.");
            e.printStackTrace();
        }
        return false; // La clave no existe en el archivo.
    }

    // Método para recuperar un registro por su código.
    public Registro recuperarRegistro(String codigoBuscar) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 3 && campos[0].trim().equals(codigoBuscar.trim())) {
                    return new Registro(campos[0], campos[1], campos[2]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al recuperar el registro. Por favor, inténtelo de nuevo más tarde.");
            e.printStackTrace();
        }
        return null; // Retornar null si el registro no se encuentra.
    }

    // Método para modificar un registro.
    public void modificarRegistro(String codigoModificar, Registro nuevoRegistro) {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean encontrado = false;
            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 3 && campos[0].equals(codigoModificar)) {
                    lineas.add(nuevoRegistro.getCodigo() + "," + nuevoRegistro.getNombre() + "," + nuevoRegistro.getApellido());
                    encontrado = true;
                    System.out.println("Registro modificado con éxito.");
                } else {
                    lineas.add(linea);
                }
            }

            if (!encontrado) {
                System.out.println("Registro no encontrado.");
            } else {
                try (PrintWriter writer = new PrintWriter(archivo)) {
                    for (String nuevaLinea : lineas) {
                        writer.println(nuevaLinea);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al modificar el registro. Por favor, inténtelo de nuevo más tarde.");
            e.printStackTrace();
        }
    }

    // Método para mostrar los códigos de registros disponibles.
    public void mostrarCodigosRegistros() {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 3 && !campos[0].equalsIgnoreCase("Código")) {
                    System.out.println("Código: " + campos[0]);
                    System.out.println(); // Separación en blanco entre registros.
                }
            }
        } catch (IOException e) {
            System.err.println("Error al mostrar los registros disponibles. Por favor, inténtelo de nuevo más tarde.");
            e.printStackTrace();
        }
    }


    // Método para mostrar todos los registros insertados sin los marcados como borrados.
    public void mostrarRegistrosInsertados() {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            System.out.println("Registros insertados:");
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().startsWith("#")) { // Omitir registros marcados como borrados.
                    String[] campos = linea.split(",");
                    if (campos.length >= 3) {
                        System.out.println("Código: " + campos[0]);
                        System.out.println("Nombre: " + campos[1]);
                        System.out.println("Apellido: " + campos[2]);
                        System.out.println(); // Separación en blanco entre registros.
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al mostrar los registros insertados. Por favor, inténtelo de nuevo más tarde.");
            e.printStackTrace();
        }
    }

    // Método para marcar un registro como borrado (Parte:3).
    public void marcarRegistroComoBorrado(String codigoBorrar) {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean encontrado = false;
            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 3 && campos[0].equals(codigoBorrar)) {
                    lineas.add("#" + linea); // Marcamos como borrado.
                    encontrado = true;
                    System.out.println("Registro marcado como borrado con éxito.");
                } else {
                    lineas.add(linea);
                }
            }

            if (!encontrado) {
                System.out.println("Registro no encontrado.");
            } else {
                try (PrintWriter writer = new PrintWriter(archivo)) {
                    for (String nuevaLinea : lineas) {
                        writer.println(nuevaLinea);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al marcar el registro como borrado. Por favor, inténtelo de nuevo más tarde.");
            e.printStackTrace();
        }
    }

    // Método para mostrar registros marcados como borrados (Parte:3).
    public void mostrarRegistrosMarcadosComoBorrados() {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            System.out.println("Registros marcados como borrados:");
            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 3 && campos[0].startsWith("#")) {
                    System.out.println("Código: " + campos[0].substring(1));
                    System.out.println("Nombre: " + campos[1]);
                    System.out.println("Apellido: " + campos[2]);
                    System.out.println();
                }
            }
        } catch (IOException e) {
            System.err.println("Error al acceder o leer el archivo. Inténtelo de nuevo más tarde.");
            e.printStackTrace();
        } catch (NullPointerException eX) {
            System.err.println("Error interno al mostrar registros marcados como borrados. Inténtelo más tarde.");
            eX.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error en el formato de registros marcados como borrados. Verifique el archivo o inténtelo más tarde.");
            e.printStackTrace();
        }
    }

    // Método para compactar el archivo eliminando registros marcados como borrados (Parte:4).
    public void compactarArchivo() {
        File archivoTemporal = new File(archivo.getParent(), "registro_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo));
             BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTemporal))) {

            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().startsWith("#")) {
                    writer.write(linea);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error al compactar el archivo. Por favor, inténtelo de nuevo más tarde.");
            e.printStackTrace();
            return; // Salir del método si ocurre una excepción.
        }

        // Renombrar el archivo original a "registro.bak".
        File archivoBak = new File(archivo.getParent(), "registro.bak");
        try {
            archivo.renameTo(archivoBak);
        } catch (SecurityException e) {
            System.err.println("Error al renombrar el archivo original. Por favor, inténtelo de nuevo más tarde.");
            e.printStackTrace();
            return; // Salir del método si ocurre una excepción.
        }

        // Renombrar el archivo temporal a "registro.txt".
        try {
            archivoTemporal.renameTo(archivo);
        } catch (SecurityException e) {
            System.err.println("Error al renombrar el archivo temporal. Por favor, inténtelo de nuevo más tarde.");
            e.printStackTrace();
            return; // Salir del método si ocurre una excepción.
        }

        System.out.println("Archivo compactado exitosamente.");
    }
}
