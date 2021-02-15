/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2021)
 * Using this library makes you subject to the license terms.
 * 
 * https://creativecommons.org/licenses/by-sa/4.0/
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
