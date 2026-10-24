package presentation;

import dao.IDao;
import metier.IMetier;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// ── Architecture Microservices Lab · TP1-REFLECTION-2026 ──────────────

/**
 * Petit conteneur d'injection de dépendances basé sur l'API de réflexion.
 */
public class Presentation2 {
    private static final String LAB_ID = "TP1-REFLECTION-2026";
    private static final String LAB_NAME = "Architecture Microservices Lab";

    public static void main(String[] args) {
        Path configPath = args.length > 0 ? Paths.get(args[0]) : Paths.get("config.txt");

        try {
            printWatermark();
            String[] classNames = loadClassNames(configPath);

            IDao dao = instantiateClass(classNames[0], IDao.class);
            System.out.println("[CONTAINER] DAO instancié : " + dao.getClass().getName());

            IMetier metier = instantiateClass(classNames[1], IMetier.class);
            System.out.println("[CONTAINER] Métier instancié : " + metier.getClass().getName());

            injectDependency(metier, "setDao", IDao.class, dao);
            System.out.println("[CONTAINER] Dépendance IDao injectée dans le métier.");

            double resultat = metier.calcul();
            System.out.println("Résultat = " + resultat);
        } catch (Exception e) {
            System.err.println("[ERREUR] Démarrage impossible : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printWatermark() {
        System.out.println("============================================================");
        System.out.println("  " + LAB_NAME);
        System.out.println("  Programmation dynamique & réflexion | " + LAB_ID);
        System.out.println("============================================================");
    }

    private static String[] loadClassNames(Path configPath) throws IOException {
        String[] classNames = new String[2];

        try (BufferedReader reader = Files.newBufferedReader(configPath)) {
            for (int i = 0; i < classNames.length; i++) {
                String line = reader.readLine();
                if (line == null || line.trim().isEmpty()) {
                    throw new IOException(
                            "La configuration doit contenir deux noms de classes non vides : "
                                    + configPath.toAbsolutePath());
                }
                classNames[i] = line.trim();
            }
        }

        return classNames;
    }

    private static <T> T instantiateClass(String className, Class<T> expectedType)
            throws ReflectiveOperationException {
        Class<?> loadedClass = Class.forName(className);

        if (!expectedType.isAssignableFrom(loadedClass)) {
            throw new IllegalArgumentException(
                    className + " n'implémente pas " + expectedType.getName());
        }

        Constructor<?> constructor = loadedClass.getDeclaredConstructor();
        Object instance = constructor.newInstance();
        return expectedType.cast(instance);
    }

    private static void injectDependency(
            Object target,
            String setterName,
            Class<?> dependencyType,
            Object dependency) throws ReflectiveOperationException {
        Method setterMethod = target.getClass().getMethod(setterName, dependencyType);
        setterMethod.invoke(target, dependency);
    }
}
