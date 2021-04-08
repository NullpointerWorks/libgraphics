/*
 * This is free and unencumbered software released into the public domain.
 * (http://unlicense.org/)
 * Nullpointer Works (2021)
 */
package com.nullpointerworks.graphics.render.shader;

import com.nullpointerworks.graphics.render.ITransformation;

/**
 * 
 * @since 1.0.0
 * @author Michiel Drost - Nullpointer Works
 */
class TransformData implements ITransformation
{
	/**
	 * 
	 * @since 1.0.0
	 */
	public float[][] transform;
	
	/**
	 * 
	 * @since 1.0.0
	 */
	public float x,y,w,h;
}
