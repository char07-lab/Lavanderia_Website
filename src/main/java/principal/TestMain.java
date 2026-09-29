package principal;

import model.Funcionario;
import service.FuncionarioService;

public class TestMain {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO TESTES DO BACKEND REFATORADO ===");
        
        try {
            // 1. Criar e preencher o objeto Funcionario com os dados obrigatórios
            Funcionario func = new Funcionario();
            func.setMatricula("12345"); // <-- Matrícula obrigatória para o banco
            func.setNome("Charles de Melo");
            func.setSetor("Lavanderia");
            func.setBox("A1");
            func.setAtendente("Responsável");
            
            // 2. Chamar o serviço de regras de negócio
            FuncionarioService service = new FuncionarioService();
            service.cadastrarFuncionario(func);
            
            System.out.println(">>> SUCESSO: Regras de negócio validadas e testadas via console!");
            
        } catch (Exception e) {
            System.err.println(">>> ERRO NO TESTE: " + e.getMessage());
        }
    }
}