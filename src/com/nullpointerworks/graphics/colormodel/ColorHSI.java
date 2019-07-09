package com.nullpointerworks.graphics.colormodel;

/**
 * hue - saturation - intensity
 */
public class ColorHSI 
{
	public ColorHSI() {}
	public ColorHSI(float h,float s,float i)
	{
		H=h; S=s; I=i;
	}
	/**
	 * hue = [0-360] radians 
	 */
	public float H = 0f;
	/**
	 * saturation [0-1] 
	 */
	public float S = 0f;
	/**
	 * intensity [0-1] 
	 */
	public float I = 0f;
	
	@Override
	public String toString()
	{
		return "HSI = "+H+" - "+S+" - "+I;
	}
}
