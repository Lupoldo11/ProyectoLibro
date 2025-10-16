package alejandrorodrigo;

import java.io.IOException;
import java.util.List;

public class Minusculas implements TransformadorTexto {
    static int line_final;
    static int line_init;
    int linea_inicial;
    int linea_final;

    public Minusculas(){}

    public void introducirLineas(int linea_init, int linea_final){
        this.linea_inicial=linea_init;
        this.linea_final=linea_final;
    }

    public static void main(String[] args) throws IOException {
        line_init = Integer.parseInt(args[0]);
        line_final = Integer.parseInt(args[1]);
        Minusculas proceso = new Minusculas();
        proceso.transformador(EjercicioLibro.lecturaArchivo(line_init, line_final));
    }

    @Override
    public void transformador(List<String> texto) {
        for (String line : texto) {
            System.out.println(line.toLowerCase());

        }
    }
}