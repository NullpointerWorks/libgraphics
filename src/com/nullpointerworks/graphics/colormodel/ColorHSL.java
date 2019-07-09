package com.nullpointerworks.graphics.colormodel;

/*
 * hue - saturation - lightness
 */
public class ColorHSL 
{
	public ColorHSL() {}
	public ColorHSL(float h,float s,float l)
	{
		H=h; S=s; L=l;
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
	 * lightness [0-1] 
	 */
	public float L = 0f;
	
	@Override
	public String toString()
	{
		return "HSL = "+H+" - "+S+" - "+L;
	}
}
