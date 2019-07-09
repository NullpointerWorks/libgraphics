package com.nullpointerworks.graphics.colormodel;

/**
 * Cyan - Magenta - Yellow - Key
 */
public class ColorCMYK 
{
	public ColorCMYK(){}
	public ColorCMYK(float c, float m, float y, float k)
	{
		C=c;M=m;Y=y;K=k;
	}
	public float C = 0;
	public float M = 0;
	public float Y = 0;
	public float K = 0;
	
	@Override
	public String toString()
	{
		return "CMYK = "+C+" - "+M+" - "+Y+" - "+K;
	}
}
