import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String NAO_INFORMADO = "NÃO INFORMADO";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //System.out.println("-----FORMULÁRIO-----");
        try {
            Path arquivo = Paths.get("formulario.txt");
            List<String> linhas = Files.readAllLines(arquivo);

            //for (String linha : linhas) {
              //  System.out.println(linha);
            //}
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }


        while (true) {
            int opcao = mostrarMenu(sc);
            System.out.println("Você escolheu a opção: " + opcao);
            if (opcao == 1) {
                System.out.println("CADASTRO DE PETS:");

                try {
                    Pet pet = new Pet();
                    Path arquivo = Paths.get("formulario.txt");
                    List<String> perguntas = Files.readAllLines(arquivo);
                    for (int i = 0; i < perguntas.size(); i++) {
                        System.out.print(perguntas.get(i));

                        if (i == 3) {
                            sc.nextLine();
                            System.out.print("Rua -  ");
                            String rua = sc.nextLine();
                            pet.setRua(rua);

                            System.out.print("Número da casa - ");
                            String casa = sc.nextLine();
                            pet.setCasa(Integer.parseInt(casa));

                            System.out.print("Bairro - ");
                            String bairro = sc.nextLine();
                            pet.setBairro(bairro);

                            System.out.print("Cidade - ");
                            String cidade = sc.nextLine();
                            pet.setCidade(cidade);

                        } else {
                            String resposta = sc.nextLine();
                            if (resposta.trim().isEmpty()) {
                                resposta = NAO_INFORMADO;
                                System.out.print(resposta);
                            }

                            if (i == 0) {
                                if (resposta.equals(NAO_INFORMADO)) {
                                    pet.setNome(NAO_INFORMADO);
                                } else {
                                    pet.setNome(validarNome(resposta));
                                }

                            }

                            if (i == 1) {
                                pet.setTipo(TIPO.valueOf(resposta.toUpperCase()));

                            }

                            if (i == 2) {
                                pet.setSexo(SEXO.valueOf(resposta.toUpperCase()));
                            }

                            if (i == 4) {
                                pet.setIdade(validarIdade(resposta));
                            }

                            if (i == 5) {
                                pet.setPeso(validarPeso(resposta));
                            }

                            if (i == 6) {
                                pet.setRaca(validarRaca(resposta));
                            }
                        }

                    }
                    System.out.println("Pet cadastrado com sucesso!");
                    System.out.println(pet.toString());
                    Pet.salvarPetEmArquivo(pet);
                    System.out.println("Pet salvo no arquivo com sucesso!");


                } catch (IllegalArgumentException e) {
                    System.out.println("Erro de validação: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Erro inesperado: " + e.getMessage());
                }
            }
            if (opcao == 2){
                buscarPet(sc,false);
                continue;
            }
            if (opcao == 3){
                alterarPet(sc);
                continue;
            }
            if (opcao == 4){
                deletarPet(sc);
                continue;
            }
            if (opcao == 5) {
                System.out.println("Encerrando sistema...");
                break;
            }
        }
    }

    public static int mostrarMenu(Scanner sc) {
        while (true) {
            System.out.println("-----MENU INICIAL-----");
            System.out.println("1 - Cadastrar um novo pet");
            System.out.println("2 - Buscar dados do pet cadastrado");
            System.out.println("3 - Alterar um pet cadastrado");
            System.out.println("4 - Deletar um pet cadastrado");
            System.out.println("5 - Sair");
            String entrada = sc.nextLine();
            try {
                int num = Integer.parseInt(entrada);
                if (num <= 0 || num > 6) {
                    System.out.println("Opção inválida. Digite um número entre 1 e 6.");
                } else {
                    return num;
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números.");
            }
        }
    }

    public static String validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }

        if (!nome.matches("[A-Za-zÀ-ÿ ]+")) {
            throw new IllegalArgumentException("Nome só pode conter letras.");
        }

        if (nome.trim().split("\\s+").length < 2) {
            throw new IllegalArgumentException("Digite nome e sobrenome.");
        }

        return nome;
    }

    public static double validarPeso(String texto) {
        texto = texto.replace(",", ".");

        double peso = Double.parseDouble(texto);

        if (peso < 0.5 || peso > 60) {
            throw new IllegalArgumentException("Peso inválido.");
        }

        return peso;
    }

    public static double validarIdade(String texto) {
        texto = texto.replace(",", ".");
        double idade = Double.parseDouble(texto);

        if (idade > 20) {
            throw new IllegalArgumentException("Idade inválida.");
        }

        if (idade < 1) {
            idade = idade / 12;
        }
        return idade;
    }

    public static String validarRaca(String raca) {
        if (raca == null || raca.trim().isEmpty()) {
            throw new IllegalArgumentException("Raça não pode ser vazio.");
        }

        if (!raca.matches("[A-Za-zÀ-ÿ ]+")) {
            throw new IllegalArgumentException("Raça inválida.");
        }
        return raca;
    }

    public static File buscarPet(Scanner sc, boolean selecionarPet){
        System.out.println("-----BUSCA DE PET-----");
        System.out.println("Escolha por qual critério você quer fazer a busca:");
        System.out.println("1 - Nome ou sobrenome");
        System.out.println("2 - Tipo");
        System.out.println("3 - Sexo");
        System.out.println("4 - Endereço");
        System.out.println("5 - Idade");
        System.out.println("6 - Peso");
        System.out.println("7 - Raça");
        int opcao = sc.nextInt();
        sc.nextLine();
        System.out.print("Digite o valor para busca: ");
        String termo = sc.nextLine().toUpperCase();

        File pasta = new File("petsCadastrados");
        File[] arquivos = pasta.listFiles((dir, name) -> name.endsWith(".txt"));

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum pet cadastrado.");
            return null;
        }

        List<File> petsEncontrados = new ArrayList<>();
        List<List<String>> dadosPets = new ArrayList<>();

        for (File arquivo : arquivos) {
            try {
                List<String> linhas = Files.readAllLines(arquivo.toPath());
                int indiceLinha = opcao - 1;

                if (indiceLinha < 0 || indiceLinha >= linhas.size()) {
                    continue;
                }
                String campoArquivo = linhas.get(indiceLinha).toUpperCase();

                if (campoArquivo.contains(termo)) {
                    petsEncontrados.add(arquivo);
                    dadosPets.add(linhas);
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler arquivo: " + arquivo.getName());
            }
        }
        if (petsEncontrados.isEmpty()) {
            System.out.println("Nenhum pet encontrado com esse critério.");
            return null;
        }

        System.out.println("\nPets encontrados:");
        for (int i = 0; i < dadosPets.size(); i++) {
            List<String> pet = dadosPets.get(i);

            System.out.println(
                    (i + 1) + ". " +
                            pet.get(0) + " - " +
                            pet.get(1) + " - " +
                            pet.get(2) + " - " +
                            pet.get(3) + " - " +
                            pet.get(4) + " - " +
                            pet.get(5) + " - " +
                            pet.get(6)
            );
        }
        if (!selecionarPet) {
            return null;
        }

        System.out.print("Escolha o número do pet para selecionar: ");
        int escolha = sc.nextInt();
        sc.nextLine();

        if (escolha < 1 || escolha > petsEncontrados.size()) {
            System.out.println("Opção inválida.");
            return null;
        }

        return petsEncontrados.get(escolha - 1);
    }

    public static void alterarPet(Scanner sc){
        while (true) {
            System.out.println("-----ALTERAÇÃO DE PET-----");
            File arquivoSelecionado = buscarPet(sc, true);
            if (arquivoSelecionado == null){
                System.out.println("Nenhum pet encontrado para alteração.");
                return;
            }
            System.out.println("Você deseja alterar algum dado desse pet?");
            System.out.println("1 - Sim");
            System.out.println("2 - Não");
            int sn = sc.nextInt();
            sc.nextLine();
            if (sn != 1 && sn != 2) {
                System.out.println("Opção inválida.");
                continue;
            }
            if (sn == 1){
                try {
                    List<String> linhas = Files.readAllLines(arquivoSelecionado.toPath());
                    System.out.println("Qual dado do pet você deseja alterar? ");
                    System.out.println("1 - Nome ou sobrenome");
                    System.out.println("2 - Endereço");
                    System.out.println("3 - Idade");
                    System.out.println("4 - Peso");
                    System.out.println("5 - Raça");
                    int dado = sc.nextInt();
                    sc.nextLine();
                    if (dado == 1) {
                        int indiceLinha = 0;
                        System.out.println("Digite o novo nome completo do pet: ");
                        String novoNome = sc.nextLine();
                        linhas.set(indiceLinha, novoNome);
                        Files.write(arquivoSelecionado.toPath(), linhas);
                        System.out.println("Nome atualizado com sucesso.");
                        return;
                    }
                    if (dado == 2) {
                        int indiceLinha = 3;
                        System.out.println("Digite a nova rua do pet: ");
                        String novaRua = sc.nextLine();
                        System.out.println("Digite o novo apartamento do pet: ");
                        String novoApe = sc.nextLine();
                        System.out.println("Digite o novo bairro do pet: ");
                        String novoBairro = sc.nextLine();
                        System.out.println("Digite a nova cidade do pet: ");
                        String novaCidade = sc.nextLine();
                        String novoEndereco = novaRua + ", " + novoApe + ", " + novoBairro + ", " + novaCidade;
                        linhas.set(indiceLinha, novoEndereco);
                        Files.write(arquivoSelecionado.toPath(), linhas);
                        System.out.println("Endereço atualizado com sucesso.");
                        return;
                    }
                    if (dado == 3) {
                        int indiceLinha = 4;
                        System.out.println("Digite a nova idade do pet: ");
                        String novaIdade = sc.nextLine();
                        linhas.set(indiceLinha, (novaIdade + " anos"));
                        Files.write(arquivoSelecionado.toPath(), linhas);
                        System.out.println("Idade atualizada com sucesso.");
                        return;
                    }
                    if (dado == 4) {
                        int indiceLinha = 5;
                        System.out.println("Digite o novo peso do pet: ");
                        String novoPeso = sc.nextLine();
                        linhas.set(indiceLinha, (novoPeso + " kg"));
                        Files.write(arquivoSelecionado.toPath(), linhas);
                        System.out.println("Peso atualizado com sucesso.");
                        return;
                    }
                    if (dado == 5) {
                        int indiceLinha = 6;
                        System.out.println("Digite a nova raça do pet: ");
                        String novaRaca = sc.nextLine();
                        linhas.set(indiceLinha, novaRaca);
                        Files.write(arquivoSelecionado.toPath(), linhas);
                        System.out.println("Raça atualizada com sucesso.");
                        return;
                    }
                    if (dado < 1 || dado > 5){
                        System.out.println("Opção inválida. Tente novamente.");
                        continue;
                    }
                } catch (IOException e){
                    System.out.println("Erro ao alterar o arquivo: " + e.getMessage());
                }

            }
            if (sn == 2){
                System.out.print("Voltando ao menu...");
                return;
            }
        }
    }

    public static void deletarPet(Scanner sc) {
        System.out.println("-----DELETAR PET-----");
        File arquivoSelecionado = buscarPet(sc, true);
        if (arquivoSelecionado == null){
            System.out.println("Nenhum pet encontrado para alteração.");
            return;
        }
        System.out.println("Você deseja deletar esse pet? ");
        System.out.println("1 - Sim");
        System.out.println("2 - Não");
        int sn = sc.nextInt();
        sc.nextLine();
        if (sn == 2){
            System.out.print("Voltando ao menu...");
            return;
        }
       if (sn == 1){
           try {
               Files.delete(arquivoSelecionado.toPath());
               System.out.println("Pet excluído com sucesso!");
               return;
           } catch (IOException e) {
               throw new RuntimeException("Erro ao remover o pet: " + e.getMessage());
           }
       }
    }
}
