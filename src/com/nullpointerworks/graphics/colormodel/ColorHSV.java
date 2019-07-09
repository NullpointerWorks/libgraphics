package com.nullpointerworks.graphics.colormodel;

/**
 * hue - saturation - value/brightness<br>
 * aka HSB
 */
public class ColorHSV 
{
	public ColorHSV() {}
	public ColorHSV(float h,float s,float v)
	{
		H=h; S=s; V=v;
	}
	/**
	 * hue = [0-360] degrees 
	 */
	public float H = 0f;
	/**
	 * saturation [0-1] 
	 */
	public float S = 0f;
	/**
	 * value [0-1] 
	 */
	public float V = 0f;
	
	@Override
	public String toString()
	{
		return "HSV = "+H+" - "+S+" - "+V;
	}
}
