/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2021)
 * Using this library makes you subject to the license terms.
 * 
 * https://creativecommons.org/licenses/by-sa/4.0/
 */
package com.nullpointerworks.graphics.render.shader;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.math.Approximate;
import com.nullpointerworks.math.matrix.Matrix3;

import com.nullpointerworks.graphics.render.RenderTransform;

/**
 * applies translations, rotations, scaling, etc to the given request
 * @since 1.0.0
 * @author Michiel Drost - Nullpointer Works
 */
public class Transform implements ITransform
{
	private final Matrix3 M3 = new Matrix3();
	
	@Override
	public RenderTransform transform(float x,float y,float sw,float sh,float a,IntBuffer img)
	{
		/*
		 * image dimensions
		 */
		float source_w 		= img.getWidth();
		float source_h 		= img.getHeight();
		
		/*
		 * scale image
		 */
		float scale_w = source_w;
		float scale_h = source_h;
		float inv_scalew = 1f;
		float inv_scaleh = 1f;
		if (sw != 1f)
		{
			inv_scalew = 1f / sw;
			scale_w = source_w * sw;
		}
		if (sh != 1f)
		{
			inv_scaleh = 1f / sh;
			scale_h = source_h * sh;
		}
		
		/*
		 * rotate image
		 */
		float rotate_w 	= scale_w;
		float rotate_h 	= scale_h;
		float sin = 0f;
		float cos = 1f;
		if (a != 0f)
		{
			sin = (float)Approximate.sin(a);
		    cos = (float)Approximate.cos(a);
		    float absin = (sin<0f)?-sin:sin;
		    float abcos = (cos<0f)?-cos:cos;
		    rotate_w 	= (abcos*scale_w + scale_h*absin);
		    rotate_h 	= (abcos*scale_h + scale_w*absin);
		}
		
		/*
		 * create matrices
		 */
	    float[][] m_rotate = 
    	{
    		{cos,-sin, scale_w*0.5f},
    		{sin, cos, scale_h*0.5f},
    		{ 0f,  0f, 1f}
    	};
	    float[][] m_scale = 
	    {
    		{inv_scalew,0f,0f},
    		{0f,inv_scaleh,0f},
    		{0f,0f,1f}
	    };
	    float[][] m_trans = 
	    {
    		{1f,0f,-x},
    		{0f,1f,-y},
    		{0f,0f,1f}
	    };
	    float[][] tmat = M3.mul(m_scale, m_rotate, m_trans);
	    
	    /*
	     * compile transformation data
	     */
		var pb = new RenderTransform();
		pb.transform = tmat;
	    pb.x = x - rotate_w*0.5f;
	    pb.y = y - rotate_h*0.5f;
		pb.w = rotate_w;
		pb.h = rotate_h;
		return pb;
	}
}
