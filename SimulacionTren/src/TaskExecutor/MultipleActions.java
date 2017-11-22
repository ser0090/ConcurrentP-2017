package TaskExecutor;

import java.util.List;

/**
 * Created by Tincho on 22/11/2017.
 */
public class MultipleActions implements Accion{
    List<Accion> acciones;

    public MultipleActions(List<Accion> acciones) {
        this.acciones = acciones;
    }

    @Override
    public void ejecutar() {
        for (Accion action: acciones) {
            action.ejecutar();
        }
    }
}
