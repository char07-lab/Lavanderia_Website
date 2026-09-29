package service;

import dao.FuncionarioDAO;
import model.Funcionario;

public class FuncionarioService {

    public void cadastrarFuncionario(Funcionario funcionario) throws Exception {
        // 1. Validação da regra de negócio (Princípio da Responsabilidade Única - SRP)
        if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
            throw new Exception("O nome do funcionário não pode estar vazio.");
        }

        // 2. Chamada real ao método existente no DAO
        FuncionarioDAO dao = new FuncionarioDAO();
        
        // Passamos null para os EPIs neste teste simples de console, 
        // salvando apenas os dados do funcionário
        dao.salvarCompleto(funcionario, null, null);

        System.out.println("Funcionário " + funcionario.getNome() + " salvo com sucesso na base de dados!");
    }
}