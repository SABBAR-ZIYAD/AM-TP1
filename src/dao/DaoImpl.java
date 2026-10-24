package dao;

// ── Architecture Microservices Lab · TP1-REFLECTION-2026 ──────────────

/**
 * Implémentation simulant une source de données externe.
 */
public class DaoImpl implements IDao {

    @Override
    public double getValue() {
        System.out.println("[DAO] Récupération de la valeur depuis la source de données...");
        return 100.0;
    }
}
