
public class PortRange {
	long start, end;

	
	public PortRange(long start) {
		this.start = start;
		this.end = start;
	}

	public PortRange(long start, long end) {
		this.start = start;
		this.end = end;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}
	
	public int isInRange(long value ) {
    	
    	if(value<start)
    		return -1;
    	else if(value>= start && value <= end)
    		return 0;
    	else return 1;
    }
	
	@Override
	public String toString() {
		return "Port range:"+start+":"+end;
	}
}
