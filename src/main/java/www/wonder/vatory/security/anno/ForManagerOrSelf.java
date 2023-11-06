package www.wonder.vatory.security.anno;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@PreAuthorize("hasAnyAuthority('manager', 'admin') || #id.equals(Authentication.Principal.id)")
public @interface ForManagerOrSelf {
}
