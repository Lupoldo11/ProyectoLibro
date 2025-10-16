package alejandrorodrigo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EjercicioLibro {

    public static int num_procesos = 5;
    public static int[] lines_init = {0,721,1443,2164,2885,3609};
    public static String[] list_procesos = {"mayus","minus","paco","minus","mayus"};
    public static String direccion_proceso = "AlejandroRodrigo.Transformador"; //luego aquí +Transformador
    public static File salida = new File("salida.txt");

    public static List<String> lecturaArchivo(int line_init, int line_final) throws IOException {
        Path path = Paths.get("../../../src/alice29.txt").toAbsolutePath();//"../../../src/alice29.txt" (normal url)
        List<String> totalLineas = Files.readAllLines(path); //src/alice29.txt (test_url)

        return totalLineas.subList(line_init,line_final+1);
    }

    public static Process lanzadorProcesos(Integer line_init, Integer line_final, String programa) throws InterruptedException, IOException {
        ProcessBuilder pb = new ProcessBuilder("java", direccion_proceso, line_init.toString(), line_final.toString(), programa);
        pb.directory(new File("./out/production/ProyectoLibro"));
        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(salida));
        return pb.start();
    }

    public static List<Process> lista_procesos = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        new File("salidaLibroAlicia.txt").delete();
        //Thread.sleep(2000);
        int i;
        for (i = 0; i < num_procesos; i++){
            lista_procesos.add(lanzadorProcesos(lines_init[i],lines_init[i+1]-1,list_procesos[i]));
        }
        escritura(i);
    }

    public static void escritura(int num_archivos) throws IOException, InterruptedException {
        for (Process p: lista_procesos){
            p.waitFor();
        }
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("salidaLibroAlicia.txt"))){
            for(int i = 0; i < num_archivos; i++){
                File tempFile = new File("salida"+i+".txt");
                try(BufferedReader reader = new BufferedReader(new FileReader(tempFile))){
                    String linea;
                    while ((linea = reader.readLine())!=null){
                        writer.write(linea);
                        writer.newLine();
                    }
                    reader.close();
                }
            }
            writer.close();
        }
        for(int i =0; i< num_archivos; i++){
            new File("salida"+i+".txt").delete();
        }
    }
}
