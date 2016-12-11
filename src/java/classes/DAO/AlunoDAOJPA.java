package classes.DAO;

import classes.Aluno;
import classes.DAO.exceptions.NonexistentEntityException;
import classes.DAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

public class AlunoDAOJPA implements Serializable {

    public AlunoDAOJPA(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**Método para realizar cadastro de um novo aluno em banco de dados.*/
    public void create(Aluno aluno) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(aluno);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**Método para editar o cadastro de um aluno no banco de dados.*/
    public void edit(Aluno aluno) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            aluno = em.merge(aluno);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = aluno.getId();
                if (findAluno(id) == null) {
                    throw new NonexistentEntityException("The aluno with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**Método para deletar um cadastro de aluno existente em banco.*/
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Aluno aluno;
            try {
                aluno = em.getReference(Aluno.class, id);
                aluno.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aluno with id " + id + " no longer exists.", enfe);
            }
            em.remove(aluno);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Aluno> findAlunoEntities() {
        return findAlunoEntities(true, -1, -1);
    }

    public List<Aluno> findAlunoEntities(int maxResults, int firstResult) {
        return findAlunoEntities(false, maxResults, firstResult);
    }

    private List<Aluno> findAlunoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Aluno.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Aluno findAluno(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Aluno.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlunoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Aluno> rt = cq.from(Aluno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }    
}
