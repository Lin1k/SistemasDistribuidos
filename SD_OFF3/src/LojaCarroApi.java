import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LojaCarroApi extends Remote {
    void adicionarCarro(Carro carro) throws RemoteException;

    void apagarCarro(Carro nomeCarro) throws RemoteException;

    List<Carro> listarCarros() throws RemoteException;

    List<Carro> pesquisarCarro(String entradaBusca) throws RemoteException;

    void alterarCarro(String nomeCarro, Carro novoCarro) throws RemoteException;

    int quantidadeCarro(String nomeCarro) throws RemoteException;

    void comprarCarro(String nomeCarro) throws RemoteException;

}