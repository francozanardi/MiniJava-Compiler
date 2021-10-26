package ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class Derivacion<SD> {
    private final List<Supplier<Estado<?, ?>>> lazyEstados;
    private final List<String> primeros;
    private Action<?, SD> returnAction;

    private Derivacion(List<String> primeros) {
        this.primeros = primeros;
        this.lazyEstados = new ArrayList<>();
    }

    public static Derivacion<Void> create(String ...primeros){
        return new Derivacion<>(Arrays.asList(primeros));
    }

    public static <T> Derivacion<T> create(Class<T> synthesizedClassOfDerivation, String ...primeros){
        return new Derivacion<>(Arrays.asList(primeros));
    }

    List<Supplier<Estado<?, ?>>> getLazyEstados() {
        return lazyEstados;
    }

    List<String> getPrimeros() {
        return primeros;
    }

    public Derivacion<SD> appendEstado(Supplier<Estado<?, ?>> lazyEstado){
        lazyEstados.add(lazyEstado);

        return this;
    }

    public <I, S> Derivacion<SD> appendEstadoWithAction(Supplier<Estado<I, S>> lazyEstado, Action<I, S> action){
        lazyEstados.add(() -> {
            Estado<I, S> estado = lazyEstado.get();
            estado.setAction(action);

            return estado;
        });

        return this;
    }

//    public Derivacion appendAction(Supplier<Estado<?>> lazyAction){
//        lazyEstados.add(lazyAction);
//
//        return this;
//    }
//
//    public <E> Derivacion appendEstadoAfter(LazyEstadoWithInheritedAttribute<E> lazyEstado, ActionWithInheritedAttribute<E> action){
//        lazyEstados.add(() -> {
//            Estado estado = lazyEstado.get();
//            action.execute();
//
//            return estado;
//        });
//
//        return this;
//    }
//
//    public <E> Derivacion appendEstadoBefore(LazyEstadoWithSynthesizedAttribute<E> lazyEstado, ActionWithSynthesizedAttribute<E> action){
//        lazyEstados.add(() -> lazyEstado.execute(action.execute()));
//
//        return this;
//    }


    public Derivacion<SD> appendEstadoRecursivo(){
        lazyEstados.add(EstadoRecursivo::new);
        return this;
    }

    public Derivacion<SD> setReturnAction(Action<?, SD> action){
        this.returnAction = action;
        return this;
    }

    public Action<?, SD> getReturnAction(){
        return this.returnAction;
    }

    //TODO
    public Action<?, ?> getReturnAction_(){
        return lazyEstados.get(lazyEstados.size()-1).get().getAction();
    }

}
