/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2020)
 * Using this library makes you subject to the license terms.
 */
package com.nullpointerworks.graphics.render;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.graphics.render.shader.SimpleRender;
import com.nullpointerworks.graphics.render.shader.Transform;

/**
 * @since 1.0.0
 * @author Michiel Drost - Nullpointer Works
 */
public class SimpleRasterizer implements Rasterizer
{
	private Transform tr;
	private SimpleRender sr;
	
	private float x,y,sw,sh,a,acc;
	
	@Override
	public void translate(float x, float y)
	{
		this.x=x;
		this.y=y;
	}
	
	@Override
	public void rotate(float a)
	{
		this.a=a;
	}
	
	@Override
	public void scale(float sw, float sh)
	{
		this.sw=sw;
		this.sh=sh;
	}
	
	public void accuracy(float acc)
	{
		this.acc=acc;
	}
	
	@Override
	public void plot(IntBuffer image, IntBuffer surface)
	{
		PlotTransform ptr = tr.run(x, y, sw, sh, a, image);
		sr.plot(acc, ptr, image, surface);
	}
}
