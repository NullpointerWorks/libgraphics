/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2019)
 * Use is subject to license terms.
 */
package com.nullpointerworks.graphics.render;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.math.geometry.g2d.Geometry2D;
import com.nullpointerworks.math.geometry.g2d.NullGeometry;

/**
 * A simple sprite container class used for image manipulation in the rendering cycle. 
 * @since 1.0.0
 */
public class PlotRequest 
{
	/**
	 * IntBuffer object placeholder for the source image.
	 * @since 1.0.0
	 */
	public IntBuffer image;
	
	/**
	 * The Cartesian x coordinate of the sprite's center.
	 * @since 1.0.0
	 */
	public float x 			= 0f;
	
	/**
	 * The Cartesian y coordinate of the sprite's center.
	 * @since 1.0.0
	 */
	public float y 			= 0f;
	
	/**
	 * The rendering accuracy for this sprite. Set to {@code 1.0} by default.
	 * @since 1.0.0
	 */
	public float accuracy 	= 1f;
	
	/**
	 * Float object placeholder for rotation.
	 * @since 1.0.0
	 */
	public Float angle 		= null;
	
	/**
	 * Float object placeholder for the width scaling.
	 * @since 1.0.0
	 */
	public Float scale_w 	= null;
	
	/**
	 * Float object placeholder for the height scaling.
	 * @since 1.0.0
	 */
	public Float scale_h 	= null;
	
	/**
	 * The bounding geometry. By default set to a {@code NullGeometry} object.
	 * @since 1.0.0
	 * @see NullGeometry
	 */
	public Geometry2D geom 	= new NullGeometry();
	
	/**
	 * The source image will act as a color reference during the rendering transformation cycle. The source image will not be modified in any way by engine internal processes.
	 * @param image - the source image/sprite to render
	 * @return a reference of this {@code PlotRequest} object
	 * @since 1.0.0
	 */
	public PlotRequest image(IntBuffer image)
	{
		this.image = image;
		return this;
	}
	
	/**
	 * Translates the center of the sprite to the given {@code (x,y)} coordinates.
	 * @param x - Cartesian x coordinate 
	 * @param y - Cartesian y coordinate
	 * @return a reference of this {@code PlotRequest} object
	 * @since 1.0.0
	 */
	public PlotRequest translate(float x, float y)
	{
		this.x=x;
		this.y=y;
		return this;
	}
	
	/**
	 * Scaling an image will render will be performed before rotating the sprite. A scaled image will appear wider or longer depending on user input.
	 * @param sw - the width scale
	 * @param sh - the height scale
	 * @return a reference of this {@code PlotRequest} object
	 * @since 1.0.0
	 */
	public PlotRequest scale(float sw, float sh)
	{
		this.scale_w=sw;
		this.scale_h=sh;
		return this;
	}
	
	/**
	 * The rotation of the sprite follows general mathematical rules. Positive rotation results in counter-clockwise image rotation.
	 * @param angle - the angle of rotation in radians
	 * @return a reference of this {@code PlotRequest} object
	 * @since 1.0.0
	 */
	public PlotRequest rotate(float angle)
	{
		this.angle = angle;
		return this;
	}
	
	/**
	 * Provide the bounding geometry in which the image will be drawn. Images will be fully rendered when no geometry set.
	 * @param geom - the bounding geometry
	 * @return a reference of this {@code PlotRequest} object
	 * @since 1.0.0
	 */
	public PlotRequest geometry(Geometry2D geom)
	{
		this.geom = geom;
		return this;
	}
	
	/**
	 * Clears any reference of objects contained in this {@code PlotRequest} object.
	 * @since 1.0.0
	 */
	public void free() 
	{
		image.free();
		image = null;
		angle = null;
		scale_w = null;
		scale_h = null;
		geom = null;
	}
}
