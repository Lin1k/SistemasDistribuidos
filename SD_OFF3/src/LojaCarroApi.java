import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LojaCarroApi extends Remote {
    List<Carro> adicionarCarro(Carro carro) throws RemoteException;

    void apagarCarro(String nomeCarro) throws RemoteException;

    List<Carro> listarCarros() throws RemoteException;

    boolean verificaCarro(String entradaBusca) throws RemoteException;

    Carro pesquisarCarro(String entradaBusca) throws RemoteException;

    Carro alterarCarro(String nomeCarro, Carro novoCarro) throws RemoteException;

    int quantidadeCarro(String nomeCarro) throws RemoteException;

    void comprarCarro(String nomeCarro) throws RemoteException;

}