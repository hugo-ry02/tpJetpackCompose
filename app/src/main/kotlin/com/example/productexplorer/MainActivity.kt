package com.example.productexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.productexplorer.ui.theme.ProductExplorerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductExplorerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductHomeScreen(
                        featuredProduct = sampleProduct(),
                        onFeaturedProductClick = {
                            // Plus tard : ouvrir le détail du produit
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// Modèle de données UI pour le produit
data class ProductUi(
    val title: String,
    val brand: String,
    val category: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val warrantyInformation: String,
    val shippingInformation: String
)

// Exemple de produit 1 (en stock)
fun sampleProduct(): ProductUi {
    return ProductUi(
        title = "Smartphone Toto X",
        brand = "TotoTech",
        category = "Smartphones",
        description = "Un smartphone léger avec un écran lumineux, une bonne autonomie et un design moderne.",
        price = 699.99,
        discountPercentage = 12.5,
        rating = 4.6,
        stock = 34,
        warrantyInformation = "Garantie constructeur : 2 ans",
        shippingInformation = "Livraison estimée : 3 à 5 jours ouvrés"
    )
}

// Exemple de produit 2 (hors stock)
fun sampleProductOutOfStock(): ProductUi {
    return ProductUi(
        title = "Casque Audio Pulse",
        brand = "SoundPeak",
        category = "Audio",
        description = "Un casque confortable conçu pour écouter de la musique, " +
                "suivre des cours en ligne et travailler dans de bonnes conditions.",
        price = 129.99,
        discountPercentage = 8.0,
        rating = 4.2,
        stock = 0,
        warrantyInformation = "Garantie constructeur : 1 an",
        shippingInformation = "Produit temporairement indisponible"
    )
}

// Composant d'image produit
@Composable
fun ProductImage(
    productTitle: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = android.R.drawable.ic_menu_gallery),
        contentDescription = "Image du produit $productTitle",
        modifier = modifier
    )
}

// Composant en-tête (titre, marque, catégorie)
@Composable
fun ProductHeader(
    title: String,
    brand: String,
    category: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = brand,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = category,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// Carte affichant le prix et la remise
@Composable
fun ProductPriceCard(
    price: Double,
    discountPercentage: Double,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "$price €",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Remise : $discountPercentage %",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// Ligne affichant la note et l'icône étoile
@Composable
fun ProductRating(
    rating: Double,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Note du produit"
        )
        Text(
            text = "$rating / 5",
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

// Carte pour le stock et les informations de livraison
@Composable
fun ProductAvailabilityCard(
    stock: Int,
    shippingInformation: String,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Disponibilité",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Stock : $stock unités",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = shippingInformation,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// Section description
@Composable
fun ProductDescription(
    description: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Description",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

// Champ en lecture seule pour la garantie
@Composable
fun ProductWarrantyField(
    warrantyInformation: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = warrantyInformation,
        onValueChange = {},
        readOnly = true,
        label = {
            Text(text = "Garantie")
        },
        modifier = modifier
    )
}

// Bouton réutilisable d'ajout au panier
@Composable
fun AddToCartButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = "Ajouter au panier")
    }
}

// Écran principal assemblant tous les composants
@Composable
fun ProductDetailScreen(
    product: ProductUi,
    onAddToCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ProductImage(
            productTitle = product.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        ProductHeader(
            title = product.title,
            brand = product.brand,
            category = product.category
        )
        ProductPriceCard(
            price = product.price,
            discountPercentage = product.discountPercentage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        ProductRating(
            rating = product.rating,
            modifier = Modifier.padding(top = 16.dp)
        )
        ProductAvailabilityCard(
            stock = product.stock,
            shippingInformation = product.shippingInformation,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        ProductDescription(
            description = product.description,
            modifier = Modifier.padding(top = 16.dp)
        )
        ProductWarrantyField(
            warrantyInformation = product.warrantyInformation,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        AddToCartButton(
            onClick = onAddToCartClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}

// =============================================================================
// TP 5 - ÉCRAN D'ACCUEIL (ProductHomeScreen)
// =============================================================================

// En-tête de l'écran d'accueil
@Composable
fun HomeHeader(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Product Explorer",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Découvrez les produits du moment",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// Écran d'accueil principal
@Composable
fun ProductHomeScreen(
    featuredProduct: ProductUi,
    onFeaturedProductClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HomeHeader()
    }
}

// Preview 1 : Produit standard en stock
@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    ProductExplorerTheme {
        ProductDetailScreen(
            product = sampleProduct(),
            onAddToCartClick = {}
        )
    }
}

// Preview 2 : Produit hors stock
@Preview(showBackground = true)
@Composable
fun ProductDetailScreenOutOfStockPreview() {
    ProductExplorerTheme {
        ProductDetailScreen(
            product = sampleProductOutOfStock(),
            onAddToCartClick = {}
        )
    }
}
