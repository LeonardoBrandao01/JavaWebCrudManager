package model.dao;

//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Company;
import model.LoyaltyProgram;
import model.ModelException;

public class MySQLLoyaltyProgramDAO implements LoyaltyProgramDAO {
    
    @Override
    public void insert(LoyaltyProgram entity) throws ModelException {
        DBHandler db = new DBHandler();
        String sqlInsert = "INSERT INTO loyalty_program VALUES "
                + "(DEFAULT, ?, ?, ?, ?, ?);";
        
        db.prepareStatement(sqlInsert);
        db.setString(1, entity.getNomePrograma());
        db.setInt(2, entity.getMilhasAcumuladas());
        db.setString(3, entity.getStatus());
        db.setDate(4, entity.getDataValidade());
        db.setInt(5, entity.getCompanhia().getId());
        
        db.executeUpdate();
    }
    
    @Override
    public void update(LoyaltyProgram entity) throws ModelException {
        DBHandler db = new DBHandler();
        String sqlUpdate = "UPDATE loyalty_program SET "
                + "nome_programa = ?, "
                + "milhas_acumuladas = ?, "
                + "status = ?, "
                + "data_validade = ?, "
                + "companhia_id = ? "
                + "WHERE id = ?;";
        
        db.prepareStatement(sqlUpdate);
        db.setString(1, entity.getNomePrograma());
        db.setInt(2, entity.getMilhasAcumuladas());
        db.setString(3, entity.getStatus());
        db.setDate(4, entity.getDataValidade());
        db.setInt(5, entity.getCompanhia().getId());
        db.setInt(6, entity.getId());
        
        db.executeUpdate();
    }
    
    @Override
    public void delete(LoyaltyProgram entity) throws ModelException {
        DBHandler db = new DBHandler();
        String sqlDelete = "DELETE FROM loyalty_program WHERE id = ?;";
        
        db.prepareStatement(sqlDelete);
        db.setInt(1, entity.getId());
        
        db.executeUpdate();
    }
    
    @Override
    public LoyaltyProgram findById(int id) throws ModelException {
        DBHandler db = new DBHandler();
        String sql = "SELECT lp.*, c.name as company_name "
                + "FROM loyalty_program lp "
                + "INNER JOIN companies c ON lp.companhia_id = c.id "
                + "WHERE lp.id = ?;";
        
        db.prepareStatement(sql);
        db.setInt(1, id);
        db.executeQuery();
        
        LoyaltyProgram loyaltyProgram = null;
        while (db.next()) {
            loyaltyProgram = createLoyaltyProgramFromResultSet(db);
            break;
        }
        
        return loyaltyProgram;
    }
    
    @Override
    public List<LoyaltyProgram> findAll() throws ModelException {
        DBHandler db = new DBHandler();
        String sql = "SELECT lp.*, c.name as company_name "
                + "FROM loyalty_program lp "
                + "INNER JOIN companies c ON lp.companhia_id = c.id "
                + "ORDER BY nome_programa;";
        
        db.createStatement();
        db.executeQuery(sql);
        
        List<LoyaltyProgram> loyaltyPrograms = new ArrayList<>();
        while (db.next()) {
            loyaltyPrograms.add(createLoyaltyProgramFromResultSet(db));
        }
        
        return loyaltyPrograms;
    }
    
    private LoyaltyProgram createLoyaltyProgramFromResultSet(DBHandler db) throws ModelException {
        LoyaltyProgram loyaltyProgram = new LoyaltyProgram(db.getInt("id"));
        
        loyaltyProgram.setNomePrograma(db.getString("nome_programa"));
        loyaltyProgram.setMilhasAcumuladas(db.getInt("milhas_acumuladas"));
        loyaltyProgram.setStatus(db.getString("status"));
        loyaltyProgram.setDataValidade(db.getDate("data_validade"));
        
        // Busca a companhia usando o DAO
        CompanyDAO companyDAO = DAOFactory.createDAO(CompanyDAO.class);
        Company company = companyDAO.findById(db.getInt("companhia_id"));
        loyaltyProgram.setCompanhia(company);
        
        return loyaltyProgram;
    }
}