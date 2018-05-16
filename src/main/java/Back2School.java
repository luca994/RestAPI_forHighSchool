import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("a")
public class Back2School extends ResourceConfig {
	public Back2School() {
		packages("it.polimi.services");
	}
}
