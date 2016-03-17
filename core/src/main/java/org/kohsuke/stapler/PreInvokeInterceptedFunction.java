package org.kohsuke.stapler;

import org.kohsuke.stapler.interceptor.Interceptor;
import org.kohsuke.stapler.interceptor.InterceptorAnnotation;
import org.kohsuke.stapler.interceptor.Stage;

import javax.servlet.ServletException;
import java.lang.reflect.InvocationTargetException;

/**
 * Function that's wrapped by {@link Interceptor} for {@link Stage#PREINVOKE}
 *
 * @see InterceptorAnnotation
 */
final class PreInvokeInterceptedFunction extends ForwardingFunction {
    private final Interceptor interceptor;

    /*package*/ PreInvokeInterceptedFunction(Function next, Interceptor i) {
        super(next);
        this.interceptor = i;
        interceptor.setTarget(next);
    }

    @Override
    public Object invoke(StaplerRequest req, StaplerResponse rsp, Object o, Object... args) throws IllegalAccessException, InvocationTargetException, ServletException {
        return interceptor.invoke(req, rsp, o, args);
    }
}