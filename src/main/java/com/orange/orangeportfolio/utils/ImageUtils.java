package com.orange.orangeportfolio.utils;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {
	public static final int BYTE_SIZE = 4096;
	
	public static byte[] compressImage(byte[] data) throws Exception{
		var deflater = new Deflater();
		deflater.setLevel(Deflater.BEST_COMPRESSION);
		deflater.setInput(data);
		deflater.finish();
		
		var outputStream = new ByteArrayOutputStream(data.length);
		var temp = new byte[BYTE_SIZE];
		
		while(!deflater.finished()) {
			var size = deflater.deflate(temp);
			outputStream.write(temp,0, size);
		}
		
		outputStream.close();
		
		var compressedImageData = outputStream.toByteArray();
		
		return compressedImageData;
	}
	
	public static byte[] decompressImage(byte[] data) throws Exception{
		var inflater = new Inflater();
		inflater.setInput(data);
		
		var outputStream = new ByteArrayOutputStream(data.length);
		var temp = new byte[BYTE_SIZE];
		
		while(!inflater.finished()) {
			var count = inflater.inflate(temp);
			outputStream.write(temp,0,count);
		}
		outputStream.close();
		
		var decompressedImageData = outputStream.toByteArray();
		
		return decompressedImageData;
	}
}
