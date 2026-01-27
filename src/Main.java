import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String NAO_INFORMADO = "NÃO INFORMADO";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("-----FORMULÁRIO-----");
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
            if (opcao == 1) {
                System.out.println("CADASTRO DE PETS:");

                try {
                    Pet pet = new Pet();
                    Path arquivo = Paths.get("formulario.txt");
                    List<String> perguntas = Files.readAllLines(arquivo);
                    for (int i = 0; i < perguntas.size(); i++) {
                        System.out.print(perguntas.get(i));

                        if (i == 3) {
                            System.out.print("Rua: ");
                            String rua = sc.nextLine();
                            pet.setRua(rua);

                            System.out.print("Número da casa: ");
                            String casa = sc.nextLine();
                            pet.setCasa(Integer.parseInt(casa));

                            System.out.print("Bairro: ");
                            String bairro = sc.nextLine();
                            pet.setBairro(bairro);

                            System.out.print("Cidade: ");
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
            System.out.println("Você escolheu a opção: " + opcao);
        }
    }

    public static int mostrarMenu(Scanner sc) {
        while (true) {
            System.out.println("-----MENU INICIAL-----");
            System.out.println("1 - Cadastrar um novo pet");
            System.out.println("2 - Alterar os dados do pet cadastrado");
            System.out.println("3 - Deletar um pet cadastrado");
            System.out.println("4 - Listar todos os pets cadastrados");
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

}