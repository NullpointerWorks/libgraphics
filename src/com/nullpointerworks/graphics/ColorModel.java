package com.nullpointerworks.graphics;

import com.nullpointerworks.graphics.colormodel.ColorCMY;
import com.nullpointerworks.graphics.colormodel.ColorCMYK;
import com.nullpointerworks.graphics.colormodel.ColorHSI;
import com.nullpointerworks.graphics.colormodel.ColorHSL;
import com.nullpointerworks.graphics.colormodel.ColorHSV;
import com.nullpointerworks.graphics.colormodel.ColorRGB;

/**
 * Color space conversion class. 
 * Contains the following conversion functions;<br>
 * RGB <-> CMYK<br>
 * RGB <-> CMY<br>
 * RGB <-> HSV<br>
 * RGB <-> HSL<br>
 * RGB <-> HSI<br> 
 * HSV <-> HSL<br>
 */
public class ColorModel 
{
	// ====================================================
	//					to CMYK
	// ====================================================
	
	/**
	 * Convert RGB to CMYK color space.<br>
	 * This assumes the RGB and CMY color space occupy the same gamut.<br>
	 */
	public static ColorCMYK toCMYK(ColorRGB rgb)
	{
		ColorCMYK cmyk = new ColorCMYK();
		
		float R = rgb.R * 0.003921569f; //  * 0.003921569f = 1/255
		float G = rgb.G * 0.003921569f;
	    float B = rgb.B * 0.003921569f;
	    
	    float K = 1f - max(R, G, B);
		float k = 1f / (1f - K);
		
	    cmyk.C 	= (1f - R - K) * k;
	    cmyk.M 	= (1f - G - K) * k;
	    cmyk.Y 	= (1f - B - K) * k;
	    cmyk.K	= K;
	    return cmyk;
	}
	
	/**
	 * Converts RGB to CMY.
	 */
	public static ColorCMY toCMY(ColorRGB rgb)
	{
		ColorCMY cmy = new ColorCMY();
		cmy.C = 255 - rgb.R;
		cmy.M = 255 - rgb.G;
		cmy.Y = 255 - rgb.B;
	    return cmy;
	}
	
	// ====================================================
	//					to RGB
	// ====================================================
	
	/**
	 * Converts CMY to RGB.
	 */
	public static ColorRGB toRGB(ColorCMY cmy)
	{
		ColorRGB rgb = new ColorRGB();
		rgb.R = 255 - cmy.C;
		rgb.G = 255 - cmy.M;
		rgb.B = 255 - cmy.Y;
	    return rgb;
	}
	
	/**
	 * Convert an CMYK object to RGB object. <br>
	 * Assumes both color spaces occupy the same gamut.
	 */
	public static ColorRGB toRGB(ColorCMYK cmyk)
	{
		ColorRGB rgb = new ColorRGB();
		float K = 255f * (1f - cmyk.K);
		rgb.R = (int)(K*(1f - cmyk.C) + 0.5f);
		rgb.G = (int)(K*(1f - cmyk.M) + 0.5f);
		rgb.B = (int)(K*(1f - cmyk.Y) + 0.5f);
		return rgb;
	}
	
	/**
	 * convert an HSV object to an RGB object
	 */
	public static ColorRGB toRGB(ColorHSV hsv)
	{
		float H = hsv.H * 0.016666667f;
		float C = hsv.V * hsv.S;
		float X = C * ( 1f - Math.abs( (H % 2f) - 1f) );
		float m = hsv.V - C;
		return compileChroma(H,C,X,m);
	}
	
	/**
	 * convert HSL to RGB objects
	 */
	public static ColorRGB toRGB(ColorHSL hsl)
	{
		float H = hsl.H * 0.016666667f;
		float C = hsl.S * (1f - Math.abs(2f*hsl.L - 1f) );
		float x = (H % 2f) - 1f;
		float X = C * ( 1f - Math.abs(x) );
		float m = hsl.L - 0.5f*C;
		return compileChroma(H,C,X,m);
	}
	
	/**
	 * Convert an HSI object into an RGB object
	 */
	public static ColorRGB toRGB(ColorHSI hsi)
	{
		float H = hsi.H * 0.016666667f;
		float Z = 1f - Math.abs( (H % 2f) - 1f );
		float C = hsi.I * hsi.S / (1f + Z);
		float X = C * Z;
		float m = 0.333333333f * hsi.I * (1f - hsi.S);
		return compileChroma(H,C,X,m);
	}
	
	/**
	 * Compile an RGB object using a Hue'-Chroma-Luma parameters<br>
	 * H = hue / 60<br>
	 * C = chroma<br>
	 * X = Luma<br>
	 * m = match lightness<br>
	 */
	private static ColorRGB compileChroma(float H, float C, float X, float m)
	{
		float R=0f, G=0f, B=0f;
		int sex = (int)(H);
		switch(sex)
		{
		case 0: R = C; G = X; B = 0f; break;
		case 1: R = X; G = C; B = 0f; break;
		case 2: R = 0f; G = C; B = X; break;
		case 3: R = 0f; G = X; B = C; break;
		case 4: R = X; G = 0f; B = C; break;
		case 5: R = C; G = 0f; B = X; break;
		}
		
        ColorRGB rgb = new ColorRGB();
        rgb.R = (int)((R+m)*255f + 0.5f);
		rgb.G = (int)((G+m)*255f + 0.5f);
		rgb.B = (int)((B+m)*255f + 0.5f);
        return rgb;
	}
	
	// ====================================================
	//					to HSV
	// ====================================================
	
	/**
	 * convert an RGB object to an HSV object
	 */
	public static ColorHSV toHSV(ColorRGB rgb)
	{
		float R = rgb.R * 0.003921569f; // = 1/255
		float G = rgb.G * 0.003921569f;
	    float B = rgb.B * 0.003921569f;
	    
	    float min = min(R, G, B);
	    float max = max(R, G, B);
	    float delta = max - min;
	    
	    float H = max;
	    float S = max;
	    float V = max;
	    
	    if(delta == 0f)
	    {
	        H = 0f;
	        S = 0f;
	    }
	    else
	    {
	        S = delta / max;
	        
	        float halfDelta = ( delta * 0.5f );
	        float invDelta = 1f / delta;
	        float delR = ( ( ( max - R ) * 0.1666667f ) + halfDelta ) * invDelta;
	        float delG = ( ( ( max - G ) * 0.1666667f ) + halfDelta ) * invDelta;
	        float delB = ( ( ( max - B ) * 0.1666667f ) + halfDelta ) * invDelta;

	        if(R == max)
	        {
	            H = delB - delG;
	        }
	        else if(G == max)
	        {
	            H = (0.333333f) + delR - delB;
	        }
	        else if(B == max)
	        {
	            H = (0.666666f) + delG - delR;
	        }
	        
	        if(H < 0f) H += 1f;
	        if(H > 1f) H -= 1f;
	    }
        
		// compile color
	    return new ColorHSV(H,S,V);
	}
	
	/** 
	 * HSL -> HSV/HSB
	 */
	public static ColorHSV toHSV(ColorHSL hsl)
	{
		ColorHSV hsv = new ColorHSV();
		hsv.H = hsl.H;
		hsv.V = hsl.L + 0.5f*hsl.S*( 1f - Math.abs(2f*hsl.L - 1f) );
		hsv.S = 2f * (hsv.V - hsl.L) / hsv.V;
		return hsv;
	}
	
	// ====================================================
	//					to HSL
	// ====================================================
	
	/**
	 * convert an RGB object to an HSL object
	 */
	public static ColorHSL toHSL(ColorRGB rgb)
	{
		float R = rgb.R * 0.003921569f; // = 1/255
		float G = rgb.G * 0.003921569f;
	    float B = rgb.B * 0.003921569f;
	    float min = min(R, G, B);
	    float max = max(R, G, B);
	    float delta = max - min;
	    
	    float H = max;
	    float S = max;
	    float L = (max+min) * 0.5f;
	    
	    if(delta == 0f)
	    {
	        H = 0f;
	        S = 0f;
	    }
	    else
	    {
	        S = delta / ( 1f - Math.abs( 2f*L - 1f ) );
	        
	        float halfDelta = ( delta * 0.5f );
	        float invDelta = 1f / delta;
	        float delR = ( ( ( max - R ) * 0.1666667f ) + halfDelta ) * invDelta;
	        float delG = ( ( ( max - G ) * 0.1666667f ) + halfDelta ) * invDelta;
	        float delB = ( ( ( max - B ) * 0.1666667f ) + halfDelta ) * invDelta;

	        if(R == max)
	        {
	            H = delB - delG;
	        }
	        else if(G == max)
	        {
	            H = (0.333333333f) + delR - delB;
	        }
	        else if(B == max)
	        {
	            H = (0.666666666f) + delG - delR;
	        }
	        
	        if(H < 0f) H += 1f;
	        if(H > 1f) H -= 1f;
	    }
        
		// compile color
	    return new ColorHSL(H,S,L);
	}
	
	/** 
	 * HSV/HSB -> HSL
	 */
	public static ColorHSL toHSL(ColorHSV hsv)
	{
		ColorHSL hsl = new ColorHSL();
		hsl.H = hsv.H;
		hsl.L = 0.5f * hsv.V * ( 2f - hsv.S );
		hsl.S = (hsv.V*hsv.S) / (1f - Math.abs(2f*hsl.L - 1f));
		return hsl;
	}
	
	// ====================================================
	//					to HSI
	// ====================================================
	
	/**
	 * Convert RGB object to HSI object
	 */
	public static ColorHSI toHSI(ColorRGB rgb)
	{
		float R = rgb.R * 0.003921569f; // = 1/255
		float G = rgb.G * 0.003921569f;
	    float B = rgb.B * 0.003921569f;
	    float max = max(R, G, B);
	    float min = min(R, G, B);
	    float delta = max - min;
	    
	    float H = 0f;
	    float S = 0f;
	    float I = (R+G+B) * 0.333333333f;
	    
	    // calculate hue
	    if(delta != 0f)
	    {
	        float halfDelta = ( delta * 0.5f );
	        float invDelta = 1f / delta;
	        float delR = ( ( ( max - R ) * 0.166666667f ) + halfDelta ) * invDelta;
	        float delG = ( ( ( max - G ) * 0.166666667f ) + halfDelta ) * invDelta;
	        float delB = ( ( ( max - B ) * 0.166666667f ) + halfDelta ) * invDelta;
	        
	        if(R == max)
	        {
	            H = delB - delG;
	        }
	        else 
	        if(G == max)
	        {
	            H = (0.333333333f) + delR - delB;
	        }
	        else 
	        if(B == max)
	        {
	            H = (0.666666666f) + delG - delR;
	        }
	        
	        if(H < 0f) H += 1f;
	        if(H > 1f) H -= 1f;
	        
		    H *= 360f;
	    }
	    
	    //calculate intensity
	    if (I != 0f)
	    {
	    	S = 1f - (min/I);
	    }
	    
	    I *= 3f;
	    
		// compile color
	    return new ColorHSI(H,S,I);
	}

	// ====================================================
	
	private static float max(float x1,float x2,float x3)
	{
		float x = (x1<x2)?x2:x1;
		return (x<x3)?x3:x;
	}
	
	private static float min(float x1,float x2,float x3)
	{
		float x = (x1<x2)?x1:x2;
		return (x<x3)?x:x3;
	}
}
