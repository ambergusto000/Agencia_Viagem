package Controller;

public class ContratacaoServicoController {

    @FXML private ComboBox<Cliente> cbCliente;
    @FXML private ComboBox<Pacote> cbPacote;
    @FXML private ComboBox<Servico> cbServico;
    @FXML private Button btnContratar;

    private ContratacaoServicoDAO contratacaoDAO;

    public void initialize() {
        // Carregar dados nos ComboBox
        cbCliente.setItems(FXCollections.observableArrayList(new ClienteDAO().listarTodos()));
        cbPacote.setItems(FXCollections.observableArrayList(new PacoteDAO().listarTodos()));
        cbServico.setItems(FXCollections.observableArrayList(new ServicoDAO().listarTodos()));
    }

    @FXML
    private void contratarServico() {
        ContratacaoServico contratacao = new ContratacaoServico();
        contratacao.setClienteId(cbCliente.getValue().getId());
        contratacao.setPacoteId(cbPacote.getValue().getId());
        contratacao.setServicoId(cbServico.getValue().getId());
        contratacao.setDataContratacao(LocalDate.now());

        try {
            contratacaoDAO.contratarServico(contratacao);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Serviço contratado com sucesso!");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
