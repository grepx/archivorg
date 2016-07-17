package gregpearce.archivorg.di.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 *  Scoping annotation to specify that the scope of an object is tied to the Activity scope.
 *  Any "Singletons" annotated with this will conform to the activity scope/lifetime.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
