package gregpearce.archivorg.di.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/**
 * Scoping annotation to specify that the scope of an object is tied to the Controller scope.
 * Any "Singletons" annotated with this will conform to the controller scope/lifetime.
 */
@Scope @Retention(RetentionPolicy.RUNTIME) public @interface ControllerScope {
}
