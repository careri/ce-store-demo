package com.careri78.cqrs.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Class Info
 * 
 * @author Carl Ericsson
 * 
 */
public final class CqrsRequestHandlerMetadata {

    public static CqrsRequestHandlerMetadata createFromHandlerClass(
            final Class<? extends ValueRequestHandlerBase> requestHandlerClass) {
        final HandlerTypeInfo typeInfo = getHandlerInterface(requestHandlerClass);
        return new CqrsRequestHandlerMetadata(requestHandlerClass, typeInfo.getRequestType(), typeInfo.getReturnType());
    }

    public static <T, TRequest extends ValueRequest<T>, TRequestHandler extends ValueRequestHandler<TRequest, T>> CqrsRequestHandlerMetadata create(
            final TRequestHandler requestHandler) {
        return createFromHandlerClass(requestHandler.getClass());
    }

    private static HandlerTypeInfo getHandlerInterface(final Class<?> requestHandlerClass) {
        // TODO: Build a tree of the type structure and keep track of the resolved / unresolved type arguments
        final String valueName = ValueRequestHandler.class.getName();
        final String name = RequestHandler.class.getName();
        final Queue<Type> queue = new LinkedList<>();
        addAllTypes(queue, requestHandlerClass);
        while (queue.size() > 0) {
            final Type t = queue.poll();
            if (t == null) {
                continue;
            } else if (t instanceof Class<?>) {
                addAllTypes(queue, (Class<?>) t);
            } else if (t instanceof ParameterizedType) {
                final ParameterizedType p = (ParameterizedType) t;
                if (p.getRawType().getTypeName().equals(name)) {
                    return new HandlerTypeInfo(p, true);
                } else if (p.getRawType().getTypeName().equals(valueName)) {
                    return new HandlerTypeInfo(p, false);
                }
            }
        }

        throw new UnsupportedOperationException(
                String.format("%s doesn't implement %s", requestHandlerClass.getName(), valueName));
    }

    private static void addAllTypes(final Queue<Type> queue, final Class<?> cls) {
        addInterface(queue, cls.getGenericInterfaces());
        addInterface(queue, cls.getInterfaces());
        queue.offer(cls.getSuperclass());
        queue.offer(cls.getGenericSuperclass());
    }

    private static void addInterface(final Queue<Type> queue, final Type[] types) {
        for (int i = 0; i < types.length; i++) {
            queue.offer(types[i]);
        }
    }

    static Class<?> getTypeParameterClass(final Type variable) {
        if (variable instanceof Class<?>) {
            return (Class<?>) variable;
        } else if (variable instanceof ParameterizedType) {
            final ParameterizedType p = (ParameterizedType) variable;
            final Type rawType = p.getRawType();
            return getTypeParameterClass(rawType);
        }

        throw new UnsupportedOperationException(
                String.format("Failed to locate type parameter %s", variable.getTypeName()));
    }

    private final Class<? extends ValueRequestHandlerBase> handlerClass;

    private final Class<?> queryClass;

    private final Class<?> returnClass;

    private CqrsRequestHandlerMetadata(final Class<? extends ValueRequestHandlerBase> handlerClass,
            final Class<?> queryClass,
            final Class<?> returnClass) {
        this.handlerClass = handlerClass;
        this.queryClass = queryClass;
        this.returnClass = returnClass;
    }

    public Class<? extends ValueRequestHandlerBase> getHandlerClass() {
        return handlerClass;
    }

    public Class<?> getQueryClass() {
        return queryClass;
    }

    public Class<?> getReturnClass() {
        return returnClass;
    }
}
