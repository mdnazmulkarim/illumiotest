

How to run/test:
1.
The principle class I have made for the solution is Firewall.java.
IllumioTest.java has main method to run.
2.
Firewall takes a csv filename in the constructor for example : Firewall fw = new Firewall("input.csv");

3.
You have to call fw.accept_packet(String direction, String protocol,String port, String ip) such as given below:

System.out.println(fw.accept_packet("inbound","tcp","400","192.168.250.2"));
output : false

My Approach:
1. I have formed a balanced binary tree with all the rules. And try to match to any node in the tree for accept_packet input.

2. I have converted any rule to a number such as: INBOUND=1, OUTBOUND=2, TCP = 1, UDP = 2, port = given number, ip = 12 digit number
    Thus (inbound,tcp,8080,192.168.1.1) converted to : 1 1 8080(=118080). This node holds a hashtable key is a range of ports(eg,118080-118080 ) and value is range of IPs
          ip : 192.168.1.1 converted to number : 192168001001. here IP range is (192168001001-192168001001)	
3. A node can hold multiple rules. Suppose if we have already encounterd port range 10000-20000 which has already formed a node. Any later occurance of a rule
with same direction, protocol and suppose port with 10100 will also hit this bunary tree node. In this case the rule will be added to the hash table of the 
already existing node.

3. Thus this gives a very quich response in terms of efficiency. 




