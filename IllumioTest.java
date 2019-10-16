
public class IllumioTest {

	public static void main(String[] args) {
		Firewall fw = new Firewall("input.csv");
		System.out.println(fw.accept_packet("inbound","tcp","400","192.168.250.2"));
		System.out.println(fw.accept_packet("inbound","tcp","400","192.168.255.2"));
	}

}
