package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContratacaoServicoDAO {
    private Connection connection;

    public ContratacaoServicoDAO(Connection connection) {
        this.connection = connection;
    }

    public void contratarServico(ContratacaoServico contratacao) throws SQLException {
        String sql = "INSERT INTO contratacao_servico (cliente_id, pacote_id, servico_id, data_contratacao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, contratacao.getClienteId());
            stmt.setInt(2, contratacao.getPacoteId());
            stmt.setInt(3, contratacao.getServicoId());
            stmt.setDate(4, Date.valueOf(contratacao.getDataContratacao()));
            stmt.executeUpdate();
        }
    }
}
