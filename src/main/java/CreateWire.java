import java.io.IOException;

public class CreateWire {
    public static void main(String[] args) throws IOException {
        CreateWireServer service = new CreateWireServer(8092);
        service.stopServer();
        service.startServer();
        service.page("localhost", 8092,"/api/status","OK");
        System.out.println(service.pageStatus("http://localhost:8092/api/status"));
    }
}
