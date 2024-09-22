package com.careri78.stores.cqrs;

final class CqrsRequestHandlerMetadata {
    private final Class<?> handlerClass;
    private final Class<?> queryClass;
    private final Class<?> returnClass;

    private CqrsRequestHandlerMetadata(Class<?> handlerClass, Class<?> queryClass, Class<?> returnClass) {
        this.handlerClass = handlerClass;
        this.queryClass = queryClass;
        this.returnClass = returnClass;
    }

    public Class<?> getHandlerClass() {
        return handlerClass;
    }

    public Class<?> getQueryClass() {
        return queryClass;
    }


    public Class<?> getReturnClass() {
        return returnClass;
    }

    public static CqrsRequestHandlerMetadata createFromHandlerClass(Class<?> requestHandlerClass) {
        Class<?>[] interfaces = requestHandlerClass.getInterfaces();
        throw new UnsupportedOperationException("Unimplemented method 'addHandler'");
        // return new CqrsRequestHandlerMetadata(requestHandlerClass);
    }

    public static <T, TRequest extends ValueRequest<T>, TRequestHandler extends ValueRequestHandler<TRequest, T>> CqrsRequestHandlerMetadata create(TRequestHandler requestHandler) {
        return createFromHandlerClass(requestHandler.getClass());
    }
}
