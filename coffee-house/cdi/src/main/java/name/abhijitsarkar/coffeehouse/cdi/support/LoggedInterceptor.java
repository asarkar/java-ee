/*
 * Copyright (c) 2014, the original author or authors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * A copy of the GNU General Public License accompanies this software,
 * and is also available at http://www.gnu.org/licenses.
 */

package name.abhijitsarkar.coffeehouse.cdi.support;

import name.abhijitsarkar.coffeehouse.support.LoggingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author Abhijit Sarkar
 */

@Logged
@Interceptor
public class LoggedInterceptor implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggedInterceptor.class);

    @AroundInvoke
    public Object logMethodEntry(InvocationContext invocationContext) throws Exception {
        Method method = invocationContext.getMethod();

        LOGGER.debug("Intercepted {} {}.{}({}).", method.getReturnType().getSimpleName(), method.getDeclaringClass(),
                method.getName(), method.getParameterTypes());

        Object[] args = invocationContext.getParameters();

        LoggingHelper.logArgs(LOGGER, args);

        return invocationContext.proceed();
    }
}
