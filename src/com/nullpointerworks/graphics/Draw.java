/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2021)
 * Using this library makes you subject to the license terms.
 * 
 * https://creativecommons.org/licenses/by-sa/4.0/
 */
package com.nullpointerworks.graphics;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.math.geometry.g2d.Geometry2D;
import com.nullpointerworks.math.geometry.g2d.Rectangle;

/**
 * 
 * @since 1.0.0
 * @author Michiel Drost - Nullpointer Works
 */
public class Draw 
{
	/**
	 * 
	 * @since 1.0.0
	 */
	public static void geometry(Geometry2D g2d, int c, IntBuffer s)
	{
		Rectangle bb = g2d.getBoundingBox();
		float fx = bb.x;
		float fy = bb.y;
		float fw = fx + bb.w;
		float fh = fy + bb.h;
		for (; fy<fh; fy+=1f)
		{
			fx = bb.x;
			for (; fx<fw; fx+=1f)
			{
				if ( g2d.isInside(fx, fy) )
				{
					s.plot( (int)(fx+0.5f), (int)(fy+0.5f), c);
				}
			}
		}
	}
	
	/**
	 * 
	 * @since 1.0.0
	 */
	public static void image(float px, float py, IntBuffer source, IntBuffer screen)
	{
		int w = source.getWidth();
		int h = source.getHeight();
		int wmax = screen.getWidth();
		int hmax = screen.getHeight();
		int x = (int)(px+0.5f);
		int y = (int)(py+0.5f);
		
		// clip negatives
		int xoff = (x<0)?-x:0;
		int yoff = (y<0)?-y:0;
		w = ((w+x) >= wmax)? -x+wmax :w;
		h = ((h+y) >= hmax)? -y+hmax :h;
		
		// draw
		for (int j=yoff,k=h; j<k; j++)
		for (int i=xoff,l=w; i<l; i++)
		{
			int c = source.grab(i, j);
			screen.plot(i+x, j+y, c);
		}
	}
	
	/**
	 * draw ellipse around the center (xc,yc)
	 * @since 1.0.0
	 */
	public static void ellipse(int xc, int yc, int width, int height, int color, IntBuffer srf)
	{
	    int a2 = width * width;
	    int b2 = height * height;
	    int fa2 = a2<<2, fb2 = b2<<2;
	    int incX, incY, x, y, sigma;
	    
	    /* 
	     * top poles 
	     */
	    incX = 0;
	    incY = a2*height;
	    sigma = a2-((a2*height-b2)<<1);
	    for (x = 0, y = height; incX <= incY; x++)
	    {
	    	srf.plot(xc + x, yc + y, color);
	    	srf.plot(xc - x, yc + y, color);
	    	srf.plot(xc + x, yc - y, color);
	    	srf.plot(xc - x, yc - y, color);
	        if (sigma >= 0)
	        {
	            sigma += fa2 * (1 - y);
	            y--;
	            incY -= a2;
	        }
	        sigma += b2 * ((x<<2) + 6);
	        incX += b2;
	    }
	    
	    /*
	     * sides
	     */
	    incX = b2*width;
	    incY = 0;
	    sigma = b2-((b2*width-a2)<<1);
	    for (x = width, y = 0; incY <= incX; y++)
	    {
	    	srf.plot(xc + x, yc + y, color);
	        srf.plot(xc - x, yc + y, color);
	        srf.plot(xc + x, yc - y, color);
	        srf.plot(xc - x, yc - y, color);
	        if (sigma >= 0)
	        {
	            sigma += fb2 * (1 - x);
	            x--;
	            incX -= b2;
	        }
	        sigma += a2 * ((y<<2) + 6);
	        incY += a2;
	    }
	}
	
	/**
	 * Bresenham circle algorithm
	 * @since 1.0.0
	 */
	public static void circle(int cx, int cy, int r, int c, IntBuffer screen)
	{
		int x,y,xC,yC,radErr;
		
		x = r;
		y = 0;
		xC = 1-(r+r);
		yC = 1;
		radErr = 0;
		
		while( x>= y)
		{
			screen.plot(cx+x, cy+y, c);
			screen.plot(cx-x, cy+y, c);
			screen.plot(cx-x, cy-y, c);
			screen.plot(cx+x, cy-y, c);
			screen.plot(cx+y, cy+x, c);
			screen.plot(cx-y, cy+x, c);
			screen.plot(cx-y, cy-x, c);
			screen.plot(cx+y, cy-x, c);
			
			y++;
			radErr += yC;
			yC += 2;
			
			if ( (radErr+radErr+xC) > 0 )
			{
				x--;
				radErr += xC;
				xC += 2;
			}
		}
	}
	
	/**
	 * Bresenham line primitive
	 * @since 1.0.0
	 */
	public static void line(int x1,int y1,int x2,int y2, int c, IntBuffer screen)
	{
		int dx = x2 - x1;
		int dy = y2 - y1;
		dx = (dx < 0)? -dx : dx;
		dy = (dy < 0)? -dy : dy;
		int sx = (x1 < x2)? 1 : -1;
		int sy = (y1 < y2)? 1 : -1;
		int err = dx - dy;
		int x = x1, y = y1;
		int[] px = screen.content();
		int w = screen.getWidth();
		int h = screen.getHeight();
		
		while (true) 
		{
			// clipping
			if (x >= 0)
			if (y >= 0)
			if (x < w)
			if (y < h)
			{
				px[x + y*w] = c;
			}
			
		    if (x == x2)
		    if (y == y2)
		        break;
		    
		    int e2 = err << 1;
		    
		    if (e2 > -dy) 
		    {
		        err = err - dy;
		        x += sx;
		    }
		    
		    if (e2 < dx) 
		    {
		        err = err + dx;
		        y += sy;
		    }
		}
	}
	
	/**
	 * draw a rectangle to a screen of the given color c
	 * @since 1.0.0
	 */
	public static void rect(int x, int y, int w, int h, 
					 int c, IntBuffer screen)
	{
		line(  x,   y, x+w,   y, c, screen);
		line(x+w,   y, x+w, y+h, c, screen);
		line(x+w, y+h, x,   y+h, c, screen);
		line(x,   y+h, x,     y, c, screen);
	}
	
	/**
	 * a triangle
	 * @since 1.0.0
	 */
	public static void triangle(int x1, int y1,
						 int x2, int y2,
						 int x3, int y3, 
						 int c, IntBuffer screen)
	{
		line(x1,y1, x2,y2, c, screen);
		line(x2,y2, x3,y3, c, screen);
		line(x3,y3, x1,y1, c, screen);
	}
}
