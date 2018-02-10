package com.me.travelapp.javafiles;

/**
 * Created by HP-USER on 10-02-2018.
 */


public class IncompatibleRatioException extends RuntimeException {

    private static final long serialVersionUID = 234608108593115395L;

    public IncompatibleRatioException() {
        super("Can't perform Ken Burns effect on rects with distinct aspect ratios!");
    }
}