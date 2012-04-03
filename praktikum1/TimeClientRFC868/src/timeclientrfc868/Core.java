package timeclientrfc868;

import java.io.ByteArrayInputStream;
import java.util.Date;

public class Core {
	
	public Core(){
			
	}
	public static Date formatTime(byte[] t){
	    ByteArrayInputStream bais = new ByteArrayInputStream(t);
	    long ptbZeit = 0;
	    int shift = 24;
	    long tmp = 0;
	    while((tmp = bais.read()) != -1){
	    	ptbZeit |= tmp << shift;
	    	shift -= 8;
	    }
	    return new Date((ptbZeit - 2208988800L) * 1000);
	  }

}
