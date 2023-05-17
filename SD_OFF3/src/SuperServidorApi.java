
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SuperServidorApi extends Remote {
    String processData(String data) throws RemoteException;
}