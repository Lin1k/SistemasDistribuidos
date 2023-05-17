import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class Usuario {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 2020;

    private static LojaCarroApi lojaCarro;
    private static Scanner scanner;

    public static void main(String[] args) {
        try {

            // Resgate do registro RMI
            Registry registro = LocateRegistry.getRegistry(SERVER_HOST, SERVER_PORT);

            // Resgate do objeto remoto
            lojaCarro = (LojaCarroApi) registro.lookup("Loja de Carros");

            // Criação do scanner para leitura da entrada do usuário
            scanner = new Scanner(System.in);

            // Executar o Menu de interação
            rodarMenu();
        } catch (Exception e) {
            System.err.println("Erro na comunicação com o servidor: " + e.toString());
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static void rodarMenu() throws RemoteException {
        System.out.println("--- Loja de Carros ---");

        if (auth()) {
            if (isFuncionario()) {
                rodarMenuFuncionario();
            } else {
                rodarMenuCliente();
            }
        } else {
            System.out.println("Falha na autenticação. Encerrando o programa...");
        }
    }

    private static boolean auth() {
        System.out.println("Digite o nome do usuário: ");
        String usuario = scanner.nextLine();

        System.out.println("Digite a senha: ");
        String senha = scanner.nextLine();

        return usuario.equals("funcionario") && senha.equals("123") ||
                usuario.equals("Cliente") && senha.equals("123");

    }

    private static boolean isFuncionario() {
        System.out.println("Você é um funcionário (S/N)?");
        String entrada = scanner.next();
        return entrada.equalsIgnoreCase("S");
    }

    private static void rodarMenuFuncionario() throws RemoteException {
        while (true) {
            System.out.println("\n--- Menu do Funcionário ---");
            System.out.println("1. Adicionar Carro");
            System.out.println("2. Apagar Carro");
            System.out.println("3. Listar Carros");
            System.out.println("4. Pesquisar Carro");
            System.out.println("5. Alterar Atributos de Carro");
            System.out.println("6. Exibir Quantidade de Carros");
            System.out.println("7. Comprar Carro");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    adicionarCarro();
                    break;
                case 2:
                    apagarCarro();
                    break;
                case 3:
                    listarCarros();
                    break;
                case 4:
                    pesquisarCarro();
                    break;
                case 5:
                    alterarCarro();
                    break;
                case 6:
                    quantidadeDisponivel();
                    break;
                case 7:
                    comprarCarro();
                    break;
                case 8:
                    System.out.println("Encerrando o programa...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void rodarMenuCliente() throws RemoteException {
        while (true) {
            System.out.println("\n--- Menu do Cliente ---");
            System.out.println("1. Listar Carros");
            System.out.println("2. Pesquisar Carro");
            System.out.println("3. Exibir Quantidade de Carros");
            System.out.println("4. Comprar Carro");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (choice) {
                case 1:
                    listarCarros();
                    break;
                case 2:
                    pesquisarCarro();
                    break;
                case 3:
                    quantidadeDisponivel();
                    break;
                case 4:
                    comprarCarro();
                    break;
                case 5:
                    System.out.println("Encerrando o programa...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void adicionarCarro() throws RemoteException {
        System.out.println("\n--- Adicionar Carro ---");
        Carro carro = new Carro("0", "0", Carro.Categoria.CARRO, 0, 0, 0);

        System.out.print("Digite o Nome: ");
        carro.setNome(scanner.nextLine());

        if (!(lojaCarro.verificaCarro(carro.getNome()))) {

            lojaCarro.adicionarCarro(carro);
            System.out.print("Digite o Renavan: ");
            carro.setRenavan(scanner.nextLine());

            System.out.print("Digite o Ano de Fabricação: ");
            carro.setAnoDeFabricacao(scanner.nextInt());
            scanner.nextLine();

            System.out.print("Digite a Quantidade Disponível: ");
            carro.setQuantidadeDisponivel(scanner.nextInt());
            scanner.nextLine();

            System.out.print("Digite o Preço: ");
            carro.setPreco(scanner.nextDouble());
            scanner.nextLine();

            System.out.println("Categorias disponíveis: ");
            Carro.Categoria[] categorias = Carro.Categoria.values();
            for (int i = 0; i < categorias.length; i++) {
                System.out.println((i + 1) + ". " + categorias[i]);
            }
            System.out.print("Escolha a Categoria: ");
            int categoryChoice = scanner.nextInt();
            scanner.nextLine();

            if (categoryChoice >= 1 && categoryChoice <= categorias.length) {
                Carro.Categoria categoria = categorias[categoryChoice - 1];
                carro.setCategoria(categoria);
                lojaCarro.adicionarCarro(carro);
                System.out.println("Carro adicionado com sucesso: " + carro);
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        } else {
            System.out.println("Carro adicionado com sucesso: " + carro);
        }
    }

    private static void apagarCarro() throws RemoteException {
        System.out.println("\n--- Apagar Carro ---");
        System.out.print("Digite o Nome do Carro a ser apagado: ");
        String nomeCarro = scanner.nextLine();
        lojaCarro.apagarCarro(nomeCarro);
        System.out.println("Carro apagado com sucesso.");
    }

    private static void listarCarros() throws RemoteException {
        System.out.println("\n--- Listar Carros ---");
        List<Carro> carros = lojaCarro.listarCarros();
        if (carros.isEmpty()) {
            System.out.println("Nenhum carro disponível.");
        } else {
            for (Carro carro : carros) {
                System.out.println(carro);
            }
        }
    }

    private static void pesquisarCarro() throws RemoteException {
        System.out.println("\n--- Pesquisar Carro ---");
        System.out.print("Digite o termo de pesquisa: ");
        Carro entradaBusca = lojaCarro.pesquisarCarro(scanner.nextLine());
        if (entradaBusca == null) {
            System.out.println("Nenhum carro encontrado.");
        } else {
            System.out.println(entradaBusca.toString());

        }
    }

    private static void alterarCarro() throws RemoteException {
        System.out.println("\n--- Alterar Atributos de Carro ---");
        System.out.print("Digite o Nome do Carro a ser alterado: ");
        String nomeCarro = scanner.nextLine();

        System.out.print("Digite o novo Renavan: ");
        String renavan = scanner.nextLine();

        System.out.print("Digite o novo Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o novo Ano de Fabricação: ");
        int anoDeFabricacao = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner

        System.out.print("Digite a nova Quantidade Disponível: ");
        int quantidadeDisponivel = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner

        System.out.print("Digite o novo Preço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine(); // Limpar o buffer do scanner

        System.out.println("Categorias disponíveis: ");
        Carro.Categoria[] categorias = Carro.Categoria.values();
        for (int i = 0; i < categorias.length; i++) {
            System.out.println((i + 1) + ". " + categorias[i]);
        }
        System.out.print("Escolha a nova Categoria: ");
        int categoryChoice = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner

        if (categoryChoice >= 1 && categoryChoice <= categorias.length) {
            Carro.Categoria categoria = categorias[categoryChoice - 1];
            Carro novoCarro = new Carro(renavan, nome, categoria, quantidadeDisponivel, preco, anoDeFabricacao);
            lojaCarro.alterarCarro(nomeCarro, novoCarro);
            System.out.println("Carro alterado com sucesso: " + novoCarro);
        } else {
            System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private static void quantidadeDisponivel() throws RemoteException {
        System.out.println("\n--- Exibir Quantidade de Carros ---");
        System.out.print("Digite o Nome do Carro: ");
        String nomeCarro = scanner.nextLine();
        int quantidade = lojaCarro.quantidadeCarro(nomeCarro);
        System.out.println("Quantidade disponível: " + quantidade);
    }

    private static void comprarCarro() throws RemoteException {
        System.out.println("\n--- Comprar Carro ---");
        System.out.print("Digite o Nome do Carro a ser comprado: ");
        String nomeCarro = scanner.nextLine();
        lojaCarro.comprarCarro(nomeCarro);
        System.out.println("Carro comprado com sucesso.");
    }
}
