/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2019)
 * Use is subject to license terms.
 */
package com.nullpointerworks.graphics.render;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.math.geometry.g2d.Geometry2D;

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
	 * Most left location of the rendering box used in the {@code Rasterizer} shader.
	 * @since 1.0.0
	 */
	public float x = 0f;
	
	/**
	 * Most up location of the rendering box used in the {@code Rasterizer} shader.
	 * @since 1.0.0
	 */
	public float y = 0f;
	
	/**
	 * Total width of the rendering box used in the {@code Rasterizer} shader.
	 * @since 1.0.0
	 */
	public float w = 0f;
	
	/**
	 * Total height of the rendering box used in the {@code Rasterizer} shader.
	 * @since 1.0.0
	 */
	public float h = 0f;
	
	/**
	 * 
	 * @since 1.0.0
	 */
	public void free() 
	{
		image = null;
		geom = null;
		transform = null;
	}
}
