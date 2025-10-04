package AlejandroRodrigo;

import java.util.List;

public interface TransformadorTexto {
    void transformador(List<String> texto);
    void introducirLineas(int linea_init, int linea_final);
}
