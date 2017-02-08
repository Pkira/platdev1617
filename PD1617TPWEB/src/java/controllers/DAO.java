/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Pedro Salgado
 */
@Singleton
public class DAO implements IDAO {

    @PersistenceContext(unitName = "PD1617TPWEBPU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

}
