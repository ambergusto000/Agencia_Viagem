package dao;

import model.ContratacaoServico;

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
        String sql = "INSERT INTO contrato_servico (contrato_id, servico_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, contratacao.getContratoId());
            stmt.setInt(2, contratacao.getServicoId());
            stmt.executeUpdate();
        }
    }
}