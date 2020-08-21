/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2020)
 * Using this library makes you subject to the license terms.
 */
package com.nullpointerworks.graphics.render.shader;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.graphics.render.RenderTransform;

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
	void plot(float a, RenderTransform dr, IntBuffer img, IntBuffer s);
}
