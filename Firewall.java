import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Firewall {
	
	public static final int INBOUND = 1;
	public static final int OUTBOUND = 2;
	
	public static final int TCP = 1;
	public static final int UDP = 2;
	
	private String fileName;
	
	private List<Node> ruleNodes;
	
	private Node tree;

	
	public Firewall() {
		
	}
	public Firewall(String fileName) {
		this.fileName = fileName;
		ruleNodes = new ArrayList<Node>();
		readRules();
		makeBinaryTree();
	}
	
	
	
	private void readRules() {
		
		List<Node> nodeList = new ArrayList<Node>();
		BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
		 try {

	            br = new BufferedReader(new FileReader(fileName));
	            while ((line = br.readLine()) != null) {

	               String[] tokens = line.split(cvsSplitBy);
	               ruleNodes.add(convertToANode(tokens[0].trim(),tokens[1].trim(),
	            		   tokens[2].trim(),tokens[3].trim()));

	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	        	 e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	}
	
	private void makeBinaryTree() {
		Collections.sort(ruleNodes, new SortByStart()); 
		System.out.println("No of input :"+ruleNodes.size());
		if(ruleNodes.size()>0)
			tree = ruleNodes.remove(ruleNodes.size()/2); 
		if(ruleNodes.size()>0)
			formTree(tree, ruleNodes.remove(ruleNodes.size()/2));
		System.out.println(tree.toString()+" is the root");
	}
	
	private void formTree(Node currNode, Node tobeAddedNode) {
		        
        int val = currNode.isInRange(tobeAddedNode.start);
        
        if(val ==0) {
        	currNode.addARange(new PortRange(tobeAddedNode.start, tobeAddedNode.end),
        			new IPRange(tobeAddedNode.ipStart,tobeAddedNode.ipEnd));
        	//System.out.println(tobeAddedNode.toString() +" Added in current node.");
        } else if(val<0) {
        	if(currNode.left ==null) {
        		currNode.left = tobeAddedNode;  
        		//System.out.println(tobeAddedNode.toString() +" Added in left node.");
        	}
        	else
        		formTree(currNode.left,tobeAddedNode);
        }else if(val>0) {
        	if(currNode.right ==null) {
        		currNode.right = tobeAddedNode;  
        		//System.out.println(tobeAddedNode.toString() +" Added in right node.");
        	}
        	else
        		formTree(currNode.right,tobeAddedNode);
        }
        if(ruleNodes.size() > 0) {
        	formTree(tree, ruleNodes.remove(ruleNodes.size()/2));
        }
        
  
	}
	
	public Node convertToANode(String direction, String protocol,String port, String ip) {
		
		Node node ;
		if(port.contains("-")){	
			
			node =  new Node(
					       convertToANumber(direction,protocol,port.substring(0,port.indexOf('-'))),
					       convertToANumber(direction,protocol,port.substring(port.indexOf('-')+1)));
		} else {
			node =  new Node(convertToANumber(direction,protocol,port));
		}
		
		if(ip.contains("-")) {
			node.setIp(convertIpToANumber(ip.substring(0,ip.indexOf('-'))), 
					convertIpToANumber(ip.substring(ip.indexOf('-')+1)));
		}else {
			node.setIp(convertIpToANumber(ip));
		}	
			
		return node;
	} 
	
	private long convertIpToANumber(String ip) {
		StringBuilder sb =  new StringBuilder();
		
		Arrays.asList(ip.split("[.]")).stream()
		.map(str -> String.format("%03d", Integer.parseInt(str)))				
		.forEach(x -> sb.append(x));
		
		return Long.parseLong(sb.toString());
	}
	
	public long convertToANumber(String direction, String ptotocol,String port) {
		
		StringBuilder sb =  new StringBuilder();
		
		if(direction.equalsIgnoreCase("inbound")) {
			sb.append(INBOUND);
		}else {
			sb.append(OUTBOUND);
		}
		
		if(ptotocol.equalsIgnoreCase("tcp")) {
			sb.append(TCP);
		}else {
			sb.append(UDP);
		}
		
		sb.append(port);		
		
		return Long.parseLong(sb.toString());
	} 
	
	private boolean foundMatch(Node currNode, Node node) {
		
		boolean match = currNode.matches(node.start, node.ipStart);
		int val = currNode.isInRange(node.start);
		 
        if(match) {
        	return true;
        } else if(val<0) {
        	if(currNode.left !=null) {
        		return foundMatch(currNode.left,node);
        	}
    	} else if(val>0) {
        	if(currNode.right !=null) {
        		return foundMatch(currNode.right,node);
        	}
        }
        return false;
    }
	
	public boolean accept_packet(String direction, String protocol,String port, String ip) {
		Node node = convertToANode(direction.trim(),protocol.trim(),
				port.trim(),ip.trim());
		return foundMatch(tree, node);
	}

}
