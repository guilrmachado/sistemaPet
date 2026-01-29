import java.io.File;
import java.io.IOException;
import java.nio.file.*;
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
            if (opcao == 6) {
                System.out.println("Encerrando sistema...");
                break;
            }
            if (opcao == 2){
                buscarPet(sc);
                break;
            }
            if (opcao == 3){
                alterarPet(sc);
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
            System.out.println("5 - Listar pets por algum critério (idade, nome, raça)");
            System.out.println("6 - Sair");
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

    public static File buscarPet(Scanner sc){
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

        boolean encontrou = false;

        for (File arquivo : arquivos) {
            try {
                List<String> linhas = Files.readAllLines(arquivo.toPath());

                int indiceLinha = opcao - 1;

                if (indiceLinha < 0 || indiceLinha >= linhas.size()) {
                    continue;
                }

                String campoArquivo = linhas.get(indiceLinha).toUpperCase();
                File arquivoEncontrado = null;
                if (campoArquivo.contains(termo)) {
                    arquivoEncontrado = arquivo;
                    return arquivoEncontrado;
                }

            } catch (IOException e) {
                System.out.println("Erro ao ler arquivo: " + arquivo.getName());
            }
        }

        if (!encontrou) {
            System.out.println("\nNenhum pet encontrado com esse critério.");
        }
        return null;
    }
    public static void alterarPet(Scanner sc){
        while (true) {
            System.out.println("-----ALTERAÇÃO DE PET-----");
            File arquivoSelecionado = buscarPet(sc);
            if (arquivoSelecionado == null){
                System.out.println("Nenhum pet encontrado para alteração.");
                return;
            }
            System.out.println("Você deseja alterar algum dado desse pet?");
            System.out.println("1 - Sim");
            System.out.println("2 - Não");
            int sn = sc.nextInt();
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
                        break;
                    }
                    if (dado == 2) {
                        int indiceLinha = 3;
                        System.out.println("Digite o novo endereço completo do pet: ");
                        String novoEndereco = sc.nextLine();
                        linhas.set(indiceLinha, novoEndereco);
                        Files.write(arquivoSelecionado.toPath(), linhas);
                        System.out.println("Endereço atualizado com sucesso.");
                        break;
                    }
                    if (dado == 3) {
                        int indiceLinha = 4;
                        System.out.println("Digite a nova idade do pet: ");
                        String novaIdade = sc.nextLine();
                        linhas.set(indiceLinha, novaIdade);
                        Files.write(arquivoSelecionado.toPath(), linhas);
                        System.out.println("Idade atualizada com sucesso.");
                        break;
                    }
                    if (dado == 4) {
                        int indiceLinha = 5;
                        System.out.println("Digite o novo peso do pet: ");
                        String novoPeso = sc.nextLine();
                        linhas.set(indiceLinha, novoPeso);
                        Files.write(arquivoSelecionado.toPath(), linhas);
                        System.out.println("Peso atualizado com sucesso.");
                        break;
                    }
                    if (dado == 5) {
                        int indiceLinha = 6;
                        System.out.println("Digite a nova raça do pet: ");
                        String novaRaca = sc.nextLine();
                        linhas.set(indiceLinha, novaRaca);
                        Files.write(arquivoSelecionado.toPath(), linhas);
                        System.out.println("Raça atualizada com sucesso.");
                        break;
                    }
                } catch (IOException e){
                    System.out.println("Erro ao alterar o arquivo: " + e.getMessage());
                }

            }
            if (sn == 2){
                System.out.print("Fim do programa.");
                break;
            }
        }
    }

}
