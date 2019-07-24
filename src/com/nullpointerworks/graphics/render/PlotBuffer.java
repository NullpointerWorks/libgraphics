package com.nullpointerworks.graphics.render;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.math.geometry.g2d.Geometry2D;
import com.nullpointerworks.math.geometry.g2d.NullGeometry;
import com.nullpointerworks.math.geometry.g2d.Rectangle;

public class PlotBuffer 
{
	public IntBuffer image;
	public Geometry2D geom 	= new NullGeometry();
	public float[][] mTransform;
	public Rectangle aabb 	= new Rectangle(0f,0f,0f,0f);
	
	public void free() 
	{
		image = null;
		aabb = null;
		geom = null;
		mTransform = null;
	}
}
