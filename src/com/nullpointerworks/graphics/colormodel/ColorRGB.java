package com.nullpointerworks.graphics.colormodel;

import com.nullpointerworks.graphics.Color;

public class ColorRGB 
{
	public ColorRGB(){}
	public ColorRGB(int r, int g, int b)
	{
		A=255; R=r; G=g; B=b;
	}
	public ColorRGB(int col)
	{
		A = (col>>24) & 0xFF;
		R = (col>>16) & 0xFF;
		G = (col>>8)  & 0xFF;
		B = (col) 	  & 0xFF;
	}

	public int A = 0;
	public int R = 0;
	public int G = 0;
	public int B = 0;
	
	@Override
	public String toString()
	{
		return "aRGB = "+A+" - "+R+" - "+G+" - "+B;
	}
	
	public int toInt()
	{
		return Color.toInt(A,R,G,B);
	}
}
