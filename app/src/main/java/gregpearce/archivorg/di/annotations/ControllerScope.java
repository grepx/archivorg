package gregpearce.archivorg.di.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/**
 * Scoping annotation to specify that the scope of an object is tied to the Controller scope.
 * Any "Singletons" annotated with this will conform to the scope/lifetime of the Controller
 * located
 * at the activity level of the router (i.e. it's not a child Controller).
 */
@Scope @Retention(RetentionPolicy.RUNTIME) public @interface ControllerScope {
}
