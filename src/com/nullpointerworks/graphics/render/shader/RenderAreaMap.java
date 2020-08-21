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
public class RenderAreaMap implements IRenderShader
{
	@Override
	public void plot(float a, RenderTransform dr, IntBuffer img, IntBuffer s)
	{
		int[] screenPX 		= s.content();
		int DEST_W 			= s.getWidth();
		int DEST_H 			= s.getHeight();
		
		int[] sourcePX 	= img.content();
		int SOURCE_W 	= img.getWidth();
		int SOURCE_H 	= img.getHeight();

		float[][] matrix 	= dr.transform;
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
				
				/*
				 * get background color
				 */
				int STRIDE = (int)(i) + (int)(j)*DEST_W;
				int screenCol = screenPX[STRIDE];
				
				/*
				 * blending colors
				 */
				int UL,UR,LL,LR;
	            UL=UR=LL=LR = screenCol;
				
	            /*
	             * get the 4 pixel colors that overlap the destination pixel
	             */
	            if (y > 0)
				if (y < SOURCE_H)
				{
		            if (x > 0)
					if (x < SOURCE_W)
						UL = sourcePX[x + y*SOURCE_W];
		            
		            if (x+1 > 0)
					if (x+1 < SOURCE_W)
						UR = sourcePX[x + 1 + y*SOURCE_W];
				}
	            
				if (y+1 > 0)
				if (y+1 < SOURCE_H) 
				{
		            if (x > 0)
					if (x < SOURCE_W)
						LL = sourcePX[x + (y+1)*SOURCE_W];
		            
		            if (x+1 > 0)
					if (x+1 < SOURCE_W)
						LR = sourcePX[x + 1 + (y+1)*SOURCE_W];
	            }
				
				/*
	             * interpolate colors
	             * make sure the lerp values are within bounds
	             */
	            int subX = (int)(16f * (v[0]-i));
	            int subY = (int)(16f * (v[1]-j));
	            subX = clamp(0,subX,16);
	            subY = clamp(0,subY,16);
	            
	            /*
	             * blend the area mapping
	             */
	            int colU = intLerpARGB(UL,UR, subX);
	            int colL = intLerpARGB(LL,LR, subX);
                int color = intLerpARGB(colU, colL, subY);
				
                /*
                 * blend transparency with destination surface
                 */
				//int alpha = (color>>24) & 0xFF;
				//if (alpha == 0) continue;
				//if (alpha < 255) color = lerp256(screenCol, color, alpha+1);
				
				screenPX[STRIDE] = color;
			}
		}
	}
	
	/*
	 * clamp a given value between two values.
	 */
	public int clamp(int lower, int x, int upper)
	{
		int x1 = (x<lower)?lower:x;
		return (x1<upper)?x1:upper;
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
	
	public int intLerpARGB(int c1, int c2, int lerp)
	{
		int r = (c1>>16)& 0xFF;
		int g = (c1>>8) & 0xFF;
		int b = (c1) 	& 0xFF;
		
		int r2 = (c2>>16)& 0xFF;
		int g2 = (c2>>8) & 0xFF;
		int b2 = (c2) 	 & 0xFF;
		
		r = intLerp16(r, r2, lerp);
		g = intLerp16(g, g2, lerp);
		b = intLerp16(b, b2, lerp);
		
		return toInt(r,g,b);
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
	
	public int intLerp16(int A, int B, int F)
	{
		return (A*(16-F) + B*F) >> 4;
	}
	
	public int toInt(int r,int g,int b)
	{
		return ( (255<<24) | r<<16 | g<<8 | b );
	}
}
