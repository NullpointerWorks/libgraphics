package com.nullpointerworks.graphics.render.shader;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.graphics.render.PlotRequest;
import com.nullpointerworks.math.Approximate;
import com.nullpointerworks.math.matrix.Matrix3;

/*
 * applies translations, rotations, scaling, etc to the given request
 */
public class Transform
{
	public void run(PlotRequest req)
	{
		/*
		 * transform source image
		 */
		IntBuffer img 		= req.image;
		Float scaleW		= req.scale_w;
		Float scaleH		= req.scale_h;
		Float rotate 		= req.angle;
		float source_w 		= img.getWidth();
		float source_h 		= img.getHeight();
		
		/*
		 * scale image
		 */
		float scale_w 	= source_w;
		float scale_h 	= source_h;
		float inv_scalew = 1f;
		float inv_scaleh = 1f;
		if (scaleW != null)
		{
			inv_scalew = 1f / scaleW;
			scale_w = source_w * scaleW;
		}
		if (scaleH != null)
		{
			inv_scaleh = 1f / scaleH;
			scale_h = source_h * scaleH;
		}
		
		/*
		 * rotate image
		 */
		float rotate_w 	= scale_w;
		float rotate_h 	= scale_h;
		float sin = 0f;
		float cos = 1f;
		if (rotate != null)
		{
			sin = (float)Approximate.sin(rotate);
		    cos = (float)Approximate.cos(rotate);
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
    		{cos,-sin, 0.5f*scale_w},
    		{sin, cos, 0.5f*scale_h},
    		{0f,0f,1f}
    	};
	    
	    float[][] m_scale = 
	    {
    		{inv_scalew,0f,0f},
    		{0f,inv_scaleh,0f},
    		{0f,0f,1f}
	    };
	    
	    float[][] m_trans = 
	    {
    		{1f,0f,-0.5f*rotate_w},
    		{0f,1f,-0.5f*rotate_h},
    		{0f,0f,1f}
	    };
	    
	    /*
	     * compile transformation data
	     */
		req.mTransform = Matrix3.mul(m_scale, m_rotate, m_trans);
		req.aabb.x = req.x - 0.5f*rotate_w;
		req.aabb.y = req.y - 0.5f*rotate_h;
		req.aabb.w = rotate_w;
		req.aabb.h = rotate_h;
	}
}
