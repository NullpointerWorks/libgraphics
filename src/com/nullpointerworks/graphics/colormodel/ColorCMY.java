package com.nullpointerworks.graphics.colormodel;

/**
 * Cyan - Magenta - Yellow
 */
public class ColorCMY 
{
	public ColorCMY(){}
	public ColorCMY(int c, int m, int y)
	{
		C=c;M=m;Y=y;
	}
	public int C = 0;
	public int M = 0;
	public int Y = 0;
	
	@Override
	public String toString()
	{
		return "CMYK = "+C+" - "+M+" - "+Y;
	}
}
