import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LojaCarro implements LojaCarroApi {

    private Map<String, Carro> ArmazenamentoCarro;

    public static void main(String[] args) {
        try {
            LojaCarro refObjRemotLojaCarro = new LojaCarro();
            LojaCarroApi skeleton = (LojaCarroApi) UnicastRemoteObject.exportObject(refObjRemotLojaCarro, 0);

            // Criação do registro RMI na porta 2020
            Registry registro = LocateRegistry.createRegistry(2020);

            // Binding do objeto remoto
            registro.bind("Loja de Carros", skeleton);
        } catch (Exception e) {
            System.err.println("Erro na Loja de Carros: " + e.toString());
            e.printStackTrace();
        }
    }

    public LojaCarro() {
        ArmazenamentoCarro = new HashMap<>();
    }

    @Override
    public void adicionarCarro(Carro carro) throws RemoteException {
        ArmazenamentoCarro.put(carro.getNome(), carro);

    }

    @Override
    public void apagarCarro(Carro nomeCarro) throws RemoteException {
        ArmazenamentoCarro.remove(nomeCarro);

    }

    @Override
    public List<Carro> listarCarros() throws RemoteException {
        return new ArrayList<>(ArmazenamentoCarro.values());
    }

    @Override
    public List<Carro> pesquisarCarro(String entradaBusca) throws RemoteException {
        List<Carro> listaSaida = new ArrayList<>();
        for (Carro carro : ArmazenamentoCarro.values()) {
            if (carro.getNome().contains(entradaBusca) || carro.getRenavan().contains(entradaBusca)) {
                listaSaida.add(carro);
            }
        }
        return listaSaida;
    }

    @Override
    public void alterarCarro(String nomeCarro, Carro novoCarro) throws RemoteException {
        if (ArmazenamentoCarro.containsKey(nomeCarro)) {
            ArmazenamentoCarro.put(nomeCarro, novoCarro);
        }
    }

    @Override
    public int quantidadeCarro(String nomeCarro) throws RemoteException {
        if (ArmazenamentoCarro.containsKey(nomeCarro)) {
            return ArmazenamentoCarro.get(nomeCarro).getQuantidadeDisponivel();
        }
        return 0;
    }

    @Override
    public void comprarCarro(String nomeCarro) throws RemoteException {
        if (ArmazenamentoCarro.containsKey(nomeCarro)) {
            Carro carro = ArmazenamentoCarro.get(nomeCarro);
            int quantidadeCarro = carro.getQuantidadeDisponivel();
            if (quantidadeCarro > 0) {
                carro.setQuantidadeDisponivel(quantidadeCarro - 1);
            }
        }

    }
}