# Product Explorer - TP 4 : Écran de détail produit

Application Android développée avec Jetpack Compose dans le cadre du **TP 4 (Bloc 2 - Compose UI)**.

## Objectif du TP
Construire un premier écran complet de détail produit en utilisant les composants fondamentaux de Jetpack Compose :
- `Text` : affichage des textes hiérarchisés avec la typographie `MaterialTheme` (`headlineSmall`, `titleMedium`, `bodyMedium`).
- `Button` : déclenchement de l'action "Ajouter au panier" via un callback (`() -> Unit`).
- `Icon` : icône étoile (`Icons.Default.Star`) alignée dans une `Row` pour la note produit.
- `Image` : affichage de l'image produit via un placeholder système (`android.R.drawable.ic_menu_gallery`) avec `contentDescription`.
- `Card` : cartes dédiées pour le prix/remise (`ProductPriceCard`) et la disponibilité/livraison (`ProductAvailabilityCard`).
- `TextField` : utilisation de `OutlinedTextField` en lecture seule pour afficher les informations de garantie.

## Organisation des Composables
L'écran `ProductDetailScreen` assemble plusieurs sous-composants réutilisables :
- `ProductImage` : image du produit.
- `ProductHeader` : titre, marque et catégorie.
- `ProductPriceCard` : prix et pourcentage de remise dans une `Card`.
- `ProductRating` : note sur 5 avec icône étoile dans une `Row`.
- `ProductAvailabilityCard` : niveau de stock et délai de livraison dans une `Card`.
- `ProductDescription` : titre de section et paragraphe de description.
- `ProductWarrantyField` : champ de garantie avec `OutlinedTextField` en `readOnly`.
- `AddToCartButton` : bouton d'action principal.

## Previews
Deux `@Preview` sont disponibles pour tester l'écran avec différents jeux de données locaux :
1. `ProductDetailScreenPreview` : produit standard en stock (*Smartphone Toto X*).
2. `ProductDetailScreenOutOfStockPreview` : produit avec stock à zéro et description plus longue (*Casque Audio Pulse*).
