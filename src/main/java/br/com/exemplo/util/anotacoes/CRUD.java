package br.com.exemplo.util.anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author William
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.FIELD})
public @interface CRUD {
    public String label() default "";
    public boolean obrigatorio() default true;
    public boolean aceitaNegativo() default true;
    public boolean visualizavel() default true;
    public boolean pesquisavel() default true;
}
