package classes.DAO;

import classes.Ocorrencia;
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

public class OcorrenciaDAOJPA implements Serializable {

    public OcorrenciaDAOJPA(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**Método para registrar em banco uma ocorrencia, assim que um aluno for pontuado por um professor.*/
    public void create(Ocorrencia ocorrencia) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(ocorrencia);
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

    /**Método para realizar alteração de uma ocorrencia já registrada em banco*/
    public void edit(Ocorrencia ocorrencia) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ocorrencia = em.merge(ocorrencia);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = ocorrencia.getId();
                if (findOcorrencia(id) == null) {
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

    /**Método para apagar uma ocorrencia.*/
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ocorrencia ocorrencia;
            try {
                ocorrencia = em.getReference(Ocorrencia.class, id);
                ocorrencia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aluno with id " + id + " no longer exists.", enfe);
            }
            em.remove(ocorrencia);
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

    public List<Ocorrencia> findOcorrenciaEntities() {
        return findOcorrenciaEntities(true, -1, -1);
    }

    public List<Ocorrencia> findOcorrenciaEntities(int maxResults, int firstResult) {
        return findOcorrenciaEntities(false, maxResults, firstResult);
    }

    private List<Ocorrencia> findOcorrenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ocorrencia.class));
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

    public Ocorrencia findOcorrencia(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ocorrencia.class, id);
        } finally {
            em.close();
        }
    }

    /**Método para contabilizar as ocorrencias registradas
     * @return Long - SingleResult*/
    public int getOcorrenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ocorrencia> rt = cq.from(Ocorrencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List <Ocorrencia> getOcorrenciasPor(Long idGrupo) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT o FROM Ocorrencia o " +
            "JOIN Aluno al on al.id = o.aluno.id where al.grupo = :idGrupo");
            q.setParameter("idGrupo", idGrupo);
            return ((List <Ocorrencia>) q.getResultList());
        } finally {
            em.close();
        }
    }
    
    public Long getPontosPor(Long idGrupo) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT SUM(o.pontos) FROM Ocorrencia o " +
            "JOIN Aluno al on al.id = o.aluno.id where al.grupo = :idGrupo");
            q.setParameter("idGrupo", idGrupo);
            return (Long) q.getSingleResult();
        } finally {
            em.close();
        }
    }
    
}
