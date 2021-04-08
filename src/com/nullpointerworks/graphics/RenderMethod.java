/*
 * This is free and unencumbered software released into the public domain.
 * (http://unlicense.org/)
 * Nullpointer Works (2021)
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
