package com.careri78.cqrs.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class CqrsRequestHandlerMetadata {
    private final Class<? extends ValueRequestHandlerBase> handlerClass;
    private final Class<?> queryClass;
    private final Class<?> returnClass;

    private CqrsRequestHandlerMetadata(Class<? extends ValueRequestHandlerBase> handlerClass, Class<?> queryClass, Class<?> returnClass) {
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

    public static CqrsRequestHandlerMetadata createFromHandlerClass(Class<? extends ValueRequestHandlerBase> requestHandlerClass) {
        ParameterizedType interfaceClass = getHandlerInterface(requestHandlerClass);
        Type[] typeParameters = interfaceClass.getActualTypeArguments();
        if (typeParameters.length != 2) {
            throw new UnsupportedOperationException(String.format("%s should have two type parameters", interfaceClass.getTypeName()));
        }
        Class<?> requestType = getTypeParameterClass(typeParameters[0]);
        Class<?> returnType = getTypeParameterClass(typeParameters[1]);
        return new CqrsRequestHandlerMetadata(requestHandlerClass, requestType, returnType);
    }

    public static <T, TRequest extends ValueRequest<T>, TRequestHandler extends ValueRequestHandler<TRequest, T>> CqrsRequestHandlerMetadata create(TRequestHandler requestHandler) {
        return createFromHandlerClass(requestHandler.getClass());
    }

    private static ParameterizedType getHandlerInterface(Class<?> requestHandlerClass) {
        String name = ValueRequestHandler.class.getName();
        Type[] interfaces = requestHandlerClass.getGenericInterfaces();
        for (Type t : interfaces) {
            if (t instanceof ParameterizedType ) {
                ParameterizedType p = (ParameterizedType)t;    
                if (p.getRawType().getTypeName().equals(name)) {
                    return p;
                }
            }
        }

        throw new UnsupportedOperationException(String.format("%s doesn't implement %s", requestHandlerClass.getName(), name));
    }

    private static Class<?> getTypeParameterClass(Type variable) {
        if (variable instanceof Class<?> ) {
            return (Class<?>)variable;
        } else if (variable instanceof ParameterizedType ) {
            ParameterizedType p = (ParameterizedType)variable;
            Type rawType = p.getRawType();
            return getTypeParameterClass(rawType);
        }

        throw new UnsupportedOperationException(String.format("%s must be a class", variable.getTypeName()));
    }
}
