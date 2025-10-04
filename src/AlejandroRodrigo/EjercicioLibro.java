package AlejandroRodrigo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class EjercicioLibro {

    public static int num_procesos = 5;
    public static int[] lines_init = {0,721,1443,2164,2885,3609};
    public static String[] list_procesos = {"mayus","minus","paco","minus","mayus"};
    public static String direccion_proceso = "AlejandroRodrigo."; //luego aquí +Transformador
    public static File salida = new File("salida.txt");

    public static List<String> lecturaArchivo(int line_init, int line_final) throws IOException {
        Path path = Paths.get("../../../src/alice29.txt").toAbsolutePath();
        List<String> totalLineas = Files.readAllLines(path);

        return totalLineas.subList(line_init,line_final+1);
    }

    public static String seleccion_proceso(String proceso){
        switch (proceso){
            case "minus" -> {return "Minusculas";}
            case "mayus" -> {return "Mayusculas";}
            case "paco" -> {return "Reemplazador";}
            default -> {return null;}
        }
    }

    public static void lanzadorProcesos(Integer line_init, Integer line_final, String programa) throws InterruptedException, IOException {
        String proceso = seleccion_proceso(programa);
        ProcessBuilder pb = new ProcessBuilder("java", direccion_proceso + proceso, line_init.toString(), line_final.toString());
        pb.directory(new File("./out/production/ProyectoLibro"));
        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(salida));
        Process p = pb.start();
        p.waitFor();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (salida.exists()) salida.delete();
        //Thread.sleep(2000);
        for (int i = 0; i < num_procesos; i++){
            lanzadorProcesos(lines_init[i],lines_init[i+1]-1,list_procesos[i]);
        }
    }
}
