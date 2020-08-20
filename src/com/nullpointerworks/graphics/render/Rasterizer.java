/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2020)
 * Using this library makes you subject to the license terms.
 */
package com.nullpointerworks.graphics.render;

import com.nullpointerworks.core.buffer.IntBuffer;

/**
 * @since 1.0.0
 * @author Michiel Drost - Nullpointer Works
 */
public interface Rasterizer 
{
	void translate(float x, float y);
	void rotate(float a);
	void scale(float sw, float sh);
	void accuracy(float acc);
	void plot(IntBuffer source, IntBuffer surface);
}
