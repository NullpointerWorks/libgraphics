/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2019)
 * Use is subject to license terms.
 */
package com.nullpointerworks.graphics.render;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.math.geometry.g2d.Geometry2D;
import com.nullpointerworks.math.geometry.g2d.Rectangle;

/**
 * 
 * @since 1.0.0
 */
public class PlotBuffer 
{
	/**
	 * 
	 * @since 1.0.0
	 */
	public IntBuffer image;
	
	/**
	 * 
	 * @since 1.0.0
	 */
	public Geometry2D geom;
	
	/**
	 * The rendering accuracy for this sprite. Set to {@code 1.0} by default.
	 * @since 1.0.0
	 */
	public float accuracy 	= 1f;
	
	/**
	 * 
	 * @since 1.0.0
	 */
	public float[][] transform;
	
	/**
	 * 
	 * @since 1.0.0
	 */
	public Rectangle aabb 	= new Rectangle(0f,0f,0f,0f);
	
	/**
	 * 
	 * @since 1.0.0
	 */
	public void free() 
	{
		image = null;
		aabb = null;
		geom = null;
		transform = null;
	}
}
