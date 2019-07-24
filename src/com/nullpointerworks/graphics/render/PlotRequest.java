package com.nullpointerworks.graphics.render;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.math.geometry.g2d.Geometry2D;
import com.nullpointerworks.math.geometry.g2d.NullGeometry;

public class PlotRequest 
{
	/*
	 * input
	 */
	public IntBuffer image;
	public float x 			= 0f;
	public float y 			= 0f;
	public Float angle 		= null;
	public Float scale_w 	= null;
	public Float scale_h 	= null;
	public Geometry2D geom 	= new NullGeometry();
	
	/*
	 * dealloc
	 */
	public void free() 
	{
		image.free();
		image = null;
		angle = null;
		scale_w = null;
		scale_h = null;
		geom = null;
	}
}
