# TP 1 — Programmation dynamique et réflexion en Java

> **ARCHITECTURE MICROSERVICES LAB**  
> Édition pédagogique identifiable : `TP1-REFLECTION-2026`

---

Ce laboratoire met en œuvre un petit conteneur d'injection de dépendances sans
framework. Les implémentations DAO et métier sont choisies dans `config.txt`,
chargées avec `Class.forName`, instanciées avec `Constructor.newInstance`, puis
reliées par l'invocation réflexive du setter `setDao`.

## Prérequis

- JDK 11 ou supérieur
- `java` et `javac` accessibles depuis le terminal

## Structure

```text
TP 1/
├── config.txt
├── README.md
└── src/
    ├── dao/
    │   ├── IDao.java
    │   └── DaoImpl.java
    ├── metier/
    │   ├── IMetier.java
    │   └── MetierImpl.java
    └── presentation/
        └── Presentation2.java
```

## Compiler et exécuter

Depuis le dossier `TP 1`, sous PowerShell :

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac --release 8 -encoding UTF-8 -d out src/dao/IDao.java src/dao/DaoImpl.java src/metier/IMetier.java src/metier/MetierImpl.java src/presentation/Presentation2.java
java -cp out presentation.Presentation2
```

L'option `--release 8` produit un bytecode également exécutable avec les JVM
plus anciennes. Avec un JDK 11 utilisé à la compilation et à l'exécution, elle
peut être omise.

Le programme accepte également le chemin d'un autre fichier de configuration :

```powershell
java -cp out presentation.Presentation2 autre-config.txt
```

## Résultat attendu

```text
============================================================
  Architecture Microservices Lab
  Programmation dynamique & réflexion | TP1-REFLECTION-2026
============================================================
[CONTAINER] DAO instancié : dao.DaoImpl
[CONTAINER] Métier instancié : metier.MetierImpl
[CONTAINER] Dépendance IDao injectée dans le métier.
[DAO] Récupération de la valeur depuis la source de données...
[METIER] Calcul effectué. Résultat : 200.0
Résultat = 200.0
```

Pour changer les implémentations sans modifier `Presentation2`, ajoutez des
classes respectant `IDao` ou `IMetier`, puis indiquez leurs noms pleinement
qualifiés dans `config.txt`.

---

<div align="center">

`ARCHITECTURE MICROSERVICES LAB · TP1-REFLECTION-2026`

*Comprendre les frameworks en reconstruisant leurs fondations.*

</div>
