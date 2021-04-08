/*
 * This is free and unencumbered software released into the public domain.
 * (http://unlicense.org/)
 * Nullpointer Works (2021)
 */
package com.nullpointerworks.graphics.render;

import com.nullpointerworks.core.buffer.IntBuffer;

/**
 * @since 1.0.0
 * @author Michiel Drost - Nullpointer Works
 */
public interface IRasterizer 
{
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @since 1.0.0
	 */
	void translate(float x, float y);
	
	/**
	 * 
	 * @param a
	 * @since 1.0.0
	 */
	void rotate(float a);
	
	/**
	 * 
	 * @param sw
	 * @param sh
	 * @since 1.0.0
	 */
	void scale(float sw, float sh);
	
	/**
	 * 
	 * @param acc
	 * @since 1.0.0
	 */
	void accuracy(float acc);
	
	/**
	 * 
	 * @param image
	 * @param surface
	 * @since 1.0.0
	 */
	void plot(IntBuffer image, IntBuffer surface);
	
}
