/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2021)
 * Using this library makes you subject to the license terms.
 * 
 * https://creativecommons.org/licenses/by-sa/4.0/
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
