/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2021)
 * Using this library makes you subject to the license terms.
 * 
 * https://creativecommons.org/licenses/by-sa/4.0/
 */
package com.nullpointerworks.graphics.render;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.graphics.render.shader.IRenderShader;
import com.nullpointerworks.graphics.render.shader.ITransform;

/**
 * @since 1.0.0
 * @author Michiel Drost - Nullpointer Works
 */
public class Rasterizer implements IRasterizer
{
	private ITransform tr;
	private IRenderShader rs;
	private float x,y,sw,sh,a,acc;
	
	/**
	 * 
	 * @param tr
	 * @param rs
	 * @since 1.0.0
	 */
	public Rasterizer(ITransform tr, IRenderShader rs)
	{
		this.tr = tr;
		this.rs = rs;
		acc = 1f;
		x = 0f;
		y = 0f;
		sw = 1f;
		sh = 1f;
		a = 0f;
	}
	
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
	
	@Override
	public void accuracy(float acc)
	{
		if (acc < 0.0001f) acc = 0.0001f;
		if (acc > 1f) acc = 1f;
		this.acc=acc;
	}
	
	@Override
	public void plot(IntBuffer image, IntBuffer surface)
	{
		var ptr = tr.transform(x, y, sw, sh, a, image);
		rs.plot(acc, ptr, image, surface);
	}
}
