/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2020)
 * Using this library makes you subject to the license terms.
 * 
 * https://creativecommons.org/licenses/by-sa/4.0/
 */
package com.nullpointerworks.graphics;

import com.nullpointerworks.graphics.render.Rasterizer;
import com.nullpointerworks.graphics.render.shader.RenderSampling;
import com.nullpointerworks.graphics.render.shader.RenderAreaMap;
import com.nullpointerworks.graphics.render.shader.Transform;

/**
 * 
 * @since 1.0.0
 * @author Michiel Drost - Nullpointer Works
 */
public class Render 
{
	/**
	 * 
	 * @return q - 
	 * @since 1.0.0
	 */
	public static Rasterizer getRasterizer(RenderMethod q)
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
