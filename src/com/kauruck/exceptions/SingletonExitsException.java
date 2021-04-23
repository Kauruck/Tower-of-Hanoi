package com.kauruck.exceptions;

import org.jetbrains.annotations.NotNull;

public class SingletonExitsException extends Exception{

    public SingletonExitsException(@NotNull Class<?> exiting){
        super("Singleton of class " + exiting.getName() + " exits allready");
    }
}
