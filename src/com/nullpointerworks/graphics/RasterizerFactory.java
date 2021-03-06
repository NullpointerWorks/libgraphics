/*
 * This is free and unencumbered software released into the public domain.
 * (http://unlicense.org/)
 * Nullpointer Works (2021)
 */
package com.nullpointerworks.graphics;

import com.nullpointerworks.graphics.render.IRasterizer;
import com.nullpointerworks.graphics.render.Rasterizer;
import com.nullpointerworks.graphics.render.shader.RenderSampling;
import com.nullpointerworks.graphics.render.shader.RenderAreaMap;
import com.nullpointerworks.graphics.render.shader.Transform;

/**
 * Simple factory for creating rasterizers.
 * @since 1.0.0
 * @author Michiel Drost - Nullpointer Works
 */
public class RasterizerFactory 
{
	/**
	 * 
	 * @return q - 
	 * @since 1.0.0
	 */
	public IRasterizer getRasterizer(RenderMethod q)
	{
		switch(q)
		{
		default:
		case SAMPLING:
			return new Rasterizer(new Transform(), new RenderSampling() );
			
		case AREA_MAP:
			return new Rasterizer(new Transform(), new RenderAreaMap() );
		}
	}
}
