/*
 * Copyright (C) 2007-2012  Marco Guazzone
 *                          [Distributed Computing System (DCS) Group,
 *                           Computer Science Institute,
 *                           Department of Science and Technological Innovation,
 *                           University of Piemonte Orientale,
 *                           Alessandria (Italy)]
 *
 * This file is part of dcj-commons.
 *
 * dcsj-commons is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * dcsj-commons is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with dcsj-commons.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.unipmn.di.dcs.common.conversion;

import org.apache.commons.codec.binary.Base64;

/**
 * Utility class for data conversion.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class Convert
{
	/**
	 * Converts a Base64 string to a byte array.
	 */
	public static byte[] Base64ToBytes(String s)
	{
		if ( s == null )
		{
			return null;
		}

		//return Base64.Decode( s ); // deprecated
		return Base64.decodeBase64( s.getBytes() );
	}

	/**
	 * Converts a Base64 byte array to a byte array.
	 */
	public static byte[] Base64ToBytes(byte[] data)
	{
		if ( data == null )
		{
			return null;
		}

		return Base64.decodeBase64( data );
	}

	/**
	 * Converts a byte array to a Base64 string.
	 */
	public static String BytesToBase64(byte[] bytes)
	{
		if ( bytes == null )
		{
			return null;
		}

		//return Base64.Encode( bytes ); // deprecated
		return new String( Base64.encodeBase64( bytes ) );
	}

	/**
	 * Converts a byte array to a Base64 byte array.
	 */
	public static byte[] BytesToBase64Bytes(byte[] bytes)
	{
		if ( bytes == null )
		{
			return null;
		}

		return Base64.encodeBase64( bytes );
	}

	/**
	 * Converts a hexadecimal string to a byte array.
	 */
	public static byte[] HexToBytes(String hexStr)
	{
		if ( hexStr == null || hexStr.length() == 0 )
		{
			return null;
		}

		// Creates an array whose size is half of the string length.
		// (note: one byte is 2 hex digit).
		byte[] bytes = new byte[ hexStr.length() / 2 ];
		int curIndex = 0;
		for ( int i = 0; i < hexStr.length(); ++i )
		{
			curIndex = (i / 2);
			String hexString = hexStr.substring( i, 2 );
			byte newByte = Byte.parseByte( hexString, 16 );
			bytes[curIndex] = newByte;
			i++;
		}
		return bytes;
	}

	/**
	 * Converts a byte array to a hexadecimal string.
	 */
	public static String BytesToHex(byte[] bytes)
	{
		if ( bytes == null )
		{
			return null;
		}

		/* Variant:
		char[] chars = new char[bytes.length * 2];
		for (int i = 0; i < bytes.length; i++)
		{
			int b = bytes[i];
			chars[i * 2] = hexDigits[b >> 4];
			chars[i * 2 + 1] = hexDigits[b & 0xF];
		}
		return new String(chars);
		*/

		StringBuffer hexStr = new StringBuffer();
		for ( byte b : bytes )
		{
			hexStr.append( String.format( "%2X", b ) );
		}

		return hexStr.toString();
	}
}
