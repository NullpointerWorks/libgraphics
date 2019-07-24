package com.nullpointerworks.graphics;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.graphics.render.PlotBuffer;
import com.nullpointerworks.graphics.render.PlotRequest;
import com.nullpointerworks.graphics.render.shader.Rasterizer;
import com.nullpointerworks.graphics.render.shader.Transform;
import com.nullpointerworks.math.geometry.g2d.Geometry2D;

public class Render 
{
	private static Transform transf = new Transform();
	private static Rasterizer raster = new Rasterizer();
	private static PlotRequest instance = null;
	private static IntBuffer destination = null;
	
	public static void prepare()
	{
		if (instance == null)
		instance = new PlotRequest();
	}
	
	public static void source(IntBuffer img)
	{
		instance.image = img.copy();
	}
	
	public static void location(float[] p)
	{
		location(p[0], p[1]);
	}
	
	public static void location(float x, float y)
	{
		instance.x = x;
		instance.y = y;
	}
	
	public static void scale(float w, float h)
	{
		instance.scale_h = h;
		instance.scale_w = w;
	}
	
	public static void rotate(float angle)
	{
		instance.angle = angle;
	}

	public static void geometry(Geometry2D geom) 
	{
		instance.geom = geom;
	}
	
	public static void destination(IntBuffer dest)
	{
		destination = dest;
	}
	
	public static void release()
	{
		PlotBuffer pb = transf.run(instance);
		raster.run(pb, destination);
		pb.free();
	}

	public static void request(PlotRequest req, IntBuffer dest)
	{
		PlotBuffer pb = transf.run(req);
		raster.run(pb, dest);
		pb.free();
	}
	
	public static void delete()
	{
		if (instance != null)
		instance.free();
		instance = null;
	}
}
