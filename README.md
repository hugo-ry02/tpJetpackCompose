# Product Explorer - TP 4 à TP 7

Application Android développée avec Jetpack Compose dans le cadre du **Bloc 2 - Compose UI**.

---

## TP 4 : Écran de détail produit (`ProductDetailScreen`)
Composants fondamentaux : `Text`, `Button`, `Icon`, `Image`, `Card`, `OutlinedTextField`.

## TP 5 : Écran d'accueil Product Explorer (`ProductHomeScreen`)
Layouts Compose : `Column`, `Row`, `Box`, `Spacer`, `Surface`.

## TP 6 : Affichage d'un catalogue local de produits (`ProductCatalogScreen`)
Listes modernes : `LazyColumn` (`item` + `items` avec `key`), `LazyRow` (`CategoryRow`), carte produit réutilisable (`ProductListItem`).

## TP 7 : Personnalisation graphique avec Material Design 3
Mise en place d'un thème personnalisé et cohérent pour toute l'application :
- **Palettes de couleurs (`Theme.kt`)** : définition de `LightColorScheme` et `DarkColorScheme` (rôles `primary`, `onPrimary`, `secondary`, `background`, `onBackground`, `surface`, `onSurface`) et sélection automatique via `isSystemInDarkTheme()`.
- **Formes personnalisées (`Theme.kt`)** : `ProductShapes` (`small`, `medium`, `large`, `extraLarge`).
- **Typographie (`Type.kt`)** : `ProductTypography` (`headlineMedium`, `titleLarge`, `titleMedium`, `bodyMedium`, `labelLarge`).
- **Application dans l'interface** : fond du catalogue, titre en couleur `primary`, description en `onBackground`, cartes produits avec `shapes.large`, élévation et couleurs du thème, chips catégories en `extraLarge` avec `secondary`/`onPrimary`.
- **Previews clair & sombre** : `ProductCatalogScreenLightPreview` et `ProductCatalogScreenDarkPreview` pour vérifier les contrastes.

---

## Previews disponibles

### Catalogue produits - Thème clair (TP 7)
![Aperçu Catalogue Light](screenshots/preview_catalog_light.png)

### Catalogue produits - Thème sombre (TP 7)
![Aperçu Catalogue Dark](screenshots/preview_catalog_dark.png)

### Autres écrans (TP 4, 5 & 6)
- Catalogue initial : `screenshots/preview_catalog.png`
- Écran d'accueil : `screenshots/preview_home.png`
- Détail produit : `screenshots/preview_product.png` / `screenshots/preview_outofstock.png`
