import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cliente {
    public static void main(String[] args) {
        try {
            //
            Registry registro = LocateRegistry.getRegistry("localhost", 1010);

            //
            SuperServidorApi superServidorApi = (SuperServidorApi) registro.lookup("SuperServidorApi");

            //
            String saida = superServidorApi.processData("Dado foda");

            System.out.println("Saída do servidor:" + saida);

        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}
