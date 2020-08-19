/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2019)
 * Use is subject to license terms.
 */
package com.nullpointerworks.graphics.render.shader;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.graphics.render.PlotBuffer;
import com.nullpointerworks.math.geometry.g2d.Geometry2D;

/**
 * 
 * @since 1.0.0
 */
public class Rasterizer
{
	/**
	 * 
	 * @since 1.0.0
	 */
	public void run(PlotBuffer dr, IntBuffer s) 
	{
		Geometry2D geom		= dr.geom;
		IntBuffer source 	= dr.image;
		float[][] matrix 	= dr.transform;
		float a 			= dr.accuracy;
		
		int[] screenPX 		= s.content();
		int DEST_W 			= s.getWidth();
		int DEST_H 			= s.getHeight();
		
		int[] sourcePX 	= source.content();
		int SOURCE_W 	= source.getWidth();
		int SOURCE_H 	= source.getHeight();
		
		float startx 	= dr.x;
		float endx 		= dr.w + dr.x;
		float starty 	= dr.y;
		float endy 		= dr.h + dr.y;

		// screen edge clipping
		startx = (startx < 0f)?0f: startx;
		starty = (starty < 0f)?0f: starty;
		endx = (endx >= DEST_W)? DEST_W-1: endx;
		endy = (endy >= DEST_H)? DEST_H-1: endy;
		
		for (float j=starty-1f, k=endy; j<k; j+=a)
		{
			for (float i=startx-1f, l=endx; i<l; i+=a)
			{				
				float[] v = {i,j};
				transform(matrix, v);
				
				if (v[0] < 0f) continue;
				int x = (int)(v[0]);
				if (x >= SOURCE_W) continue;
				
				if (v[1] < 0f) continue;
				int y = (int)(v[1]);
				if (y >= SOURCE_H) continue;
				
				if (!geom.isInside(v[0]+startx, v[1]+starty)) continue;
				
				int color = sourcePX[x + y*SOURCE_W];
				int alpha = (color>>24) & 0xFF;
				if (alpha == 0) continue;
				
				int plotx = (int)(i);
				int ploty = (int)(j);
				int STRIDE = plotx + ploty*DEST_W;
				
				if (alpha < 255) 
				{
					int screenCol = screenPX[STRIDE];
					color = lerp256(screenCol, color, alpha+1);
				}
				screenPX[STRIDE] = color;
			}
		}
	}

	/**
	 * 
	 * @since 1.0.0
	 */
	protected final void transform(float[][] m, float[] v)
	{
		float vx,vy; float[] mp = m[0];
		vx = mp[0]*v[0] + mp[1]*v[1] + mp[2]; mp = m[1];
		vy = mp[0]*v[0] + mp[1]*v[1] + mp[2]; v[0] = vx; v[1] = vy;
	}

	/**
	 * Integer interpolation of ARGB 32-bit colors
	 * @since 1.0.0
	 */
	protected int lerp256(int c1, int c2, int lerp) 
	{
		int ag1 = c1 & 0xFF00FF00;
		int ag2 = c2 & 0xFF00FF00;
		int rb1 = c1 & 0x00FF00FF;
		int rb2 = c2 & 0x00FF00FF;
		int l 	= (256-lerp);
		ag1 = ag1*l + ag2*lerp;
		rb1 = rb1*l + rb2*lerp;
		ag1 = ag1>>8;
		rb1 = rb1>>8;
	    return (ag1 & 0xFF00FF00) + (rb1 & 0x00FF00FF);
	}
}
