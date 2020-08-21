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
				
				UL = sourcePX[x + y*SOURCE_W];
	            if (x+1 > 0)
				if (x+1 < SOURCE_W)
					UR = sourcePX[x + 1 + y*SOURCE_W];
	            
				if (y+1 > 0)
				if (y+1 < SOURCE_H) 
				{
					LL = sourcePX[x + (y+1)*SOURCE_W];
		            
		            if (x+1 > 0)
					if (x+1 < SOURCE_W)
						LR = sourcePX[x + 1 + (y+1)*SOURCE_W];
	            }
				
				/*
	             * interpolate colors
	             * make sure the lerp values are within bounds
	             */
	            int subX = (int)(256f * (v[0]-x));
	            int subY = (int)(256f * (v[1]-y));
	            subX = clamp(0,subX,256);
	            subY = clamp(0,subY,256);
	            
	            /*
	             * blend the area mapping
	             */
	            int colU = lerp256(UL,UR, subX);
	            int colL = lerp256(LL,LR, subX);
                int color = lerp256(colU, colL, subY);
				
                /*
                 * blend transparency with destination surface
                 */
				int alpha = (color>>24) & 0xFF;
				if (alpha == 0) continue;
				if (alpha < 255) color = lerp256(screenCol, color, alpha+1);
				
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
	
	private int lerp256(int c1, int c2, int lerp)
	{
		int a = (c1>>24)& 0xFF;
		int r = (c1>>16)& 0xFF;
		int g = (c1>>8) & 0xFF;
		int b = (c1) 	& 0xFF;

		int a2 = (c2>>24)& 0xFF;
		int r2 = (c2>>16)& 0xFF;
		int g2 = (c2>>8) & 0xFF;
		int b2 = (c2) 	 & 0xFF;
		
		int l = (256-lerp);
		a = (a*l + a2*lerp) >> 8;
		r = (r*l + r2*lerp) >> 8;
		g = (g*l + g2*lerp) >> 8;
		b = (b*l + b2*lerp) >> 8;
		
		return toInt(a,r,g,b);
	}
	
	private int toInt(int a,int r,int g,int b)
	{
		return ( (a<<24) | r<<16 | g<<8 | b );
	}
}
