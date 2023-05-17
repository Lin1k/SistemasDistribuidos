import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class LojaCarro implements LojaCarroApi {

    Map<String, Carro> ArmazenamentoCarro;
    Map<String, Integer> QuantidadeTotal = new HashMap<>();

    List<Carro> carros = new ArrayList<>();

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
        List<Carro> loteInicial = new ArrayList<>();
        loteInicial.add(new Carro("1111", "Uno", Carro.Categoria.ECONOMICO, 1, 29999, 2000));
        loteInicial.add(new Carro("1234", "Onix", Carro.Categoria.ECONOMICO, 1, 39999, 2020));
        loteInicial.add(new Carro("3333", "Ka", Carro.Categoria.ECONOMICO, 1, 39999, 2020));
        loteInicial.add(new Carro("4444", "HB20", Carro.Categoria.ECONOMICO, 1, 50999, 2016));
        loteInicial.add(new Carro("5555", "Transtorno", Carro.Categoria.EXECUTIVO, 1, 52999, 2014));
        loteInicial.add(new Carro("6666", "Manivela", Carro.Categoria.INTERMEDIARIO, 1, 54999, 2015));
        loteInicial.add(new Carro("7777", "HB20S", Carro.Categoria.EXECUTIVO, 1, 51999, 2015));
        loteInicial.add(new Carro("8888", "Logan", Carro.Categoria.EXECUTIVO, 1, 52999, 2016));
        loteInicial.add(new Carro("9999", "McQueen", Carro.Categoria.EXECUTIVO, 1, 104999, 2022));
        loteInicial.add(new Carro("0000", "Corolla", Carro.Categoria.EXECUTIVO, 1, 89999, 2022));
        loteInicial.add(new Carro("2222", "Civic", Carro.Categoria.EXECUTIVO, 1, 84999, 2021));
        loteInicial.add(new Carro("4321", "Cruze", Carro.Categoria.EXECUTIVO, 1, 83999, 2021));

        Map<String, Integer> quantidadeInicial = new HashMap<>();
        for (Carro carro : loteInicial) {
            if (quantidadeInicial.containsKey(carro.getNome())) {
                quantidadeInicial.put(carro.getNome(), quantidadeInicial.get(carro.getRenavan()) + 1);
            } else {
                quantidadeInicial.put(carro.getNome(), 1);
            }
        }

        loteInicial = AtualizarCarros(loteInicial, quantidadeInicial);
        this.carros = loteInicial;
        this.QuantidadeTotal = quantidadeInicial;

        ArmazenamentoCarro = new HashMap<>();
        for (Carro carro : carros) {
            ArmazenamentoCarro.put((carro.getNome()), carro);
        }
    }

    @Override
    public List<Carro> adicionarCarro(Carro novoCarro) {
        carros.add(novoCarro);
        Map<String, Integer> quantidadeInicial = new HashMap<>();
        for (Carro carro : carros) {
            if (quantidadeInicial.containsKey(carro.getNome())) {
                quantidadeInicial.put(carro.getNome(), quantidadeInicial.get(carro.getNome()) + 1);
            } else {
                quantidadeInicial.put(carro.getNome(), 1);
            }
        }
        QuantidadeTotal = quantidadeInicial;
        carros = AtualizarCarros(carros, quantidadeInicial);
        return carros;

    }

    @Override
    public void apagarCarro(String nomeCarro) throws RemoteException {
        ArmazenamentoCarro.remove(nomeCarro);

    }

    @Override
    public List<Carro> listarCarros() {
        return new ArrayList<>(ArmazenamentoCarro.values());
    }

    @Override
    public boolean verificaCarro(String entradaBusca) throws RemoteException {
        boolean aux = false;
        for (Carro carro : ArmazenamentoCarro.values()) {
            if (carro.getNome().contains(entradaBusca) || carro.getRenavan().contains(entradaBusca)) {
                carro.setQuantidadeDisponivel(carro.getQuantidadeDisponivel() + 1);
                aux = true;
                return aux;
            }
        }
        return aux;
    }

    public Carro pesquisarCarro(String entradaBusca) throws RemoteException {
        for (Carro carro : ArmazenamentoCarro.values()) {
            if (carro.getNome().contains(entradaBusca) || carro.getRenavan().contains(entradaBusca)) {
                return carro;
            }
        }
        return null;
    }

    @Override
    public Carro alterarCarro(String nomeCarro, Carro novoCarro) throws RemoteException {

        Carro carro = new Carro(nomeCarro, nomeCarro, null, 0, 0, 0);
        for (Carro carroDesatualizado : carros) {
            if (carroDesatualizado.getNome().equalsIgnoreCase(nomeCarro)
                    || carroDesatualizado.getRenavan().equalsIgnoreCase(novoCarro.getRenavan())) {
                carro = carroDesatualizado;
                break;
            }
        }
        carro.setAnoDeFabricacao(novoCarro.getAnoDeFabricacao());
        carro.setAnoDeFabricacao(novoCarro.getAnoDeFabricacao());
        carro.setAnoDeFabricacao(novoCarro.getAnoDeFabricacao());
        return novoCarro;
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

    public List<Carro> AtualizarCarros(List<Carro> carros, Map<String, Integer> qtd) {
        List<Carro> carrosAtualizado = new ArrayList<>();

        for (Entry<String, Integer> map : qtd.entrySet()) {
            for (Carro carro : carros) {
                if (carro.getNome() == map.getKey()) {
                    carro.setQuantidadeDisponivel(map.getValue());
                    carrosAtualizado.add(carro);
                }
            }
        }
        Collections.sort(carrosAtualizado);
        return carrosAtualizado;
    }
}