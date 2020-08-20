/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2020)
 * Using this library makes you subject to the license terms.
 * 
 * https://creativecommons.org/licenses/by-sa/4.0/
 */
package com.nullpointerworks.graphics;

import com.nullpointerworks.graphics.render.Rasterizer;
import com.nullpointerworks.graphics.render.SimpleRasterizer;

/**
 * 
 * @since 1.0.0
 * @author Michiel Drost - Nullpointer Works
 */
public class Render 
{
	/**
	 * 
	 * @return
	 * @since 1.0.0
	 */
	public static Rasterizer getRasterizer()
	{
		return new SimpleRasterizer();
	}
	
	
}
