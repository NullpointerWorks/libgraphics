/**
 * This is free and unencumbered software released into the public domain.
 * (http://unlicense.org/)
 * Nullpointer Works (2021)
 *
 * @version 1.0.0
 * @author Michiel Drost - Nullpointer Works
 */
module libnpw.graphics
{
	requires transitive libnpw.core;
	requires transitive libnpw.math;
	requires libnpw.util;
	requires libnpw.color;
	
	exports com.nullpointerworks.graphics;
	exports com.nullpointerworks.graphics.image;
	exports com.nullpointerworks.graphics.image.io;
	exports com.nullpointerworks.graphics.render;
	exports com.nullpointerworks.graphics.render.shader;
}
