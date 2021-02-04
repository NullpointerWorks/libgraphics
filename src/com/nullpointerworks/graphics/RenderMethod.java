/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2021)
 * Using this library makes you subject to the license terms.
 * 
 * https://creativecommons.org/licenses/by-sa/4.0/
 */
package com.nullpointerworks.graphics;

public enum RenderMethod 
{
	/**
	 * Rotation by image sampling. Low quality rendering.
	 */
	SAMPLING,
	
	/**
	 * Rotation by area mapping. Good quality image rotation.
	 */
	AREA_MAP
}
