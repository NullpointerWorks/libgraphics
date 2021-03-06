/*
 * This is free and unencumbered software released into the public domain.
 * (http://unlicense.org/)
 * Nullpointer Works (2021)
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
