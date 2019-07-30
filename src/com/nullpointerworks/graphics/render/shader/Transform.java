/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2019)
 * Use is subject to license terms.
 */
package com.nullpointerworks.graphics.render.shader;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.graphics.render.PlotBuffer;
import com.nullpointerworks.graphics.render.PlotRequest;
import com.nullpointerworks.math.Approximate;
import com.nullpointerworks.math.matrix.Matrix3;

/*
 * applies translations, rotations, scaling, etc to the given request
 */
public class Transform
{
	private Matrix3 M3 = new Matrix3();
	
	public PlotBuffer run(PlotRequest req)
	{
		/*
		 * transform source image
		 */
		IntBuffer img 		= req.image;
		Float x				= req.x;
		Float y				= req.y;
		Float scaleW		= req.scale_w;
		Float scaleH		= req.scale_h;
		Float rotate 		= req.angle;
		float source_w 		= img.getWidth();
		float source_h 		= img.getHeight();
		
		/*
		 * scale image
		 */
		float scale_w = source_w;
		float scale_h = source_h;
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
    		{cos,-sin, scale_w*0.5f-1f},
    		{sin, cos, scale_h*0.5f-1f},
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
		PlotBuffer pb 	= new PlotBuffer();
		pb.geom 		= req.geom;
		pb.image 		= img;
		pb.transform 	= tmat;
	    pb.aabb.x 		= x - rotate_w*0.5f;
	    pb.aabb.y 		= y - rotate_h*0.5f;
		pb.aabb.w 		= rotate_w;
		pb.aabb.h 		= rotate_h;
		return pb;
	}
}
