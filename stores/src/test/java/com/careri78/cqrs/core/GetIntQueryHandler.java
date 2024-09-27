package com.careri78.cqrs.core;

import java.util.concurrent.CompletableFuture;

/**
* Class Info
* 
* @author Carl Ericsson
* 
*/
public final class GetIntQueryHandler implements ValueRequestHandler<GetIntQuery, Integer> {

    @Override
    public CompletableFuture<Integer> getAsync(GetIntQuery query) {
        CompletableFuture<Integer> future = CompletableFuture.completedFuture(query.getValue());
        return future;
    }

}
