/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2021)
 * Using this library makes you subject to the license terms.
 * 
 * https://creativecommons.org/licenses/by-sa/4.0/
 */
package com.nullpointerworks.graphics.render.shader;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.graphics.render.ITransformation;

public interface ITransform 
{
	/**
	 * 
	 * @param x
	 * @param y
	 * @param sw
	 * @param sh
	 * @param a
	 * @param img
	 * @return
	 * @since 1.0.0
	 */
	ITransformation transform(float x, float y, float sw, float sh, float a, IntBuffer img);
}
