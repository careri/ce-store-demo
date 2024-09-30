package com.careri78.cqrs.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

final class HandlerTypeInfo {
    private final Class<?> requestType;
    private final Class<?> returnType;

    public HandlerTypeInfo(final ParameterizedType type, final Boolean isNoValue) {
        if (isNoValue) {
            final Type[] typeParameters = type.getActualTypeArguments();
            if (typeParameters.length != 1) {
                throw new UnsupportedOperationException(
                        String.format("%s should have one type parameters", type.getTypeName()));
            }
            this.requestType = CqrsRequestHandlerMetadata.getTypeParameterClass(typeParameters[0]);
            this.returnType = Void.class;
        } else {
            final Type[] typeParameters = type.getActualTypeArguments();
            if (typeParameters.length != 2) {
                throw new UnsupportedOperationException(
                        String.format("%s should have two type parameters", type.getTypeName()));
            }
            this.requestType = CqrsRequestHandlerMetadata.getTypeParameterClass(typeParameters[0]);
            this.returnType = CqrsRequestHandlerMetadata.getTypeParameterClass(typeParameters[1]);
        }
    }

    public Class<?> getRequestType() {
        return requestType;
    }

    public Class<?> getReturnType() {
        return returnType;
    }
}