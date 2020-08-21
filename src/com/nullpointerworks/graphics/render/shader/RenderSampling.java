/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2019)
 * Using this library makes you subject to the license terms.
 */
package com.nullpointerworks.graphics.render.shader;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.graphics.render.RenderTransform;

/**
 * 
 * @since 1.0.0
 * @author Michiel Drost - Nullpointer Works
 */
public class RenderSampling implements IRenderShader
{
	@Override
	public void plot(float a, RenderTransform dr, IntBuffer img, IntBuffer s)
	{
		float[][] matrix 	= dr.transform;
		
		int[] screenPX 		= s.content();
		int DEST_W 			= s.getWidth();
		int DEST_H 			= s.getHeight();
		
		int[] sourcePX 	= img.content();
		int SOURCE_W 	= img.getWidth();
		int SOURCE_H 	= img.getHeight();
		
		float startx 	= dr.x;
		float endx 		= dr.w + dr.x;
		float starty 	= dr.y;
		float endy 		= dr.h + dr.y;

		// screen edge clipping
		startx = (startx < 0f)?0f: startx;
		starty = (starty < 0f)?0f: starty;
		endx = (endx >= DEST_W)? DEST_W-1: endx;
		endy = (endy >= DEST_H)? DEST_H-1: endy;
		
		for (float j=starty; j<endy; j+=a)
		{
			for (float i=startx; i<endx; i+=a)
			{				
				float[] v = {i,j};
				transform(matrix, v);
				
				if (signum(v[0])) continue;
				if (signum(v[1])) continue;
				
				int x = (int)(v[0]);
				if (x >= SOURCE_W) continue;
				int y = (int)(v[1]);
				if (y >= SOURCE_H) continue;
				
				int color = sourcePX[x + y*SOURCE_W];
				int alpha = (color>>24) & 0xFF;
				if (alpha == 0) continue;
				int STRIDE = (int)(i) + (int)(j)*DEST_W;
				
				if (alpha < 255) 
				{
					int screenCol = screenPX[STRIDE];
					color = lerp256(screenCol, color, alpha+1);
				}
				screenPX[STRIDE] = color;
			}
		}
	}
	
	/*
	 * might fail on NaN
	 */
	private final boolean signum(float f) 
	{
	    return (Float.floatToRawIntBits(f)>>>31) == 1;
	}

	private final void transform(float[][] m, float[] v)
	{
		float vx,vy; float[] mp = m[0];
		vx = mp[0]*v[0] + mp[1]*v[1] + mp[2]; mp = m[1];
		vy = mp[0]*v[0] + mp[1]*v[1] + mp[2]; v[0] = vx; v[1] = vy;
	}
	
	private final int lerp256(int c1, int c2, int lerp) 
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
