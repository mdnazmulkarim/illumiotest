import java.util.Enumeration;
import java.util.Hashtable;

class Node 
{ 
    long start; 
    long end ; 
    long ipStart;
    long ipEnd;
    Node left, right; 
    
    Hashtable<PortRange, IPRange> hashTable;
    
  
    public Node(long item) 
    { 
        start = item; 
        end = item;
        left = right = null; 
        hashTable = new Hashtable<PortRange, IPRange>();
    } 
    
    public Node(long s, long e) 
    { 
        start = s; 
        end = e;
        left = right = null; 
        hashTable = new Hashtable<PortRange, IPRange>();
    } 
    
    public void setIp(long ip) {
    	ipStart = ipEnd = ip;
    	hashTable.put(new PortRange(start,end), new IPRange(ipStart, ipEnd));
    }
    public void setIp(long ip1, long ip2) {
    	ipStart = ip1;
    	ipEnd = ip2;
    	hashTable.put(new PortRange(start,end), new IPRange(ipStart, ipEnd));
    }
    
    public int isInRange(long value ) {
    	
    	if(value<start)
    		return -1;
    	else if(value>= start && value <= end)
    		return 0;
    	else return 1;
    }
    
    public void addARange(PortRange portRange, IPRange ipRange) {
    	hashTable.put(portRange, ipRange);
    }
    
    public boolean matches(long port, long ip) {
    	
    	Enumeration<PortRange> enumeration = hashTable.keys();
    	while(enumeration.hasMoreElements()) {
    		 
    		PortRange key = enumeration.nextElement();
            if(key.isInRange(port)==0 && hashTable.get(key).isInRange(ip)==0)
            	return true;
        }
    	return false;
    }
    
    
    
    @Override
    public String toString() {
    	return "start:"+start + ",end:" + end +",IP Start:"+ipStart+",IP End:"+ipEnd;    	
    }
} 