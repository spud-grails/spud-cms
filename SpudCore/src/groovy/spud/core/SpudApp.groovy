package spud.core

import java.lang.annotation.ElementType
import java.lang.annotation.Target
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention

// This annotation allows role level restrictions to be applied in a decoupled manner, Check the spud.core.SecurityFilters and spud.core.SecurityService for more info
@Retention(RetentionPolicy.RUNTIME)
public @interface SpudApp {
	String name()
	String thumbnail()
	String subsection() default "false"
}
