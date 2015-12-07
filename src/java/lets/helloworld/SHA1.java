package lets.helloworld;

import java.util.ArrayList;
import java.util.HashMap;

public class SHA1 {
	private int[] h = { 0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476,
			0xC3D2E1F0 };
        private int[] j = { 0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476,
			0xC3D2E1F0 };
	private int numberOfBlocks;

	/**
	 * This is the constructor of the SHA-1
	 */
	public SHA1() {
		this.numberOfBlocks = 0;
	}

	public String encrypt(String message) {
		this.numberOfBlocks=0;
                this.h = j.clone();
		String temp=this.textToBinary(message);
		temp = this.padding(temp);
//		System.out.println(temp.length());
		HashMap<Integer, ArrayList<Integer>> pad = this.messageSplit(temp);
//		System.out.println(numberOfBlocks);
		for (int i = 0; i < numberOfBlocks; i++) {
			this.hashing(this.bitsExtension(pad.get(i)));
		}
		return this.hashCombine();
	}

	/**
	 * This function paddles the message, it appends 1 first, then adds 0's so
	 * the length of the message mod 512 is 448 Then append a 64 bits binary
	 * number of the length of the message
	 * 
	 * @param m
	 * @return paddled message
	 */
	private String padding(String m) {
		// record the length of the string
		int ml = m.length();
		// add 1 to the end of the message
		m = m + "1";
		// convert the length of the string to unsigned string
		String msl = Integer.toUnsignedString(ml, 2);
		// append 0 for the message
		for (int i = 0; i < (447 - (ml % 512)); i++) {
			m += '0';
		}
		// append the size of the string in the end of the message
		int binayMessageLength = msl.length();
		for (int i = 0; i < 64 - binayMessageLength; i++) {
			msl = '0' + msl;
		}
		// add two parts of the message together;
		return m + msl;
	}

	/**
	 * This function splits the string into multiple substrings in order to
	 * proceed SHA-1
	 * 
	 * @param m
	 * @return HashMap<Integer, ArrayList<String>>
	 */
	private HashMap<Integer, ArrayList<Integer>> messageSplit(String m) {
		HashMap<Integer, ArrayList<Integer>> split = new HashMap<Integer, ArrayList<Integer>>();
		// ArrayList<String> temp =new ArrayList<String>();
		// Calculate the number of 512 chunks
		int iterations = m.length() / 512;
		for (int i = 0; i < iterations; i++) {
			// Split each chunk into 16 of 32 bits strings
			ArrayList<Integer> chunks = new ArrayList<Integer>();
			for (int j = i * 16; j < i * 16 + 16; j++) {
				// Maybe I should use String because the shift
				chunks.add(Integer.parseUnsignedInt(
						m.substring(j * 32, j * 32 + 32), 2));

				// This is implemented for debugging
//				 temp.add(j+ " " + "time: 0x"+
//				 Integer.toUnsignedString(Integer.parseUnsignedInt(m.substring(j
//				 * 32, j * 32 + 32), 2),16));
			}
			// Put the chunk into the i'th iteration box
	//		 System.out.println(temp);
	//		 temp.clear();
			split.put(i, chunks);
			this.numberOfBlocks++;
		}

		return split;
	}

	/**
	 * This function takes the 16 32 bits array and extends to 80 32 bits Array
	 * 
	 * @param m
	 * @return the extension of the 16 chunks
	 */
	private ArrayList<Integer> bitsExtension(ArrayList<Integer> m) {
	//	 ArrayList<String> temp =new ArrayList<String>();
		ArrayList<Integer> output = new ArrayList<Integer>();
		// w0--w15 are the same
		output.addAll(m);
		for (int i = 16; i < 80; i++) {
			// w[i] = (w[i-3] xor w[i-8] xor w[i-14] xor w[i-16])
			int a = output.get(i - 3) ^ output.get(i - 8) ^ output.get(i - 14)
					^ output.get(i - 16);
			// this gets the most Significant bit of the number, big endian
			int mask = a & 0x80000000;
			mask = mask >>> 31;
			// left rotation
			a = (a << 1) ^ mask;
			// This is implemented for debugging
//			 temp.add(i +": 0x"+Integer.toUnsignedString(a, 16));
			output.add(a);
		}
		// System.out.println(temp);
		return output;
	}

	/**
	 * This is the hushing part of the function
	 * 
	 * @param m
	 */
	private void hashing(ArrayList<Integer> w) {
		int a, b, c, d, e, f, temp, k;
		// Initialize hash value for this chunk
		a = h[0];
		b = h[1];
		c = h[2];
		d = h[3];
		e = h[4];
		f = 0;
		k = 0;
		for (int i = 0; i < 80; i++) {
			// Round 1
			if (i < 20) {
				f = (b & c) | ((~b) & d);
				k = 0x5A827999;
			}
			// Round 2
			else if (i > 19 && i < 40) {
				f = b ^ c ^ d;
				k = 0x6ED9EBA1;
			}
			// Round 3
			else if (i > 39 && i < 60) {
				f = (b & c) | (b & d) | (c & d);
				k = 0x8F1BBCDC;
			}
			// Round 4
			else if (i > 59 && i < 80) {
				f = b ^ c ^ d;
				k = 0xCA62C1D6;
			}
			// create a mask for the most left 5 digits of a
			int mask = a & 0xf8000000;
			mask = mask >>> 27;
			// a left rotate 5
			temp = ((a << 5) ^ mask) + f + e + k + w.get(i);
			e = d;
			d = c;
			// create a mask for the most left 30 digits of c
			mask = b & 0xfffffffc;
			// System.out.println("  "+Integer.toUnsignedString(c, 2));
			mask = mask >>> 2;
			// System.out.println("00"+Integer.toBinaryString(mask));
			// System.out.println(Integer.toBinaryString(c<<30));
			c = (b << 30) ^ mask;
			// System.out.println(Integer.toUnsignedString(c, 2));
			// System.out.println();
			b = a;
			a = temp;
			// update the hash chunks
			// System.out.println("i= " + i + " A= " + Integer.toHexString(a) +
			// " B=" + Integer.toHexString(b) + " C=" + Integer.toHexString(c)
			// + " D=" + Integer.toHexString(d) + " E=" +
			// Integer.toHexString(e));
		}
		h[0] += a;
		h[1] += b;
		h[2] += c;
		h[3] += d;
		h[4] += e;

		// System.out.println("Final A= " + Integer.toBinaryString(h[0]) + " B="
		// + Integer.toBinaryString(h[1]) + " C=" + Integer.toBinaryString(h[2])
		// + " D=" + Integer.toBinaryString(h[3]) + " E=" +
		// Integer.toBinaryString(h[4]));

	}

	/**
	 * This is the final step that combines all the strings together in
	 * Hexadecimal form
	 * 
	 * @return Final result of the SHA-1
	 */
	private String hashCombine() {
		String temp = "";
		for (int i = 0; i < 5; i++) {
			// convert h[i] to hex
			String t = Integer.toHexString(h[i]);
			// check the length of the string, if it is less than 8, then pad
			// the front with 0
			int tl = t.length();
			if (tl < 8) {
				for (int j = 0; j < 8 - tl; j++) {
					t = '0' + t;
				}
			}
			// Add the paddled string to the result
			temp += t;
		}
		return temp;
	}
	
	/**
	 * This method converts the letter to the binary string
	 * @param message
	 * @return
	 */
	private String textToBinary(String message) {
		StringBuilder output = new StringBuilder();
		String temp = "";
		for (int i = 0; i < message.length(); i++) {
			temp = Integer.toBinaryString((int) message.charAt(i));
			while(!(temp.length()%4==0)){
				temp='0'+temp;
			}
			output.append(temp);
		}
//		System.out.println(output.toString());
		return output.toString();
	}

}
