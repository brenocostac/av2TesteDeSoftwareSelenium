package automatizado.testes;

import automatizado.pageObject.ProdutoPO;
import org.junit.*;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProdutoTest extends BaseTest{
    private static ProdutoPO produtoPage;

    /**
     * Método executado antes de todos os testes.
     * Inicializa o navegador, carrega a página de produtos e abre o modal de cadastro.
     *
     * @author Breno
     * @date 29/11/2024
     */
    @BeforeClass
    public static void prepararTestes() {
        driver.get("file:///C:/ws-faculdade/automatizado/sistema/produtos.html");
        produtoPage = new ProdutoPO(driver);
        produtoPage.abrirModalCadastro();
    }

    /**
     * Método executado após todos os testes.
     * Limpa os campos de formulário e fecha o modal de cadastro.
     *
     * @author Breno
     * @date 29/11/2024
     */
    @AfterClass
    public static void aposTestes() {
        produtoPage.inputCodigo.clear();
        produtoPage.inputNome.clear();
        produtoPage.inputQuantidade.clear();
        produtoPage.inputValor.clear();
        produtoPage.inputData.clear();
        produtoPage.fecharModalCadastro();
    }

    /**
     * Teste que verifica que não é possível salvar um produto com campos parcialmente preenchidos.
     * Preenche alguns campos e tenta salvar, esperando que uma mensagem de erro seja exibida.
     *
     * @author Breno
     * @date 29/11/2024
     */
    @Test
    public void TC001_naoDevePermitirSalvarProdutoComCamposParcialmentePreenchidos() {
        produtoPage.preencherFormulario("123", "Pc teste", "5", "", "2024-11-29");
        produtoPage.salvarProduto();
        String resultado = produtoPage.obterMensagemErro();
        assertTrue(produtoPage.obterMensagemErro().contains("Todos os campos são obrigatórios para o cadastro!"));
    }

    /**
     * Teste que verifica que não é possível cadastrar um produto com todos os campos vazios.
     * Tenta salvar o produto com os campos em branco e espera que uma mensagem de erro seja exibida.
     *
     * @author Breno
     * @date 29/11/2024
     */
    @Test
    public void TC002_naoDevePermitirCadastrarProdutoComTodosOsCamposVazios() {
        produtoPage.salvarProduto();
        assertTrue(produtoPage.obterMensagemErro().contains("Todos os campos são obrigatórios para o cadastro!"));
        produtoPage.fecharModalCadastro();
    }

    /**
     * Teste que verifica que um produto é adicionado corretamente à tabela após ser salvo.
     * Preenche todos os campos com dados válidos, salva o produto e verifica se ele foi adicionado à tabela.
     *
     * @author Breno
     * @date 29/11/2024
     */
    @Test
    public void TC003_deveAdicionarProdutoNaTabela() {
        produtoPage.preencherFormulario("123", "Pc teste", "5", "55.55", "2024-11-29");
        produtoPage.salvarProduto();
        assertTrue(produtoPage.verificarProdutoNaTabela("123"));
    }
}
