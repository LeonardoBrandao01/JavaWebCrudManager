package model.dao;

import java.util.List;
import model.LoyaltyProgram;
import model.ModelException;

public interface LoyaltyProgramDAO {
    void insert(LoyaltyProgram entity) throws ModelException;
    void update(LoyaltyProgram entity) throws ModelException;
    void delete(LoyaltyProgram entity) throws ModelException;
    LoyaltyProgram findById(int id) throws ModelException;
    List<LoyaltyProgram> findAll() throws ModelException;
}

