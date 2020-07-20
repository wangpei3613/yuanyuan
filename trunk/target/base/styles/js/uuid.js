function uuid(len, radix) { 
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split(''); 
    var uuid = [], i; 
    radix = radix || chars.length; 
    
    if (len) { 
      // Compact form 
      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix]; 
    } else { 
      // rfc4122, version 4 form 
      var r; 
    
      // rfc4122 requires these characters 
      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-'; 
      uuid[14] = '4'; 
    
      // Fill in random data.  At i==19 set the high bits of clock sequence as 
      // per rfc4122, sec. 4.1.5 
      for (i = 0; i < 36; i++) { 
        if (!uuid[i]) { 
          r = 0 | Math.random()*16; 
          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r]; 
        } 
      } 
    } 
    
    return uuid.join(''); 
}  


/* utf.js - UTF-8 <=> UTF-16 convertion
*
* Copyright (C) 1999 Masanao Izumo <iz@onicos.co.jp>
* Version: 1.0
* LastModified: Dec 25 1999
* This library is free.  You can redistribute it and/or modify it.
*/

/*
* Interfaces:
* utf8 = utf16to8(utf16);
* utf16 = utf16to8(utf8);
*/

function utf16to8(str) {
   var out, i, len, c;

   out = "";
   len = str.length;
   for(i = 0; i < len; i++) {
	c = str.charCodeAt(i);
	if ((c >= 0x0001) && (c <= 0x007F)) {
	    out += str.charAt(i);
	} else if (c > 0x07FF) {
	    out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
	    out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));
	    out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));
	} else {
	    out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));
	    out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));
	}
   }
   return out;
}

function utf8to16(str) {
   var out, i, len, c;
   var char2, char3;

   out = "";
   len = str.length;
   i = 0;
   while(i < len) {
	c = str.charCodeAt(i++);
	switch(c >> 4)
	{ 
	  case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7:
	    // 0xxxxxxx
	    out += str.charAt(i-1);
	    break;
	  case 12: case 13:
	    // 110x xxxx   10xx xxxx
	    char2 = str.charCodeAt(i++);
	    out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));
	    break;
	  case 14:
	    // 1110 xxxx  10xx xxxx  10xx xxxx
	    char2 = str.charCodeAt(i++);
	    char3 = str.charCodeAt(i++);
	    out += String.fromCharCode(((c & 0x0F) << 12) |
					   ((char2 & 0x3F) << 6) |
					   ((char3 & 0x3F) << 0));
	    break;
	}
   }

   return out;
}