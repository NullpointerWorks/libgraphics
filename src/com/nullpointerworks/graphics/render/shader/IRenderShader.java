/*
 * This is free and unencumbered software released into the public domain.
 * (http://unlicense.org/)
 * Nullpointer Works (2021)
 */
package com.nullpointerworks.graphics.render.shader;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.graphics.render.ITransformation;

/**
 * 
 * @since 1.0.0
 * @author Michiel Drost - Nullpointer Works
 */
public interface IRenderShader 
{
	/**
	 * 
	 * @param a
	 * @param dr
	 * @param img
	 * @param s
	 * @since 1.0.0
	 */
	void plot(float a, ITransformation dr, IntBuffer img, IntBuffer s);
}
