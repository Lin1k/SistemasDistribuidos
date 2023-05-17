
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;
import java.util.List;

public class Servidor implements SuperServidorApi {
    private SuperServidorApi NoLider;
    private List<SuperServidorApi> NosSeguidores;
    private int NoIndex;

    public class NoSeguidor1 implements SuperServidorApi {

        @Override
        public String processData(String data) throws RemoteException {
            // TODO Auto-generated method skeleton
            return null;
        }

    }

    public class NoSeguidor2 implements SuperServidorApi {

        @Override
        public String processData(String data) throws RemoteException {
            // TODO Auto-generated method skeleton
            return null;
        }

    }

    public class NoSeguidor3 implements SuperServidorApi {

        @Override
        public String processData(String data) throws RemoteException {
            // TODO Auto-generated method skeleton
            return null;
        }

    }

    public Servidor() {
        super();
        NosSeguidores = new ArrayList<>();

        // Aqui a criação e registro dos nós escravos
        try {
            NoSeguidor1 noSeguidor1 = new NoSeguidor1();
            SuperServidorApi skeleton1 = (SuperServidorApi) UnicastRemoteObject.exportObject(noSeguidor1, 0);
            Registry registro1 = LocateRegistry.createRegistry(00001);
            registro1.bind("NoSeguidor1", skeleton1);
            NosSeguidores.add(noSeguidor1);

            NoSeguidor2 noSeguidor2 = new NoSeguidor2();
            SuperServidorApi skeleton2 = (SuperServidorApi) UnicastRemoteObject.exportObject(noSeguidor2, 0);
            Registry registro2 = LocateRegistry.createRegistry(00002);
            registro2.bind("NoSeguidor2", skeleton2);
            NosSeguidores.add(noSeguidor2);

            NoSeguidor3 noSeguidor3 = new NoSeguidor3();
            SuperServidorApi skeleton3 = (SuperServidorApi) UnicastRemoteObject.exportObject(noSeguidor3, 0);
            Registry registro3 = LocateRegistry.createRegistry(00003);
            registro3.bind("NoSeguidor3", skeleton3);
            NosSeguidores.add(noSeguidor3);

        } catch (Exception e) {
            // TODO: handle exception
        }
        // Certifique-se de registrar os objetos remotos corretamente no registro RMI
        // Exemplo: NoSeguidor.add(new SlaveNode1()); NoSeguidor.add(new SlaveNode2());
        // NoSeguidor.add(new SlaveNode3());

        // Defina o nó mestre como o primeiro nó escravo
        if (!NosSeguidores.isEmpty()) {
            NoLider = NosSeguidores.get(0);
        }
    }

    @Override
    public String processData(String data) throws RemoteException {
        // Verifica se o nó mestre está disponível
        /*
         * if (NoLider != null) {
         * try {
         * return NoLider.processData(data);
         * } catch (RemoteException e) {
         * // Ocorreu uma exceção no nó mestre, então selecionamos outro nó como mestre
         * handleNoLiderErro();
         * }
         * }
         */

        // Nenhum nó mestre disponível, seleciona um nó escravo usando Round Robin
        SuperServidorApi No = getNextNoSeguidor();
        return No.processData(data);
    }

    private synchronized SuperServidorApi getNextNoSeguidor() {
        SuperServidorApi No = NosSeguidores.get(NoIndex);
        NoIndex = (NoIndex + 1) % NosSeguidores.size();
        return No;
    }

    public synchronized void disableNode(int index) {
        if (index >= 0 && index < NosSeguidores.size()) {
            NosSeguidores.remove(index);
            if (NoLider != null && NoLider.equals(NosSeguidores.get(index))) {
                NoLider = null;
            }
        }
    }

    /*
     * private synchronized void handleNoLiderErro() {
     * // Remove o nó mestre da lista de nós escravos
     * NoSeguidor.remove(NoLider);
     * 
     * // Seleciona um novo nó mestre (se disponível)
     * if (!NoSeguidor.isEmpty()) {
     * NoLider = NoSeguidor.get(0);
     * } else {
     * NoLider = null; // Nenhum nó mestre disponível
     * }
     * }
     */
    public static void main(String[] args) {
        try {
            SuperServidorApi obj = new Servidor();
            SuperServidorApi skeleton = (SuperServidorApi) UnicastRemoteObject.exportObject(obj, 0);

            // Criação do registro RMI na porta 1099
            Registry registroRMI = LocateRegistry.createRegistry(1010);

            // Binding do objeto remoto no registro
            registroRMI.bind("SuperServidorApi", skeleton);

            System.out.println("Servidor pronto para receber requisicoes.");
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}